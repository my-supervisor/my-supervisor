package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
		name="data_field",
		label="Champ de données"
)
public interface DataField extends com.supervisor.sdk.datasource.Recordable {
	
	@Field(
		label="Modèle de données", 
		rel= Relation.MANY2ONE
	)
	DataModel model() throws IOException;
	
	@Field(label="Code")
	String code() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Description")
	String description() throws IOException;
	
	@Field(label="Type")
	DataFieldType type() throws IOException;
	
	@Field(label="Style")
	DataFieldStyle style() throws IOException;
	
	DataFieldDependencies dependencies() throws IOException;
	
	void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException;
	
	DataField EMPTY = new DataField() {

		@Override
		public UUID id() {
			return null;
		}

		@Override
		public LocalDateTime creationDate() throws IOException {
			
			return null;
		}

		@Override
		public UUID creatorId() throws IOException {
			
			return null;
		}

		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}

		@Override
		public UUID lastModifierId() throws IOException {
			
			return null;
		}

		@Override
		public UUID ownerId() throws IOException {
			
			return null;
		}

		@Override
		public String tag() throws IOException {
			
			return null;
		}

		@Override
		public Base base() {
			
			return null;
		}

		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
			return null;
		}

		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}

		@Override
		public DataModel model() throws IOException {
			
			return null;
		}

		@Override
		public String code() throws IOException {
			
			return null;
		}

		@Override
		public String name() throws IOException {
			
			return null;
		}

		@Override
		public String description() throws IOException {
			
			return null;
		}

		@Override
		public DataFieldType type() throws IOException {
			
			return null;
		}

		@Override
		public DataFieldStyle style() throws IOException {
			
			return null;
		}

		@Override
		public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
				throws IOException {
			
			
		}

		@Override
		public DataFieldDependencies dependencies() throws IOException {
			
			return null;
		}
		
	};
}
