package com.rafalpiotrowski.ssp.infrastructure;

import java.util.UUID;

public class SessionId {

    private final UUID uuid;

    public SessionId() {
        this.uuid = UUID.randomUUID();
    }

    public String getIdAsString() {
        return uuid.toString();
    }
}
