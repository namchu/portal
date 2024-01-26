package com.axonivy.portal.selenium.page;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.axonivy.portal.selenium.common.WaitHelper;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

public class NewDashboardDetailsEditPage extends TemplatePage {

  public static final String TASK_WIDGET = "Task List";
  public static final String CASE_WIDGET = "Case List";
  public static final String PROCESS_WIDGET = "Process List";
  public static final String PROCESS_VIEWER_WIDGET = "Process Viewer";
  public static final String CUSTOM_WIDGET = "Custom Widget";
  public static final String STATISTIC_WIDGET = "Statistic chart";
  public static final String WELCOME_WIDGET = "Welcome widget";
  public static final String NEWS_WIDGET = "News feed widget";
  
  private static final String CUSTOM_WIDGET_TYPE_DROPDOWN_ID = "widget-configuration-form:new-widget-configuration-component:custom-widget-type_label";

  @Override
  protected String getLoadedLocator() {
    return "#add-button";
  }

  public TaskEditWidgetNewDashBoardPage addNewTaskWidget() {
    addWidgetByName("Task List");
    return new TaskEditWidgetNewDashBoardPage();
  }

  public CaseEditWidgetNewDashBoardPage addNewCaseWidget() {
    addWidgetByName("Case List");
    return new CaseEditWidgetNewDashBoardPage();
  }

  public ProcessEditWidgetNewDashBoardPage addNewProcessWidget() {
    addWidgetByName("Process List");
    return new ProcessEditWidgetNewDashBoardPage();
  }
  
  public ProcessViewerWidgetNewDashBoardPage addNewProcessViewerWidget() {
    addWidgetByName("Process Viewer");
    return new ProcessViewerWidgetNewDashBoardPage();
  }

  public CustomWidgetNewDashBoardPage addNewCustomrWidget() {
    addWidgetByName("Custom Widget");
    return new CustomWidgetNewDashBoardPage();
  }

  public void addWidgetByName(String name) {
    WaitHelper.waitNumberOfElementsToBe(WebDriverRunner.getWebDriver(), By.cssSelector("div[id$='new-widget-dialog_content']"), 1);
    $("div[id$='new-widget-dialog_content']").shouldBe(appear, DEFAULT_TIMEOUT)
        .$$("div.new-widget-dialog__item").filter(text(name)).first().$("tr.ui-widget-content")
        .$("button[id^='new-widget-dialog-content']").shouldBe(getClickableCondition()).click();
  }

  public DashboardConfigurationPage backToConfigurationPage() {
    clickByJavaScript($("[id='back-to-configuration']"));
    return new DashboardConfigurationPage();
  }

  public void deleteCompactModeProcess() {
    $("[id$=':delete-widget-2']").shouldBe(appear).click();
    getRemoveWidgetDialog().shouldBe(appear, DEFAULT_TIMEOUT).click();
    getRemoveWidgetButton().click();
    getRemoveWidgetDialog().shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  public void deleteImageModeProcess() {
    $("button[id$=':process-action-button']").shouldBe(appear, DEFAULT_TIMEOUT).click();
    $("[id$=':process-action-menu']").shouldBe(appear, DEFAULT_TIMEOUT)
      .$("span.si-bin-1").shouldBe(appear, DEFAULT_TIMEOUT).click();
    getRemoveWidgetDialog().shouldBe(appear, DEFAULT_TIMEOUT).click();
    getRemoveWidgetButton().click();
    getRemoveWidgetDialog().shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  public void deleteFullModeProcess() {
    $("button[id$=':process-action-button']").shouldBe(appear).click();
    $("[id$=':process-action-menu']").shouldBe(appear, DEFAULT_TIMEOUT)
      .$("span.si-bin-1").shouldBe(appear, DEFAULT_TIMEOUT).click();
    getRemoveWidgetDialog().shouldBe(appear, DEFAULT_TIMEOUT).click();
    getRemoveWidgetButton().click();
    getRemoveWidgetDialog().shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  public void deleteCombinedModeProcess() {
    try {
      openDeleteCombinedModeProcessDialog();
    } catch (Exception e) {
      openDeleteCombinedModeProcessDialog();
    }
    getRemoveWidgetButton().click();
    getRemoveWidgetDialog().shouldBe(disappear, DEFAULT_TIMEOUT);
  }
  
  public void clickOnRemoveWidgetButton() {
    getRemoveWidgetButton().shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    getRemoveWidgetButton().shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  private void openDeleteCombinedModeProcessDialog() {
    $(".process-grid-item__action--combined .si-bin-1").shouldBe(appear, DEFAULT_TIMEOUT).click();
    getRemoveWidgetDialog().shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public ProcessEditWidgetNewDashBoardPage editFullModeProcess() {
    $("button[id$=':process-action-button']").shouldBe(appear).click();
    $("[id$=':process-item:grid-process-action-component:edit-process']").shouldBe(appear, DEFAULT_TIMEOUT)
        .$("span.si-graphic-tablet-drawing-pen").shouldBe(appear, DEFAULT_TIMEOUT).click();
    $("div[id='new-widget-configuration-dialog']").shouldBe(appear, DEFAULT_TIMEOUT);
    return new ProcessEditWidgetNewDashBoardPage();
  }
  
  private SelenideElement getRemoveWidgetDialog() {
    return $("div[id='remove-widget-dialog']");
  }

  private SelenideElement getRemoveWidgetButton() {
    return $("button[id='remove-widget-button']");
  }

  public StatisticEditWidgetNewDashboardPage addNewStatisticWidget() {
    addWidgetByName("Statistic chart");
    return new StatisticEditWidgetNewDashboardPage();
  }

  public StatisticWidgetDashboardPage selectStatisticWidget() {
    return new StatisticWidgetDashboardPage();
  }

  public SelenideElement getTitleByIndex(int index) {
    return $("a[id='dashboard-title-" + index + "']");
  }

  public SelenideElement getIconByIndex(int index, String icon) {
    return $("a[id='dashboard-title-" + index + "'] span." + icon);
  }

  public ElementsCollection getWidgets() {
    return $("div[id='dashboard-body']").$$("div.grid-stack-item");
  }

  public void clickOnRestoreDashboard() {
    $("[id$='restore-button-group']").shouldBe(appear, DEFAULT_TIMEOUT).$("button[id$='restore-button']")
        .shouldBe(enabled, DEFAULT_TIMEOUT).shouldBe(getClickableCondition()).click();
    $("div[id$='restore-confirm-dialog']").shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public SelenideElement getRestoreDashboardMessage() {
    return $("div[id$='restore-confirm-dialog']").shouldBe(appear, DEFAULT_TIMEOUT)
        .$("span.dashboard-template-name-message").shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public void restoreDashboardToStandard() {
    $("div[id$='restore-confirm-dialog']").shouldBe(appear, DEFAULT_TIMEOUT).$("button[id$='reset-dashboard-button']")
        .shouldBe(appear, DEFAULT_TIMEOUT).shouldBe(getClickableCondition()).click();
  }

  public SelenideElement getRestoreDashboardButton() {
    return $("[id$='restore-button-group']").shouldBe(appear, DEFAULT_TIMEOUT).$("button[id$='restore-button']")
        .shouldBe(exist, DEFAULT_TIMEOUT);
  }

  public DashboardNewsWidgetConfigurationPage addNewsFeedWidget() {
    addWidgetByName("News feed widget");
    return new DashboardNewsWidgetConfigurationPage();
  }
  
  public void addNewCustomrWidget(String processName) {
    addCustomWidgetByName(processName);
  }
  
  public void addCustomWidgetByName(String name) {
    $("div[id$='new-widget-dialog_content']").shouldBe(appear, DEFAULT_TIMEOUT).$$("div.new-widget-dialog__item")
        .filter(text(name)).first().$("button[id^='new-custom-widget-dialog-content']")
        .shouldBe(getClickableCondition()).click();
  }
  
  public CustomWidgetNewDashBoardPage addExternalPageWidget() {
    addWidgetByName("External Page");
    return new CustomWidgetNewDashBoardPage();
  }
  
  public void waitForCaseWidgetLoaded() {
    $("div[id$='dashboard-cases-container']").shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='dashboard-cases']")
        .shouldBe(appear, DEFAULT_TIMEOUT);
  }
  
  public WebElement addWidget() {
    $("button[id='add-button']").shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    return $("div[id$='new-widget-dialog']").shouldBe(appear, DEFAULT_TIMEOUT);
  }
  
  public void clickOnAddCustomWidget() {
    waitForElementDisplayed(By.id("new-widget-dialog"), Boolean.TRUE);
    waitForElementClickableThenClick(By.id("new-widget-dialog-content:1:add-widget"));
  }

  public void selectCustomWidgetTypeProcess() {
    waitForElementDisplayed(By.id(CUSTOM_WIDGET_TYPE_DROPDOWN_ID), Boolean.TRUE);
    waitForElementClickableThenClick(By.id(CUSTOM_WIDGET_TYPE_DROPDOWN_ID));
    waitForElementDisplayed(By.id("widget-configuration-form:new-widget-configuration-component:custom-widget-type_items"), Boolean.TRUE);
    waitForElementClickableThenClick(By.id("widget-configuration-form:new-widget-configuration-component:custom-widget-type_1"));
  }
  
}
