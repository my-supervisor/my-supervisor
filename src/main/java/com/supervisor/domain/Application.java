package com.supervisor.domain;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@com.supervisor.sdk.metadata.Recordable(
	name="base_application", 
	label="Application"
)
public interface Application extends Recordable {
	
	@Field(
		label="User",
		rel=Relation.MANY2ONE
	)
	User user() throws IOException;
	
	@Field(
		label="Profile",
		rel=Relation.MANY2ONE
	)
	Profile profile() throws IOException;
	
	@Field(label="Module")
	String module() throws IOException;
	
	void changeProfile(final Profile newProfile) throws IOException;
	
	Application EMPTY = new Application() {
		
		@Override
		public String tag() throws IOException {
			
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
			return null;
		}
		
		@Override
		public Long lastModifierId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public Long id() {
			
			return null;
		}
		
		@Override
		public UUID guid() throws IOException {
			
			return null;
		}
		
		@Override
		public Long creatorId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime creationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public Base base() {
			
			return null;
		}
		
		@Override
		public User user() throws IOException {
			
			return null;
		}
		
		@Override
		public String module() throws IOException {
			
			return null;
		}

		@Override
		public Profile profile() throws IOException {
			return Profile.EMPTY;
		}

		@Override
		public void changeProfile(Profile newProfile) throws IOException {
			
			
		}
	};
}
