package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum RelationshipArgument {

	@JsonProperty("Variable")
	VARIABLE("Variable") {
		@JsonProperty("name")
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	},

	@JsonProperty("Literal")
	LITERAL("Literal") {
		@JsonProperty("value")
		private Object value; // Use appropriate type based on your data

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	},

	@JsonProperty("Column")
	COLUMN("Column") {
		@JsonProperty("name")
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	};

	private final String type;

	RelationshipArgument(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}