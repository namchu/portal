package portal.guitest.document.screenshot;

import static portal.guitest.common.Variable.DEEPL_AUTH_KEY;
import static portal.guitest.common.Variable.ENABLE_DEEPL_TRANSLATION;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ch.ivy.addon.portalkit.util.ScreenshotUtil;
import portal.guitest.common.ScreenshotTest;
import portal.guitest.common.TestAccount;
import portal.guitest.page.HomePage;

public class MultiLanguageTranslationSupportScreenshotTest extends ScreenshotTest {
  private HomePage homePage;
  
  @Override
  @Before
  public void setup() {
    super.setup();
    updatePortalSetting(DEEPL_AUTH_KEY.getKey(), "DEEPL_AUTH_KEY");
    updatePortalSetting(ENABLE_DEEPL_TRANSLATION.getKey(), "true");
    homePage = new HomePage();
  }
  
  @Test
  public void screenshotDashboardConfigurationUserGuide() throws IOException {
    login(TestAccount.ADMIN_USER);
    redirectToRelativeLink(createSampleDashboardUrl);
    showNewDashboard();
    var dashboardConfigurationPage = homePage.openDashboardConfigurationPage();
    dashboardConfigurationPage.createPrivateDashboardFromScratch();
    ScreenshotUtil.captureElementScreenshot(dashboardConfigurationPage.getDashboardCreationDialog(),
        ScreenshotUtil.MULTIPLE_LANGUAGE_CONFIGURATION_FOLDER + "create-private-dashboard-dialog");
    dashboardConfigurationPage.openMultiLanguageDialog();
    ScreenshotUtil.captureElementScreenshot(dashboardConfigurationPage.getDashboardMultiLanguageDialog(),
        ScreenshotUtil.MULTIPLE_LANGUAGE_CONFIGURATION_FOLDER + "dashboard-multi-language-dialog");
    dashboardConfigurationPage.setTranslatedTitle();
    dashboardConfigurationPage.clickOnTextToTranslate(1);
    ScreenshotUtil.captureElementScreenshot(dashboardConfigurationPage.getDashboardMultiLanguageDialog(),
        ScreenshotUtil.MULTIPLE_LANGUAGE_CONFIGURATION_FOLDER + "overlay-panel-translation");
    dashboardConfigurationPage.clickOkMultiLanguageDialog();
    dashboardConfigurationPage.clickOkCreateDashboard();
  }
}