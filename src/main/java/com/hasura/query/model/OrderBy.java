package com.hasura.query.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBy {

    /**
     * Elements to order by, in priority order
     */
    @JsonProperty("elements")
    private List<OrderByElement> elements;

    public List<OrderByElement> getElements() {
        return elements;
    }

    public void setElements(List<OrderByElement> elements) {
        this.elements = elements;
    }
}
