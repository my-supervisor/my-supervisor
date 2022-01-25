package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.interaction.Interactable;

@com.supervisor.sdk.metadata.Recordable(
	name="supervisor_formular_extended_to_model_source", 
	label="Formular extended to model source"
)
public interface FormularExtendedToModelSource extends com.supervisor.sdk.datasource.Recordable, Interactable {

	@Field(
		label="Expression",
		rel= Relation.MANY2ONE
	)
	FormularExtendedToModelExpression expr() throws IOException;
	
	@Field(
		label="Model",
		rel=Relation.MANY2ONE
	)
	DataSheetModel model() throws IOException;
	
	AggregatedModel expressionModel() throws IOException;
	
	@Field(
		label="Model field",
		rel=Relation.MANY2ONE
	)
	EditableDataField modelField() throws IOException;
	
	@Field(label="Comparator")
    Comparator comparator() throws IOException;
	
	@Field(
		label="Reference",
		rel=Relation.MANY2ONE
	)
	DataField reference() throws IOException;
	
	@Field(
		label="Field to extend",
		rel=Relation.MANY2ONE
	)
	EditableDataField fieldToExtend() throws IOException;
	
	@Field(label="Is active ?")
	boolean active() throws IOException;
	
	@Field(label="Is interacting ?")
	boolean interacting() throws IOException;
	
	void activate(boolean active) throws IOException;
	
	void update(EditableDataField modelField, Comparator comparator, DataField reference, EditableDataField fieldToExtend) throws IOException;
	
	boolean isBasedOn(DataModel model) throws IOException;
	boolean isStrictBasedOn(DataModel model) throws IOException;
	
	final FormularExtendedToModelSource EMPTY = new FormularExtendedToModelSource() {

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
		public boolean interacting() throws IOException {
			
			return false;
		}

		@Override
		public FormularExtendedToModelExpression expr() throws IOException {
			
			return null;
		}

		@Override
		public DataSheetModel model() throws IOException {
			
			return null;
		}

		@Override
		public EditableDataField modelField() throws IOException {
			
			return null;
		}

		@Override
		public Comparator comparator() throws IOException {
			
			return null;
		}

		@Override
		public DataField reference() throws IOException {
			
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
		public void update(EditableDataField modelField, Comparator comparator, DataField reference, EditableDataField fieldToExtend)
				throws IOException {
			
			
		}

		@Override
		public boolean isBasedOn(DataModel model) throws IOException {
			
			return false;
		}

		@Override
		public boolean isStrictBasedOn(DataModel model) throws IOException {
			
			return false;
		}

		@Override
		public AggregatedModel expressionModel() throws IOException {
			
			return null;
		}

		@Override
		public EditableDataField fieldToExtend() throws IOException {
			
			return null;
		}
		
	};
}
