package com.hasura.query.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "column", value = ColumnField.class),
    @JsonSubTypes.Type(name = "relationship", value = RelationshipField.class),
})
public abstract class Field {

    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

// TODO revisit in case of defining separate classes for Column and Relationship fields 
class ColumnField extends Field {

    @JsonProperty("column")
    private String column;

    @JsonProperty("fields")
    private NestedField fields;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public NestedField getFields() {
		return fields;
	}

	public void setFields(NestedField fields) {
		this.fields = fields;
	}

}

class RelationshipField extends Field {

    @JsonProperty("query")
    private Query query;

    @JsonProperty("relationship")
    private String relationship;

    @JsonProperty("arguments")
    private HashMap<String, RelationshipArgument> arguments;

}