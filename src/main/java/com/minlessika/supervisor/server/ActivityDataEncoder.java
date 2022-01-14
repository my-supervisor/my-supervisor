package com.minlessika.supervisor.server;

import java.io.IOException;

import org.atmosphere.config.managed.Encoder;

import com.minlessika.supervisor.server.ActivityRealtime.ActivityData;

public final class ActivityDataEncoder implements Encoder<ActivityData, String> {
	
    @Override
    public String encode(ActivityData m) {
        try {
            return ActivityRealtime.mapper.writeValueAsString(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
