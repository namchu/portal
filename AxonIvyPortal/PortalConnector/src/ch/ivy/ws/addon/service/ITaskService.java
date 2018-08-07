package ch.ivy.ws.addon.service;

import java.util.List;

import ch.ivy.ws.addon.WSException;
import ch.ivy.ws.addon.bo.NoteServiceResult;
import ch.ivy.ws.addon.bo.TaskServiceResult;
import ch.ivy.ws.addon.types.IvySecurityMember;
import ch.ivy.ws.addon.types.IvyTask;
import ch.ivyteam.ivy.workflow.ITask;
import ch.ivyteam.ivy.workflow.TaskState;

/**
 * Task service provides a set of service methods for Ivy task instances
 * 
 * @author mde
 *
 */
public interface ITaskService {

  /**
   * Find all tasks for user by filter
   */
  public TaskServiceResult findTasksByFilter(String username, String filter, List<String> apps, Long serverId,
      String serverUrl, TaskState... states) throws WSException;

  /**
   * Find task by identifier
   */
  public TaskServiceResult findTask(Integer identifier, String serverUrl) throws WSException;

  /**
   * Park task for user
   */
  public IvyTask parkTask(String username, Integer idenfier, String serverUrl) throws WSException;

  /**
   * Delegate a task to a given security member
   */
  public TaskServiceResult delegateTask(Integer idenfier, IvySecurityMember securityMember, String serverUrl)
      throws WSException;

  /**
   * Create note for task
   * 
   * @param note
   * @return
   * @throws WSException
   */
  public NoteServiceResult createNote(String username, Integer taskId, String message) throws WSException;

  /**
   * Find all notes for a task
   * 
   * @param taskId
   * @return list of case notes
   * @throws WSException
   */
  public NoteServiceResult findNotes(Integer taskId) throws WSException;

  /**
   * Resets a task
   */
  public TaskServiceResult resetTask(Integer taskId, String serverUrl) throws WSException;

  public TaskServiceResult findTasksByCriteria(TaskSearchCriteria taskSearchCriteria, String serverUrl) throws WSException;

  /**
   * Check if the given user can resume the given task
   */
  public TaskServiceResult canUserResumeTask(Integer taskId, String userName) throws WSException;


  /**
   * Finds task by id
   * 
   * @param taskId
   * @param errors
   * @return
   */
  public ITask findTask(final Integer taskId, List<WSException> errors);

  /**
   * Finds all running tasks
   * 
   * @param username
   * @param apps
   * @param serverId
   * @param serverUrl
   * @return
   * @throws WSException
   */
  public TaskServiceResult findAllRunningTasks(String username, List<String> apps, Long serverId, String serverUrl)
      throws WSException;

  public TaskServiceResult findTasksOfCase(Long caseId, String serverUrl) throws WSException;
}
