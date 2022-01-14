package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.supervisor.data.sharing.Sharable;
import com.minlessika.supervisor.indicator.Indicators;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_data_sheet_model", 
	label="Data sheet model",
	comodel=DataModel.class
)
public interface DataSheetModel extends DataModel, Sharable {
	
	@Field(label="Peut fusionner ?")
	boolean canMergeAtSameDate() throws IOException;
	
	EditableDataFields scalarEditableFields() throws IOException;
	EditableDataFields editableFields() throws IOException;
	
	ExtendedDataSheetModels parents() throws IOException;
	ExtendedDataSheetModels children() throws IOException;
	
	Sharing sharing() throws IOException;
	DataSheetOfModels sheets() throws IOException;
	
	void merging(boolean canMergeAtSameDate) throws IOException;
	
	List<DataFieldOfSheet> generateFields() throws IOException;
	
	void initialize() throws IOException;
	void importDataFrom(DataSheetModel source) throws IOException;
	
	boolean isTable() throws IOException;
	
	DataSheetModel EMPTY = new DataSheetModel() {

		@Override
		public Activity activity() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataModelType type() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String code() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String name() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String description() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean active() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void update(String code, String name, String description) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void activate(boolean active) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Long id() {
			return 0L;
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
		public boolean canMergeAtSameDate() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ExtendedDataSheetModels parents() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Sharing sharing() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataSheetOfModels sheets() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AggregatedModels aggregatedModels() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Indicators indicatorsThatDependsOn() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void merging(boolean canMergeAtSameDate) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<DataFieldOfSheet> generateFields() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void importDataFrom(DataSheetModel source) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ExtendedDataSheetModels children() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataFields fields() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void initialize() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isTemplate() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void templating(boolean template) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public EditableDataFields editableFields() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EditableDataFields scalarEditableFields() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isTable() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean dependsOn(DataModel model) throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean strictDependsOn(DataModel model) throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean interacting() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}
		
	};
}
