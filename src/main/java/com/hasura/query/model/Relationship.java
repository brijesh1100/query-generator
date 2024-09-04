package com.hasura.query.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Relationship {


    @JsonProperty("column_mapping")
    private Map<String, String> columnMapping;

    @JsonProperty("relationship_type")
    private RelationshipType relationshipType;

    @JsonProperty("target_collection")
    private String targetCollection;

    @JsonProperty("arguments")
    private Map<String, RelationshipArgument> arguments;


    public Map<String, String> getColumnMapping() {
        return columnMapping;
    }

    public void setColumnMapping(Map<String, String> columnMapping) {
        this.columnMapping = columnMapping;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getTargetCollection() {
        return targetCollection;
    }

    public void setTargetCollection(String targetCollection) {
        this.targetCollection = targetCollection;
    }

    public Map<String, RelationshipArgument> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, RelationshipArgument> arguments) {
        this.arguments = arguments;
    }
}

enum RelationshipType{
	Object, Array
}
