package com.rafalpiotrowski.cb.infrastructure.handler;

import com.rafalpiotrowski.cb.application.GraphApplicationService;
import com.rafalpiotrowski.cb.application.command.RemoveNodeCommand;
import com.rafalpiotrowski.cb.domain.graph.GraphException;
import com.rafalpiotrowski.cb.infrastructure.ConditionalCommandHandler;
import com.rafalpiotrowski.cb.infrastructure.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveNodeCommandHandler implements ConditionalCommandHandler {

    private static final String REQUEST = "REMOVE NODE ";
    private static final String SUCCESS_RESPONSE = "NODE REMOVED";
    private static final String ERROR_RESPONSE = "ERROR: NODE NOT FOUND";

    private GraphApplicationService graphApplicationService;

    @Autowired
    public RemoveNodeCommandHandler(GraphApplicationService graphApplicationService) {
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
            graphApplicationService.removeNode(new RemoveNodeCommand(nodeName));
            return SUCCESS_RESPONSE;
        } catch (GraphException exception) {
            return ERROR_RESPONSE;
        }
    }
}
