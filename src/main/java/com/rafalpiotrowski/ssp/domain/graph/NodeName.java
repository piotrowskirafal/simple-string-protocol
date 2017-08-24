package com.rafalpiotrowski.ssp.domain.graph;

import org.apache.commons.lang3.StringUtils;

public class NodeName {

    private final String name;

    public NodeName(String name) {
        assertIsAlphanumeric(name);
        this.name = name;
    }

    private void assertIsAlphanumeric(String name) {
        if (StringUtils.isBlank(name) || !name.matches("[A-Za-z0-9\\-]+")) {
            throw new IllegalArgumentException(String.format("Node name must be alphanumeric [name: %s].", name));
        }
    }

    public String getAsString() {
        return name;
    }
}
