package com.hasura.query.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PathElement {
	
    @JsonProperty("relationship")
    private String relationship;

    @JsonProperty("arguments")
    private HashMap<String, RelationshipArgument> arguments;

    @JsonProperty("predicate")
    private Expression predicate;

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public HashMap<String, RelationshipArgument> getArguments() {
        return arguments;
    }

    public void setArguments(HashMap<String, RelationshipArgument> arguments) {
        this.arguments = arguments;
    }

    public Expression getPredicate() {
        return predicate;
    }

    public void setPredicate(Expression predicate) {
        this.predicate = predicate;
    }
}