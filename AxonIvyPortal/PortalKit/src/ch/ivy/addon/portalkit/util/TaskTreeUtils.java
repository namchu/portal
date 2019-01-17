package ch.ivy.addon.portalkit.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.util.TreeUtils;

import ch.ivy.addon.portalkit.bo.TaskNode;
import ch.ivy.addon.portalkit.enums.MenuKind;
import ch.ivy.addon.portalkit.enums.PortalLibrary;
import ch.ivy.addon.portalkit.ivydata.searchcriteria.TaskCategorySearchCriteria;
import ch.ivy.addon.portalkit.service.ApplicationService;
import ch.ivy.addon.portalkit.service.IvyAdapterService;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.workflow.category.CategoryTree;
import ch.ivyteam.ivy.workflow.query.TaskQuery;


/**
 * Provide the utilities related to Task Tree
 * 
 * @author BOLT
 */
public class TaskTreeUtils {

  public static final String DELIMITER = "/";
  
  private static CheckboxTreeNode root;

  private TaskTreeUtils() {}
  
  /**
   * Convert categories of tasks to TreeNode
   * 
   * @param root
   * @param categoryTree
   * @param isRootAllTask
   * @param menuState
   */
  public static void convertToTreeNode(TreeNode root, CategoryTree categoryTree, boolean isRootAllTask, String menuState) {
    for (CategoryTree category : categoryTree.getChildren()) {
      String name = category.getCategory().getName();
      String categoryRawPath = category.getRawPath();
      String nodeType = root.getType() + DELIMITER + category.getCategory().getName(Locale.ENGLISH).replaceAll(" ", "_");
      TreeNode childNode = buildTaskCategoryNode(root, name, nodeType, categoryRawPath, isRootAllTask, menuState);
      root.getChildren().add(childNode);
      if (CollectionUtils.isNotEmpty(category.getChildren())) {
        convertToTreeNode(childNode, category, isRootAllTask, menuState);
      }
    }
  }

  private static TreeNode buildTaskCategoryNode(TreeNode parentNode, String newNodeName, String nodeType, String category, boolean isRootAllTask, String menuState) {
    TaskNode newNodeData = new TaskNode();
    newNodeData.setValue(newNodeName);
    newNodeData.setMenuKind(MenuKind.TASK);
    newNodeData.setCategory(category);
    newNodeData.setRootNodeAllTask(isRootAllTask);
    
    TreeNode newNode = new DefaultTreeNode(nodeType, newNodeData, parentNode);
    if (menuState.contains(nodeType) && isSelectedCategory(menuState, nodeType)) {
      newNode.getParent().setExpanded(true);
    }
    return newNode;
  }

  private static boolean isSelectedCategory(String menuState, String nodeType) {
    return (menuState.indexOf(nodeType) + nodeType.length() == menuState.length())
        ||( menuState.charAt(menuState.indexOf(nodeType) + nodeType.length()) == '/');
  }
  
  public static CheckboxTreeNode buildTaskCategoryCheckboxTreeRoot() {
    if (root != null) {
      return root;
    }
    
    root = buildRoot();
    ApplicationService service = new ApplicationService();
    List<String> involvedApplications = service.findActiveIvyAppsBasedOnConfiguration(Ivy.session().getSessionUserName());
    TaskQuery taskQuery = SubProcessCall.withPath("Functional Processes/BuildTaskQuery")
        .withStartSignature("buildTaskQuery()")
        .call()
        .get("taskQuery", TaskQuery.class);
    CategoryTree allTaskCategoryTree = findAllTaskCategoryTree(involvedApplications, taskQuery);
    convertToCheckboxTreeNode(root, allTaskCategoryTree);
    sortNode(root);
    return root;
  }
  
  private static CategoryTree findAllTaskCategoryTree(List<String> involvedApplications, TaskQuery taskQuery) {
    Map<String, Object> params = new HashMap<>();
    TaskCategorySearchCriteria criteria = new TaskCategorySearchCriteria();
    criteria.setCustomTaskQuery(taskQuery);
    criteria.setApps(involvedApplications);
    params.put("taskCategorySearchCriteria", criteria);
    Map<String, Object> response = IvyAdapterService.startSubProcess(
        "findCategoriesByCriteria(ch.ivy.addon.portalkit.ivydata.searchcriteria.TaskCategorySearchCriteria)", params,
        Arrays.asList(PortalLibrary.PORTAL_TEMPLATE.getValue()));
    CategoryTree allTaskCategoryTree = (CategoryTree) response.get("categoryTree");
    return allTaskCategoryTree;
  }
  
  private static void convertToCheckboxTreeNode(CheckboxTreeNode root, CategoryTree categoryTree) {
    String nodeType = "default";
    for (CategoryTree category : categoryTree.getChildren()) {
      String name = category.getCategory().getName();
      String categoryRawPath = category.getRawPath();
      CheckboxTreeNode childNode = buildTaskCategoryCheckBoxTreeNode(root, name, nodeType, categoryRawPath);
      root.getChildren().add(childNode);
      if (CollectionUtils.isNotEmpty(category.getChildren())) {
        convertToCheckboxTreeNode(childNode, category);
      }
    }
  }
  
  private static void sortNode(TreeNode node) {
    Comparator<TreeNode> comparator = (firstNode, secondNode) -> {
      TaskNode firstNodeData = (TaskNode) firstNode.getData();
      TaskNode secondNodeData = (TaskNode) secondNode.getData();
      return firstNodeData.getValue().compareToIgnoreCase(secondNodeData.getValue());
    };
    TreeUtils.sortNode(node, comparator);
  }
  
  private static CheckboxTreeNode buildTaskCategoryCheckBoxTreeNode(CheckboxTreeNode parentNode, String newNodeName, String nodeType, String category) {
    List<TreeNode> childNodes = parentNode.getChildren();
    for (TreeNode childNode : childNodes) {
      TaskNode childNodeData = (TaskNode) childNode.getData();
      if (category.equalsIgnoreCase(childNodeData.getValue())) {
        return (CheckboxTreeNode) childNode;
      }
    }

    TaskNode nodeData = buildTaskNodeFrom(newNodeName, category);
    CheckboxTreeNode checkboxTreeNode = new CheckboxTreeNode(nodeType, nodeData, parentNode);
    checkboxTreeNode.setExpanded(true);
    checkboxTreeNode.setSelected(false);
    return checkboxTreeNode;
  }

  private static TaskNode buildTaskNodeFrom(String name, String category) {
    TaskNode nodeData = new TaskNode();
    nodeData.setValue(name);
    nodeData.setMenuKind(MenuKind.TASK);
    nodeData.setCategory(category);
    nodeData.setRootNodeAllTask(false);
    nodeData.setFirstCategoryNode(false);
    return nodeData;
  }
  
  private static CheckboxTreeNode buildRoot() {
    TaskNode nodeData = new TaskNode();
    nodeData.setValue(StringUtils.EMPTY);
    nodeData.setMenuKind(MenuKind.TASK);
    nodeData.setCategory(StringUtils.EMPTY);
    nodeData.setRootNodeAllTask(true);
    nodeData.setFirstCategoryNode(true);
    CheckboxTreeNode checkboxTreeNode = new CheckboxTreeNode(StringUtils.EMPTY, nodeData, null);
    checkboxTreeNode.setExpanded(true);
    checkboxTreeNode.setSelected(false);
    return checkboxTreeNode;
  }

  public static String getLastCategoryFromCategoryPath(String categoryPath) {
    if (!StringUtils.isBlank(categoryPath)) {
      String[] categories = categoryPath.split("/");
      return categories[categories.length - 1];
    }
    return "";
  }
}
