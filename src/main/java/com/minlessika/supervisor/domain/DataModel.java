package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.indicator.Indicators;
import com.minlessika.supervisor.interaction.Interactable;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_data_model", 
		label="Modèle de données"
)
public interface DataModel extends Recordable, Interactable {
		
	@Field(
		label="Activité",
		rel=Relation.MANY2ONE
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
		public Activity activity() throws IOException {
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
		public DataFields fields() throws IOException {
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
		public DataModelType type() throws IOException {
			// TODO Auto-generated method stub
			return null;
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
		public Indicators indicatorsThatDependsOn() throws IOException {
			// TODO Auto-generated method stub
			return null;
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
		public AggregatedModels aggregatedModels() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean interacting() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}
	};
}
