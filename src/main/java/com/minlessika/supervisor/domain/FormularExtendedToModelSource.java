package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.interaction.Interactable;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_formular_extended_to_model_source", 
	label="Formular extended to model source"
)
public interface FormularExtendedToModelSource extends Recordable, Interactable {

	@Field(
		label="Expression",
		rel=Relation.MANY2ONE
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
		public boolean interacting() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public FormularExtendedToModelExpression expr() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataSheetModel model() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EditableDataField modelField() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Comparator comparator() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataField reference() throws IOException {
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
		public void update(EditableDataField modelField, Comparator comparator, DataField reference, EditableDataField fieldToExtend)
				throws IOException {
			// TODO Auto-generated method stub
			
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

		@Override
		public AggregatedModel expressionModel() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EditableDataField fieldToExtend() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
}
