package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum OrderDirection {

    @JsonProperty("asc")
    ASC("asc"),

    @JsonProperty("desc")
    DESC("desc");

    private final String value;

    OrderDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}