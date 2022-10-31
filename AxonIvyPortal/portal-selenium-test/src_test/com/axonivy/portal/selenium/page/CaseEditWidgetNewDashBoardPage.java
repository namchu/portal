package com.axonivy.portal.selenium.page;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class CaseEditWidgetNewDashBoardPage extends TemplatePage {

  private static final String FILTER_CASE_NAME = "Case name";
  private static final String FILTER_CASE_STATE = "State";

  private String caseEditWidgetId;

  public CaseEditWidgetNewDashBoardPage() {
    this("div[id='new-widget-configuration-dialog']");
  }

  public CaseEditWidgetNewDashBoardPage(String caseWidgetId) {
    this.caseEditWidgetId = caseWidgetId;
  }

  private int getIndexFiltertByName(String name) {
    ElementsCollection elementsTH =
        $(caseEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='user-filter']").$$("div");
    for (int i = 0; i < elementsTH.size(); i++) {
      if (elementsTH.get(i).getText().equalsIgnoreCase(name)) {
        return i;
      }
    }
    return 0;
  }

  private SelenideElement getAvailableFilterInput(String filterName) {
    int index = getIndexFiltertByName(filterName);
    return $(caseEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='user-filter']").$$("div").get(index + 1)
        .$("input");
  }
  
  private SelenideElement getAvailableFilterCheckbox(String filterName) {
    int index = getIndexFiltertByName(filterName);
    return $(caseEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='user-filter']").$$("div").get(index + 1)
        .$(".ui-selectcheckboxmenu");
  }
  
  
  private SelenideElement getValueOfCheckBox(String value) {
    return $("div.ui-selectcheckboxmenu-items-wrapper").shouldBe(appear, DEFAULT_TIMEOUT)
        .$$("li.ui-selectcheckboxmenu-item").filter(text(value)).first().$("div.ui-chkbox-box");
  }
  
  private SelenideElement getCloseCheckBox() {
    return $("div.ui-selectcheckboxmenu-panel").shouldBe(appear, DEFAULT_TIMEOUT).$("a.ui-selectcheckboxmenu-close");
  }
  
  public void selectStateAsInProgress() {
    getValueOfCheckBox("In progress").shouldBe(getClickableCondition()).click();
    getCloseCheckBox().shouldBe(getClickableCondition()).click();
  }

  private SelenideElement widgetTitle() {
    return $(caseEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("span[id$='widget-title-group']")
        .$("input[id$='widget-title']");
  }

  public void changeWidgetTitle(String name) {
    widgetTitle().clear();
    widgetTitle().sendKeys(name);
  }

  public void preview() {
    $(caseEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("button[id$='preview-button']")
        .shouldBe(getClickableCondition()).click();
  }

  public void filterCaseName(String caseName) {
    getAvailableFilterInput(FILTER_CASE_NAME).sendKeys(caseName);
  }
  
  public void filterCaseState() {
    getAvailableFilterCheckbox(FILTER_CASE_STATE).shouldBe(getClickableCondition()).click();
  }

  private ElementsCollection getRowOfTableCasePreview() {
    return $(caseEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='case-widget-preview:dashboard-cases']")
        .$$("table tbody tr");
  }

  public ElementsCollection countCases() {
    return getRowOfTableCasePreview();
  }

  public void nextPageTable() {
    $(caseEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='case-widget-preview:dashboard-cases']")
        .$("a.ui-paginator-next").shouldBe(getClickableCondition()).click();
  }

  public void waitPreviewTableLoaded() {
    $(caseEditWidgetId).$("div[id$=':dashboard-cases-container']").shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public void save() {
    $(caseEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("button[id$='widget-configuration-save-button']")
        .shouldBe(getClickableCondition()).click();
    $(caseEditWidgetId).shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  public void openColumnManagementDialog() {
    $("div[id$='case-widget-preview:dashboard-cases-container']").shouldBe(appear, DEFAULT_TIMEOUT)
        .$("a[id$='column-toggler']").click();
    getColumnManagementDialog().shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public SelenideElement getColumnManagementDialog() {
    return $("div[id$='column-management-dialog']");
  }

  public void selectCustomType() {
    selectFieldType("CUSTOM");
    getCustomFieldCategory().shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public void selectStandardType() {
    selectFieldType("STANDARD");
    getCustomFieldCategory().shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  private SelenideElement getCustomFieldCategory() {
    return getColumnManagementDialog().$("input[id$=':column-management-form:custom-field-categories-selection_input']");
  }

  public void selectFieldType(String type) {
    getColumnManagementDialog().$("div[id$='field-type-selection'] span.ui-icon-triangle-1-s")
        .shouldBe(Condition.appear, DEFAULT_TIMEOUT).shouldBe(Condition.enabled, DEFAULT_TIMEOUT).click();
    $("div[id$='column-management-form:field-type-selection_panel'] li[data-label='" + type + "']").click();
  }

  public void removeAddedField(String field) {
    SelenideElement removeLink = getAddedFieldRemoveLink(field);
    removeLink.click();
    removeLink.shouldBe(Condition.disappear, DEFAULT_TIMEOUT);
  }

  public SelenideElement getAddedFieldRemoveLink(String field) {
    return getColumnManagementDialog().$("tbody td.js-column-field-" + field + " a");
  }

  public SelenideElement getCustomField(String field) {
    getCustomFieldSelection().click();
    SelenideElement customFieldPanel = $("span[id$='column-management-form:custom-field-selection_panel']");
    customFieldPanel.shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    return customFieldPanel.$("li[data-item-value='" + field + "']");
  }

  public String addFirstCustomField() {
    selectCustomType();
    getCustomFieldSelection().click();
    SelenideElement customFieldPanel = $("span[id$='column-management-form:custom-field-selection_panel']");
    customFieldPanel.shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    SelenideElement firstFieldElement = customFieldPanel.$("li").shouldBe(getClickableCondition());
    String field = firstFieldElement.getText();
    firstFieldElement.click();

    getColumnManagementDialog().$("button[id$='field-add-btn']").click();

    getAddedFieldRemoveLink(field).shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    return field;
  }

  private SelenideElement getCustomFieldSelection() {
    return getColumnManagementDialog().$("span[id$='custom-field-selection'] button");
  }

  public SelenideElement getStandardField(String field) {
    getStandardFieldSelection().click();
    SelenideElement standardFieldPanel = $("div[id$='column-management-form:standard-field-selection_panel']");
    standardFieldPanel.shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    return standardFieldPanel.$("li[data-label='" + field + "']");
  }

  public String addFirstStandardField() {
    getStandardFieldSelection().click();
    SelenideElement standardFieldPanel = $("div[id$='column-management-form:standard-field-selection_panel']");
    standardFieldPanel.shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    SelenideElement firstFieldElement = standardFieldPanel.$("li").shouldBe(getClickableCondition());
    String field = firstFieldElement.getText();
    firstFieldElement.click();

    getFieldDisplayName().clear();
    getFieldDisplayName().sendKeys(field);

    getColumnManagementDialog().$("button[id$='field-add-btn']").click();

    getAddedFieldRemoveLink(field).shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    return field;
  }

  private SelenideElement getStandardFieldSelection() {
    getColumnManagementDialog().$("div[id$='standard-field-selection'] .ui-selectonemenu-trigger").shouldBe(getClickableCondition(), DEFAULT_TIMEOUT);
    return getColumnManagementDialog().$("div[id$='standard-field-selection'] .ui-selectonemenu-trigger");
  }

  private SelenideElement getFieldDisplayName() {
    return getColumnManagementDialog().$("input[id$='field-display-name']");
  }

  @Override
  protected String getLoadedLocator() {
    return ".case-configuration__creators";
  }
}
