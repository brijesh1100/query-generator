package com.hasura.query.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NestedObject {

    @JsonProperty("fields")
    private HashMap<String, Field> fields;

    public HashMap<String, Field> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Field> fields) {
        this.fields = fields;
    }
}
