package com.rafalpiotrowski.cb.infrastructure.handler;

import com.rafalpiotrowski.cb.application.GraphApplicationService;
import com.rafalpiotrowski.cb.application.command.AddEdgeCommand;
import com.rafalpiotrowski.cb.domain.graph.GraphException;
import com.rafalpiotrowski.cb.infrastructure.ConditionalCommandHandler;
import com.rafalpiotrowski.cb.infrastructure.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddEdgeCommandHandler implements ConditionalCommandHandler {

    private static final String REQUEST = "ADD EDGE ";
    private static final String SUCCESS_RESPONSE = "EDGE ADDED";
    private static final String ERROR_RESPONSE = "ERROR: NODE NOT FOUND";
    private final GraphApplicationService graphApplicationService;

    @Autowired
    public AddEdgeCommandHandler(GraphApplicationService graphApplicationService) {
        this.graphApplicationService = graphApplicationService;
    }

    @Override
    public boolean canHandle(String command) {
        return command.startsWith(REQUEST);
    }

    @Override
    public String handle(String command, SessionData sessionData) {
        String[] params = command.replaceFirst(REQUEST, "").split(" ");
        AddEdgeCommand addEdgeCommand = new AddEdgeCommand(params[0], params[1], Integer.valueOf(params[2]));
        try {
            graphApplicationService.addEdge(addEdgeCommand);
            return SUCCESS_RESPONSE;
        } catch (GraphException exception) {
            exception.printStackTrace();
            return ERROR_RESPONSE;
        }
    }
}
