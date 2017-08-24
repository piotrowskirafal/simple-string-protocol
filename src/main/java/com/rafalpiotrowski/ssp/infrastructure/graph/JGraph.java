package com.rafalpiotrowski.ssp.infrastructure.graph;


import com.rafalpiotrowski.ssp.domain.graph.Graph;
import com.rafalpiotrowski.ssp.domain.graph.NodeName;
import com.rafalpiotrowski.ssp.domain.graph.NodeWeight;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.ClosestFirstIterator;

import java.util.HashSet;
import java.util.Set;

public class JGraph implements Graph {

    private final WeightedGraph<String, DefaultWeightedEdge> graph;

    public JGraph(WeightedGraph<String, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    @Override
    public boolean containsNode(NodeName nodeName) {
        return graph.containsVertex(nodeName.getAsString());
    }

    @Override
    public boolean containsEdge(NodeName nodeXName, NodeName nodeYName) {
        return graph.containsEdge(nodeXName.getAsString(), nodeYName.getAsString());
    }

    @Override
    public void addNode(NodeName nodeName) {
        graph.addVertex(nodeName.getAsString());
    }

    @Override
    public void addEdge(NodeName nodeXName, NodeName nodeYName, NodeWeight weight) {
        DefaultWeightedEdge edge = graph.addEdge(nodeXName.getAsString(), nodeYName.getAsString());
        graph.setEdgeWeight(edge, weight.getAsInteger());
    }

    @Override
    public void removeNode(NodeName nodeName) {
        graph.removeVertex(nodeName.getAsString());
    }

    @Override
    public void removeAllEdges(NodeName nodeXName, NodeName nodeYName) {
        graph.removeAllEdges(nodeXName.getAsString(), nodeYName.getAsString());
    }

    @Override
    public Integer findShortestPath(NodeName nodeXName, NodeName nodeYName) {
        DijkstraShortestPath<String, DefaultWeightedEdge> shortestPathAlgorithm = new DijkstraShortestPath<>(graph);
        Double weight = shortestPathAlgorithm.getPathWeight(nodeXName.getAsString(), nodeYName.getAsString());
        return weight.equals(Double.POSITIVE_INFINITY) ? Integer.MAX_VALUE : weight.intValue();
    }

    @Override
    public Set<String> findNodesCloserThan(NodeWeight nodeWeight, NodeName nodeName) {
        Double weight = nodeWeight.getAsInteger() - 0.1;
        ClosestFirstIterator<String, DefaultWeightedEdge> closestFirstIterator = new ClosestFirstIterator<>(graph, nodeName.getAsString(), weight);
        Set<String> nodes = new HashSet<>();
        if (closestFirstIterator.hasNext()) {
            closestFirstIterator.next();
        }
        closestFirstIterator.forEachRemaining(nodes::add);
        return nodes;
    }
}
