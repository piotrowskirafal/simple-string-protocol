package com.rafalpiotrowski.ssp.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.integration.ip.tcp.connection.TcpConnectionOpenEvent;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;

@Component
public class TcpEventsListener {

    private final MessageChannel toTcp;
    private final SessionRepository sessionRepository;
    private final CommandDispatcher commandDispatcher;

    @Autowired
    public TcpEventsListener(MessageChannel toTcp, SessionRepository sessionRepository, CommandDispatcher commandDispatcher) {
        this.toTcp = toTcp;
        this.sessionRepository = sessionRepository;
        this.commandDispatcher = commandDispatcher;
    }

    @EventListener
    public void handle(TcpConnectionOpenEvent event) {
        SessionData session = sessionRepository.get(event.getConnectionId());
        String message = commandDispatcher.start(session);
        sendMessage(event.getConnectionId(), message);
    }

    @EventListener
    public void handle(TcpConnectionExceptionEvent event) {
        if (event.getCause() instanceof SocketTimeoutException) {
            SessionData session = sessionRepository.get(event.getConnectionId());
            String message = commandDispatcher.end(session);
            sendMessage(event.getConnectionId(), message);
        }
    }

    private void sendMessage(String connectionId, String message) {
        toTcp.send(MessageBuilder.withPayload(message).setHeader(IpHeaders.CONNECTION_ID, connectionId).build());
    }
}
