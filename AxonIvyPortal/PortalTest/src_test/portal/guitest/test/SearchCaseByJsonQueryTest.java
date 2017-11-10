package portal.guitest.test;

import org.junit.Before;
import org.junit.Test;

import portal.guitest.common.BaseTest;
import portal.guitest.common.TestAccount;
import portal.guitest.page.CasePage;
import portal.guitest.page.HomePage;
import portal.guitest.page.LoginPage;

public class SearchCaseByJsonQueryTest extends BaseTest {
    
    @Before
    @Override
    public void setup() {
      super.setup();
      navigateToUrl(createTestingTasksUrl);
      navigateToUrl(HomePage.PORTAL_HOME_PAGE_URL);
      
      LoginPage loginPage = new LoginPage(TestAccount.ADMIN_USER);
      loginPage.login();
    }
    
    @Test
    public void testSearchCaseByJsonQuery() {
        navigateToUrl("internalSupport/15FA421D253C6746/searchByCaseJsonQuery.ivp");
        CasePage casePage = new CasePage();
        assertEquals(1, casePage.getNumberOfCases());
    }

}
