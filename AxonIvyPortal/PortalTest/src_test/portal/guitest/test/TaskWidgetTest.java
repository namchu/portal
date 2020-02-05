package portal.guitest.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jayway.awaitility.Awaitility;

import portal.guitest.common.BaseTest;
import portal.guitest.common.DateTimePattern;
import portal.guitest.common.TaskState;
import portal.guitest.common.TestAccount;
import portal.guitest.page.AdminSettingsPage;
import portal.guitest.page.CaseDetailsPage;
import portal.guitest.page.HomePage;
import portal.guitest.page.TaskDetailsPage;
import portal.guitest.page.TaskWidgetPage;

public class TaskWidgetTest extends BaseTest {

  private static final String GRANT_DELEGATE_OWN_TASK_PERMISSION_PROCESS_URL =
      "portalKitTestHelper/14DE09882B540AD5/grantOnlyDelegateOwnTasksPermission.ivp";
  private static final String DENY_DELEGATE_OWN_TASK_PERMISSION_PROCESS_URL =
      "portalKitTestHelper/14DE09882B540AD5/undoOnlyDelegateOwnTasksPermission.ivp";

  private TaskDetailsPage taskDetailsPage;
  
  @Override
  @Before
  public void setup() {
    super.setup();
    createTestingTasks();
    redirectToRelativeLink(HomePage.PORTAL_HOME_PAGE_URL);
  }

  @Test
  public void testEnterTaskDetailWhenClickOnTaskRowAndGoBack() {
    TaskWidgetPage taskWidgetPage = new TaskWidgetPage();

    taskWidgetPage.expand();
    taskDetailsPage = taskWidgetPage.openTaskDetails(0);
    assertEquals("Task Details", taskDetailsPage.getPageTitle());

    taskWidgetPage = taskDetailsPage.goBackToTaskListFromTaskDetails();
    assertEquals("Tasks", taskWidgetPage.getPageTitle());
  }
  
  @Test
  public void testEnterTaskDetailFromTaskActionAndGoBack() {
    TaskWidgetPage taskWidgetPage = new TaskWidgetPage();

    taskWidgetPage.expand();
    taskDetailsPage = taskWidgetPage.openTaskDetailsFromActionMenu(0);
    assertEquals("Task Details", taskDetailsPage.getPageTitle());

    taskWidgetPage = taskDetailsPage.goBackToTaskListFromTaskDetails();
    assertEquals("Tasks", taskWidgetPage.getPageTitle());
  }

  @Test
  public void testTasksInPortalHomePageUpdatedAfterExpandToFullMode() {
    TaskWidgetPage taskWidgetPage = new TaskWidgetPage();
    assertEquals(3, taskWidgetPage.countTasks());

    taskWidgetPage.createTestingTasksInNewWindow();
    taskWidgetPage.expand();
    assertEquals(6, taskWidgetPage.countTasks());
  }

  @Test
  public void testOpenRelatedCaseOfTask() {
    TaskWidgetPage taskWidgetPage = new TaskWidgetPage();
    taskWidgetPage.expand();
    taskWidgetPage.openTaskDetails(0);

    String relatedCaseName = taskWidgetPage.getRelatedCase();
    CaseDetailsPage casePage = taskWidgetPage.openRelatedCaseOfTask();

    String caseName = casePage.getCaseName();
    assertTrue(relatedCaseName.contains(caseName));
  }

  @Test
  public void testReserveTask() {
    TaskWidgetPage taskWidgetPage = new TaskWidgetPage();
    taskWidgetPage.expand();
    taskWidgetPage.sideStepMenuOnActionButton(0);
    taskWidgetPage.reserveTask(0);
    taskWidgetPage.waitAjaxIndicatorDisappear();
    assertEquals(TaskState.RESERVED, taskWidgetPage.getTaskState(0));
    taskWidgetPage.sideStepMenuOnActionButton(0);
    taskWidgetPage.resetTask(0);
    taskWidgetPage.waitAjaxIndicatorDisappear();
    assertEquals(TaskState.OPEN, taskWidgetPage.getTaskState(0));
  }

  @Test
  public void testChangeTaskDeadline() {
    int firstTask = 0;
    LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
    String tomorrowStringLiteral = tomorrow.format(DateTimeFormatter.ofPattern(DateTimePattern.DATE_TIME_PATTERN));

    TaskWidgetPage taskWidgetPage = new TaskWidgetPage();
    taskWidgetPage.expand();
    taskWidgetPage.openTaskDetails(firstTask);
    taskWidgetPage.waitAjaxIndicatorDisappear();
    taskWidgetPage.changeExpiryOfTaskAt(tomorrowStringLiteral);
    taskWidgetPage.waitAjaxIndicatorDisappear();
    assertEquals(tomorrowStringLiteral, taskWidgetPage.getExpiryOfTaskAt());
  }

  @Test
  public void testStartButtonStatus() {
    login(TestAccount.ADMIN_USER);
    HomePage homePage = new HomePage();
    TaskWidgetPage taskWidgetPage = homePage.getTaskWidget();
    taskWidgetPage.expand();
    taskWidgetPage.filterTasksInExpendedModeBy("Annual Leave Request");
    Assert.assertFalse(taskWidgetPage.isTaskStartEnabled(0));
    taskWidgetPage.filterTasksInExpendedModeBy("Sick Leave Request");
    Awaitility.waitAtMost(5, TimeUnit.SECONDS).until(() -> taskWidgetPage.isTaskStartEnabled(0));
  }

  @Test
  public void testDisplayDelegateButton() {
    login(TestAccount.ADMIN_USER);
    redirectToRelativeLink(GRANT_DELEGATE_OWN_TASK_PERMISSION_PROCESS_URL);
    HomePage homePage = new HomePage();
    TaskWidgetPage taskWidgetPage = homePage.getTaskWidget();
    taskWidgetPage.expand();
    assertFalse(taskWidgetPage.isTaskDelegateOptionDisable(1));
    assertTrue(taskWidgetPage.isTaskDelegateOptionDisable(2));
    redirectToRelativeLink(DENY_DELEGATE_OWN_TASK_PERMISSION_PROCESS_URL);
  }

  @Test
  public void testDisplayTaskAndCaseCategory() {
    login(TestAccount.ADMIN_USER);
    HomePage homePage = new HomePage();
    TaskWidgetPage taskWidgetPage = homePage.getTaskWidget();
    taskWidgetPage.openTaskList();
    taskWidgetPage.openTaskDetails(0);
    assertEquals("OtherLeave/Maternity", taskWidgetPage.getTaskCategory());
    assertEquals("LeaveRequest", taskWidgetPage.getCaseCategory());
  }

  @Test
  public void testShowTaskCount() { 
    HomePage homePage = new HomePage();
    TaskWidgetPage taskWidgetPage = homePage.getTaskWidget();
    assertEquals("In Dashboard, Task Count != 3", 3, taskWidgetPage.getTaskCount().intValue());
    taskWidgetPage.openTaskList();
    assertEquals("In Task list, Task Count != 3", 3, taskWidgetPage.getTaskCount().intValue());
  }
  
  @Test
  public void testDisableTaskCount() {
    login(TestAccount.ADMIN_USER);
    HomePage homePage = new HomePage();
    AdminSettingsPage adminSettingsPage = homePage.openAdminSettings();
    adminSettingsPage.setDisabledTaskCount();

    TaskWidgetPage taskWidgetPage = homePage.getTaskWidget();
    taskWidgetPage.openTaskList();
    assertEquals("In Task list, Task Count is not disabled", null, taskWidgetPage.getTaskCount());
  }
}
