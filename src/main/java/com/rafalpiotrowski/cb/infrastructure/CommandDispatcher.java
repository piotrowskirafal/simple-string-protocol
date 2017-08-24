package com.rafalpiotrowski.cb.infrastructure;

import com.rafalpiotrowski.cb.infrastructure.handler.ByeCommandHandler;
import com.rafalpiotrowski.cb.infrastructure.handler.ErrorCommandHandler;
import com.rafalpiotrowski.cb.infrastructure.handler.HelloCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommandDispatcher {

    private final Collection<ConditionalCommandHandler> commandHandlers;
    private final HelloCommandHandler helloCommandHandler;
    private final ByeCommandHandler byeCommandHandler;
    private final ErrorCommandHandler errorCommandHandler;

    @Autowired
    public CommandDispatcher(Collection<ConditionalCommandHandler> commandHandlers, HelloCommandHandler helloCommandHandler, ByeCommandHandler byeCommandHandler, ErrorCommandHandler errorCommandHandler) {
        this.commandHandlers = commandHandlers;
        this.helloCommandHandler = helloCommandHandler;
        this.byeCommandHandler = byeCommandHandler;
        this.errorCommandHandler = errorCommandHandler;
    }

    public String start(SessionData sessionData) {
        return helloCommandHandler.handle(sessionData);
    }

    public String dispatch(String command, SessionData sessionData) {
        return findCommandHandler(command).handle(command, sessionData);
    }

    public String end(SessionData sessionData) {
        return byeCommandHandler.handle(sessionData);
    }

    private CommandHandler findCommandHandler(String command) {
        return commandHandlers.stream()
                .filter(handler -> handler.canHandle(command))
                .map(CommandHandler.class::cast)
                .findFirst().orElse(errorCommandHandler);
    }
}
