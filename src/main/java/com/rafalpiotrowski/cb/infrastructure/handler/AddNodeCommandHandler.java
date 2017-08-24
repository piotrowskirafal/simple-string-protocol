package com.rafalpiotrowski.cb.infrastructure.handler;

import com.rafalpiotrowski.cb.application.GraphApplicationService;
import com.rafalpiotrowski.cb.application.command.AddNodeCommand;
import com.rafalpiotrowski.cb.domain.graph.GraphException;
import com.rafalpiotrowski.cb.infrastructure.ConditionalCommandHandler;
import com.rafalpiotrowski.cb.infrastructure.SessionData;
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
