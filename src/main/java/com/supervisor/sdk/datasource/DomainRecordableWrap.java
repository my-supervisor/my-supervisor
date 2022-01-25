package com.supervisor.sdk.datasource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class DomainRecordableWrap implements Recordable {

	private final Recordable origin;
	
	public DomainRecordableWrap(Recordable origin) {
		this.origin = origin;
	}
	
	@Override
	public Long id() {
		return origin.id();
	}

	@Override
	public UUID guid() throws IOException {
		return origin.guid();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return origin.creationDate();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return origin.lastModificationDate();
	}

	@Override
	public Long lastModifierId() throws IOException {
		return origin.lastModifierId();
	}

	@Override
	public Long ownerId() throws IOException {
		return origin.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return origin.tag();
	}

	@Override
	public Long creatorId() throws IOException {
		return origin.creatorId();
	}

	@Override
	public Base base() {
		return origin.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return origin.listOf(clazz);
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return origin.listOf(clazz, viewScript);
	}
	
	@Override
	public boolean equals(Object o){
		return origin.equals(o);
	}
	
	@Override
	public int hashCode(){
		return origin.hashCode();
	}
}
