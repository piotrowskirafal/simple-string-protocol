package com.rafalpiotrowski.cb.infrastructure;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionRepository {
    private Map<String, SessionData> storage = new ConcurrentHashMap<>();

    public SessionData get(String connectionId) {
        SessionData session = storage.getOrDefault(connectionId, new SessionData(new SessionId()));
        storage.put(connectionId, session);
        return session;
    }
}
