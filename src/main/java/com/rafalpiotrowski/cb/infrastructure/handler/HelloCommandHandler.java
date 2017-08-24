package com.rafalpiotrowski.cb.infrastructure.handler;

import com.rafalpiotrowski.cb.application.GraphApplicationService;
import com.rafalpiotrowski.cb.application.command.WelcomeUserCommand;
import com.rafalpiotrowski.cb.infrastructure.ConditionalCommandHandler;
import com.rafalpiotrowski.cb.infrastructure.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloCommandHandler implements ConditionalCommandHandler {

    private static final String REQUEST = "HI, I'M ";
    private static final String RESPONSE = "HI %s";
    private static final String GREETINGS_RESPONSE = "HI, I'M %s";

    private GraphApplicationService graphApplicationService;

    @Autowired
    public HelloCommandHandler(GraphApplicationService graphApplicationService) {
        this.graphApplicationService = graphApplicationService;
    }

    @Override
    public boolean canHandle(String command) {
        return command.startsWith(REQUEST);
    }

    @Override
    public String handle(String command, SessionData sessionData) {
        String userName = command.replaceFirst(REQUEST, "");
        graphApplicationService.welcomeUser(new WelcomeUserCommand(userName), sessionData);
        return String.format(RESPONSE, userName);
    }

    public String handle(SessionData sessionData) {
        return String.format(GREETINGS_RESPONSE, sessionData.getId());
    }
}
