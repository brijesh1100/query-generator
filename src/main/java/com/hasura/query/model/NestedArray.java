package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NestedArray {

    /**
     * Fields to be retrieved for elements within the array
     */
    @JsonProperty("fields")
    private NestedField fields;

    public NestedField getFields() {
        return fields;
    }

    public void setFields(NestedField fields) {
        this.fields = fields;
    }
}