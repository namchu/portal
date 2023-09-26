package portal.guitest.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.jayway.awaitility.Awaitility;
import com.jayway.awaitility.Duration;

import portal.guitest.common.BaseTest;
import portal.guitest.common.NavigationHelper;
import portal.guitest.page.NewDashboardPage2;
import portal.guitest.page.ProcessWidgetPage;
import portal.guitest.page.ProcessWidgetPage.AddNewExternalLinkDialog;
import portal.guitest.page.UserFavoriteProcessPage;
import portal.guitest.page.UserFavoriteProcessPage.SettingProcessLanguageDialog;
import portal.guitest.page.UserProfilePage;

public class ProcessWidgetTest extends BaseTest {

  private static final String EXAMPLE_APPLICATION_PROCESSES = "portal-developer-examples/178ED537303DFF8E/start.ivp";
  private static final String CLEAN_ALL_FAVORITE_PROCESSES = "(For autotest) Clean all favorite processes";
  private static final String CASE_MAP_LEAVES = "Case Map: Leave Request";
  private static final String APPRAISAL = "Appraisal";
  private static final String AGOOGLE_LINK = "AGoogle";
  private static final String AAGOOGLE_LINK = "AAGoogle";
  private static final String EMPLOYEE_SEARCH = "Employee Search";
  private static final String EMPLOYEE_SEARCH_IN_IFRAME = "Employee Search in Iframe";
  private static final String CREATE_INVESTMENT = "Create Investment";

  private NewDashboardPage2 newDashboardPage2;
  ProcessWidgetPage processWidget;

  @Before
  @Override
  public void setup() {
    super.setup();
    newDashboardPage2 = new NewDashboardPage2();
  }

  @Test
  public void testCaseMapIsDisplayedInExpandedMode() {
    processWidget = NavigationHelper.navigateToProcessList();
    assertNotNull(processWidget.getProcess(CASE_MAP_LEAVES));
    resetLanguageOfCurrentUser();
  }

  @Test
  public void testDeleteProcess() {
    String processName = "AGoogle";
    String processLink = "google.com";
    processWidget = NavigationHelper.navigateToProcessList();
    createPrivateExternalTestProcess(processName, processLink);
    
    processName = "AAGoogle";
    processLink = "google.com";
    createPrivateExternalTestProcess(processName, processLink);
    
    // backToCompactProcessWidget(); TODO z1
    UserFavoriteProcessPage addNewProcessDialog = processWidget.openNewProcessDialog();
    addNewProcessDialog.selectProcessByName(AAGOOGLE_LINK);
    addNewProcessDialog.submitForm();
    
    addNewProcessDialog = processWidget.openNewProcessDialog();
    addNewProcessDialog.selectProcessByName(AGOOGLE_LINK);
    addNewProcessDialog.submitForm();
    
    int numberOfProcesses = processWidget.getNumberOfFavoriteUserProcesses();
    processWidget.clickEditSwitchLink();
    int indexOfProcess = numberOfProcesses - 1;
    processWidget.checkDeleteItem(indexOfProcess);
    assertTrue(processWidget.isDeleteProcessItemSelected(indexOfProcess));
    processWidget.clickSaveProcess();
    assertEquals(numberOfProcesses - 1, processWidget.getNumberOfFavoriteUserProcesses());
    
    processWidget = NavigationHelper.navigateToProcessList();
    processWidget.selectViewMode(ProcessWidgetPage.GRID_MODE);
    processWidget.waitForGridProcessListDisplayed();
    processWidget.clickMoreButtonOfFirstGridProcess();
    processWidget.deleteGridProcess(0);
    // backToCompactProcessWidget(); TODO z1
    
    assertEquals(0, processWidget.getNumberOfFavoriteUserProcesses());
    resetLanguageOfCurrentUser();
  }

  @Test
  // May not run on IE version 11.0.20 or later due to Selenium.
  public void testOpenExternalProcessInNewTab() {
    String processName = "AGoogle";
    String processLink = "google.com";
    processWidget = NavigationHelper.navigateToProcessList();
    createPrivateExternalTestProcess(processName, processLink);
    
    // backToCompactProcessWidget(); TODO z1
    UserFavoriteProcessPage addNewProcessDialog = processWidget.openNewProcessDialog();
    addNewProcessDialog.selectProcessByName(AGOOGLE_LINK);
    addNewProcessDialog.submitForm();
    
    assertEquals(1, newDashboardPage2.countBrowserTab());

    processWidget.startProcess(AGOOGLE_LINK);
    Awaitility.await().atMost(new Duration(5, TimeUnit.SECONDS)).until(() -> newDashboardPage2.countBrowserTab() > 1);
    newDashboardPage2.switchLastBrowserTab();
    Awaitility.await().atMost(new Duration(5, TimeUnit.SECONDS)).until(() -> newDashboardPage2.getPageTitle().length() > 1);
    assertEquals("Google", newDashboardPage2.getPageTitle());
    resetLanguageOfCurrentUser();
  }

  @Test
  public void testAddProcessBySelectingIvyProcess() {
    processWidget = NavigationHelper.navigateToProcessList();
    UserFavoriteProcessPage addNewProcessDialog = processWidget.openNewProcessDialog();
    addNewProcessDialog.selectProcessByName(CLEAN_ALL_FAVORITE_PROCESSES);
    addNewProcessDialog.submitForm();
    assertNotNull(processWidget.getProcess(CLEAN_ALL_FAVORITE_PROCESSES));
    resetLanguageOfCurrentUser();
  }

  @Test
  public void testAddProcessWithMultilingual() {
    processWidget = NavigationHelper.navigateToProcessList();
    UserFavoriteProcessPage addNewProcessDialog = processWidget.openNewProcessDialog();
    addNewProcessDialog.selectProcessByName(APPRAISAL);
    SettingProcessLanguageDialog settingProcessLanguageDialog = addNewProcessDialog.openAddlanguageDialog();
    settingProcessLanguageDialog.fillProcessNamesByLocaleName();
    addNewProcessDialog.submitForm();
    
    assertNotNull(processWidget.getProcess(String.format("%s - %s", APPRAISAL, "English Name*")));
    
    // Change language to German
    changeLanguage(3);
    
    processWidget = NavigationHelper.navigateToProcessList();
    assertNotNull(processWidget.getProcess(String.format("%s - %s", APPRAISAL, "German Name")));
    resetLanguageOfCurrentUser();
  }

  private void changeLanguage(int selectionIndex) {
    UserProfilePage userProfilePage = newDashboardPage2.openMyProfilePage();
    userProfilePage.selectLanguage(selectionIndex);
    newDashboardPage2 = userProfilePage.save();
  }
  

  @Test
  public void testBreadCrumb() {
    processWidget = NavigationHelper.navigateToProcessList();
    assertEquals("Processes", processWidget.getTextOfCurrentBreadcrumb());

    processWidget.goToHomeFromBreadcrumb();
    newDashboardPage2 = new NewDashboardPage2();
    assertEquals(true, newDashboardPage2.isDisplayed());
    resetLanguageOfCurrentUser();
  }

  private void createPrivateExternalTestProcess(String processName, String processLink) {
    AddNewExternalLinkDialog addNewExternalLinkDialog = processWidget.openNewExternalLinkDialog();
    addNewExternalLinkDialog.inputDataForPrivateExternalLink(processName, processLink);
    addNewExternalLinkDialog.submitForm();
  }

}
