package com.minlessika.supervisor.server;

import java.io.IOException;

import org.atmosphere.config.managed.Decoder;

import com.minlessika.supervisor.server.ActivityRealtime.ActivityData;

public final class ActivityDataDecoder implements Decoder<String, ActivityData> {
	
    @Override
    public ActivityData decode(String s) {
        try {
            return ActivityRealtime.mapper.readValue(s, ActivityData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
