package com.rafalpiotrowski.cb.application.query;

public class CloserThanQuery {

    private Integer weight;
    private String nodeName;

    public CloserThanQuery(Integer weight, String nodeName) {
        this.weight = weight;
        this.nodeName = nodeName;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getNodeName() {
        return nodeName;
    }
}
