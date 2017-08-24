package com.rafalpiotrowski.ssp.application;

import com.rafalpiotrowski.ssp.application.command.*;
import com.rafalpiotrowski.ssp.application.query.CloserThanQuery;
import com.rafalpiotrowski.ssp.application.query.ShortestPathQuery;
import com.rafalpiotrowski.ssp.domain.graph.GraphQueryService;
import com.rafalpiotrowski.ssp.domain.graph.GraphService;
import com.rafalpiotrowski.ssp.domain.graph.NodeName;
import com.rafalpiotrowski.ssp.domain.graph.NodeWeight;
import com.rafalpiotrowski.ssp.infrastructure.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GraphApplicationService {

    private final GraphService graphService;

    private final GraphQueryService graphQueryService;

    @Autowired
    public GraphApplicationService(GraphService graphService, GraphQueryService graphQueryService) {
        this.graphService = graphService;
        this.graphQueryService = graphQueryService;
    }

    public void welcomeUser(WelcomeUserCommand command, SessionData sessionData) {
        sessionData.put(SessionData.Key.USER_NAME, command.getUserName());
    }

    public synchronized void addNode(AddNodeCommand command) {
        NodeName nodeName = new NodeName(command.getNodeName());
        graphService.addNode(nodeName);
    }

    public synchronized void addEdge(AddEdgeCommand command) {
        NodeName nodeXName = new NodeName(command.getNodeXName());
        NodeName nodeYName = new NodeName(command.getNodeYName());
        NodeWeight nodeWeight = new NodeWeight(command.getWeight());
        graphService.addEdge(nodeXName, nodeYName, nodeWeight);
    }

    public synchronized void removeNode(RemoveNodeCommand command) {
        NodeName nodeName = new NodeName(command.getNodeName());
        graphService.removeNode(nodeName);
    }

    public synchronized void removeEdge(RemoveEdgeCommand command) {
        NodeName nodeXName = new NodeName(command.getNodeXName());
        NodeName nodeYName = new NodeName(command.getNodeYName());
        graphService.removeEdge(nodeXName, nodeYName);
    }

    public synchronized Integer findShortestPath(ShortestPathQuery query) {
        NodeName nodeXName = new NodeName(query.getNodeXName());
        NodeName nodeYName = new NodeName(query.getNodeYName());
        return graphQueryService.findShortestPath(nodeXName, nodeYName);
    }

    public synchronized Set<String> findNodesCloserThan(CloserThanQuery query) {
        NodeWeight nodeWeight = new NodeWeight(query.getWeight());
        NodeName nodeName = new NodeName(query.getNodeName());
        return graphQueryService.findNodesCloserThan(nodeWeight, nodeName);
    }
}
