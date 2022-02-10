package com.supervisor.sdk.utils;

import java.util.UUID;

public final class OptUUID {

    private final String value;

    public OptUUID(final UUID value) {
        this(value.toString());
    }

    public OptUUID(final String value) {
        this.value = value;
    }

    public boolean isPresent() {
        return ! (value.equals("") || value.equals("0"));
    }

    public UUID get() {
        if (!this.isPresent()) {
            throw new IllegalArgumentException("UUID is not present !");
        }
        return UUID.fromString(this.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
