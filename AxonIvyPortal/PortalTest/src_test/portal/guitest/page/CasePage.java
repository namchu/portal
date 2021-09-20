package portal.guitest.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import portal.guitest.common.Sleeper;

public class CasePage extends TemplatePage {
  private static final String CASE_ITEM_LIST_SELECTOR = "li[class='ui-datascroller-item']";
  private static final String CASE_NAME_CSS_SELECTOR = "span[class='case-header-name-cell']";
  private final static String CASE_PAGE_LOCATION = "id('case-widget:case-list')";
  private static final String DESTROY_CASE_ID = "case-widget:case-list-scroller:%s:case-item:destroy-case";
  private static final String CASE_ITEM_DETAILS_ID = "case-widget:case-list-scroller:%s:case-item:case-details";
  private static final String CASE_NAME_ID = "case-widget:case-list-scroller:%d:case-item:case-header:case-name-form:case-name-edit-inplace";

  @Override
  protected String getLoadedLocator() {
    return CASE_PAGE_LOCATION;
  }

  public WebElement selectCaseItem(int index) {
    String caseItemId = String.format(CASE_ITEM_DETAILS_ID, index);
    return findElementById(caseItemId);
  }

  public boolean isCaseItemSelected(int index) {
    return findElementById(String.format(CASE_ITEM_DETAILS_ID, index)).getAttribute("class").contains(
        "case-list-item-expanded");
  }

  private WebElement getDestroyButtonOfCaseItem(int index) {
    String destroyButtonId = String.format(DESTROY_CASE_ID, index);
    WebElement destroyButton = findElementById(destroyButtonId);
    findElementById(destroyButtonId);
    return destroyButton;
  }

  public void clickDestroyButton(int index) {
    WebElement destroyButton = getDestroyButtonOfCaseItem(index);
    destroyButton.click();
  }

  public boolean isDestroyButtonVisible(int index) {
    try {
      getDestroyButtonOfCaseItem(index);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public void confimDestruction() {
    String destroyCaseDialogId = "case-widget:destroy-case-confirmation-dialog";
    waitForElementDisplayed(By.id(destroyCaseDialogId), true);
    WebElement destroyConfirmationDialog = findElementById(destroyCaseDialogId);
    WebElement confirmButton = findChildElementById(destroyConfirmationDialog, "case-widget:confirm-destruction");
    confirmButton.click();
  }

  public CaseDetailsPage openDetailsOfCaseHasName(String caseName) {
    List<WebElement> caseItems = findListElementsByCssSelector(CASE_ITEM_LIST_SELECTOR);
    for (WebElement caseItem : caseItems) {
      if (caseItem.findElement(By.cssSelector(CASE_NAME_CSS_SELECTOR)).getText().equals(caseName)) {
        caseItem.findElement(By.cssSelector(CASE_NAME_CSS_SELECTOR)).click();
        waitAjaxIndicatorDisappear();
        CaseDetailsPage detailsPage = new CaseDetailsPage(caseItem);
        return detailsPage;
      }
    }
    throw new NoSuchElementException("Cannot find details of case that has name " + caseName);
  }

  public int getNumberOfCases() {
    List<WebElement> caseItems = findListElementsByCssSelector(CASE_ITEM_LIST_SELECTOR);
    return caseItems.size();
  }

  public String getCaseName(int index) {
    waitForElementDisplayed(By.cssSelector("*[id$='case-widget:case-list']"), true);
    WebElement selectedCaseNameElement =
        findElementById(String.format(CASE_NAME_ID, index));
    return selectedCaseNameElement.getText();
  }

  public boolean isCaseDisplayed(String name) {
    List<WebElement> caseNameElements = findListElementsByClassName("case-header-name-cell");
    return caseNameElements.stream().anyMatch(caseNameElement -> name.equals(caseNameElement.getText()));
  }


  public void openAdvancedFilter(String filterName, String filterIdName) {
    click(By.id("case-widget:filter-add-action"));
    WebElement filterSelectionElement = findElementById("case-widget:filter-add-form:filter-selection");
    findChildElementsByTagName(filterSelectionElement, "LABEL").forEach(filterElement -> {
      if (filterName.equals(filterElement.getText())) {
        filterElement.click();
        return;
      }
    });
    waitForElementDisplayed(
        By.cssSelector("span[id$='" + filterIdName + "-filter:filter-open-form:advanced-filter-item-container']"), true);
  }

  public void filterByDescription(String text) {
    click(By.cssSelector("button[id$='description-filter:filter-open-form:advanced-filter-command']"));
    WebElement descriptionInput =
        findElementByCssSelector("input[id$='description-filter:filter-input-form:description']");
    enterKeys(descriptionInput, text);
    click(By.cssSelector("button[id$='description-filter:filter-input-form:update-command']"));
    Sleeper.sleep(2000);
  }

  public void saveFilter(String filterName) {
    click(By.id("case-widget:filter-save-action"));
    Sleeper.sleep(2000);
    WebElement filterNameInput = findElementById("case-widget:filter-save-form:save-filter-set-name-input");
    enterKeys(filterNameInput, filterName);
    click(findElementById("case-widget:filter-save-form:filter-save-command"));
    Sleeper.sleep(2000);
  }

  public Object getFilterName() {
    click(findElementById("case-widget:filter-selection-form:filter-name"));
    WebElement descriptionInput = findElementByCssSelector(".user-definied-filter-container");

    return descriptionInput.getText();
  }

  public boolean isFilterSelectionVisible() {
    return isElementPresent(By.id("case-widget:filter-selection-form:filter-selection-panel"));
  }

  public int countCategoryRoots() {
    List<WebElement> taskElements = findListElementsByCssSelector("span[class*='js-second-level-menu']");
    return taskElements.size();
  }

  public void toggleCategoryMenu() {
    click(findElementByClassName("second-level-menu-header"));
  }
  
  public int getCaseCount() {
    WebElement caseTitleElement = findElementById("case-widget:case-widget-title");
    String title = caseTitleElement.getText();
    return Integer.parseInt(title.substring(title.lastIndexOf("(") + 1, title.length() -1));
  }
}