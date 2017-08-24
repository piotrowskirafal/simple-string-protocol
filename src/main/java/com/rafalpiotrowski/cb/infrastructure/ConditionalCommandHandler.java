package com.rafalpiotrowski.cb.infrastructure;

public interface ConditionalCommandHandler extends CommandHandler {

    boolean canHandle(String command);
}
