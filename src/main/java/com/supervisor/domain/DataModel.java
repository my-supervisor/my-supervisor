package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.indicator.Indicators;
import com.supervisor.interaction.Interactable;

@com.supervisor.sdk.metadata.Recordable(
		name="supervisor_data_model", 
		label="Modèle de données"
)
public interface DataModel extends com.supervisor.sdk.datasource.Recordable, Interactable {
		
	@Field(
		label="Activité",
		rel= Relation.MANY2ONE
	)
	Activity activity() throws IOException;
	
	@Field(label="Is in interaction ?")	
	boolean interacting() throws IOException;
	
	@Field(label="Est un modèle ?")
	boolean isTemplate() throws IOException;
	
	@Field(label="Type")
	DataModelType type() throws IOException;
	
	@Field(label="Code")
	String code() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Description")
	String description() throws IOException;
	
	DataFields fields() throws IOException;
	AggregatedModels aggregatedModels() throws IOException;
	Indicators indicatorsThatDependsOn() throws IOException;
	
	boolean dependsOn(DataModel model) throws IOException;
	boolean strictDependsOn(DataModel model) throws IOException;
	
	@Field(label="Active")
	boolean active() throws IOException;
	
	void update(String code, String name, String description) throws IOException;
	
	void activate(boolean active) throws IOException;
	void templating(boolean template) throws IOException;
	
	DataModel EMPTY = new DataModel() {

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
		public Activity activity() throws IOException {
			
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
		public DataFields fields() throws IOException {
			
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
		public DataModelType type() throws IOException {
			
			return null;
		}

		@Override
		public boolean isTemplate() throws IOException {
			
			return false;
		}

		@Override
		public void templating(boolean template) throws IOException {
			
			
		}

		@Override
		public Indicators indicatorsThatDependsOn() throws IOException {
			
			return null;
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
		public AggregatedModels aggregatedModels() throws IOException {
			
			return null;
		}

		@Override
		public boolean interacting() throws IOException {
			
			return false;
		}
	};
}
