package com.axonivy.portal.selenium.page;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.axonivy.portal.selenium.common.WaitHelper;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;

public class StatisticWidgetPage extends TemplatePage {
  public static final String TASK_BY_PRIORITY_CHART_NAME = "Task by priority chart";
  public static final String CASE_BY_STATE_CHART_NAME = "Case by state chart";
  public static final String TASK_BY_EXPIRY_CHART_NAME = "Task by expiry chart";
  public static final String ELAPSED_TIME_CHART_NAME = "Elapsed time chart";
  public static final String CASE_BY_FINISHED_TASK_CHART_NAME = "Case by finished task chart";
  public static final String CASE_BY_FINISHED_TIME_CHART_NAME = "Case by finished time chart";
  public static final String CASES_BY_CATEGORY_CHART_NAME = "Cases by category chart";

  @Override
  protected String getLoadedLocator() {
    return "[id='statistics-widget']";
  }

  public TaskAnalysisWidgetPage navigateToTaskAnalysisPage() {
    var taskAnalysLink = $("a[id='statistics-widget:task-analysis-page-navigation-link']")
        .shouldBe(Condition.appear, DEFAULT_TIMEOUT).shouldBe(getClickableCondition());
    WaitHelper.waitForNavigation(() -> taskAnalysLink.click());
    $("[id='task-widget']").shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    return new TaskAnalysisWidgetPage();
  }

  public Set<String> getAllChartNames() {
    $(By.id("chart-name-0")).shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    return $$("div[id$=':chart-name-container'] .chart-name").shouldHave(size(1), DEFAULT_TIMEOUT).asFixedIterable().stream().map(e -> e.getText()).collect(Collectors.toSet());
  }

  public String getRestoreDefaultButtonName() {
    return findElementByCssSelector("span[id$='restore-default-chart-link-label']").getText();
  }

  public void switchCreateMode() {
    waitForElementClickableThenClick($("a[id$='create-chart-link']"));
    waitForElementDisplayed(By.cssSelector("a[id$='statistics-widget:back-from-chart-creation']"), true);
  }

  public void backToDashboard() {
    waitForElementClickableThenClick($("a[id$='back-from-chart-creation']"));
    waitForElementDisplayed(By.cssSelector("a[id$='create-chart-link']"), true);
  }

  public void createCaseByFinishedTask() {
    var caseByFinishedTaskSelector = "button[id$=':chart-management-form:create-case-by-finished-task-link']";
//    waitForElementDisplayed(By.cssSelector(caseByFinishedTaskSelector), true);
//    clickByCssSelector(caseByFinishedTaskSelector);
    waitForElementClickableThenClick($(caseByFinishedTaskSelector));
    waitForElementDisplayed(By.cssSelector("div[id$='add-chart-dialog']"), true);
//    waitUntilAnimationFinished(DEFAULT_TIMEOUT, "statistics-widget\\:chart-creation-widget\\:add-statistic-form\\:chart-name-list\\:0\\:chart-name-input", CLASS_PROPERTY);

    inputNameForSupportedLanguages(CASE_BY_FINISHED_TASK_CHART_NAME);
    waitForElementClickableThenClick($(By.cssSelector("button[id$='chart-save-command']")));
    waitForElementDisplayed(By.cssSelector("div[id$='add-chart-dialog']"), false);
    waitForElementExisted(By.cssSelector("span[class='ui-growl-title']"), true);
  }

  private void inputNameForSupportedLanguages(String value) {
    for (int i = 0; i < 4; i++) {
      findElementByCssSelector("input[id$='" + i + ":chart-name-input']").sendKeys(value);
    }
  }

  @SuppressWarnings("deprecation")
  public void restoreDefaultCharts() {
    new WebDriverWait(WebDriverRunner.getWebDriver(), DEFAULT_TIMEOUT).until((webDriver) -> {
      WebElement restoreDefault = findElementByCssSelector("span[id$='restore-default-chart-link-label']");
      try {
        return restoreDefault.getText().contains("Restore default");
      } catch (WebDriverException e) {
        System.out.println("Exception when waiting for element existed, try again.");
      }
      return false;
    });
    waitForElementClickableThenClick($("span[id$='restore-default-chart-link-label']"));
    waitAjaxIndicatorDisappear();
    waitForElementDisplayed(By.id("statistics-widget:restore-confirmation-dialog"), true, 30);
    waitForElementClickableThenClick($(By.id("statistics-widget:confirm-restore")));
    waitAjaxIndicatorDisappear();
  }

  public String getChartName(int chartIndex) {
    new WebDriverWait(WebDriverRunner.getWebDriver(), DEFAULT_TIMEOUT).until((webDriver) -> {
      try {
        return findElementByCssSelector(String.format("div[id$='%d:chart-name-container'] .chart-name", chartIndex)).getText().length() > 1;
      } catch (WebDriverException e) {
        System.out.println("Exception when waiting for element existed, try again.");
      }
      return false;
    });
    WebElement chartName = findElementByCssSelector(String.format("div[id$='%d:chart-name-container'] .chart-name", chartIndex));
    return chartName.getText();
  }
}
