package com.rafalpiotrowski.ssp.domain.graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphService {

    private final GraphRepository graphRepository;

    @Autowired
    public GraphService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public void addNode(NodeName nodeName) {
        Graph graph = graphRepository.loadGraph();
        if (graph.containsNode(nodeName)) {
            throw new GraphException(String.format("Node already exists [node name: %s]", nodeName));
        }
        graph.addNode(nodeName);
    }

    public void addEdge(NodeName nodeXName, NodeName nodeYName, NodeWeight nodeWeight) {
        Graph graph = graphRepository.loadGraph();
        if (!graph.containsNode(nodeXName) || !graph.containsNode(nodeYName)) {
            throw new GraphException(String.format("Node was not found [node x: %s, node y: %s]", nodeXName, nodeYName));
        }
        graph.addEdge(nodeXName, nodeYName, nodeWeight);
    }

    public void removeNode(NodeName nodeName) {
        Graph graph = graphRepository.loadGraph();
        if (!graph.containsNode(nodeName)) {
            throw new GraphException(String.format("Can't remove node, it does not exist [node name: %s]", nodeName));
        }
        graph.removeNode(nodeName);
    }

    public void removeEdge(NodeName nodeXName, NodeName nodeYName) {
        Graph graph = graphRepository.loadGraph();
        if (!graph.containsNode(nodeXName) || !graph.containsNode(nodeYName)) {
            throw new GraphException(String.format("Can't remove edge because node does not exist [node x: %s, node y: %s]", nodeXName, nodeYName));
        }
        if (graph.containsEdge(nodeXName, nodeYName)) {
            graph.removeAllEdges(nodeXName, nodeYName);
        }
    }
}
