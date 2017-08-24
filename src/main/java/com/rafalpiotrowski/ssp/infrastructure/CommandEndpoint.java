package com.rafalpiotrowski.ssp.infrastructure;

import com.rafalpiotrowski.ssp.infrastructure.config.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessageEndpoint
public class CommandEndpoint {

    private final SessionRepository sessionRepository;
    private final CommandDispatcher commandDispatcher;

    @Autowired
    public CommandEndpoint(SessionRepository sessionRepository, CommandDispatcher commandDispatcher) {
        this.sessionRepository = sessionRepository;
        this.commandDispatcher = commandDispatcher;
    }

    @ServiceActivator(inputChannel = Channel.TO_APP, outputChannel = Channel.TO_TCP)
    public String dispatchCommand(String input, @Header(IpHeaders.CONNECTION_ID) String connectionId) {
        return commandDispatcher.dispatch(input, sessionRepository.get(connectionId));
    }
}