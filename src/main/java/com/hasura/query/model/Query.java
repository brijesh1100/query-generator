package com.hasura.query.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {

	/**
	 * Aggregate fields of the query
	 */
	@JsonProperty("aggregates")
	private HashMap<String, Aggregate> aggregates;

	/**
	 * Fields of the query
	 */
	@JsonProperty("fields")
	private HashMap<String, Field> fields;

	@JsonProperty("limit")
	private Integer limit;

	@JsonProperty("offset")
	private Integer offset;

	@JsonProperty("order_by")
	private OrderBy orderBy; 

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	@JsonProperty("predicate")
	private Expression predicate;


	public HashMap<String, Aggregate> getAggregates() {
		return aggregates;
	}

	public void setAggregates(HashMap<String, Aggregate> aggregates) {
		this.aggregates = aggregates;
	}

	public HashMap<String, Field> getFields() {
		return fields;
	}

	public void setFields(HashMap<String, Field> fields) {
		this.fields = fields;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Expression getPredicate() {
		return predicate;
	}

	public void setPredicate(Expression predicate) {
		this.predicate = predicate;
	}
}
