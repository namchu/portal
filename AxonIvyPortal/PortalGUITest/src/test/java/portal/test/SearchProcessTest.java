package portal.test;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import portal.common.BaseTest;
import portal.common.TestAccount;
import portal.page.HomePage;
import portal.page.LoginPage;
import portal.page.TaskTemplatePage;
import portal.page.TemplatePage.GlobalSearch;

public class SearchProcessTest extends BaseTest {

  private HomePage homePage;

  @Before
  public void setup() {
    super.setup();
    navigateToUrl(HomePage.PORTAL_HOME_PAGE_URL);

    LoginPage loginPage = new LoginPage(TestAccount.DEMO_USER);
    loginPage.login();
    homePage = new HomePage();
  }

  @Test
  public void testFindProcessByNameAndOpenProcessList() {
    GlobalSearch globalSearch = homePage.getGlobalSearch();
    assertTrue(globalSearch.isDisplayed());

    globalSearch.clickOnGlobalSearchIcon();
    homePage.waitForElementDisplayed(globalSearch.getSearchContainer(), true);
    globalSearch.inputSearchKeyword("Employee Leave Request");

    homePage.waitAjaxIndicatorDisappear();

    String processName = "Employee Leave Request";
    assertEquals(processName, globalSearch.getProcessResult());
    
    globalSearch.startProcessOnGlobalSearch(processName);
    TaskTemplatePage taskPage = new TaskTemplatePage();
    assertTrue(taskPage.getPageUrl().contains("ProcessLeaves2"));
  }

  @Test
  public void testFindNotFoundOutData() {
    GlobalSearch globalSearch = homePage.getGlobalSearch();
    assertTrue(globalSearch.isDisplayed());

    globalSearch.clickOnGlobalSearchIcon();
    homePage.waitForElementDisplayed(globalSearch.getSearchContainer(), true);
    globalSearch.inputSearchKeyword("no process found");

    homePage.waitAjaxIndicatorDisappear();
    WebElement processResult = globalSearch.getEmptySearchResult();

    assertEquals("No records found.", processResult.getText());
  }
}
