package com.rafalpiotrowski.ssp.application.command;

public class AddEdgeCommand {

    private String nodeXName;
    private String nodeYName;
    private Integer weight;

    public AddEdgeCommand(String nodeXName, String nodeYName, Integer weight) {
        this.nodeXName = nodeXName;
        this.nodeYName = nodeYName;
        this.weight = weight;
    }

    public String getNodeXName() {
        return nodeXName;
    }

    public String getNodeYName() {
        return nodeYName;
    }

    public Integer getWeight() {
        return weight;
    }
}
