package com.rafalpiotrowski.cb.domain.graph;

public class NodeWeight {

    private final Integer weight;

    public NodeWeight(Integer weight) {
        assertThatWeightIsNonNegative(weight);
        this.weight = weight;
    }

    private void assertThatWeightIsNonNegative(Integer weight) {
        if (weight < 0) {
            throw new IllegalArgumentException(String.format("Node wight must be non-negative [weight: %s]", weight));
        }
    }

    public Integer getAsInteger() {
        return weight;
    }
}
