package portal.guitest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.server.browserlaunchers.Sleeper;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StatisticWidgetPage extends TemplatePage {
  private WebElement statisticWidget;
  private final static String TASK_MENU_ID = "main-menu-container:main-menu-form:main-menu-container_node_1";

  public StatisticWidgetPage() {
    WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    Sleeper.sleepTight(1000);
    waitForPageLoaded();
    waitForElementDisplayed(By.id("statistics-widget"), true);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("statistics-widget")));
    statisticWidget = findElementById("statistics-widget");
  }

  @Override
  protected String getLoadedLocator() {
    return "id('statistics-widget')";
  }

  public WebElement getStatisticWidget() {
    return statisticWidget;
  }

  public boolean isTaskMenuOpen() {
    return isElementDisplayed(By.id(TASK_MENU_ID + "_0"));
  }

  public void switchMode() {
    WebElement switchLink = findElementById("statistics-widget:statistic-link:statistic-link");
    switchLink.click();
  }

  public void switchCreateMode() {
    waitForElementDisplayed(By.id("statistics-widget:create-chart-link"), true, DEFAULT_TIMEOUT);
    WebElement switchLink = findElementById("statistics-widget:create-chart-link");
    switchLink.click();
  }

  public TaskAnalysisWidgetPage navigateToTaskAnalysisPage() {
    String taskAnalysisLinkString = "statistics-widget:task-analysis-page-navigation-link";
    waitForElementDisplayed(By.id(taskAnalysisLinkString), true, DEFAULT_TIMEOUT);
    WebElement taskAnalysisLink = findElementById(taskAnalysisLinkString);

    taskAnalysisLink.click();
    waitForElementDisplayed(By.id("task-widget"), true, DEFAULT_TIMEOUT);

    return new TaskAnalysisWidgetPage();
  }

  public boolean isCompactMode() {
    waitForPageLoaded();
    waitForElementDisplayed(By.id("statistics-widget:widget-container"), true, DEFAULT_TIMEOUT);
    WebElement statisticContainer = findElementById("statistics-widget:widget-container");
    return statisticContainer.getAttribute(CLASS_PROPERTY).contains("compact-mode");
  }

  public boolean isFullMode() {
    return !isCompactMode();
  }

  public boolean isCreateMode() {
    waitForElementDisplayed(By.id("statistics-widget:chart-list-container"), true, DEFAULT_TIMEOUT);
    return isElementPresent(By.id("statistics-widget:chart-list-container"));
  }
  
  public boolean hasCreateChartsLink(){
    return isElementPresent(By.id("statistics-widget:create-chart-link-label"));
  }

  public boolean hasTaskAnalysisLink() {
    return isElementPresent(By.id("statistics-widget:task-analysis-page-navigation-link"));
  }
}
