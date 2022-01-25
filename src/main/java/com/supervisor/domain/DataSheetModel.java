package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sharing.Sharable;
import com.supervisor.indicator.Indicators;

@com.supervisor.sdk.metadata.Recordable(
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
			
			return null;
		}

		@Override
		public DataModelType type() throws IOException {
			
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
		public boolean active() throws IOException {
			
			return false;
		}

		@Override
		public void update(String code, String name, String description) throws IOException {
			
			
		}

		@Override
		public void activate(boolean active) throws IOException {
			
			
		}

		@Override
		public Long id() {
			return 0L;
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
		public boolean canMergeAtSameDate() throws IOException {
			
			return false;
		}

		@Override
		public ExtendedDataSheetModels parents() throws IOException {
			
			return null;
		}

		@Override
		public Sharing sharing() throws IOException {
			
			return null;
		}

		@Override
		public DataSheetOfModels sheets() throws IOException {
			
			return null;
		}

		@Override
		public AggregatedModels aggregatedModels() throws IOException {
			
			return null;
		}

		@Override
		public Indicators indicatorsThatDependsOn() throws IOException {
			
			return null;
		}

		@Override
		public void merging(boolean canMergeAtSameDate) throws IOException {
			
			
		}

		@Override
		public List<DataFieldOfSheet> generateFields() throws IOException {
			
			return null;
		}

		@Override
		public void importDataFrom(DataSheetModel source) throws IOException {
			
			
		}

		@Override
		public ExtendedDataSheetModels children() throws IOException {
			
			return null;
		}

		@Override
		public DataFields fields() throws IOException {
			
			return null;
		}

		@Override
		public void initialize() throws IOException {
			
			
		}

		@Override
		public boolean isTemplate() throws IOException {
			
			return false;
		}

		@Override
		public void templating(boolean template) throws IOException {
			
			
		}

		@Override
		public EditableDataFields editableFields() throws IOException {
			
			return null;
		}

		@Override
		public EditableDataFields scalarEditableFields() throws IOException {
			
			return null;
		}

		@Override
		public boolean isTable() throws IOException {
			
			return false;
		}

		@Override
		public boolean dependsOn(DataModel model) throws IOException {
			
			return false;
		}

		@Override
		public boolean strictDependsOn(DataModel model) throws IOException {
			
			return false;
		}

		@Override
		public boolean interacting() throws IOException {
			
			return false;
		}
		
	};
}
