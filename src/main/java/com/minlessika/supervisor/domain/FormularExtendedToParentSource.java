package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.interaction.Interactable;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_formular_extended_to_parent_source", 
	label="Formular extended to parent source"
)
public interface FormularExtendedToParentSource extends Recordable, Interactable {

	@Field(
		label="Model",
		rel=Relation.MANY2ONE
	)
	FormularExtendedToParentExpression expr() throws IOException;
	
	@Field(
		label="Model",
		rel=Relation.MANY2ONE,
		ignore=true
	)
	DataModel model() throws IOException;
	
	@Field(
		label="List source",
		rel=Relation.MANY2ONE
	)
	ListDataFieldSource listSource() throws IOException;
	
	@Field(
		label="Field",
		rel=Relation.MANY2ONE
	)
	EditableDataField field() throws IOException;
	
	@Field(label="Is active ?", ignore=true)
	boolean active() throws IOException;
	
	void activate(boolean active) throws IOException;
	
	void update(EditableDataField field) throws IOException;
	
	boolean isBasedOn(DataModel model) throws IOException;
	boolean isStrictBasedOn(DataModel model) throws IOException;
	
	final FormularExtendedToParentSource EMPTY = new FormularExtendedToParentSource() {
		
		@Override
		public void update(EditableDataField field) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public EditableDataField field() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean active() throws IOException {
			// TODO Auto-generated method stub
			return false;
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
		public LocalDateTime creationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long creatorId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long lastModifierId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long ownerId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String tag() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Base base() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FormularExtendedToParentExpression expr() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ListDataFieldSource listSource() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean interacting() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void activate(boolean active) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DataModel model() throws IOException {
			// TODO Auto-generated method stub
			return null;
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
