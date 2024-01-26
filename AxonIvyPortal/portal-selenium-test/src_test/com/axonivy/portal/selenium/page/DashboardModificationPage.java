package com.axonivy.portal.selenium.page;

import static com.codeborne.selenide.Selenide.$;

import java.util.List;

import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;

import com.axonivy.portal.selenium.common.WaitHelper;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

public class DashboardModificationPage extends TemplatePage {

  @Override
  protected String getLoadedLocator() {
    return "#dashboard-modification-component\\:dashboard-modification-container";
  }

  public ElementsCollection getDashboardRows() {
    SelenideElement dashboardTable = $("tbody[id='dashboard-modification-component:dashboard-table_data']").shouldBe(appear, DEFAULT_TIMEOUT);
    return dashboardTable.$$("tr:not(.ui-datatable-empty-message)");
  }

  public SelenideElement getDashboardRowByName(String dashboardName) {
    for(SelenideElement dashboardRow : getDashboardRows()) {
      if(dashboardRow.$("td:nth-child(1)").getText().contentEquals(dashboardName)) {
        return dashboardRow;
      }
    }
    return null;
  }

  private SelenideElement getEditDashboardDialog() {
    return $("div[id$='configuration-dashboard-detail-dialog']");
  }

  public NewDashboardDetailsEditPage navigateToEditDashboardDetailsByName(String dashboardName) {
    SelenideElement dashboardRow = getDashboardRowByName(dashboardName);
    if (dashboardRow != null) {
      dashboardRow.$("[id$=':configure-dashboard']").click();
      NewDashboardDetailsEditPage newDashboardDetailsEditPage = new NewDashboardDetailsEditPage();
      return newDashboardDetailsEditPage;
    }
    return null;
  }

  public void clickEditDashboardByName(String dashboardName) {
    SelenideElement dashboardRow = getDashboardRowByName(dashboardName);
    dashboardRow.$("[id$=':edit']").click();
    getEditDashboardDialog().shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public void clickDeleteDashboardByName(String dashboardName) {
    SelenideElement dashboardRow = getDashboardRowByName(dashboardName);
    dashboardRow.$("[id$=':delete-dashboard']").click();
    SelenideElement deleteConfirmDialog = $("[id$=':remove-dashboard-dialog']").shouldBe(appear, DEFAULT_TIMEOUT);
    deleteConfirmDialog.$("button[id$=':remove-dashboard-button']").shouldBe(appear, DEFAULT_TIMEOUT).shouldBe(getClickableCondition()).click();
    deleteConfirmDialog.shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  public void editDashboardInfo(String newName, String newDescription, List<String> permissions) {
    SelenideElement editDashboardDialog = getEditDashboardDialog();
    editDashboardDialog.$("input[id$=':dashboard-title']").clear();
    editDashboardDialog.$("input[id$=':dashboard-title']").sendKeys(newName);
    editDashboardDialog.$("input[id$=':dashboard-description']").clear();
    editDashboardDialog.$("input[id$=':dashboard-description']").sendKeys(newDescription);

    ElementsCollection selectedPermissions = editDashboardDialog.$("div[id$=':dashboard-permission']").$$("li.ui-state-active");
    if (!selectedPermissions.isEmpty()) {
      for(SelenideElement permission : selectedPermissions) {
        permission.$("span.ui-icon-close").click();
      }
    }

    editDashboardDialog.$("div[id$=':dashboard-permission']").$("button.ui-autocomplete-dropdown").click();
    WaitHelper.waitNumberOfElementsToBe(WebDriverRunner.getWebDriver(), By.cssSelector("span[id$=':dashboard-permission_panel']"), 1);
    $("span[id$=':dashboard-permission_panel']").$$("tr.ui-autocomplete-item").asDynamicIterable().forEach(item -> {
      for(String permissionName : permissions) {
        if (item.$("td").getText().contains(permissionName)) {
          item.click();
        }
      }
    });
    editDashboardDialog.$("button[id$='dashboard-detail-save-button']").click();
    editDashboardDialog.shouldBe(disappear, DEFAULT_TIMEOUT);
  }
  
  public void editPrivateDashboardInfo(String newName, String newDescription) {
    SelenideElement editDashboardDialog = getEditDashboardDialog();
    editDashboardDialog.$("input[id$=':dashboard-title']").clear();
    editDashboardDialog.$("input[id$=':dashboard-title']").sendKeys(newName);
    editDashboardDialog.$("input[id$=':dashboard-description']").clear();
    editDashboardDialog.$("input[id$=':dashboard-description']").sendKeys(newDescription);
    
    editDashboardDialog.$("button[id$='dashboard-detail-save-button']").click();
    editDashboardDialog.shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  public NewDashboardPage navigateToNewDashboardPage() {
    $("a[id='dashboard-form:cancel-button']").click();
    NewDashboardPage newDashboardPage = new NewDashboardPage();
    newDashboardPage.waitForAbsencesGrowlMessageDisplay();
    return newDashboardPage;
  }

  public SelenideElement getDashboardCellByNameAndPosition(String dashboardName, int position) {
    SelenideElement dashboard = getDashboardRowByName(dashboardName);
    dashboard.shouldBe(appear);
    return dashboard.$("td:nth-child(" + position + ")");
  }

  public SelenideElement getDashboardExportButtonOfDashboard(String dashboardName) {
    SelenideElement dashboard = getDashboardRowByName(dashboardName);
    dashboard.shouldBe(appear);
    return dashboard.$("td:last-child button[id $=':export-dashboard']");
  }
  
  public SelenideElement getDashboardShareLinkButton() {
    return $("button[id$='share-dashboard']");
  }
  
  public void getDashboardShareLinkDialog() {
    getDashboardShareLinkButton().shouldBe(appear, DEFAULT_TIMEOUT).shouldBe(getClickableCondition()).click();
    $("div[id$=':share-dashboard-dialog']").shouldBe(appear, DEFAULT_TIMEOUT);
  }
}
