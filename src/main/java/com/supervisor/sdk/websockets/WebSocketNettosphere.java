package com.supervisor.sdk.websockets;

import com.supervisor.sdk.takes.Args;
import com.supervisor.sdk.takes.BaseUri;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.nettosphere.Config.Builder;
import org.atmosphere.nettosphere.Nettosphere;
import org.takes.Request;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class WebSocketNettosphere implements WebSocketServer {

	private final Map<String, String> map;
	private Nettosphere wsServer;
	private final List<Class<?>> resources;
	
	public WebSocketNettosphere(final List<Class<?>> resources, final String ...args) {
		this(Arrays.asList(args), resources);
	}
	
	public WebSocketNettosphere(final Iterable<String> args, final List<Class<?>> resources) {
		this.map = new Args(args).asMap();
		this.resources = resources;
	}
	
	@Override
	public String host() {
		String host = "localhost";
		
		if(map.get("ws-host") != null)
			host = map.get("ws-host");
		
		return host;
	}

	@Override
	public int port() {
		int port = 9091;
		
		if(map.get("ws-port") != null)
			port = Integer.parseInt(map.get("ws-port"));
		
		return port;
	}

	@Override
	public void start() throws IOException {
		
		if(wsServer == null) {
			Builder builder = new Builder()
	                   .host(host())
	                   .port(port());
			
			for (Class<?> res : resources) {
				builder.resource(res);
			}
			
			wsServer = new Nettosphere.Builder()
					                  .config(builder.build())
					                  .build();
		} else {
			if(wsServer.isStarted())
				wsServer.stop();
		}
				
		wsServer.start();
	}

	@Override
	public void publish(Class<?> serviceClazz, Object data, String headerKey, String headerValue) throws IOException {
		
		ManagedService service = serviceClazz.getAnnotation(ManagedService.class);
		String path = service.path();
		
		Broadcaster broadcaster = wsServer.framework().getBroadcasterFactory().lookup(path);
		if(broadcaster == null)
			return;
		
		Set<AtmosphereResource> atmResources = resourcesOf(headerKey, headerValue, broadcaster);
		if(!atmResources.isEmpty())
			broadcaster.broadcast(data, atmResources);
	}

	@Override
	public boolean hasHeader(Class<?> serviceClazz, String key, String value) {
				
		boolean has = false;
		
		ManagedService service = serviceClazz.getAnnotation(ManagedService.class);
		String path = service.path();
		
		Broadcaster broadcaster = wsServer.framework().getBroadcasterFactory().lookup(path);
		if(broadcaster == null)
			return has;
		
		Collection<AtmosphereResource> atmResources = broadcaster.getAtmosphereResources();
		if(atmResources.isEmpty())
			return has;		
		
		has = atmResources.parallelStream()
		                  .anyMatch(r -> r.getRequest().headersMap().containsKey(key) && r.getRequest().headersMap().get(key).equals(value));
		
		return has;
	}
	
	private Set<AtmosphereResource> resourcesOf(String headerKey, String headerValue, Broadcaster broadcaster) {
		
		Set<AtmosphereResource> ress = new HashSet<>();
		Collection<AtmosphereResource> atmResources = broadcaster.getAtmosphereResources();
		
		for (AtmosphereResource res : atmResources) {
			
			Map<String, String> headersMap = res.getRequest().headersMap();
			if(headersMap.containsKey(headerKey) && headersMap.get(headerKey).equals(headerValue)) {
				ress.add(res);
			}		
		}
		
		return ress;
	}

	@Override
	public String bare(final Request req) {
		return new BaseUri(req, port()).toString();
	}
}
