package com.axonivy.portal.developerexamples.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.portal.developerexamples.dto.UserProcess;
import com.axonivy.portal.developerexamples.enums.ProcessType;

import ch.ivyteam.ivy.workflow.start.IWebStartable;

public class UserProcessMapper {
  public static final String DEFAULT_PROCESS_ICON = "si si-hierarchy-6 si-rotate-270";
  private UserProcessMapper() {}

  public static List<UserProcess> toUserProcesses(List<IWebStartable> processes) {
    return processes.stream().map(UserProcessMapper::toUserProcess).collect(Collectors.toList());
  }

  public static UserProcess toUserProcess(IWebStartable process) {
    UserProcess userProcess = new UserProcess();
    userProcess.setProcessId(process.getId());
    userProcess.setProcessType(ProcessType.IVY_PROCESS);
    userProcess.setProcessName(stripHtmlTags(process.getDisplayName()));
    userProcess.setLink(process.getLink().getRelativeEncoded());
    userProcess.setDescription(process.getDescription());
    String icon = process.customFields().value("cssIcon");
    if (StringUtils.isEmpty(icon)) {
      icon = DEFAULT_PROCESS_ICON;
    }
    userProcess.setIcon(icon);
    return userProcess;
  }

  // public static UserProcess toUserProcess(DashboardProcess process) {
  // UserProcess userProcess = new UserProcess();
  // userProcess.setProcessId(process.getId());
  // userProcess.setProcessType(process.getType());
  // userProcess.setLink(process.getStartLink());
  // userProcess.setDescription(process.getDescription());
  // userProcess.setIcon(process.getIcon());
  // return userProcess;
  // }
  //
  // public static UserProcess toUserProcess(ExpressProcess expressProcess, String expressStartLink) {
  // String startLink = expressStartLink + "?workflowID=" + expressProcess.getId();
  // UserProcess userProcess = new UserProcess();
  // userProcess.setProcessId(expressProcess.getId());
  // userProcess.setProcessType(ProcessType.EXPRESS_PROCESS);
  // userProcess.setProcessName(stripHtmlTags(expressProcess.getProcessName()));
  // userProcess.setLink(startLink);
  // userProcess.setDescription(expressProcess.getProcessDescription());
  // userProcess.setIcon(expressProcess.getIcon());
  // return userProcess;
  // }
  //
  // public static UserProcess toUserProcess(ExternalLink externalLink) {
  // UserProcess userProcess = new UserProcess();
  // userProcess.setProcessId(externalLink.getId().toString());
  // userProcess.setProcessType(ProcessType.EXTERNAL_LINK);
  // userProcess.setProcessName(stripHtmlTags(externalLink.getName()));
  // userProcess.setLink(externalLink.getLink());
  // userProcess.setDescription(externalLink.getDescription());
  // userProcess.setIcon(externalLink.getIcon());
  // return userProcess;
  // }

  private static String stripHtmlTags(String text) {
    return text.replaceAll("\\<.*?>", "");
  }
}
