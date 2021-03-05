package ch.ivy.addon.portalkit.bo;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import ch.ivy.addon.portalkit.enums.ProcessType;
import ch.ivy.addon.portalkit.service.ProcessStartCollector;
import ch.ivy.addon.portalkit.util.ExpressManagementUtils;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.ISecurityMember;
import ch.ivyteam.ivy.security.IUser;

/*
 * Used for merging express process and ivy process into a process list
 */
public class PortalExpressProcess implements Process {
  private ExpressProcess process;
  private static final String EXPRESS_WORKFLOW_ID_PARAM = "?workflowID=";
  private String processOwnerDisplayName;
  private String ableToStart;

  public PortalExpressProcess(ExpressProcess process) {
    this.process = process;

    ExpressManagementUtils utils = new ExpressManagementUtils();

    String processOwner = utils.getValidMemberName(process.getProcessOwner());
    String processOwnerName = StringUtils.isNotBlank(processOwner) ? processOwner.substring(1) : null;

    IUser user = processOwnerName != null ? Ivy.session().getSecurityContext().users().find(processOwnerName) : null;

    this.processOwnerDisplayName = Optional.ofNullable(user).map(IUser::getDisplayName).orElse(StringUtils.EMPTY);
    
    for (String username : this.process.getProcessPermissions()) {
      ISecurityMember assignee = Ivy.session().getSecurityContext().findSecurityMember(username);
      String ableStartName = Optional.ofNullable(assignee).map(ISecurityMember::getDisplayName).orElse(StringUtils.EMPTY);
      this.ableToStart = StringUtils.isBlank(ableToStart) ? ableStartName : String.join(";", ableToStart, ableStartName);
    }
  }

  @Override
  public String getStartLink() {
    return generateWorkflowStartLink();
  }

  @Override
  public String getDescription() {
    StringBuilder builder = new StringBuilder();
    if (StringUtils.isNotBlank(process.getProcessDescription())) {
      builder.append(process.getProcessDescription()).append(". ");
    }
    return builder.append(Ivy.cms().co("/ch.ivy.addon.portalkit.ui.jsf/caseList/defaultColumns/CREATOR"))
        .append(": ")
        .append(this.processOwnerDisplayName)
        .toString();
  }

  private String generateWorkflowStartLink() {
    ProcessStartCollector processStartCollector = new ProcessStartCollector();
    return processStartCollector.findExpressWorkflowStartLink() + EXPRESS_WORKFLOW_ID_PARAM + this.process.getId();
  }

  @Override
  public String getName() {
    return process.getProcessName();
  }

  @Override
  public ExpressProcess getProcess() {
    return process;
  }

  @Override
  public String getId() {
    return process.getId();
  }

  @Override
  public ProcessType getType() {
    return ProcessType.EXPRESS_PROCESS;
  }

  @Override
  public String getTypeName() {
    return ProcessType.EXPRESS_PROCESS.name();
  }

  public String getProcessOwnerDisplayName() {
    return processOwnerDisplayName;
  }

  public void setProcessOwnerDisplayName(String processOwenerDisplayName) {
    this.processOwnerDisplayName = processOwenerDisplayName;
  }

  @Override
  public String getIcon() {
    String icon = this.process.getIcon();
    return StringUtils.isBlank(icon) ? "si si-startup-launch" : icon;
  }
  
  public String getAbleToStart() {
    return ableToStart;
  }

  public void setAbleToStart(String ableToStart) {
    this.ableToStart = ableToStart;
  }

}
