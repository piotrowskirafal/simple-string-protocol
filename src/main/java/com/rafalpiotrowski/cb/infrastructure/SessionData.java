package com.rafalpiotrowski.cb.infrastructure;

import java.util.HashMap;
import java.util.Map;

public class SessionData {

    private final SessionId sessionId;
    private final long startTime;
    private Map<String, String> data;

    public SessionData(SessionId sessionId) {
        this.sessionId = sessionId;
        this.startTime = System.currentTimeMillis();
        data = new HashMap<>();
    }

    public interface Key {
        String USER_NAME = "userName";
    }

    public String getId() {
        return sessionId.getIdAsString();
    }

    public long getSessionDuration() {
        return System.currentTimeMillis() - startTime;
    }

    public void put(String key, String value) {
        data.put(key, value);
    }

    public String get(String key) {
        return data.get(key);
    }
}
