package com.rafalpiotrowski.cb.infrastructure;

public interface CommandHandler {

    String handle(String command, SessionData sessionData);
}
