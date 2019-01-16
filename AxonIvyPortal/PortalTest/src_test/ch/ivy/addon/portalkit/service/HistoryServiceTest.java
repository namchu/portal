package ch.ivy.addon.portalkit.service;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ch.ivy.addon.portalkit.bo.History;
import ch.ivy.addon.portalkit.util.UserUtils;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IUser;
import ch.ivyteam.ivy.workflow.INote;
import ch.ivyteam.ivy.workflow.ITask;
import ch.ivyteam.ivy.workflow.TaskState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Ivy.class, UserUtils.class})
public class HistoryServiceTest {

  private static final String SYSTEM_USER_NAME = "SYSTEM";

  @Test
  public void testCreateHistoriesFromINotesAndITasks() {
    HistoryService historyService = new HistoryService();
    List<History> histories = historyService.getHistories(createITasks(), createINotes());
    Assert.assertEquals(2, histories.size());
    Assert.assertEquals(History.HistoryType.NOTE, histories.get(0).getType());
    Assert.assertEquals(History.HistoryType.TASK, histories.get(1).getType());
  }

  @Test
  public void testCreateHistoriesFromINotesAndITasksExcludingTechnicalNotes() {
    mockStatic(UserUtils.class);
    boolean excludeTechnicalNote = true;
    List<ITask> tasks = new ArrayList<>(createTechnicalITasks());
    tasks.addAll(createITasks());
    List<INote> notes = new ArrayList<>(createTechnicalINotes());
    notes.addAll(createINotes());
    HistoryService historyService = new HistoryService();
    List<History> histories = historyService.getHistories(tasks, notes, excludeTechnicalNote);
    System.out.println(histories.size());
    Assert.assertEquals(2, histories.size());
    Assert.assertEquals(History.HistoryType.NOTE, histories.get(0).getType());
    Assert.assertEquals(History.HistoryType.TASK, histories.get(1).getType());
  }

  private List<ITask> createITasks() {
    ITask mockTask = Mockito.mock(ITask.class);
    Mockito.when(mockTask.getId()).thenReturn(Long.valueOf(4));
    Mockito.when(mockTask.getName()).thenReturn("Sample task");
    Mockito.when(mockTask.getState()).thenReturn(TaskState.SUSPENDED);

    Mockito.when(mockTask.getActivatorName()).thenReturn("demo");
    Mockito.when(mockTask.getStartTimestamp()).thenReturn(new Date());
    return Arrays.asList(mockTask);
  }

  private List<ITask> createTechnicalITasks() {
    ITask mockTask = Mockito.mock(ITask.class);
    Mockito.when(mockTask.getId()).thenReturn(Long.valueOf(4));
    Mockito.when(mockTask.getName()).thenReturn("Sample task");
    Mockito.when(mockTask.getState()).thenReturn(TaskState.SUSPENDED);
    Mockito.when(mockTask.getActivatorName()).thenReturn(SYSTEM_USER_NAME);
    Mockito.when(mockTask.getWorkerUserName()).thenReturn(SYSTEM_USER_NAME);
    Mockito.when(mockTask.getStartTimestamp()).thenReturn(new Date());
    return Arrays.asList(mockTask);
  }

  private List<INote> createINotes() {
    INote mockNote = Mockito.mock(INote.class);
    Mockito.when(mockNote.getId()).thenReturn(Long.valueOf(3));
    Mockito.when(mockNote.getMessage()).thenReturn("Sample message");
    Mockito.when(mockNote.getCreationTimestamp()).thenReturn(oneHourFromNow().getTime());

    IUser mockUser = Mockito.mock(IUser.class);
    Mockito.when(mockUser.getDisplayName()).thenReturn("demo");
    Mockito.when(mockNote.getWritter()).thenReturn(mockUser);
    return Arrays.asList(mockNote);
  }

  private List<INote> createTechnicalINotes() {
    INote mockNote = Mockito.mock(INote.class);
    Mockito.when(mockNote.getId()).thenReturn(Long.valueOf(3));
    Mockito.when(mockNote.getMessage()).thenReturn("Sample message");
    Mockito.when(mockNote.getCreationTimestamp()).thenReturn(oneHourFromNow().getTime());

    IUser mockUser = Mockito.mock(IUser.class);
    Mockito.when(mockUser.getDisplayName()).thenReturn(SYSTEM_USER_NAME);
    Mockito.when(mockNote.getWritterName()).thenReturn(SYSTEM_USER_NAME);
    Mockito.when(mockNote.getWritter()).thenReturn(mockUser);
    return Arrays.asList(mockNote);
  }

  private Calendar oneHourFromNow() {
    Calendar oneHourFromNow = Calendar.getInstance();
    oneHourFromNow.add(Calendar.HOUR, 1);
    return oneHourFromNow;
  }

}
