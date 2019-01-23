package ch.ivy.addon.portalkit.ivydata.dto;

import java.util.List;

import ch.ivy.addon.portalkit.bo.ElapsedTimeStatistic;
import ch.ivy.addon.portalkit.bo.ExpiryStatistic;
import ch.ivy.addon.portalkit.bo.PriorityStatistic;
import ch.ivy.addon.portalkit.ivydata.exception.PortalIvyDataException;
import ch.ivyteam.ivy.workflow.ITask;
import ch.ivyteam.ivy.workflow.category.CategoryTree;

public class IvyTaskResultDTO {

  private List<ITask> tasks;
  private long totalTasks;
  private CategoryTree categoryTree;
  private PriorityStatistic priorityStatistic;
  private ExpiryStatistic expiryStatistic;
  private ElapsedTimeStatistic elapsedTimeStatistic;
  private List<PortalIvyDataException> errors;

  public List<ITask> getTasks() {
    return tasks;
  }

  public void setTasks(List<ITask> tasks) {
    this.tasks = tasks;
  }

  public long getTotalTasks() {
    return totalTasks;
  }

  public void setTotalTasks(long totalTasks) {
    this.totalTasks = totalTasks;
  }
  
  public CategoryTree getCategoryTree() {
    return categoryTree;
  }

  public void setCategoryTree(CategoryTree categoryTree) {
    this.categoryTree = categoryTree;
  }

  public PriorityStatistic getPriorityStatistic() {
    return priorityStatistic;
  }

  public void setPriorityStatistic(PriorityStatistic priorityStatistic) {
    this.priorityStatistic = priorityStatistic;
  }

  public ExpiryStatistic getExpiryStatistic() {
    return expiryStatistic;
  }

  public void setExpiryStatistic(ExpiryStatistic expiryStatistic) {
    this.expiryStatistic = expiryStatistic;
  }

  public ElapsedTimeStatistic getElapsedTimeStatistic() {
    return elapsedTimeStatistic;
  }

  public void setElapsedTimeStatistic(ElapsedTimeStatistic elapsedTimeStatistic) {
    this.elapsedTimeStatistic = elapsedTimeStatistic;
  }

  public List<PortalIvyDataException> getErrors() {
    return errors;
  }

  public void setErrors(List<PortalIvyDataException> errors) {
    this.errors = errors;
  }

}
