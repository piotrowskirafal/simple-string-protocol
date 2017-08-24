package com.rafalpiotrowski.ssp.infrastructure;

public interface ConditionalCommandHandler extends CommandHandler {

    boolean canHandle(String command);
}
