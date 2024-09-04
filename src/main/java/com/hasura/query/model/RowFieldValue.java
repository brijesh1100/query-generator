package com.hasura.query.model;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RowFieldValue {

	private final Object value;

    public RowFieldValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public Optional<RowSet> asRowSet() {
        if (value instanceof RowSet) {
            return Optional.of((RowSet) value);
        } else {
            return Optional.empty();
        }
    }
}