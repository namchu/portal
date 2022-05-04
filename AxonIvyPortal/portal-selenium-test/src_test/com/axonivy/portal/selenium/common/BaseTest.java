package com.axonivy.portal.selenium.common;

import static com.axonivy.portal.selenium.common.Variable.SHOW_USER_GUIDE;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.jupiter.api.AfterEach;

import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.codeborne.selenide.WebDriverRunner;

import ch.ivy.addon.portalkit.enums.PortalPermission;
/**
 * A base test that other tests extend it. It will test on browser IE by default. It provides feature to take screenshot
 * of failed tests and utility methods.
 *
 */
public class BaseTest {

  private String designerLogoutUrl = "http://localhost:8081/designer/logout";
  private final static String LOGIN_URL_PATTERN = "/PortalKitTestHelper/1636734E13CEC872/login.ivp?username=%s&password=%s";
  private final static String PORTAL_HOME_PAGE_URL = "/PortalTemplate/1549F58C18A6C562/DefaultApplicationHomePage.ivp";

  public BaseTest() {}
  
  @AfterEach
  public void tearDown() {
    WebDriverRunner.getWebDriver().quit();
  }
  
  protected String createTestingTasksUrl = "portal-developer-examples/162511D2577DBA88/CategoriedLeaveRequest.ivp";
  protected String createRelatedTaskWithNoResponsible = "InternalSupport/14B2FC03D2E87141/TestRelatedTaskWithNoResponsible.ivp";
  protected String createCaseWithTechnicalCaseUrl = "InternalSupport/15B1EA24CCF377E8/OrderPizza.ivp";
  protected String create12CasesWithCategoryUrl = "internalSupport/15C7B30FB93C827E/create12CasesWithCategory.ivp";
  protected String businessCaseUrl = "internalSupport/15B1EA24CCF377E8/updateCheckInTime.ivp";
  protected String hideCaseUrl = "portal-developer-examples/16583F0F73864543/createHiddenTechnicalCase.ivp";
  protected String createTestingCaseMapUrl = "internalSupport/764871e4-cf70-401f-83fb-9e99fa897fc4.icm";
  protected String createTestingCaseUrlForCustomizationAdditionalCaseDetails = "portal-developer-examples/1624D1F5CBEA5332/createInvestmentRequest.ivp";
  protected String createTestingCaseUrlForDefaultAdditionalCaseDetails = "internalSupport/14B2FC03D2E87141/DefaultAdditionalCaseDetails.ivp";
  protected String createTestingCaseContainOneTask = "internalSupport/14B2FC03D2E87141/CreateSupportTicket.ivp";
  protected String createTaskWithNotExistedActivatorUrl = "internalSupport/14B2FC03D2E87141/createTaskWithNotExistedActivator.ivp";
  protected String expressStartLink = "axonIvyExpress/15798655494F25E1/AxonIvyExpressWF.ivp";
  protected String cleanupDataLink = "portalKitTestHelper/1511A66AF619A768/cleanData.ivp";
  protected String createAlphaCompanyUrl = "portal-developer-examples/1624C1C79661758C/createAlphaCompany.ivp";
  protected String viewAlphaCompanyProcessHistoryUrl = "portal-developer-examples/1624C1C79661758C/viewProcessHistoryOfAlphaCompany.ivp";
  protected String viewAlphaCompanyProcessHistoryInDialogUrl = "portal-developer-examples/1624C1C79661758C/viewProcessHistoryOfAlphaCompanyInDialog.ivp";
  protected String createNewPaymentUrl = "portal-developer-examples/162511D2577DBA88/createNewPayment.ivp";
  protected String documentTableComponentUrl = "portal-developer-examples/16B447235433958E/start.ivp";
  protected String cleanUpAbsencesAndSubstituesLink = "portalKitTestHelper/1511A66AF619A768/cleanAbsencesAndSubstitues.ivp";
  protected String createUserFavoriteProcess = "portalKitTestHelper/153CACC26D0D4C3D/createTestUserFavoriteProcess.ivp";
  protected String createCasesForCaseListCustomization = "portal-developer-examples/162511D2577DBA88/createCasesForCaseListCustomization.ivp";
  protected String processChainShowcaseUrl = "portal-developer-examples/164DB506D12B25CF/showSampleProcessChain.ivp";
  protected String userSelectionComponentShowcaseUrl = "portal-developer-examples/170514494945ADB9/start.ivp";
  protected String roleSelectionComponentShowcaseUrl = "portal-developer-examples/175495F02A2BCEB2/start.ivp";
  protected String startUserExampleProcess = "portal-user-examples/17236DB1D3DA14C0/userExampleGuide.ivp";
  protected String userIsOwnerUrl = "internalSupport/16A68510A341BE6E/userIsOwner.ivp";
  protected String showTaskNoteHistoryUrl = "portalTemplate/16044EDBC0E23859/showTaskNoteHistory.ivp?selectedTaskId=%s";
  protected String showCaseNoteHistoryUrl = "portalTemplate/1603506A872272C6/showCaseNoteHistory.ivp?caseId=%s";
  protected String createTaskWithSystemState = "portalKitTestHelper/153CACC26D0D4C3D/createTaskWithSystemState.ivp";
  protected String createTechnicalStateUrl = "portal-developer-examples/162511D2577DBA88/createTechnicalStateTasks.ivp";
  protected String portalKitTestHelperPasswordResetUrl = "portalKitTestHelper/176463FD4BBF6C93/PasswordReset.ivp";
  protected String portalTemplatePasswordResetUrl = "portalTemplate/1549F58C18A6C562/PasswordResetPage.ivp?token=%s&username=%s";
  protected String createExpressProcess = "axonIvyExpress/15798655494F25E1/AxonIvyExpressWF.ivp";
  protected String cleanSessionCacheUrl = "http://localhost:8081/designer/pro/PortalKitTestHelper/17208192E0AF4185/cleanSessionCache.ivp";
  protected String reorderDashboardUrl =
      "portalTemplate/1549F58C18A6C562/DashboardReorder.ivp?isPublicDashboard=%s";
  protected String createSampleDashboardUrl = "portalKitTestHelper/17F2050944B46BB0/createSampleDashboard.ivp";
  protected String createTestingEscalationTasksUrl = "portal-developer-examples/162511D2577DBA88/CreateTaskForEscalation.ivp";
  protected String createPublicDashboadByJSonFileUrl = "PortalKitTestHelper/17F2050944B46BB0/createPublicDashboardByJSonFile.ivp?filePath=";

  protected void redirectToNewDashBoard() {
    open(EngineUrl.createProcessUrl(PORTAL_HOME_PAGE_URL));
  }

  /**
   * Default setup for each test
   * It will clean up all test data and login with account demo
   */
  public void setup() {
    launchBrowserAndGotoRelativeLink(cleanupDataLink);
    updatePortalSetting(SHOW_USER_GUIDE.getKey(), "false");
  }
  
  /**
   * Alternative setup, just login with input account, don't cleanup anything
   * @param relativePath
   * @param account
   */
  public void setupWithAlternativeLinkAndAccount(String relativePath, TestAccount account) {
    launchBrowserAndGotoRelativeLink(relativePath);
    login(account);
  }

  public void launchBrowserAndGotoRelativeLink(String relativeProcessStartLink) {
    try {
      open(UrlHelpers.generateAbsoluteProcessStartLink(relativeProcessStartLink));
    } catch (Exception e) {
      throw new PortalGUITestException(e);
    }
  }

  public void redirectToRelativeLink(String relativeProcessStartUrl) {
    LinkNavigator.redirectToRelativeLink(relativeProcessStartUrl);
  }

  public void launchBrowserAndLogoutInDesigner() {
    try {
      open(designerLogoutUrl);
    } catch (Exception e) {
      throw new PortalGUITestException(e);
    }
  }

  public void createTestingTasks() {
    redirectToRelativeLink(createTestingTasksUrl);
  }

  public void createRelatedTaskWithNoResponsible() {
    redirectToRelativeLink(createRelatedTaskWithNoResponsible);
  }
  
  public void grantTaskReadAllPermissionsToCurrentUser() {
    String grantAllPermissionsForAdminUserURL = "portalKitTestHelper/14DE09882B540AD5/grantTaskReadAllPermissionsToCurrentUser.ivp";
    redirectToRelativeLink(grantAllPermissionsForAdminUserURL);
  }
  
  public void grantTaskReadOwnCaseTaskPermissionsToCurrentUser() {
    String grantAllPermissionsForAdminUserURL = "portalKitTestHelper/14DE09882B540AD5/grantTaskReadOwnCaseTaskPermissionsToCurrentUser.ivp";
    redirectToRelativeLink(grantAllPermissionsForAdminUserURL);
  }

  public void denyReadAllPermissionFromCurrentUser() {
    String grantAllPermissionsForAdminUserURL = "portalKitTestHelper/14DE09882B540AD5/denyReadAllPermissionFromCurrentUser.ivp";
    redirectToRelativeLink(grantAllPermissionsForAdminUserURL);
  }
  
  public void denyDocumentOfInvolvedCaseWritePemissionFromCurrentUser() {
    String denyDocumentOfInvolvedCaseWritePemissionURL = "portalKitTestHelper/14DE09882B540AD5/denyDocumentOfInvolvedCaseWritePemission.ivp";
    redirectToRelativeLink(denyDocumentOfInvolvedCaseWritePemissionURL);
  }
  
  public void grantDocumentOfInvolvedCaseWritePemissionToCurrentUser() {
    String grantDocumentOfInvolvedCaseWritePemissionURL = "portalKitTestHelper/14DE09882B540AD5/grantDocumentOfInvolvedCaseWritePemission.ivp";
    redirectToRelativeLink(grantDocumentOfInvolvedCaseWritePemissionURL);
  }
  
  public void grantSpecificPortalPermission(PortalPermission portalPermission) {
    String grantSpecificPortalPermissionLink = "portalKitTestHelper/14DE09882B540AD5/grantSpecificPortalPermission.ivp?portalPermission=%s";
    redirectToRelativeLink(String.format(grantSpecificPortalPermissionLink, portalPermission.getValue()));
  }

  public void denySpecificPortalPermission(PortalPermission portalPermission) {
    String denySpecificPortalPermissionLink = "portalKitTestHelper/14DE09882B540AD5/denySpecificPortalPermission.ivp?portalPermission=%s";
    redirectToRelativeLink(String.format(denySpecificPortalPermissionLink, portalPermission.getValue()));
  }

  public void cleanUpGlobalVariables(){
    String cleanUpURL = "portalKitTestHelper/1511A66AF619A768/cleanupGlobalVars.ivp";
    redirectToRelativeLink(cleanUpURL);
  }
  
  public void resetLanguageOfCurrentUser(){
    redirectToRelativeLink("portalKitTestHelper/1511A66AF619A768/resetLanguageOfCurrentUser.ivp");
  }
  
  public void createThirdPartyApp() {
	  redirectToRelativeLink("PortalKitTestHelper/153CACC26D0D4C3D/createThirdPartyApp.ivp");
    
  }
  
  public void refreshPage(){
    refresh();
  }
  
  protected void login(TestAccount testAccount) {
    String username;
    String password;
    try {
      username = URLEncoder.encode(testAccount.getUsername(), "UTF-8");
      password = URLEncoder.encode(testAccount.getPassword(), "UTF-8");
      open(EngineUrl.createProcessUrl(String.format(LOGIN_URL_PATTERN, username, password)));
    } catch (UnsupportedEncodingException e) {
      throw new PortalGUITestException(e);
    }
  }

  public void updatePortalSetting(String portalSettingName, String portalSettingValue) {
    try {
      String encodeSettingName = URLEncoder.encode(portalSettingName, "UTF-8");
      String encodeSettingValue = URLEncoder.encode(portalSettingValue, "UTF-8");
      String updatePortalSettingLink = "portalKitTestHelper/17208192E0AF4185/updatePortalSetting.ivp?settingName=%s&settingValue=%s";
      redirectToRelativeLink(String.format(updatePortalSettingLink, encodeSettingName, encodeSettingValue));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
  
  public void updateGlobalVariable(String variableName, String variableValue) {
    try {
      String encodeVariableName = URLEncoder.encode(variableName, "UTF-8");
      String encodeVariableValue = URLEncoder.encode(variableValue, "UTF-8");
      String updateGlobalVariableLink = "portalKitTestHelper/1749B87B8C1B77BE/updateGlobalVariable.ivp?variableName=%s&variableValue=%s";
      redirectToRelativeLink(String.format(updateGlobalVariableLink, encodeVariableName, encodeVariableValue));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
  
  public void goToTaskNoteHistoryPage(String taskId) {
     redirectToRelativeLink(String.format(showTaskNoteHistoryUrl, taskId));
  }
  
  public void goToCaseNoteHistoryPage(String caseId) {
    redirectToRelativeLink(String.format(showCaseNoteHistoryUrl, caseId));
 }
  
}
