package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.interaction.Interactable;

@com.supervisor.sdk.metadata.Recordable(
	name="supervisor_formular_extended_to_parent_source", 
	label="Formular extended to parent source"
)
public interface FormularExtendedToParentSource extends com.supervisor.sdk.datasource.Recordable, Interactable {

	@Field(
		label="Model",
		rel= Relation.MANY2ONE
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
			
			
		}
		
		@Override
		public EditableDataField field() throws IOException {
			
			return null;
		}
		
		@Override
		public boolean active() throws IOException {
			
			return false;
		}

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
		public FormularExtendedToParentExpression expr() throws IOException {
			
			return null;
		}

		@Override
		public ListDataFieldSource listSource() throws IOException {
			
			return null;
		}

		@Override
		public boolean interacting() throws IOException {
			
			return false;
		}

		@Override
		public void activate(boolean active) throws IOException {
			
			
		}

		@Override
		public DataModel model() throws IOException {
			
			return null;
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
