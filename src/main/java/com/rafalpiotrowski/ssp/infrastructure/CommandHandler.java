package com.rafalpiotrowski.ssp.infrastructure;

public interface CommandHandler {

    String handle(String command, SessionData sessionData);
}
