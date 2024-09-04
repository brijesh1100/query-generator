package com.hasura.query.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "Related", value = RelatedExists.class),
    @JsonSubTypes.Type(name = "Unrelated", value = UnrelatedExists.class),
})
public abstract class ExistsInCollection {

    public abstract String getType(); // Abstract method to be implemented by subclasses
}

// Related Exists
class RelatedExists extends ExistsInCollection {
    
    @JsonProperty("relationship")
    private String relationship;

    @JsonProperty("arguments")
    private HashMap<String, RelationshipArgument> arguments;

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

    @Override
    public String getType() {
        return "Related";
    }
}

// Unrelated Exists
class UnrelatedExists extends ExistsInCollection {
    
    @JsonProperty("collection")
    private String collection;

    @JsonProperty("arguments")
    private HashMap<String, RelationshipArgument> arguments;

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public HashMap<String, RelationshipArgument> getArguments() {
        return arguments;
    }

    public void setArguments(HashMap<String, RelationshipArgument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String getType() {
        return "Unrelated";
    }
}
