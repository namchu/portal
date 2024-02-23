package com.axonivy.portal.migration.dashboard.converter.v113;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.portal.bo.jsonversion.AbstractJsonVersion;
import com.axonivy.portal.bo.jsonversion.DashboardJsonVersion;
import com.axonivy.portal.enums.dashboard.filter.FilterOperator;
import com.axonivy.portal.migration.common.IJsonConverter;
import com.axonivy.portal.migration.common.search.JsonWidgetSearch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import ch.ivy.addon.portalkit.enums.DashboardColumnType;
import ch.ivy.addon.portalkit.enums.DashboardStandardTaskColumn;
import ch.ivy.addon.portalkit.enums.DashboardWidgetType;

public class DashboardTaskWidgetConverter implements IJsonConverter {

  private static final String NO_CATEGORY = "[No Category]";

  @Override
  public AbstractJsonVersion version() {
    return new DashboardJsonVersion("11.3.0");
  }

  @Override
  public void convert(JsonNode jsonNode) {
    List<JsonNode> taskWidgets = new JsonWidgetSearch(jsonNode).type(DashboardWidgetType.TASK.name()).findWidgets();

    for (JsonNode taskWidget : taskWidgets) {
      ArrayNode columns = Optional.ofNullable(taskWidget.get("columns")).filter(JsonNode::isArray)
          .map(ArrayNode.class::cast).get();

      columns.elements().forEachRemaining(col -> {
        DashboardStandardTaskColumn field = DashboardStandardTaskColumn.findBy(col.get("field").asText());
        if (field == null) {
          migrateCustomColumn(taskWidget, col);
        } else {
          migrateStandardColumn(taskWidget, col, field);
        }
        removeOldFiltersFields(col);
      });
    }
  }

  private void migrateCustomColumn(JsonNode taskWidget, JsonNode col) {
    /**
     * will be handle in the next story for CustomField
     */
  }

  private void migrateStandardColumn(JsonNode taskWidget, JsonNode col, DashboardStandardTaskColumn field) {
    switch (field) {
    case CREATED -> {
      convertDateFilters(initFilterNode(taskWidget), col.get("filterFrom"), col.get("filterTo"),
          DashboardStandardTaskColumn.CREATED.getField(), true);
    }
    case EXPIRY -> {
      convertDateFilters(initFilterNode(taskWidget), col.get("filterFrom"), col.get("filterTo"),
          DashboardStandardTaskColumn.EXPIRY.getField(), true);
    }
    case NAME -> {
      convertStringFilters(initFilterNode(taskWidget), col.get("filter"), DashboardStandardTaskColumn.NAME.getField(),
          true);
    }
    case DESCRIPTION -> {
      convertStringFilters(initFilterNode(taskWidget), col.get("filter"),
          DashboardStandardTaskColumn.DESCRIPTION.getField(), true);
    }
    case RESPONSIBLE -> {
      convertListFilter(initFilterNode(taskWidget), (ArrayNode) col.get("filterList"),
          DashboardStandardTaskColumn.RESPONSIBLE.getField(), true);
    }
    case STATE -> {
      convertListFilter(initFilterNode(taskWidget), (ArrayNode) col.get("filterList"),
          DashboardStandardTaskColumn.STATE.getField(), true);
    }
    case PRIORITY -> {
      convertListFilter(initFilterNode(taskWidget), (ArrayNode) col.get("filterList"),
          DashboardStandardTaskColumn.PRIORITY.getField(), true);
    }
    case CATEGORY -> {
      convertCategoryFilter(initFilterNode(taskWidget), (ArrayNode) col.get("filterList"),
          DashboardStandardTaskColumn.CATEGORY.getField());
    }
    case APPLICATION -> {
      convertListFilter(initFilterNode(taskWidget), (ArrayNode) col.get("filterList"),
          DashboardStandardTaskColumn.APPLICATION.getField(), true);
    }
    default -> {
    }
    }
  }

  private ArrayNode initFilterNode(JsonNode widget) {
    if (widget.get("filters") == null) {
      return ((ObjectNode) widget).putArray("filters");
    }
    return Optional.ofNullable(widget.get("filters")).filter(JsonNode::isArray).map(ArrayNode.class::cast).get();
  }

  private void convertListFilter(ArrayNode filters, ArrayNode filterList, String field, boolean isStandardField) {
    if (filterList == null || filterList.size() == 0) {
      return;
    }

    // If the new complex filters has filter for the same field, skip migrate
    filters.elements().forEachRemaining(filter -> {
      if (filter.get("field").asText().contentEquals(field)) {
        return;
      }
    });

    DashboardColumnType type = isStandardField ? DashboardColumnType.STANDARD : DashboardColumnType.CUSTOM;
    ObjectNode newFilterNode = filters.addObject();
    newFilterNode.set("field", new TextNode(field));
    newFilterNode.set("type", new TextNode(type.getType()));
    newFilterNode.set("operator", new TextNode(FilterOperator.IN.name()));

    ArrayNode valuesNode = newFilterNode.putArray("values");
    filterList.elements().forEachRemaining(node -> {
      valuesNode.add(new TextNode(node.asText()));
    });
  }

  private void convertDateFilters(ArrayNode filters, JsonNode filterFrom, JsonNode filterTo, String field, boolean isStandardField) {
    boolean isEmptyFilterFrom = filterFrom == null || StringUtils.isBlank(filterFrom.asText());
    boolean isEmptyFilterTo = filterTo == null || StringUtils.isBlank(filterTo.asText());

    if (isEmptyFilterFrom && isEmptyFilterTo) {
      return;
    }

    filters.elements().forEachRemaining(filter -> {
      if (filter.get("field").asText().contentEquals(field)) {
        return;
      }
    });

    DashboardColumnType type = isStandardField ? DashboardColumnType.STANDARD : DashboardColumnType.CUSTOM;
    ObjectNode newFilterNode = filters.addObject();
    newFilterNode.set("field", new TextNode(field));
    newFilterNode.set("type", new TextNode(type.getType()));
    newFilterNode.set("operator", new TextNode(FilterOperator.BETWEEN.getOperator()));

    if (!isEmptyFilterFrom) {
      newFilterNode.set("from", new TextNode(filterFrom.asText()));
    }

    if (!isEmptyFilterTo) {
      newFilterNode.set("to", new TextNode(filterTo.asText()));
    }
  }

  private void convertStringFilters(ArrayNode filters, JsonNode filterText, String field, boolean isStandardField) {
    if (filterText == null || StringUtils.isBlank(filterText.asText())) {
      return;
    }

    // If the new complex filters has filter for the same field, skip migrate
    filters.elements().forEachRemaining(filter -> {
      if (filter.get("field").asText().contentEquals(field)) {
        return;
      }
    });

    DashboardColumnType type = isStandardField ? DashboardColumnType.STANDARD : DashboardColumnType.CUSTOM;
    ObjectNode newFilterNode = filters.addObject();
    newFilterNode.set("field", new TextNode(field));
    newFilterNode.set("type", new TextNode(type.getType()));
    newFilterNode.set("operator", new TextNode(FilterOperator.CONTAINS.getOperator()));

    ArrayNode valuesNode = newFilterNode.putArray("values");
    valuesNode.add(new TextNode(filterText.asText()));
  }

  private void convertCategoryFilter(ArrayNode filters, ArrayNode filterList, String field) {
    if (filterList == null || filterList.size() == 0) {
      return;
    }

    // If the new complex filters has filter for the same field, skip migrate
    filters.elements().forEachRemaining(filter -> {
      if (filter.get("field").asText().contentEquals(field)) {
        return;
      }
    });

    ObjectNode newFilterNode = filters.addObject();
    newFilterNode.set("field", new TextNode(field));
    newFilterNode.set("type", new TextNode(DashboardColumnType.STANDARD.getType()));
    newFilterNode.set("operator", new TextNode(FilterOperator.IN.name()));

    ArrayNode valuesNode = newFilterNode.putArray("values");
    filterList.elements().forEachRemaining(node -> {
      if (node.asText().contentEquals(NO_CATEGORY) && CollectionUtils.size(filterList.elements()) == 1) {
        // If category filter only have one option: No category
        // Choose operator: No category
        newFilterNode.set("operator", new TextNode(FilterOperator.NO_CATEGORY.name()));
      } else if (!node.asText().contentEquals(NO_CATEGORY)) {
        // Otherwise add all selected categories beside "No category"
        valuesNode.add(new TextNode(node.asText()));
      }
    });
  }

  private void removeOldFiltersFields(JsonNode column) {
    ObjectNode columnObj = (ObjectNode) column;
    columnObj.remove("filter");
    columnObj.remove("filterFrom");
    columnObj.remove("filterTo");
    columnObj.remove("filterList");
  }

}
