package com.hasura.query.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "column", value = ColumnTargetType.class),
    @JsonSubTypes.Type(name = "single_column_aggregate", value = SingleColumnAggregateTarget.class),
    @JsonSubTypes.Type(name = "star_count_aggregate", value = StarCountAggregateTarget.class),
})
public abstract class OrderByTarget {

    public abstract String getType(); // Abstract method to be implemented by subclasses

    @JsonProperty("path") // Consistent name for path across all subtypes
    private List<PathElement> path;

    public List<PathElement> getPath() {
        return path;
    }

    public void setPath(List<PathElement> path) {
        this.path = path;
    }
}

// Column Target (Reuse existing class)
class ColumnTargetType extends OrderByTarget {
    
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return "column";
    }
}

// Single Column Aggregate Target
class SingleColumnAggregateTarget extends OrderByTarget {
    
    @JsonProperty("column")
    private String column;

    @JsonProperty("function")
    private String function;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String getType() {
        return "single_column_aggregate";
    }
}

// Star Count Aggregate Target
class StarCountAggregateTarget extends OrderByTarget {
    
    @Override
    public String getType() {
        return "star_count_aggregate";
    }
}
