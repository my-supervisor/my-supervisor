package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sharing.Sharable;

@com.supervisor.sdk.metadata.Recordable(
		name="supervisor_data_sheet", 
		label="Feuille de données"
)
public interface DataSheet extends com.supervisor.sdk.datasource.Recordable, Sharable {
	
	@Field(
		label="Modèle d'origine", 
		rel= Relation.MANY2ONE
	)
	DataSheetModel model() throws IOException;
	
	@Field(label="Référence")
	String reference() throws IOException;
	
	@Field(label="Date")
	LocalDate date() throws IOException;
	
	User owner() throws IOException;
	User creator() throws IOException;
	
	DataFieldOfSheets fields() throws IOException;
	IndicatorDependencies indicatorsThatDependsOn() throws IOException;
	
	void update(LocalDate date) throws IOException;
	void validate() throws IOException;
	
	DataSheet EMPTY = new DataSheet() {
		
		@Override
		public String tag() throws IOException {
			return null;
		}
		
		@Override
		public UUID ownerId() throws IOException {
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
		public void validate() throws IOException {
			
		}
		
		@Override
		public void update(LocalDate date) throws IOException {
			
		}
		
		@Override
		public String reference() throws IOException {
			return null;
		}
		
		@Override
		public User owner() throws IOException {
			return null;
		}
		
		@Override
		public DataSheetModel model() throws IOException {
			return DataSheetModel.EMPTY;
		}
		
		@Override
		public DataFieldOfSheets fields() throws IOException {
			return null;
		}
		
		@Override
		public LocalDate date() throws IOException {
			return null;
		}
		
		@Override
		public User creator() throws IOException {
			return null;
		}

		@Override
		public Base base() {
			return Base.EMPTY;
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
		public Activity activity() throws IOException {

			return null;
		}

		@Override
		public IndicatorDependencies indicatorsThatDependsOn() throws IOException {

			return null;
		}
	};
}
