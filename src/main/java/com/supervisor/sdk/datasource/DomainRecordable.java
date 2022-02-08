package com.supervisor.sdk.datasource;

import com.google.common.base.Objects;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class DomainRecordable<A1 extends Recordable> implements Recordable {

	protected final Record<A1> record;
	
	public DomainRecordable(final Record<A1> record) {
		this.record = record;
	}
	
	@Override
	public UUID id() {
		return record.id();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return record.creationDate();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return record.lastModificationDate();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return record.lastModifierId();
	}

	@Override
	public UUID ownerId() throws IOException {
		return record.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return record.tag();
	}

	@Override
	public UUID creatorId() throws IOException {
		return record.creatorId();
	}

	@Override
	public Base base() {
		return record.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return record.listOf(clazz);
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return record.listOf(clazz, viewScript);
	}
	
	@Override
	public boolean equals(Object o){
		
		if(o == null)
		    return false;

		if(this.getClass() != o.getClass())
		    return false;
		  
		if(this == o)
			return true;
		
		@SuppressWarnings("unchecked")
		A1 item = (A1)o;
		
		return id().equals(item.id());
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(id());
	}
}
