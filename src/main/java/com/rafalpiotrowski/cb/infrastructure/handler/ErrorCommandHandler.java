package com.rafalpiotrowski.cb.infrastructure.handler;

import com.rafalpiotrowski.cb.infrastructure.CommandHandler;
import com.rafalpiotrowski.cb.infrastructure.SessionData;
import org.springframework.stereotype.Component;

@Component
public class ErrorCommandHandler implements CommandHandler {

    private static final String RESPONSE = "SORRY, I DIDN'T UNDERSTAND THAT";

    @Override
    public String handle(String command, SessionData sessionData) {
        return RESPONSE;
    }
}
