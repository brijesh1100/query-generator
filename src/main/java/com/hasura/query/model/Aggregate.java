package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "column_count", value = ColumnCountAggregate.class),
    @JsonSubTypes.Type(name = "single_column", value = SingleColumnAggregate.class),
    @JsonSubTypes.Type(name = "star_count", value = StarCountAggregate.class),
})
public abstract class Aggregate {

	// Abstract method to be implemented by subclasses
    public abstract String getType(); 

 // Consistent name for column across subtypes that use it
    @JsonProperty("column") 
    private String column;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}

// Column Count Aggregate
class ColumnCountAggregate extends Aggregate {
    
    @JsonProperty("distinct")
    private boolean distinct;

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public String getType() {
        return "column_count";
    }
}

// Single Column Aggregate
class SingleColumnAggregate extends Aggregate {
    
    @JsonProperty("function")
    private String function;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String getType() {
        return "single_column";
    }
}

// Star Count Aggregate
class StarCountAggregate extends Aggregate {
    
    @Override
    public String getType() {
        return "star_count";
    }
}
