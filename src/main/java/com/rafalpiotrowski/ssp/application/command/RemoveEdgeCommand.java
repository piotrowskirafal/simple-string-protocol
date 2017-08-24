package com.rafalpiotrowski.ssp.application.command;

public class RemoveEdgeCommand {

    private String nodeXName;
    private String nodeYName;

    public RemoveEdgeCommand(String nodeXName, String nodeYName) {
        this.nodeXName = nodeXName;
        this.nodeYName = nodeYName;
    }

    public String getNodeXName() {
        return nodeXName;
    }

    public String getNodeYName() {
        return nodeYName;
    }
}
