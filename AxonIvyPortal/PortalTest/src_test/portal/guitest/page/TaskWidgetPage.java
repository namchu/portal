package portal.guitest.page;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.server.browserlaunchers.Sleeper;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jayway.awaitility.Awaitility;
import com.jayway.awaitility.Duration;

import portal.guitest.common.TaskState;

public class TaskWidgetPage extends TemplatePage {

  private String taskWidgetId;
  private static final String UI_INPLACE_SAVE = "ui-inplace-save";
  private static final String TASK_ACTION = "task-actions";
  private static final String CLASS = "class";
  private static final String ID_END = "*[id$='";
  private static final String ID_CONTAIN = "*[id*='";
  private static final String TASK_ITEM = ":task-item']";
  private static final String TASK_ITEM_TASK_INFO = ":task-item:task-info']";
  private static final String TASK_STATE_OPEN_ID =
      "task-widget:task-list-scroller:%d:task-item:task-state-component:task-state-open";
  private static final String TASK_STATE_RESERVED_ID =
      "task-widget:task-list-scroller:%d:task-item:task-state-component:task-state-reserved";
  private static final String KEYWORD_FILTER_SELECTOR =
      "input[id='task-widget:filter-form:filter-container:ajax-keyword-filter']";
  private static final String KEYWORD_FILTER_SELECTOR_EXPANDED_MODE =
      "input[id='task-widget:expanded-mode-filter-form:expanded-mode-filter-container:ajax-keyword-filter']";

  public TaskWidgetPage() {
    this("task-widget");
  }

  public TaskWidgetPage(String taskWidgetId) {
    this.taskWidgetId = taskWidgetId;
  }

  @Override
  protected String getLoadedLocator() {
    return "//*[contains(@id,'task-view')]";
  }

  @Override
  protected long getTimeOutForLocator() {
    return 150L;
  }

  public void expand() {
    WebElement fullModeButton = findElementById(taskWidgetId + ":task-list-link:task-list-link");
    fullModeButton.click();
    ensureNoBackgroundRequest();
    waitForLocatorDisplayed("id('" + taskWidgetId + ":filter-save-action')");
  }

  public TaskDetailsPage openTaskDetails(int index) {
    clickOnTaskEntryInFullMode(index, true);
    return new TaskDetailsPage();
  }

  public void clickBackButtonFromTaskDetails() {
    findElementById("task-detail-template:task-detail-title-form:back-to-previous-page").click();
  }

  public void showNoteHistory() {
    click(driver.findElement(By.cssSelector("a[id$='show-more-note-link']")));
  }

  private TaskDetailsPage clickOnTaskEntryInFullMode(int index, boolean isDetailsShown) {
    findElementByCssSelector("div[id$='" + index + "\\:task-item\\:task-info']").click();
    TaskDetailsPage taskDetailsPage = new TaskDetailsPage();
    return taskDetailsPage;
  }

  public boolean isTaskShowDetails() {
    try {
      WebElement taskDetails = findElementByCssSelector("span[id='task-detail-template:task-detail-container']");
      return taskDetails.isDisplayed();
    } catch (NoSuchElementException exception) {
      return false;
    }
  }

	public TaskTemplatePage startTask(int index) {
		WebElement taskListElement = findElementById(taskWidgetId + ":task-list-scroller");
		if (taskListElement.getAttribute(CLASS).contains("compact-mode")) {
			String cssSelector = ID_CONTAIN + index + TASK_ITEM;
			refreshAndWaitElement(cssSelector);
			waitForElementPresent(By.cssSelector(cssSelector), true);
			findElementByCssSelector(cssSelector).click();
		} else {
			String cssSelector = ID_END + index + TASK_ITEM_TASK_INFO;
			refreshAndWaitElement(cssSelector);
			waitForElementPresent(By.cssSelector(cssSelector), true);
			findElementByCssSelector(cssSelector).click();
		}
		waitForElementPresent(By.id(TASK_ACTION), true);
		return new TaskTemplatePage();
	}

	private void refreshAndWaitElement(String cssSelector) {
		Awaitility.await().atMost(new Duration(5, TimeUnit.SECONDS)).until(() -> {
			if (findListElementsByCssSelector(cssSelector).isEmpty()) {
				refresh();
				return false;
			} else {
				return true;
			}
		});
	}

  public boolean isTaskDelegateOptionDisable(int index) {
    sideStepMenuOnMoreButton(index);
    waitForElementDisplayed(By.id(taskWidgetId + ":task-list-scroller:" + index + ":task-item:task-action:additional-options:task-delegate-command"), true);
    WebElement delegateButton =
        findElementById(taskWidgetId + ":task-list-scroller:" + index + ":task-item:task-action:additional-options:task-delegate-command");
    return delegateButton.getAttribute(CLASS).contains("ui-state-disabled");
  }

  public int countTasks() {
    List<WebElement> taskElements = findListElementsByCssSelector("div[class*='task-start-list-item']");
    return taskElements.size();
  }

  public void filterTasksBy(String keyword) {
    WebElement keywordFilter = findElementByCssSelector(KEYWORD_FILTER_SELECTOR);
    keywordFilter.clear();
    keywordFilter.sendKeys(keyword);
    Sleeper.sleepTight(2000);
    waitAjaxIndicatorDisappear();
  }

  public void filterTasksInExpendedModeBy(String keyword) {
    waitForElementDisplayed(By.cssSelector(KEYWORD_FILTER_SELECTOR_EXPANDED_MODE), true);
    WebElement keywordFilter = findElementByCssSelector(KEYWORD_FILTER_SELECTOR_EXPANDED_MODE);
    keywordFilter.clear();
    keywordFilter.sendKeys(keyword);
    Sleeper.sleepTight(2000);
    waitAjaxIndicatorDisappear();
  }

  public boolean isFilterDisplayed() {
    return isElementPresent(By.cssSelector(KEYWORD_FILTER_SELECTOR));
  }

  public long getIdOfTaskHasIndex(int index) {
    String text = findElementById(
        taskWidgetId + ":task-list-scroller:" + index + ":task-item:task-start-item-view:task-start-task-state")
            .getText().replaceAll("\\D", "");
    return Long.valueOf(text);
  }

  public CaseDetailsPage openRelatedCaseOfTask() {
    click(findElementByCssSelector("a[id$='related-case']"));
    return new CaseDetailsPage();
  }

  public String getRelatedCase() {
    WebElement relatedCaseLink = findElementByCssSelector("a[id$='related-case']");
    return relatedCaseLink.getText();
  }

  public void sideStepMenuOnMoreButton(int taskId) {
    String moreButton = String.format("button[id$='%d\\:task-item\\:task-action\\:additional-options\\:task-side-steps-menu'] span.fa-ellipsis-v", taskId);
    waitForElementDisplayed(By.cssSelector(moreButton), true);
    findElementByCssSelector(moreButton).click();;
    waitAjaxIndicatorDisappear();
    waitForElementDisplayed(By.cssSelector("a[id$='adhoc-side-step-item']"), true);
  }

  public boolean isMoreButtonDisplayed(int taskId) {
    String moreButton = String.format(
        taskWidgetId + ":task-list-scroller:%d:task-item:task-action:additional-options:task-side-steps-menu", taskId);
    return isElementDisplayedById(moreButton);
  }
  
  public void reserveTask(int taskId) {
    String reserveCommandButton = String.format(
        taskWidgetId + ":task-list-scroller:%d:task-item:task-action:additional-options:task-reserve-command", taskId);
    waitForElementDisplayed(By.id(reserveCommandButton), true);
    click(findElementById(reserveCommandButton));
  }

  public void resetTask(int taskId) {
    String resetCommandButton =
        String.format(taskWidgetId + ":task-list-scroller:%s:task-item:task-action:additional-options:task-reset-command", taskId);
    waitForElementDisplayed(By.id(resetCommandButton), true);
    click(findElementById(resetCommandButton));
  }

  public void resetReservedTask(int taskId) {
    String resetCommandButton =
        String.format(taskWidgetId + ":task-list-scroller:%s:task-item:resume-task-action:task-reset-command", taskId);
    waitForElementDisplayed(By.id(resetCommandButton), true);
    click(findElementById(resetCommandButton));
    waitAjaxIndicatorDisappear();
  }

  public boolean isTaskStartEnabled(int taskId) {
    String startCommandButton =
        String.format(taskWidgetId + ":task-list-scroller:%d:task-item:task-action:task-action-component", taskId);
    WebElement element = findElementById(startCommandButton);
    return !element.getAttribute(CLASS).contains("ui-state-disabled");
  }

  public TaskState getTaskState(int taskId) {
    WebElement stateCell =
        findElementById(taskWidgetId + ":task-list-scroller:" + taskId + ":task-item:task-state-component:task-state");
    if (stateCell != null) {
      String stateClass = stateCell.findElement(By.className("fa")).getAttribute(CLASS);
      return TaskState.fromClass(stateClass.substring(stateClass.indexOf("task-state-")));
    }
    return null;
  }

  public void changeExpiryOfTaskAt(String dateStringLiteral) {
    click(findElementById("task-detail-template:general-information:expiry-form:edit-inplace_display"));
    waitForElementDisplayed(By.id("task-detail-template:general-information:expiry-form:expiry-calendar"), true);
    WebElement taskExpiryInlineEdit = findElementById("task-detail-template:general-information:expiry-form:expiry-calendar");
    taskExpiryInlineEdit.sendKeys(dateStringLiteral);

    WebElement editor = findElementById("task-detail-template:general-information:expiry-form:edit-inplace_editor");
    WebElement saveButton = findChildElementByClassName(editor, UI_INPLACE_SAVE);
    saveButton.click();
  }

  public String getExpiryOfTaskAt(int index) {
    waitForElementDisplayed(By.id("task-detail-template:general-information:expiry-form:edit-inplace_display"), true);
    WebElement taskExpiry = findElementById("task-detail-template:general-information:expiry-form:edit-inplace_display");
    return taskExpiry.getText();
  }

  public boolean isTaskPriorityChangeComponentPresented(int index) {
    return isElementPresent(By.id(String.format(
        taskWidgetId + ":task-list-scroller:%d:task-item:general-info:priority-form:edit-priority-inplace", index)));
  }

  public boolean isTaskNameChangeComponentPresented(int index) {
    return isElementPresent(By.id(
        String.format(taskWidgetId + ":task-list-scroller:%d:task-item:task-name-edit-form:task-name-input", index)));
  }

  public void changeDescriptionOfTask(String description) {
    findElementByCssSelector("span[id$='task-desription-inplace_display']").click();
    WebElement taskNameInput = findElementByCssSelector("textarea[id$='task-description-input']");
    waitForElementDisplayed(taskNameInput, true);
    taskNameInput.clear();
    taskNameInput.sendKeys(description);
    findElementByCssSelector("span[id$='task-desription-inplace_editor']  .ui-inplace-save").click();
    waitAjaxIndicatorDisappear();
  }

  public String getTaskDescription() {
    return findElementByCssSelector("span[id$='task-description-output']").getText();
  }

  public String getDescriptionInHeaderOfTaskAt(int index) {
    String taskNameId =
        String.format(taskWidgetId + ":task-list-scroller:%d:task-item:task-name-component:task-description", index);
    waitForElementDisplayed(By.id(taskNameId), true);
    WebElement taskName = findElementById(taskNameId);
    return taskName.getText();
  }

  public String getTaskCategory() {
    return findElementByCssSelector("span[id$='task-category']").getText();
  }

  public String getCaseCategory() {
    return findElementByCssSelector("span[id$='case-category']").getText();
  }
  
  public boolean isTaskDescriptionChangeComponentPresented(int index) {
    return isElementPresent(By.id(String.format(
        taskWidgetId + ":task-list-scroller:%d:task-item:description:task-description-form:task-description-input",
        index)));
  }

  public boolean isTaskListColumnExist(String columnHeaderText) {
    String taskListHeaderSelector = taskWidgetId + ":task-widget-sort-menu";
    waitForElementDisplayed(By.id(taskListHeaderSelector), true);
    WebElement taskListHeader = findElementById(taskListHeaderSelector);
    for (WebElement column : taskListHeader.findElements(By.tagName("a"))) {
      if (columnHeaderText.equals(column.getText())) {
        return true;
      }
    }
    return false;
  }

  public void sortTaskListByColumn(String columnHeaderText) {
    WebElement taskListHeader = findElementById(taskWidgetId + ":task-widget-sort-menu");
    for (WebElement column : taskListHeader.findElements(By.tagName("a"))) {
      if (columnHeaderText.equals(column.getText())) {
        column.click();
        break;
      }
    }
  }

  public String getTaskListCustomCellValue(int index, String columnId) {
    WebElement cell = findElementById(
        String.format(taskWidgetId + ":task-list-scroller:%d:task-item:%s-component:%s", index, columnId, columnId));
    return cell.getText();
  }

  public void openTaskDelegateDialog(int index) {
    sideStepMenuOnMoreButton(index);
    Awaitility.await().atMost(new Duration(5, TimeUnit.SECONDS))
        .until(() -> findElementByCssSelector("a[id$='task-delegate-command']").isDisplayed());
    findElementByCssSelector("a[id$='task-delegate-command']").click();
  }

  public boolean isDelegateTypeSelectAvailable() {
    return isElementPresent(By.cssSelector("div[id$=':activator-panel']"));
  }

  public boolean isDelegateTypeDisabled(int taskIndex, int index) {
    WebElement delegateTypeRadioButton = findElementByCssSelector(String.format(
        "input[id$='%d\\:task-item\\:task-action\\:additional-options\\:task-delegate-form\\:activator-type-select\\:%d']", taskIndex, index));
    
    return Optional.ofNullable(delegateTypeRadioButton.getAttribute("disabled")).isPresent();
  }

  public boolean isDelegateListSelectionAvailable() {
    return isElementPresent(By.cssSelector("div[id$='select-delegate-panel']"));
  }

  public AdhocPage addAdhoc(int taskIndex) {
    WebElement moreAction = findElementByCssSelector(ID_END + taskIndex + ":task-item:task-side-steps-menu']");
    moreAction.click();
    waitAjaxIndicatorDisappear();

    WebDriverWait wait = new WebDriverWait(getDriver(), 10);
    WebElement adhocMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.side-step-item")));

    try {
      adhocMenuItem.click();
    } catch (Exception e) {
      adhocMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.side-step-item")));
      adhocMenuItem.click();
    }
    waitForElementPresent(By.id(TASK_ACTION), true);
    return new AdhocPage();
  }

  /**
   * Get name from task item in task scroller at specific index. Task item must not be expanded.
   * 
   * @param index
   * @return task name
   */
  public String getNameOfTaskAt(int index) {
    WebElement name = findElementByCssSelector(ID_END + index + ":task-item:task-name-component:task-name']");
    return name.getText();
  }

  public String getResposibleOfTaskAt(int index) {
    WebElement taskStartInfo = findElementByCssSelector("div[id$='" + index + TASK_ITEM_TASK_INFO);
    WebElement responsibleSpan = taskStartInfo.findElement(By.cssSelector("span[id$='task-responsible']"));
    return responsibleSpan.getText();
  }

  public boolean isFilterSelectionVisible() {
    return isElementPresent(By.id(taskWidgetId + ":filter-selection-form:filter-selection-panel"));
  }

  public void openAdvancedFilter(String filterName, String filterIdName) {
    waitForElementDisplayed(By.cssSelector("button[id$='filter-add-action']"), true);
    findElementByCssSelector("button[id$='filter-add-action']").click();
    WebElement filterSelectionElement = findElementById(taskWidgetId + ":filter-add-form:filter-selection");
    List<WebElement> elements = findChildElementsByTagName(filterSelectionElement, "LABEL");
    for (WebElement element : elements) {
      if (element.getText().equals(filterName)) {
        element.click();
        break;
      }
    }
    waitForElementDisplayed(
        By.cssSelector("span[id$='" + filterIdName + "-filter:filter-open-form:advanced-filter-item-container']"),
        true);
  }

  public boolean isAdvancedFilterDisplayed(String filterIdName) {
    return isElementDisplayed(
        By.cssSelector("span[id$='" + filterIdName + "-filter:filter-open-form:advanced-filter-item-container']"));
  }

  public String getFilterValue(String filterId) {
    WebElement filterElement =
        findElementByCssSelector("button[id$='" + filterId + ":filter-open-form:advanced-filter-command']");
    return filterElement.getText();
  }

  public String getStateFilterSelection(int pos) {
    WebElement stateFilterSelectionElement =
        findElementByCssSelector("label[for$='state-filter:filter-input-form:state-selection:" + pos + "']");
    return stateFilterSelectionElement.getText();
  }

  public void openStateFilter() {
    click(By.cssSelector("button[id$='state-filter:filter-open-form:advanced-filter-command']"));
  }

  public void filterByDescription(String text) {
    click(By.cssSelector("button[id$='description-filter:filter-open-form:advanced-filter-command']"));
    WebElement descriptionInput =
        findElementByCssSelector("input[id$='description-filter:filter-input-form:description']");
    enterKeys(descriptionInput, text);
    click(By.cssSelector("button[id$='description-filter:filter-input-form:update-command']"));
    Sleeper.sleepTight(2000);
  }

  public void filterByCustomerName(String text) {
    click(By.cssSelector(
        "button[id$='" + taskWidgetId + ":customer-name-filter:filter-open-form:advanced-filter-command']"));
    WebElement customerNameInput =
        findElementByCssSelector("input[id$='customer-name-filter:filter-input-form:customVarChar5']");
    enterKeys(customerNameInput, text);
    click(By.cssSelector("button[id$='" + taskWidgetId + ":customer-name-filter:filter-input-form:update-command']"));
    Sleeper.sleepTight(2000);
  }

  public void saveFilter(String filterName) {
    click(By.id(taskWidgetId + ":filter-save-action"));
    Sleeper.sleepTight(2000);
    WebElement filterNameInput = findElementById(taskWidgetId + ":filter-save-form:save-filter-set-name-input");
    enterKeys(filterNameInput, filterName);
    click(findElementById(taskWidgetId + ":filter-save-form:filter-save-command"));
    Sleeper.sleepTight(2000);
  }

  public String getFilterName() {
    click(findElementById(taskWidgetId + ":filter-selection-form:filter-name"));
    WebElement descriptionInput = findElementByCssSelector(".user-definied-filter-container");

    return descriptionInput.getText();
  }

  public String getTaskId() {
    String taskTitleCssSelection = "span[id$='task-start-task-id']";
    String taskTitle = findElementByCssSelector(taskTitleCssSelection).getText();
    String taskId = taskTitle.substring(taskTitle.indexOf("#") + 1, taskTitle.indexOf(")"));
    return taskId;
  }

  public boolean hasNoTask() {
    WebElement noTaskMessage = findElementByCssSelector("label[class*='no-task-message']");
    return noTaskMessage.isDisplayed();
  }

  public void startAndCancelTask() {
    findElementByCssSelector("*[id$='0:task-item:task-info']").click();
    waitForElementDisplayed(By.id(TASK_ACTION), true);
    click(findElementByClassName("portal-cancel-button"));
  }

  public boolean isTaskListShown() {
    WebElement taskDetails = findElementByCssSelector("div.js-task-list-container");
    waitForPageLoaded();
    return taskDetails.isDisplayed();
  }

  public String getStateInCompactMode(int index) {
    WebElement taskListElement = findElementById(taskWidgetId + ":task-list-scroller");
    WebElement taskElement = taskListElement.findElement(By.cssSelector(ID_CONTAIN + index + TASK_ITEM));
    WebElement state = taskElement.findElement(By.cssSelector("*[id*='task-start-task-state']"));
    return state.getText().substring(state.getText().indexOf(' ') + 1);
  }

  public void startTaskWithoutUI(int index) {
    WebElement taskListElement = findElementById(taskWidgetId + ":task-list-scroller");
    if (taskListElement.getAttribute(CLASS).contains("compact-mode")) {
      findElementByCssSelector(ID_CONTAIN + index + TASK_ITEM).click();
    } else {
      findElementByCssSelector(ID_END + index + TASK_ITEM_TASK_INFO).click();
    }
  }

  public int getTaskCount() {
    WebElement taskTitleElement = findElementById("task-widget:task-widget-title");
    String title = taskTitleElement.getText();
    return Integer.parseInt(title.substring(title.lastIndexOf("(") + 1, title.length() - 1));
  }

  public boolean isTaskStateDone(int index) {
    try {
      findElementById(String.format(TASK_STATE_OPEN_ID, index));
    } catch (NoSuchElementException e) {
      return false;
    }
    return true;
  }

  public boolean isTaskStateReserved(int index) {
    try {
      findElementById(String.format(TASK_STATE_RESERVED_ID, index));
    } catch (NoSuchElementException e) {
      return false;
    }
    return true;
  }

  public boolean isTaskActionDisplayed(String action, int taskIndex) {
    return isElementDisplayedById(String
        .format("task-widget:task-list-scroller:%d:task-item:task-action:additional-options:%s", taskIndex, action));
  }

  public boolean isTaskResetDisplayed() {
    return isTaskActionDisplayed("task-reset-command", 0);
  }

  public boolean isTaskDelegateDisplayed() {
    return isTaskActionDisplayed("task-delegate-command", 0);
  }

  public boolean isTaskReserverDisplayed() {
    return isTaskActionDisplayed("task-reserve-command", 0);
  }

  public boolean isAdhocSideStepDisplayed() {
    return isElementDisplayed(By.cssSelector("a[id$='adhoc-side-step-item']"));
  }

}
