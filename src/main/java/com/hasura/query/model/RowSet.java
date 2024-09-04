package com.hasura.query.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RowSet {

    @JsonProperty("aggregates")
    private final Map<String, Object> aggregates;

    @JsonProperty("rows")
    private final List<Map<String, RowFieldValue>> rows;

    public RowSet(Map<String, Object> aggregates, List<Map<String, RowFieldValue>> rows) {
        this.aggregates = aggregates;
        this.rows = rows;
    }

    public Map<String, Object> getAggregates() {
        return aggregates;
    }

    public List<Map<String, RowFieldValue>> getRows() {
        return rows;
    }
}