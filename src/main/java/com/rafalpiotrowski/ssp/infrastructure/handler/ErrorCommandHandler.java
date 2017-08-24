package com.rafalpiotrowski.ssp.infrastructure.handler;

import com.rafalpiotrowski.ssp.infrastructure.CommandHandler;
import com.rafalpiotrowski.ssp.infrastructure.SessionData;
import org.springframework.stereotype.Component;

@Component
public class ErrorCommandHandler implements CommandHandler {

    private static final String RESPONSE = "SORRY, I DIDN'T UNDERSTAND THAT";

    @Override
    public String handle(String command, SessionData sessionData) {
        return RESPONSE;
    }
}
