package ch.ivy.addon.portalkit.casefilter;

import ch.ivyteam.ivy.workflow.query.CaseQuery;

public abstract class CaseFilter {
  protected static final String ALL = "All";
  protected static final String COMMA = ", ";
  protected static final String GREATER_EQUAL = ">= %s";
  protected static final String LESS_EQUAL = "<= %s";
  protected static final String DASH = "%s - %s";
  protected static final String DOUBLE_QUOTES = "\"%s\"";

  /**
   * <p>
   * The string label is displayed in filter item and checkbox filter item.
   * </p>
   * <p>
   * <b>Example: </b> <code><pre>
   * State: DONE, RUNNING
   * Label is "state", and value is "DONE, RUNNING".
   * </pre></code>
   * </p>
   * 
   * @return label: String
   */
  public abstract String label();

  /**
   * <p>
   * The string value is displayed in filter item.
   * </p>
   * <p>
   * <b>Example: </b> <code><pre>
   * State: DONE, RUNNING
   * Label is "state", and value is "DONE, RUNNING".
   * </pre></code>
   * </p>
   * <p>
   * <b>Format: </b> <br/>
   * To make it consistent on UI, use the constants in TaskFilter to display.<br/>
   * Return the constant ALL if filtered values are empty or full list of suggestion selections.
   * </p>
   * 
   * @return value: String
   */
  public abstract String value();

  /**
   * <p>
   * Build CaseQuery with filtered values.
   * </p>
   * <p>
   * <b>Example: </b> <code><pre>
   * if (StringUtils.isBlank(description)) {
   *   return null;
   * }
   * String containingKeyword = String.format("%%%s%%", description.trim());
   * return CaseQuery.create().where().description().isLikeIgnoreCase(containingKeyword);
   * </pre></code>
   * </p>
   * 
   * @return must be null if filtered values are empty.
   */
  public abstract CaseQuery buildQuery();

  /**
   * <p>
   * Set filtered variables to new values (mostly empty) after close/deselect filter item on UI.
   * </p>
   * <p>
   * <b>Example: </b> <code><pre>
   * In DescriptionFilter: description = "";
   * In StateFilter: selectedStates = Arrays.asList(CaseState.DONE, CaseState.RUNNING);
   * </pre></code>
   * </p>
   */
  public abstract void resetValues();

  /**
   * <p>
   * Override this method if need to validate filtered values. If values are incorrect, use the
   * methods: validationFailed and addMessage
   * </p>
   * <p>
   * <b>Example: </b> <code>
   * <pre>
   * String message = ...;
   * FacesContext.getCurrentInstance().validationFailed();
   * FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
   * </pre>
   * </code>
   * </p>
   */
  public void validate() {};
}
