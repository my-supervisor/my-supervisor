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

    public boolean isEmpty() {
        return value.equals("") || value.equals("0");
    }

    public boolean isPresent() {
        return ! value.isEmpty();
    }

    public UUID value() {
        final UUID guid;
        if (this.isEmpty()) {
            guid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        } else {
            guid = UUID.fromString(this.value);
        }
        return guid;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
