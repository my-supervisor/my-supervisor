package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.data.sharing.Sharable;
import com.minlessika.supervisor.interaction.Interactable;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_list_data_field_source", 
	label="List data field source"
)
public interface ListDataFieldSource extends Recordable, Sharable, Interactable {
	
	@Field(
		label="Field",
		rel=Relation.MANY2ONE
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
			// TODO Auto-generated method stub
			return null;
		}
		
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
		public void update(DataModel model, DataField fieldToDisplay, DataField orderField) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public DataField orderField() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DataModel model() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DataField fieldToDisplay() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ListDataField field() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean active() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void activate(boolean active) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean interacting() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isBasedOn(DataModel model) throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isStrictBasedOn(DataModel model) throws IOException {
			// TODO Auto-generated method stub
			return false;
		}
	};
}
