package com.minlessika.supervisor.server;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@ManagedService(path = "/ws/activity")
public final class ActivityRealtime {
	
	ReentrantLock lock = new ReentrantLock();
	
	public static ObjectMapper mapper;
	private final Logger logger = LoggerFactory.getLogger(ActivityRealtime.class);
	
    public ActivityRealtime() {
    	
    	lock.lock();
	    try {
	    	if(mapper == null) {
	    		mapper = new ObjectMapper();
		    	// Hack time module to allow 'Z' at the end of string (i.e. javascript json's)
		    	JavaTimeModule javaTimeModule = new JavaTimeModule();
				javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
				javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
				mapper.registerModule(javaTimeModule);
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
				mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    	}	    	
	    } finally {
	        lock.unlock();
	    }    	
    }
    
    @Get
    public void onOpen(final AtmosphereResource r) {
        r.addEventListener(new AtmosphereResourceEventListenerAdapter() {
            @Override
            public void onSuspend(AtmosphereResourceEvent event) {            	            	
            	logger.info("User {} connected.", r.uuid());
            }

            @Override
            public void onDisconnect(AtmosphereResourceEvent event) {
                if (event.isCancelled()) {
                	logger.info("User {} unexpectedly disconnected", r.uuid());
                } else if (event.isClosedByClient()) {
                	logger.info("User {} closed the connection", r.uuid());
                }
            }
        });
    }

    @org.atmosphere.config.service.Message(encoders = {ActivityDataEncoder.class}, decoders = {ActivityDataDecoder.class})
    public ActivityData onMessage(AtmosphereResource r, ActivityData data) throws IOException {
    	logger.info("Message send : Activity {} for User {}", data.id, r.uuid());
    	return data;
    }
    
    public final static class ActivityData {
    	
    	private long id;
    	private List<IndicatorData> indics;
    	
    	public ActivityData() {
    		
    	}
    	
    	public ActivityData(long id, List<IndicatorData> indics) {
    		this.id = id;
    		this.indics = indics;
    	}
    	
    	public Long getId() {
            return id;
    	}

        public void setId(long id) {
            this.id = id;
        }
        
    	public void setIndics(List<IndicatorData> indics) {
            this.indics = indics;
        }
    	
    	public List<IndicatorData> getIndics() {
            return indics;
        }
    	
    	public void add(IndicatorData data) {
    		indics.add(data);
    	}
    }
    
    public final static class IndicatorData {

        private String code;

        public IndicatorData() {

        }
        
        public IndicatorData(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
