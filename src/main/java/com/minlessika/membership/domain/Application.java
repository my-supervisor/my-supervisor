package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@com.minlessika.sdk.metadata.Recordable(
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
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long lastModifierId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long id() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public UUID guid() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long creatorId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public LocalDateTime creationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Base base() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public User user() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String module() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Profile profile() throws IOException {
			return Profile.EMPTY;
		}

		@Override
		public void changeProfile(Profile newProfile) throws IOException {
			// TODO Auto-generated method stub
			
		}
	};
}
