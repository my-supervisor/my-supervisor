package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
	name="supervisor_data_field_of_sheet", 
	label="Data field of sheet",
	inheritFields=false
)
public interface DataFieldOfSheet extends EditableDataField {
	
	@Field(
		label="Feuille de données", 
		rel= Relation.MANY2ONE
	)
	DataSheet sheet() throws IOException;
	
	@Field(
		name="origin_field_id", 
		label="Champ de données d'origine", 
		rel=Relation.MANY2ONE
	)
	EditableDataField origin() throws IOException;
	
	int columnView() throws IOException;
	
	@Field(label="Valeur", isMandatory=false)
	String value() throws IOException;
	
	void update(String value) throws IOException;
	
	DataFieldOfSheet EMPTY = new DataFieldOfSheet() {

		@Override
		public int order() throws IOException {
			
			return 0;
		}

		@Override
		public boolean isMandatory() throws IOException {
			
			return false;
		}

		@Override
		public UserScope userScope() throws IOException {
			
			return null;
		}

		@Override
		public void order(int order) throws IOException {
			
			
		}

		@Override
		public void makeMandatory(boolean mandatory) throws IOException {
			
			
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
		public DataSheet sheet() throws IOException {
			
			return null;
		}

		@Override
		public EditableDataField origin() throws IOException {
			
			return null;
		}

		@Override
		public int columnView() throws IOException {
			
			return 0;
		}

		@Override
		public String value() throws IOException {
			
			return null;
		}

		@Override
		public void update(String value) throws IOException {
			
			
		}

		@Override
		public DataSheetModel model() throws IOException {
			
			return null;
		}

		@Override
		public void changeUserScope(UserScope scope) throws IOException {
			
			
		}

		@Override
		public DataFieldDependencies dependencies() throws IOException {
			
			return null;
		}
		
	};
}
