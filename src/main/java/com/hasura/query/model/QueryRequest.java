package com.hasura.query.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryRequest {

	/**
	 * The name of the collection from which data will be retrieved.
	 */
	@JsonProperty("collection")
	private String collection;

	/**
	 * The query syntax tree representing the selection criteria, aggregations, etc.
	 */
	@JsonProperty("query")
	private Query query;

	@JsonProperty("arguments")
	private TreeMap<String, Argument> arguments;

	@JsonProperty("collection_relationships")
	private TreeMap<String, Relationship> collectionRelationships;
	@JsonProperty("variables")
	private List<Map<String, Object>> variables; 

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public TreeMap<String, Argument> getArguments() {
		return arguments;
	}

	public void setArguments(TreeMap<String, Argument> arguments) {
		this.arguments = arguments;
	}

	public TreeMap<String, Relationship> getCollectionRelationships() {
		return collectionRelationships;
	}

	public void setCollectionRelationships(TreeMap<String, Relationship> collectionRelationships) {
		this.collectionRelationships = collectionRelationships;
	}

	public List<Map<String, Object>> getVariables() {
		return variables;
	}

	public void setVariables(List<Map<String, Object>> variables) {
		this.variables = variables;
	}
}
