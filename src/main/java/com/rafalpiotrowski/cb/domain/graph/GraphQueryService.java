package com.rafalpiotrowski.cb.domain.graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GraphQueryService {

    private GraphRepository graphRepository;

    @Autowired
    public GraphQueryService(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public Integer findShortestPath(NodeName nodeXName, NodeName nodeYName) {
        Graph graph = graphRepository.loadGraph();
        if (!graph.containsNode(nodeXName) || !graph.containsNode(nodeYName)) {
            throw new GraphException(String.format("Can't find shortest path because node does not exist [node x: %s, node y: %s]", nodeXName, nodeYName));
        }
        return graph.findShortestPath(nodeXName, nodeYName);
    }

    public Set<String> findNodesCloserThan(NodeWeight weight, NodeName nodeName) {
        Graph graph = graphRepository.loadGraph();
        if (!graph.containsNode(nodeName)) {
            throw new GraphException(String.format("Can't find nodes closer than, because node does not exist [node: %s]", nodeName));
        }
        Set<String> nodes = graph.findNodesCloserThan(weight, nodeName);
        return sortNodesAlphabetically(nodes);
    }

    private Set<String> sortNodesAlphabetically(Set<String> nodes) {
        return nodes.stream()
                .sorted(String::compareTo)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
