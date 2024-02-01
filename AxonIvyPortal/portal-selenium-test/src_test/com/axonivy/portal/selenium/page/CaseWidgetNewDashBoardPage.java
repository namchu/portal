package com.axonivy.portal.selenium.page;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.openqa.selenium.By;

import com.axonivy.portal.selenium.common.FilterOperator;
import com.axonivy.portal.selenium.common.FilterValueType;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class CaseWidgetNewDashBoardPage extends TemplatePage {

  private static final String YOUR_CASES_WIDGET = "Your Cases";
  private static final String FILTER_CASE_NAME = "Case name";
  private static final String FILTER_CASE_STATE = "State";

  private String caseWidgetId;
  private String caseWidgetName;

  public CaseWidgetNewDashBoardPage() {
    this("div[id$='dashboard-cases']", YOUR_CASES_WIDGET);
  }

  public CaseWidgetNewDashBoardPage(String caseWidgetName) {
    this("div[id$='dashboard-cases']", caseWidgetName);
  }

  public CaseWidgetNewDashBoardPage(String caseWidgetId, String caseWidgetName) {
    this.caseWidgetId = caseWidgetId;
    this.caseWidgetName = caseWidgetName;
  }

  @Override
  protected String getLoadedLocator() {
    return "[id$='dashboard-cases-container']";
  }

  private int getIndexWidgetByColumn(String columnName) {
    ElementsCollection elementsTH = $(caseWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$$("table thead tr th");
    for (int i = 0; i < elementsTH.size(); i++) {
      if (elementsTH.get(i).getText().equalsIgnoreCase(columnName)) {
        return i;
      }
    }
    return 0;
  }

  private int getIndexWidgetByColumnScrollable(String columnName) {
    ElementsCollection elementsTH =
        $(caseWidgetId).$(".ui-datatable-scrollable-header").shouldBe(appear, DEFAULT_TIMEOUT).$$("table thead tr th");
    for (int i = 0; i < elementsTH.size(); i++) {
      if (elementsTH.get(i).getAttribute("aria-label").equalsIgnoreCase(columnName)) {
        return i;
      }
    }
    return 0;
  }

  private ElementsCollection getColumnsOfTableWidget() {
    return $(caseWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$$("table tbody tr td");
  }

  private ElementsCollection getColumnOfTableWidget(int rowIndex) {
    return $(caseWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$$("table tbody tr ").get(rowIndex).$$("td");
  }

  public ElementsCollection expand() {
    return $$("div.widget__header").filter(text(caseWidgetName));
  }

  private ElementsCollection getCasesOfCaseWidgetHasName(String caseName) {
    return getColumnsOfTableWidget().filter(Condition.cssClass("dashboard-cases__name")).filter(text(caseName));
  }

  private SelenideElement getCaseOfCaseWidgetHasIndex(int index) {
    return getColumnsOfTableWidget().get(index);
  }

  public CaseDetailsPage openDetailsCase(String caseName) {
    getCasesOfCaseWidgetHasName(caseName).first().shouldBe(getClickableCondition()).click();
    return new CaseDetailsPage();
  }

  public ElementsCollection countCases(String caseName) {
    return getCasesOfCaseWidgetHasName(caseName);
  }

  public CaseDetailsPage openDetailsFirstCase() {
    getCaseOfCaseWidgetHasIndex(0).shouldBe(getClickableCondition()).click();
    return new CaseDetailsPage();
  }

  private SelenideElement getColumnOfCaseHasIndex(int index, String columnName) {
    int startIndex = getIndexWidgetByColumn(columnName);
    return getColumnOfTableWidget(index).get(startIndex);
  }

  private SelenideElement getColumnOfCaseHasActionIndex(int index, String columnName) {
    int startIndex = getIndexWidgetByColumnScrollable(columnName);
    return getColumnOfTableWidget(index).get(startIndex).$("span a");
  }

  public SelenideElement stateOfFirstCase() {
    return getColumnOfCaseHasIndex(0, "state");
  }

  public void openFilterWidget() {
    $$("div.table-widget-panel").filter(text(caseWidgetName)).first().shouldBe(appear, DEFAULT_TIMEOUT)
        .$(".widget__filter-sidebar-link").shouldBe(getClickableCondition()).click();
    $("[id$=':widget-saved-filters-items").shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public CaseEditWidgetNewDashBoardPage openEditWidget() {
    $$("div.table-widget-panel div.widget__header").filter(text(caseWidgetName)).first()
        .shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='widget-header-actions']").$("[id*='edit-widget']")
        .shouldBe(getClickableCondition()).click();
    return new CaseEditWidgetNewDashBoardPage();
  }

  private SelenideElement getFilterInput(String inputField) {
    return $("div[id$='widget-filter-content']").shouldBe(appear, DEFAULT_TIMEOUT)
        .$$("div.widget-filter-panel div.ui-g").filter(text(inputField)).first().$("input.ui-inputfield");
  }

  public void filterCaseName(String input) {
    getFilterInput(FILTER_CASE_NAME).sendKeys(input);
  }

  public void clearFilterCaseName() {
    getFilterInput(FILTER_CASE_NAME).clear();
  }

//  public void filterCaseState() {
//    getFilterCheckBox(FILTER_CASE_STATE).shouldBe(getClickableCondition()).click();
//  }

  public void selectStateAsDone() {
    getValueOfCheckBox("Done").shouldBe(getClickableCondition()).click();
    getCloseCheckBox().shouldBe(getClickableCondition()).click();
  }

  public void selectStateAsOpen() {
    getValueOfCheckBox("Open").shouldBe(getClickableCondition()).click();
    getCloseCheckBox().shouldBe(getClickableCondition()).click();
  }

  private SelenideElement getFilterCheckBox(String inputField) {
    return $("div[id$='widget-filter-content']").shouldBe(appear, DEFAULT_TIMEOUT)
        .$$("div.widget-filter-panel div.ui-g").filter(text(inputField)).first();
  }

  private SelenideElement getCloseCheckBox() {
    return $("div.ui-selectcheckboxmenu-panel").shouldBe(appear, DEFAULT_TIMEOUT).$("a.ui-selectcheckboxmenu-close");
  }

  private SelenideElement getValueOfCheckBox(String value) {
    return $("div.ui-selectcheckboxmenu-items-wrapper").shouldBe(appear, DEFAULT_TIMEOUT)
        .$$("li.ui-selectcheckboxmenu-item").filter(text(value)).first().$("div.ui-chkbox-box");
  }

  public void applyFilter() {
    $("div.filter-overlay-panel__footer").shouldBe(appear, DEFAULT_TIMEOUT).$$("button[id$='apply-button']")
        .filter(text("Apply")).first().shouldBe(getClickableCondition()).click();
    $("[id$='case-case_1:filter-overlay-panel-1']").shouldBe(Condition.disappear, DEFAULT_TIMEOUT);
  }

  public void nextPageTable() {
    $$("div.table-widget-panel").filter(text(caseWidgetName)).first().$("a.ui-paginator-next")
        .shouldBe(getClickableCondition()).click();
  }

  public void resetFilter() {
    $("div.filter-overlay-panel__footer").shouldBe(appear, DEFAULT_TIMEOUT).$$("button[id$='reset-button']")
        .filter(text("Reset")).first().shouldBe(getClickableCondition()).click();
  }

  public void selectState(String state) {
    getValueOfCheckBox(state).shouldBe(getClickableCondition()).click();
    getCloseCheckBox().shouldBe(getClickableCondition()).click();
  }

  public void clickOnCaseActionLink(int caseIndex) {
    getColumnOfCaseHasActionIndex(caseIndex, "Actions").shouldBe(getClickableCondition()).click();
  }

  public void turnOffActionsPanel(int caseIndex) {
    $$("div.widget__header").filter(text(caseWidgetName)).first().shouldBe(getClickableCondition()).click();
    $$(String.format("div.js-case-side-steps-panel-case_1-%d", caseIndex)).first().shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  public ElementsCollection getActiveCaseActions(int caseIndex) {
    clickOnCaseActionLink(caseIndex);
    return $$(String.format("div.js-case-side-steps-panel-case_1-%d", caseIndex)).filter(appear).first()
        .shouldBe(appear, DEFAULT_TIMEOUT).$("div.ui-overlaypanel-content").$$("a[class*='action-step-item']");
  }

  public void destroyCase(int caseIndex) {
    getActiveCaseActions(caseIndex).filter(text("Destroy")).first().shouldBe(getClickableCondition()).click();
    confirmDestroy();
  }

  private void confirmDestroy() {
    $("div[id$='destroy-case-confirmation-dialog']").shouldBe(appear, DEFAULT_TIMEOUT)
        .$("button[id$='confirm-destruction-dashboard-cases']").shouldBe(getClickableCondition()).click();
  }

  public SelenideElement getCreatorAvatar() {
    return $(".dashboard-cases__creator > .has-avatar > .ui-avatar").shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public void deleteCaseWidget() {
    $$("div.table-widget-panel div.widget__header").filter(text(caseWidgetName)).first()
        .shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='widget-header-actions']").$("[id*='delete-widget']")
        .shouldBe(getClickableCondition()).click();
  }

  public void clickExportExcel() {
    expand().first().$(".widget__info-sidebar-link").shouldBe(appear, DEFAULT_TIMEOUT).shouldBe(getClickableCondition())
        .click();
    $("div.info-overlay-panel__footer").$(".dashboard-excel-export-form").$("a").shouldBe(getClickableCondition())
        .click();
  }

  public void clickOnCustomActionButton(int rowIndex, String columnName) {
    SelenideElement custom = $("a[id$=':custom-description']").shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    custom.shouldBe(getClickableCondition()).click();
  }
  
  ////////////////// 
  
  // Not support tree input like Category...
  public SelenideElement addFilter(String columnName, FilterOperator operator, Object... values) {
    $("div[id$='widget-filter-content']").shouldBe(appear, DEFAULT_TIMEOUT);
    int currentIndex = $$("div[id$=':filter-component:filter-selection-panel']").size();
    $("button[id$=':add-filter']").shouldBe(getClickableCondition()).click();
    $$("div[id$=':filter-component:filter-selection-panel']").shouldBe(CollectionCondition.sizeGreaterThanOrEqual(currentIndex + 1));
    selectFilterColumnName(columnName, currentIndex);
    if (operator != null) {
      selectFilterOperator(operator, currentIndex);
    }
    
    if (values.length > 0) {
      
    }
    return getNewFilter(currentIndex);
  }
  
  public SelenideElement getNewFilter(int filterIndex) {
    String latestFilter = String.format("div[id$=':%s:filter-component:filter-selection-panel']", filterIndex);
    return $(latestFilter).shouldBe(Condition.appear, DEFAULT_TIMEOUT);
  }
  
  public void selectFilterColumnName(String columnName, int filterIndex) {
    var filterElement = getNewFilter(filterIndex);
    filterElement.$("div[id$=':filter-component:field-selection']").shouldBe(getClickableCondition()).click();
    String columnSelection = String.format("div[id$=':%s:filter-component:field-selection_panel']", filterIndex);
    $(columnSelection).$$("ul li").filter(text(columnName)).first().click();
  }
  
  public void selectFilterOperator(FilterOperator operator, int filterIndex) {
    var filterElement = getNewFilter(filterIndex);
    filterElement.$("div[id$=':operator-selection']").shouldBe(getClickableCondition()).click();
    $("div[id$=':operator-selection_panel'] ul[id$=':operator-selection_items']").$$("li").filter(text(operator.getValue())).first().click();
  }
  
  public void inputValueOnLatestFilter(FilterValueType type, Object... values) {
    int currentIndex = $$("div[id$=':filter-component:filter-selection-panel']").size();
    if (currentIndex < 1) {
      return;
    }
    var filterElement = getNewFilter(currentIndex - 1);
    switch (type) {
      case STATE_TYPE:
        filterElement.$("div[id$=':states']").shouldBe(getClickableCondition()).click();
        selectState(String.valueOf(values[0]));
        break;
      case TEXT, NUMBER:
        var textField = filterElement.$("div[id$=':text-list-panel']").$(".ui-chips.ui-widget").$("input").shouldBe(appear);
        for (int i=0; i < values.length; i++) {
          textField.clear();
          textField.sendKeys(String.valueOf(values[i]));
          textField.pressEnter();
        }
        break;
      case DATE:
        var dateInput = filterElement.$$(".date-picker-panel input").shouldHave(CollectionCondition.sizeGreaterThanOrEqual(values.length));
        for (int i=0; i < dateInput.size(); i++) {
          dateInput.get(i).clear();
          dateInput.get(i).sendKeys(String.valueOf(values[i]));
        }
        break;
      case CREATOR_TYPE:
        var creatorInput = filterElement.$("div[id$=':creators']").shouldBe(appear);
        creatorInput.$("button[id$=':creators_button']").shouldBe(getClickableCondition()).click();
        var selectPanel = $("span[id$=':creators_panel'][style*='display: block']").shouldBe(appear);
        for (int i=0; i < values.length; i++) {
//          filterElement.$("div[id$=':creators']").shouldBe(getClickableCondition()).click();
          creatorInput.$("input").sendKeys(String.valueOf(values[i]));
          selectPanel.$(".ui-avatar-text").shouldBe(appear);
          selectPanel.shouldBe(getClickableCondition()).click();
//          var selectPanel = $$("span[id$=':creators_panel']").filter(Condition.visible);
//          selectPanel.$("")
//          waitForElementDisplayed($("div[id*='creators_panel'] .ui-avatar-text"), true);
//          waitForElementClickableThenClick($(By.xpath("//*[contains(@id, 'filter-component:') and contains(@id, ':creators_panel')]")));
//          creatorInput.pressEnter(); case-case_1:filter-form-1:filter-list:4:filter-component:j_id_t_2_8u_1_1_3_b6:creators_panel
        }
        break;
      case CATEGORY_TYPE:
        break;
      default:
        break;
    }
  }
  
  public void filterByCreator(String text) {
    waitForElementClickableThenClick(
        $(By.cssSelector("button[id$='creator-filter:filter-open-form:advanced-filter-command']")));
    $("input[id$='creator-filter:filter-input-form:creator-component:creator-select_input']").sendKeys(text);
    waitForElementDisplayed($("div[id*='creator-filter'] .ui-avatar-text"), true);
    waitForElementClickableThenClick(
        "span[id$=':creator-filter:filter-input-form:creator-component:creator-select_panel']");
    waitForElementClickableThenClick(
        $(By.cssSelector("button[id$='creator-filter:filter-input-form:update-command']")));
  }
  
  
}
