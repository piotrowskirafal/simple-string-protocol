package com.rafalpiotrowski.cb.infrastructure.handler;

import com.rafalpiotrowski.cb.infrastructure.ConditionalCommandHandler;
import com.rafalpiotrowski.cb.infrastructure.SessionData;
import org.springframework.stereotype.Component;

@Component
public class ByeCommandHandler implements ConditionalCommandHandler {

    private static final String REQUEST = "BYE MATE!";
    private static final String RESPONSE = "BYE %s, WE SPOKE FOR %s MS";

    @Override
    public boolean canHandle(String command) {
        return command.startsWith(REQUEST);
    }

    @Override
    public String handle(String command, SessionData sessionData) {
        return handle(sessionData);
    }

    public String handle(SessionData sessionData) {
        return String.format(RESPONSE, sessionData.get(SessionData.Key.USER_NAME), sessionData.getSessionDuration());
    }
}
