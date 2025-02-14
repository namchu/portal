package ch.ivy.addon.portalkit.taskfilter.impl;

import java.text.DateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import ch.ivy.addon.portalkit.service.DateTimeGlobalSettingService;
import ch.ivy.addon.portalkit.taskfilter.TaskFilter;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.query.TaskQuery;

public class TaskExpiredDateFilter extends TaskFilter {

  private Date fromExpiredDate;
  private Date toExpiredDate;

  @Override
  public String label() {
    StringBuilder sb = new StringBuilder();
    sb.append(Ivy.cms().co("/ch.ivy.addon.portalkit.ui.jsf/taskList/defaultColumns/EXPIRY_TIME"))
    .append(" (")
    .append(Ivy.cms().co("/ch.ivy.addon.portalkit.ui.jsf/common/filter/from"))
    .append("/")
    .append(Ivy.cms().co("/ch.ivy.addon.portalkit.ui.jsf/common/filter/to"))
    .append(")");
    return sb.toString();
  }

  @Override
  public String value() {
    DateFormat formatter = DateTimeGlobalSettingService.getInstance().getDefaultDateTimeFormatter();
    if (fromExpiredDate != null && toExpiredDate != null) {
      return String.format(DASH, formatter.format(fromExpiredDate), formatter.format(toExpiredDate));
    }

    if (fromExpiredDate != null) {
      return String.format(GREATER_EQUAL, formatter.format(fromExpiredDate));
    }

    if (toExpiredDate != null) {
      return String.format(LESS_EQUAL, formatter.format(toExpiredDate));
    }
    return ALL;
  }

  @Override
  public TaskQuery buildQuery() {
    if (fromExpiredDate == null && toExpiredDate == null) {
      return null;
    }

    TaskQuery query = TaskQuery.create();
    if (fromExpiredDate != null) {
      query.where().expiryTimestamp().isGreaterOrEqualThan(fromExpiredDate);
    }

    if (toExpiredDate != null) {
      query.where().expiryTimestamp().isLowerOrEqualThan(toExpiredDate);
    }
    return query;
  }

  @Override
  public void resetValues() {
    fromExpiredDate = null;
    toExpiredDate = null;
  }
  
  @Override
  public void validate() {
    if (fromExpiredDate != null && toExpiredDate != null && (fromExpiredDate.compareTo(toExpiredDate) > 0)) {
      FacesContext.getCurrentInstance().validationFailed();
      FacesContext.getCurrentInstance().addMessage(
          "advanced-filter-error-messages",
          new FacesMessage(FacesMessage.SEVERITY_ERROR, Ivy.cms().co(
              "/ch.ivy.addon.portalkit.ui.jsf/common/dateFromBiggerThanTo"), null));
    }
  }

  public Date getFromExpiredDate() {
    return fromExpiredDate;
  }

  public void setFromExpiredDate(Date fromExpiredDate) {
    this.fromExpiredDate = fromExpiredDate;
  }

  public Date getToExpiredDate() {
    return toExpiredDate;
  }

  public void setToExpiredDate(Date toExpiredDate) {
    this.toExpiredDate = toExpiredDate;
  }
  
  @Override
  public boolean defaultFilter() {
    return true;
 }
}
