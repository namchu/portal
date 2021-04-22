package portal.guitest.page;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.jayway.awaitility.Awaitility;
import com.jayway.awaitility.Duration;

public class AbsencePage extends TemplatePage {

	@Override
	protected String getLoadedLocator() {
		return "id('absences-management-form')";
	}

	@SuppressWarnings("deprecation")
  public NewAbsencePage openNewAbsenceDialog() {
		String selector = "button[id*='add-absence']";
		waitForElementDisplayed(By.cssSelector(selector), true);
		clickByCssSelector(selector);
		waitAjaxIndicatorDisappear();
		return new NewAbsencePage();
	}

	public int countAbsences() {
		waitForElementDisplayed(By.cssSelector("a[id*='absence-table']"), true);
		return findListElementsByCssSelector("td.absences-table-action-column").size();
	}

	@SuppressWarnings("deprecation")
  public void showAbsencesInThePast(boolean shown) {
		WebElement checkBox = findElementByCssSelector("input[id*='show-absence-in-the-past']");
		boolean checkBoxSelected = checkBox.isSelected();
		if (checkBoxSelected != shown) {
			clickByCssSelector("div[id*='show-absence-in-the-past'] div.ui-chkbox-box");
			waitAjaxIndicatorDisappear();
		}
	}

  public String getMyDeputy() {
    String deputiesSelector = "a[id$='absences-management-form:substitute-table:0:selected-deputies-link']";
    waitForElementDisplayed(By.cssSelector(deputiesSelector), true);
    return findElementByCssSelector(deputiesSelector).getText();
  }
  
	public List<String> getIAMDeputyFor() {
    List<WebElement> noteAuthorElements = findListElementsByCssSelector("tbody[id*='substitution-table_data'] > tr > td");
    return noteAuthorElements.stream().map(w -> w.getText()).collect(Collectors.toList());
  }
  
  public void setDeputy(List<String> fullNames) {
    try {
      clickSelectedDeputiesLink();
    } catch (Exception e) {
      clickSelectedDeputiesLink();
    }
    for (String fullName : fullNames) {
      selectDeputy(fullName);
    }
    click(By.id("deputy-selection-form:save-deputy-button"));
    waitForJQueryAndPrimeFaces(DEFAULT_TIMEOUT);
	}

  private void clickSelectedDeputiesLink() {
    waitForJQueryAndPrimeFaces(DEFAULT_TIMEOUT);
    String deputiesSelector = "a[id$='absences-management-form:substitute-table:0:selected-deputies-link']";
    waitForElementDisplayed(By.cssSelector(deputiesSelector), true);
    Awaitility.await().atMost(new Duration(10, TimeUnit.SECONDS)).until(() -> {
      try {
        WebElement deputiesLink = findElementByCssSelector(deputiesSelector);
        deputiesLink.click();
      } catch (Exception e) {
        // to avoid choose-deputy-dialog displays before deputiesLink is clicked
      }
      return findElementById("choose-deputy-dialog").isDisplayed();
    });
  }

  private void selectDeputy(String responsible) {
    type(By.id("deputy-selection-form:user-selection-component:user-selection_input"), responsible);
    waitForElementDisplayed(By.id("deputy-selection-form:user-selection-component:user-selection_panel"), true);
    click(By.xpath("//*[@id='deputy-selection-form:user-selection-component:user-selection_panel']/table/tbody/tr"));
    waitForJQueryAndPrimeFaces(DEFAULT_TIMEOUT);
    click(By.id("deputy-selection-form:add-deputy-button"));
    waitForJQueryAndPrimeFaces(DEFAULT_TIMEOUT);
  }

  public void setSubstitutedByAdmin(String substitutedUser) {
		String selectedUserInput = "input[id$=':user-absence-selection-component:user-absence_input']";
		waitForElementDisplayed(By.cssSelector(selectedUserInput), true);
		WebElement substituted = findElementByCssSelector(selectedUserInput);
		substituted.clear();
		substituted.sendKeys(substitutedUser);
		waitForJQueryAndPrimeFaces(DEFAULT_TIMEOUT);
		String itemSelector = "tr[data-item-label*='" + substitutedUser + "']";
		waitForElementDisplayed(By.cssSelector(itemSelector), true);
		clickByCssSelector(itemSelector);
		waitForJQueryAndPrimeFaces(DEFAULT_TIMEOUT);
	}
	
	public String getSubstitutedByAdmin(int rowIndex) {
	  WebElement deputyForTable = findElementByCssSelector("[id$=':substitution-table']");
	  WebElement deputyFor = deputyForTable.findElement(By.cssSelector(String.format("[id$='%d:substitution-for']", rowIndex)));
	  return deputyFor.getText();
	}
	
  @SuppressWarnings("deprecation")
  public void saveSubstitute() {
    clickByCssSelector("button[id$='absences-management-form:save-substitute']");
    waitAjaxIndicatorDisappear();
  }
  
  public WebElement getAbsenceForm() {
    return findElementById("absences-management-form");
  }

  public WebElement getAddAbsenceDialog() {
    return findElementById("absence-dialog");
  }
  
  public void waitForAbsencesGrowlMessageDisplay() {
    WebElement growlMessage = findElementByCssSelector("div[id$='absences-management-form:absences-management-info_container']");
    waitForElementDisplayed(growlMessage.findElement(By.className("ui-growl-item-container")), true, 5);
  }
}
