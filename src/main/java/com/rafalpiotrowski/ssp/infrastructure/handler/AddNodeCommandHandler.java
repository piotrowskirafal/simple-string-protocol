package com.rafalpiotrowski.ssp.infrastructure.handler;

import com.rafalpiotrowski.ssp.application.GraphApplicationService;
import com.rafalpiotrowski.ssp.application.command.AddNodeCommand;
import com.rafalpiotrowski.ssp.domain.graph.GraphException;
import com.rafalpiotrowski.ssp.infrastructure.ConditionalCommandHandler;
import com.rafalpiotrowski.ssp.infrastructure.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddNodeCommandHandler implements ConditionalCommandHandler {

    private static final String REQUEST = "ADD NODE ";
    private static final String SUCCESS_RESPONSE = "NODE ADDED";
    private static final String ERROR_RESPONSE = "ERROR: NODE ALREADY EXISTS";

    private GraphApplicationService graphApplicationService;

    @Autowired
    public AddNodeCommandHandler(GraphApplicationService graphApplicationService) {
        this.graphApplicationService = graphApplicationService;
    }

    @Override
    public boolean canHandle(String command) {
        return command.startsWith(REQUEST);
    }

    @Override
    public String handle(String command, SessionData sessionData) {
        String nodeName = command.replaceFirst(REQUEST, "");
        try {
            graphApplicationService.addNode(new AddNodeCommand(nodeName));
            return SUCCESS_RESPONSE;
        } catch (GraphException exception) {
            return ERROR_RESPONSE;
        }
    }
}
