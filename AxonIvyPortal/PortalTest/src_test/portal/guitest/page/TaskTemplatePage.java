package portal.guitest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TaskTemplatePage extends TemplatePage {

  @Override
  protected String getLoadedLocator() {
    return "id('side-steps-menu')";
  }

  public void openStatusTab() {
    String statusTabXpath = "//a[@href='#status-tab']";
    waitForElementDisplayed(By.xpath(statusTabXpath), true);
    click(findElementByXpath(statusTabXpath));
  }

  public boolean containsCaseDetails() {
    WebElement caseDetails = findElementByCssSelector("div[id$='case-item']");
    return caseDetails.isDisplayed();
  }

  public void addNewNote(String content) {
    String addNoteButtonId = "case-item:history:add-note-command";
    String addNoteDialogId = "case-item:history:add-note-dialog";
    findElementById(addNoteButtonId).click();
    waitForElementDisplayed(By.cssSelector("div[id^='" + addNoteDialogId + "']"), true);
    WebElement addNoteDialog = findElementByCssSelector("div[id^='" + addNoteDialogId + "']");
    addNoteDialog.findElement(By.cssSelector("textarea[id$='note-content']")).sendKeys(content);
    addNoteDialog.findElement(By.cssSelector("button[id$='save-add-note-command']")).click();
    waitAjaxIndicatorDisappear();
  }

  public void openDocumentUploadingDialog() {
    findElementById("case-item:document:add-document-command").click();
    String documentUploadingDialogId = "case-item:document:document-upload-dialog";
    waitForElementDisplayed(By.cssSelector("[id^='" + documentUploadingDialogId +"']"), true);
  }

  public Boolean isDocumentUploadingDialogDisplayed() {
    String documentUploadingDialogId = "case-item:document:document-upload-dialog";
    return findElementByCssSelector("[id^='" + documentUploadingDialogId +"']").isDisplayed();
  }

  public TaskWidgetPage openFinishedTaskInHistoryArea() {
    String firstFinishedTaskCssSelector = "div[id='case-item:history:case-histories'] > div > a";
    return openATaskInCaseDetails(firstFinishedTaskCssSelector);
  }

  public TaskWidgetPage openFirstRelatedTaskInHistoryArea() {
    String firstFinishedTaskCssSelector = "div[id='case-item:related-tasks'] a";
    return openATaskInCaseDetails(firstFinishedTaskCssSelector);
  }

  public TaskWidgetPage openATaskInCaseDetails(String taskCssSelector) {
    WebElement task = findElementByCssSelector(taskCssSelector);
    task.click();
    return new TaskWidgetPage();
  }

  public int countNoteItems() {
    return findListElementsByCssSelector("a[id$='note-content']").size();
  }

  public int countHistoryItems() {
    return findListElementsByCssSelector("a[id$='show-task-note-link']")
        .size();
  }

  public int countRelatedTasks() {
    return findListElementsByCssSelector("div[id='case-item:related-tasks'] a").size();
  }

  public void openSideStepMenu() {
    String sideStepId = "side-steps-menu";
    waitForElementDisplayed(By.id(sideStepId), true);
    click(findElementById(sideStepId));
  }

  public int countSideSteps() {
    String sideStepPanelId = "side-steps-panel";
    waitForElementDisplayed(By.id(sideStepPanelId), true);
    return findListElementsByCssSelector("a[id$='side-step-item']").size();
  }
  
  public void clickCancelButton() {
    driver.findElement(By.className("portal-cancel-button")).click();
  }
  
  public void showNoteHistory() {
    click(driver.findElement(By.cssSelector("a[id$='show-more-note-link']")));
  }
  
  public String getCaseName(){
    return findElementById("case-item:case-header:case-header-name-cell").getText();
  }
}
