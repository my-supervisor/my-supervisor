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
	name="membership_profile", 
	label="Profil"
)
public interface Profile extends Recordable {
	
	final String ADMIN_TAG = "ADMIN";
	final String ANONYMOUS_TAG = "ANONYMOUS";
	final String USER_TAG = "USER";
	
	@Field(label="Module")
	String module() throws IOException;
	
	@Field(
		label="Profil parent",
		rel=Relation.MANY2ONE,
		isMandatory=false
	)
	Profile parent() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Code")
	String code() throws IOException;
	
	ProfileAccesses accesses() throws IOException;
	Accesses exceptedAccesses() throws IOException;
	
	void update(String code, String name) throws IOException;
	
	boolean hasAccess(String code) throws IOException;
	boolean hasAccess(String code, String value) throws IOException;
	void validateAccessibility(String code) throws IOException;
	void validateAccessibility(String code, String value) throws IOException;
	void changeParent(Profile profile) throws IOException;
	boolean isUpperOrEqualTo(Profile profile) throws IOException;
	
	boolean isAdmin() throws IOException;
	boolean isAnonymous() throws IOException;
	boolean isSimpleUser() throws IOException;
	
	Profile EMPTY = new Profile() {
		
		@Override
		public void update(String code, String name) throws IOException {
			throw new UnsupportedOperationException("#update");
		}
		
		@Override
		public String name() throws IOException {
			return null;
		}
		
		@Override
		public String code() throws IOException {
			return null;
		}
		
		@Override
		public ProfileAccesses accesses() throws IOException {
			throw new UnsupportedOperationException("#accesses");
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
		public LocalDateTime creationDate() throws IOException {
			return null;
		}

		@Override
		public Long creatorId() throws IOException {
			return null;
		}

		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			return null;
		}

		@Override
		public Long lastModifierId() throws IOException {
			return null;
		}

		@Override
		public Long ownerId() throws IOException {
			return null;
		}

		@Override
		public String tag() throws IOException {
			return null;
		}

		@Override
		public boolean hasAccess(String access) throws IOException {
			return false;
		}

		@Override
		public boolean hasAccess(String access, String value) throws IOException {
			return false;
		}

		@Override
		public void validateAccessibility(String access) throws IOException {
			throw new UnsupportedOperationException("#validateAccessibility1code");
		}

		@Override
		public void validateAccessibility(String access, String value) throws IOException {
			throw new UnsupportedOperationException("#validateAccessibility2code");
		}

		@Override
		public Base base() {
			return Base.EMPTY;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			return null;
		}

		@Override
		public Accesses exceptedAccesses() throws IOException {
			throw new UnsupportedOperationException("#exceptedAccesses");
		}

		@Override
		public boolean isAdmin() throws IOException {
			return false;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}

		@Override
		public Profile parent() throws IOException {
			
			return null;
		}

		@Override
		public void changeParent(Profile profile) throws IOException {
			
			
		}

		@Override
		public boolean isUpperOrEqualTo(Profile profile) throws IOException {
			
			return false;
		}

		@Override
		public String module() throws IOException {
			
			return null;
		}

		@Override
		public boolean isAnonymous() throws IOException {
			
			return false;
		}

		@Override
		public boolean isSimpleUser() throws IOException {
			
			return false;
		}
	};
}
