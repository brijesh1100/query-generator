package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(name = "variable", value = VariableArgument.class),
		@JsonSubTypes.Type(name = "literal", value = LiteralArgument.class), })
public abstract class Argument {

	// Abstract method to be implemented by subclasses
	public abstract String getType(); 

	@JsonProperty("value") // Consistent name for value across all subtypes
	private Object value; // Use appropriate type for your literal values

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}

// Variable Argument
class VariableArgument extends Argument {

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
		return "variable";
	}
}

// Literal Argument
class LiteralArgument extends Argument {

	@Override
	public String getType() {
		return "literal";
	}
}
