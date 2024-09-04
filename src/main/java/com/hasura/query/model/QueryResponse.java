package com.hasura.query.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResponse {

	private List<RowSet> rowSets;

    public QueryResponse(List<RowSet> rowSets) {
        this.rowSets = rowSets;
    }

    public List<RowSet> getRowSets() {
        return rowSets;
    }

    public void setRowSets(List<RowSet> rowSets) {
        this.rowSets = rowSets;
    }
}