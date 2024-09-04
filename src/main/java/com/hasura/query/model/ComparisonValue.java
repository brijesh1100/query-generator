package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "column", value = ColumnValue.class),
    @JsonSubTypes.Type(name = "scalar", value = ScalarValue.class),
    @JsonSubTypes.Type(name = "variable", value = VariableValue.class),
})
public abstract class ComparisonValue {

    public abstract String getType(); // Abstract method to be implemented by subclasses

    @JsonProperty("value")  // Use a generic name for value across all subtypes
    private Object value; // Use appropriate type for your scalar values

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

// Column Value
class ColumnValue extends ComparisonValue {
    
    @JsonProperty("column")
    private ComparisonTarget column;

    public ComparisonTarget getColumn() {
        return column;
    }

    public void setColumn(ComparisonTarget column) {
        this.column = column;
    }

    @Override
    public String getType() {
        return "column";
    }
}

// Scalar Value
class ScalarValue extends ComparisonValue {
    
    @Override
    public String getType() {
        return "scalar";
    }
}

// Variable Value
class VariableValue extends ComparisonValue {
    
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
        return "Variable";
    }
}
