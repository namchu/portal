package ch.ivy.addon.portal.generic.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import ch.ivy.addon.portal.generic.navigation.PortalNavigator;
import ch.ivy.addon.portalkit.bo.RemoteCase;
import ch.ivy.addon.portalkit.bo.RemoteTask;
import ch.ivy.addon.portalkit.bo.RemoteWebStartable;
import ch.ivy.addon.portalkit.persistence.domain.Application;
import ch.ivy.addon.portalkit.persistence.variable.GlobalVariable;
import ch.ivy.addon.portalkit.service.ApplicationService;
import ch.ivy.addon.portalkit.service.GlobalSettingService;
import ch.ivy.addon.portalkit.util.SecurityServiceUtils;
import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.process.call.SubProcessCallResult;

@ManagedBean
@ViewScoped
public class UserMenuBean {

  private List<RemoteWebStartable> foundWebStartables;
  private List<RemoteTask> foundTasks;
  private List<RemoteCase> foundCases;
  private String searchKeyword;
  private String userName;

  private boolean hasNoRecordsFound;

  public void logout() {
    Ivy.session().logoutSessionUser();
    Ivy.session().getSecurityContext().destroySession(Ivy.session().getIdentifier());
  }

  public String getUserName() {
    userName = Ivy.session().getSessionUserName();
    return userName;
  }

  public boolean isShowServerInformation() {
    GlobalSettingService globalSettingSerive = new GlobalSettingService();
    String isShowServerInformation = globalSettingSerive.findGlobalSettingValue(GlobalVariable.SHOW_ENVIRONMENT_INFO);
    return Boolean.parseBoolean(isShowServerInformation);
  }

  public boolean isHiddenLogout() {
    GlobalSettingService globalSettingSerive = new GlobalSettingService();
    String isHiddenLogout = globalSettingSerive.findGlobalSettingValue(GlobalVariable.HIDE_LOGOUT_BUTTON);
    return Boolean.parseBoolean(isHiddenLogout);
  }

  public boolean isHiddenChangePassword(){
    GlobalSettingService globalSettingSerive = new GlobalSettingService();
    String isHiddenChangePassword = globalSettingSerive.findGlobalSettingValue(GlobalVariable.HIDE_CHANGE_PASSWORD_BUTTON);
    return Boolean.parseBoolean(isHiddenChangePassword);
  }
  
  public String getHomePageURL() throws Exception {
    ApplicationService applicationService = new ApplicationService();
    String homePageURL = getHomePageFromSetting();
    if (CollectionUtils.isEmpty(applicationService.findAllIvyApplications())) {
      if (!StringUtils.isEmpty(homePageURL)) {
        return homePageURL;
      } else {
        return new PortalNavigator().getPortalStartUrl();
      }
    }

    Long serverId = SecurityServiceUtils.getServerIdFromSession();
    String selectedApp = SecurityServiceUtils.getApplicationNameFromSession();
    String selectedAppDisplayName = SecurityServiceUtils.getApplicationDisplayNameFromSession();

    if (isDefaultPortalApp() || serverId == null || selectedApp == null || selectedAppDisplayName == null) {
      return SecurityServiceUtils.getDefaultPortalStartUrl();
    }

    Application selectedApplication =
        applicationService.findByDisplayNameAndNameAndServerId(selectedAppDisplayName, selectedApp, serverId);
    return selectedApplication.getLink();
  }
  
  public void navigateToHomePage() throws Exception{
    FacesContext.getCurrentInstance().getExternalContext().redirect(getHomePageURL());
  }

  private String getHomePageFromSetting() {
    GlobalSettingService globalSettingSerive = new GlobalSettingService();
    return globalSettingSerive.findGlobalSettingValue(GlobalVariable.HOMEPAGE_URL);
  }

  private boolean isDefaultPortalApp() {
    return IApplication.PORTAL_APPLICATION_NAME.equals(Ivy.wf().getApplication().getName());
  }

  @SuppressWarnings("unchecked")
  public void search() {
    String keyword = searchKeyword.trim();
    if (StringUtils.isBlank(keyword)) {
      foundWebStartables = new ArrayList<>();
      foundTasks = new ArrayList<>();
      foundCases = new ArrayList<>();
      return;
    }

    Long serverId = SecurityServiceUtils.getServerIdFromSession();
    String selectedApp = SecurityServiceUtils.getApplicationNameFromSession();

    SubProcessCallResult result = SubProcessCall.withPath("Functional Processes/GlobalSearch")
        .withParam("keyword", keyword)
        .withParam("serverId", serverId)
        .withParam("applicationName", selectedApp)
        .call();
    foundWebStartables = (List<RemoteWebStartable>) result.get("webStartables");
    foundTasks = (List<RemoteTask>) result.get("tasks");
    foundCases = (List<RemoteCase>) result.get("cases");
    hasNoRecordsFound = CollectionUtils.isEmpty(foundWebStartables) && CollectionUtils.isEmpty(foundTasks) && CollectionUtils.isEmpty(foundCases);
  }

  public void resetSearchData() {
    searchKeyword = StringUtils.EMPTY;
    foundWebStartables = new ArrayList<>();
    foundTasks = new ArrayList<>();
    foundCases = new ArrayList<>();
    hasNoRecordsFound = false;
  }

  public String getSearchKeyword() {
    return searchKeyword;
  }

  public void setSearchKeyword(String searchKeyword) {
    this.searchKeyword = searchKeyword;
  }

  public List<RemoteWebStartable> getFoundWebStartables() {
    return foundWebStartables;
  }

  public List<RemoteTask> getFoundTasks() {
    return foundTasks;
  }

  public List<RemoteCase> getFoundCases() {
    return foundCases;
  }

  public boolean isHasNoRecordsFound() {
    return hasNoRecordsFound;
  }
}
