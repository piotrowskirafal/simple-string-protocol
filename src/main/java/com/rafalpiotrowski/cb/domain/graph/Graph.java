package com.rafalpiotrowski.cb.domain.graph;

import java.util.Set;

public interface Graph {

    boolean containsNode(NodeName nodeName);

    boolean containsEdge(NodeName nodeXName, NodeName nodeYName);

    void addNode(NodeName nodeName);

    void addEdge(NodeName nodeXName, NodeName nodeYName, NodeWeight weight);

    void removeNode(NodeName nodeName);

    void removeAllEdges(NodeName nodeXName, NodeName nodeYName);

    Integer findShortestPath(NodeName nodeXName, NodeName nodeYName);

    Set<String> findNodesCloserThan(NodeWeight nodeWeight, NodeName nodeName);
}
