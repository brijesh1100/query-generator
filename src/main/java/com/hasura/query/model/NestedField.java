package com.hasura.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum NestedField {

    @JsonProperty("Object")
    OBJECT("Object") {
        @JsonProperty("object")
        private NestedObject object;

        public NestedObject getObject() {
            return object;
        }

        public void setObject(NestedObject object) {
            this.object = object;
        }
    },

    @JsonProperty("Array")
    ARRAY("Array") {
        @JsonProperty("array")
        private NestedArray array;

        public NestedArray getArray() {
            return array;
        }

        public void setArray(NestedArray array) {
            this.array = array;
        }
    };

    private final String type;

    NestedField(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}