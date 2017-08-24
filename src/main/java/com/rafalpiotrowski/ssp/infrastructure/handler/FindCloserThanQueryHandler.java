package com.rafalpiotrowski.ssp.infrastructure.handler;

import com.rafalpiotrowski.ssp.application.GraphApplicationService;
import com.rafalpiotrowski.ssp.application.query.CloserThanQuery;
import com.rafalpiotrowski.ssp.domain.graph.GraphException;
import com.rafalpiotrowski.ssp.infrastructure.ConditionalCommandHandler;
import com.rafalpiotrowski.ssp.infrastructure.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FindCloserThanQueryHandler implements ConditionalCommandHandler {

    private static final String REQUEST = "CLOSER THAN ";
    private static final String ERROR_RESPONSE = "ERROR: NODE NOT FOUND";

    private GraphApplicationService graphApplicationService;

    @Autowired
    public FindCloserThanQueryHandler(GraphApplicationService graphApplicationService) {
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
            Collection<String> nodes = graphApplicationService.findNodesCloserThan(new CloserThanQuery(Integer.valueOf(params[0]), params[1]));
            return String.join(",", nodes);
        } catch (GraphException exception) {
            return ERROR_RESPONSE;
        }
    }
}
