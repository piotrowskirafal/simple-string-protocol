package com.rafalpiotrowski.ssp.application.query;

public class ShortestPathQuery {

    private String nodeXName;
    private String nodeYName;

    public ShortestPathQuery(String nodeXName, String nodeYName) {
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
