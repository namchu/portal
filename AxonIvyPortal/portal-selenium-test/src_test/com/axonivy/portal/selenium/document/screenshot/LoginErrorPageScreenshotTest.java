package com.axonivy.portal.selenium.document.screenshot;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.portal.selenium.common.BaseTest;
import com.axonivy.portal.selenium.common.ScreenshotTest;
import com.axonivy.portal.selenium.common.ScreenshotUtils;
import com.axonivy.portal.selenium.page.AbstractPage;

@IvyWebTest
public class LoginErrorPageScreenshotTest extends ScreenshotTest {
  private final String PORTAL_LOGIN_PAGE_DISPLAY = "PortalLoginPageDisplay";

  @Test
  public void testLoginErrorPage() throws IOException {
    updateGlobalVariable(PORTAL_LOGIN_PAGE_DISPLAY, ScreenshotUtils.FALSE);
    redirectToRelativeLink(BaseTest.LOGOUT_URL);
    ScreenshotUtils.resizeBrowser(new Dimension(1024, 768));
    waitForPageLoad();
    ScreenshotUtils.capturePageScreenshot(ScreenshotUtils.LOGIN_FOLDER + "login-error-page");
    updateGlobalVariable(PORTAL_LOGIN_PAGE_DISPLAY, ScreenshotUtils.TRUE);
  }

  private void waitForPageLoad() {
    var loginErrorPage = new AbstractPage() {
      @Override
      protected String getLoadedLocator() {
        return ".login-error-page";
      }
    };

    loginErrorPage.waitPageLoaded();
  }
}
