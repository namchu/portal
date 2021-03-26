package ch.ivy.addon.portalkit.dto.dashboard.casecolumn;

import java.io.Serializable;

import ch.ivy.addon.portalkit.enums.DashboardColumnFormat;
import ch.ivy.addon.portalkit.enums.DashboardStandardCaseColumn;
import ch.ivyteam.ivy.workflow.ICase;

public class CreatedDateColumnModel extends CaseColumnModel implements Serializable {

  private static final long serialVersionUID = -4315469062114036720L;

  @Override
  public void initDefaultValue() {
    this.header = defaultIfEmpty(this.header, cms("/ch.ivy.addon.portalkit.ui.jsf/caseList/defaultColumns/CREATION_TIME"));
    this.field = DashboardStandardCaseColumn.CREATED.getField();
    this.style = defaultIfEmpty(this.style, "width: 100px");
    this.styleClass = defaultIfEmpty(this.styleClass, "dashboard-tasks__created-date u-text-align-center");
    this.format = DashboardColumnFormat.TIMESTAMP;
  }
  
  @Override
  public Object display(ICase caze) {
    if (caze == null) {
      return null;
    }
    return caze.getStartTimestamp();
  }
}
