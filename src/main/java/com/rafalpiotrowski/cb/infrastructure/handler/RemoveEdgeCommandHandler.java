package com.rafalpiotrowski.cb.infrastructure.handler;

import com.rafalpiotrowski.cb.application.GraphApplicationService;
import com.rafalpiotrowski.cb.application.command.RemoveEdgeCommand;
import com.rafalpiotrowski.cb.domain.graph.GraphException;
import com.rafalpiotrowski.cb.infrastructure.ConditionalCommandHandler;
import com.rafalpiotrowski.cb.infrastructure.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveEdgeCommandHandler implements ConditionalCommandHandler {

    private static final String REQUEST = "REMOVE EDGE ";
    private static final String SUCCESS_RESPONSE = "EDGE REMOVED";
    private static final String ERROR_RESPONSE = "ERROR: NODE NOT FOUND";

    private GraphApplicationService graphApplicationService;

    @Autowired
    public RemoveEdgeCommandHandler(GraphApplicationService graphApplicationService) {
        this.graphApplicationService = graphApplicationService;
    }

    @Override
    public boolean canHandle(String command) {
        return command.startsWith(REQUEST);
    }

    @Override
    public String handle(String command, SessionData sessionData) {
        String[] params = command.replaceFirst(REQUEST, "").split(" ");
        try {
            graphApplicationService.removeEdge(new RemoveEdgeCommand(params[0], params[1]));
            return SUCCESS_RESPONSE;
        } catch (GraphException exception) {
            return ERROR_RESPONSE;
        }
    }
}
