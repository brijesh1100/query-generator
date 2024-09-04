package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum UnaryComparisonOperator {

    @JsonProperty("is_null")
    IS_NULL("is_null");

    private final String value;

    UnaryComparisonOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}