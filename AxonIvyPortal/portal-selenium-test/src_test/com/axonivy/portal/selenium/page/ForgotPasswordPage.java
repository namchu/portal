package com.axonivy.portal.selenium.page;

import static com.codeborne.selenide.Selenide.$;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;

import com.axonivy.portal.selenium.common.TestAccount;
import com.codeborne.selenide.SelenideElement;

public class ForgotPasswordPage extends TemplatePage {
  private static final String RESULT_MESSAGE_SELECTOR = "span[class='ui-messages-info-summary'], span[class='ui-messages-error-summary']";

  private static final long FORGOT_PASSWORD_TIMEOUT = 60;

  private SelenideElement emailTextField;
  private SelenideElement sendButton;
  private TestAccount testAccount;

  @Override
  protected String getLoadedLocator() {
    return "id('forgot-password:forgot-password-form:send-command')";
  }

  public ForgotPasswordPage() {
    waitForPageLoad();
    this.emailTextField = $("input[id='forgot-password:forgot-password-form:email']");
    this.sendButton = $("button[id='forgot-password:forgot-password-form:send-command']");
  }

  public ForgotPasswordPage(TestAccount testAccount) {
    this();
    this.testAccount = testAccount;
  }

  public void send() {
    emailTextField.sendKeys(testAccount.getEmail());
    waitForElementClickableThenClick(sendButton);
    waitForElementDisplayed(By.cssSelector(RESULT_MESSAGE_SELECTOR), true, FORGOT_PASSWORD_TIMEOUT);
  }

  public boolean isProcessed() {
    SelenideElement summayMessageSpan = findElementByCssSelector(RESULT_MESSAGE_SELECTOR);
    return summayMessageSpan != null && StringUtils.isNotBlank(summayMessageSpan.getText());
  }
}