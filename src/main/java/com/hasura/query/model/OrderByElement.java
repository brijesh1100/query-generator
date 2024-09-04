package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderByElement {

    /**
     * Direction of the ordering (ASC or DESC)
     */
    @JsonProperty("order_direction")
    private OrderDirection orderDirection;

    /**
     * The target field to order by
     */
    @JsonProperty("target")
    private OrderByTarget target;

    public OrderDirection getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(OrderDirection orderDirection) {
        this.orderDirection = orderDirection;
    }

    public OrderByTarget getTarget() {
        return target;
    }

    public void setTarget(OrderByTarget target) {
        this.target = target;
    }
}