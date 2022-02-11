package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sharing.Sharable;
import com.supervisor.interaction.Interactable;

@com.supervisor.sdk.metadata.Recordable(
	name="list_data_field_source",
	label="List data field source"
)
public interface ListDataFieldSource extends com.supervisor.sdk.datasource.Recordable, Sharable, Interactable {
	
	@Field(
		label="Field",
		rel= Relation.MANY2ONE
	)
	ListDataField field() throws IOException;
	
	@Field(
		label="Model",
		rel=Relation.MANY2ONE
	)
	DataModel model() throws IOException;
	
	@Field(
		label="Field to display",
		rel=Relation.MANY2ONE
	)
	DataField fieldToDisplay() throws IOException;
	
	@Field(
		label="Order field",
		rel=Relation.MANY2ONE
	)
	DataField orderField() throws IOException;
	
	@Field(label="Is active ?")
	boolean active() throws IOException;
	
	void activate(boolean active) throws IOException;
	
	void update(DataModel model, DataField fieldToDisplay, DataField orderField) throws IOException;
	
	boolean isBasedOn(DataModel model) throws IOException;
	boolean isStrictBasedOn(DataModel model) throws IOException;
	
	final ListDataFieldSource EMPTY = new ListDataFieldSource() {
		
		@Override
		public Activity activity() throws IOException {
			
			return null;
		}
		
		@Override
		public String tag() throws IOException {
			
			return null;
		}
		
		@Override
		public UUID ownerId() throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
			return null;
		}
		
		@Override
		public UUID lastModifierId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public UUID id() {
			
			return null;
		}
		
		@Override
		public UUID creatorId() throws IOException {
			
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
		public void update(DataModel model, DataField fieldToDisplay, DataField orderField) throws IOException {
			
			
		}
		
		@Override
		public DataField orderField() throws IOException {
			
			return null;
		}
		
		@Override
		public DataModel model() throws IOException {
			
			return null;
		}
		
		@Override
		public DataField fieldToDisplay() throws IOException {
			
			return null;
		}
		
		@Override
		public ListDataField field() throws IOException {
			
			return null;
		}
		
		@Override
		public boolean active() throws IOException {
			
			return false;
		}
		
		@Override
		public void activate(boolean active) throws IOException {
			
			
		}

		@Override
		public boolean interacting() throws IOException {
			
			return false;
		}

		@Override
		public boolean isBasedOn(DataModel model) throws IOException {
			
			return false;
		}

		@Override
		public boolean isStrictBasedOn(DataModel model) throws IOException {
			
			return false;
		}
	};
}
