package com.axonivy.portal.selenium.common;

public class FirefoxConfiguration extends AbstractBrowserConfiguration {

  @Override
  protected String getDriverPathInClasspath() {
    return "./resources/GeckoFireFoxDriver.exe";
  }
}
