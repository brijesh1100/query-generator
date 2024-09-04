package com.hasura.query.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "column", value = ColumnTarget.class),
    @JsonSubTypes.Type(name = "root_collection_column", value = RootCollectionColumnTarget.class),
})
public abstract class ComparisonTarget {

    public abstract String getType(); // Abstract method to be implemented by subclasses

    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

// Column Target
class ColumnTarget extends ComparisonTarget {
    
    @JsonProperty("path")
    private List<PathElement> path;

    public List<PathElement> getPath() {
        return path;
    }

    public void setPath(List<PathElement> path) {
        this.path = path;
    }

    @Override
    public String getType() {
        return "column";
    }
}

// Root Collection Column Target
class RootCollectionColumnTarget extends ComparisonTarget {
    
    @Override
    public String getType() {
        return "root_collection_column";
    }
}
