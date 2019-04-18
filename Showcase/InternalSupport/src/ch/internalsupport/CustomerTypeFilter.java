package ch.internalsupport;

import static ch.ivy.addon.portalkit.constant.CustomFields.CUSTOM_VARCHAR_FIELD1;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ch.ivy.addon.portalkit.taskfilter.TaskFilter;
import ch.ivyteam.ivy.workflow.query.TaskQuery;

public class CustomerTypeFilter extends TaskFilter {

  private String selectedCustomerType;
  private List<String> customerTypes = Arrays.asList(ALL, "Exterior", "Interior");

  @Override
  public String label() {
    return "Customer type";
  }

  @Override
  public String value() {
    if (StringUtils.isBlank(selectedCustomerType)) {
      selectedCustomerType = ALL;
    }
    
    return !ALL.equals(selectedCustomerType) ? String.format(
        DOUBLE_QUOTES, selectedCustomerType) : ALL;
  }

  @Override
  public TaskQuery buildQuery() {
    if (StringUtils.isBlank(selectedCustomerType) || ALL.equals(selectedCustomerType)) {
      return null;
    }

    String containingKeyword = String.format("%%%s%%", selectedCustomerType.trim());
    return TaskQuery.create().where().customField().stringField(CUSTOM_VARCHAR_FIELD1).isLikeIgnoreCase(containingKeyword);
  }

  @Override
  public void resetValues() {
    selectedCustomerType = "";
  }

  public String getSelectedCustomerType() {
    return selectedCustomerType;
  }

  public void setSelectedCustomerType(String selectedCustomerType) {
    this.selectedCustomerType = selectedCustomerType;
  }

  public List<String> getCustomerTypes() {
    return customerTypes;
  }

  public void setCustomerTypes(List<String> customerTypes) {
    this.customerTypes = customerTypes;
  }

}
