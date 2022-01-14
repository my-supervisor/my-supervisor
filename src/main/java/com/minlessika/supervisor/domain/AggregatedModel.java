package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.data.sharing.Sharable;
import com.minlessika.supervisor.indicator.Indicators;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_aggregated_model", 
	label="Modèle agrégé",
	comodel=DataModel.class
)
public interface AggregatedModel extends DataModel, Sharable {
	
	@Field(
		label="Date de référence",
		rel=Relation.MANY2ONE
	)
	DataField dateReference() throws IOException;
	
	@Field(
		label="Modèle à agréger",
		rel=Relation.MANY2ONE
	)
	DataModel model() throws IOException;
	
	@Field(
		label="Modèle de base",
		rel=Relation.MANY2ONE
	)
	DataSheetModel coreModel() throws IOException;
	
	@Field(
		label="Activity de base",
		rel=Relation.MANY2ONE,
		ignore=true
	)
	Activity coreActivity() throws IOException;
	
	EditableDataFields baseFields() throws IOException;
	ParamDataFields params() throws IOException;
	ModelFilters filters() throws IOException;
	FormularDataFields formulars() throws IOException;
	
	List<DataSheetModel> compatibleModels() throws IOException;
	List<DataFieldCompatibility> compatibilityOf(DataSheetModel model) throws IOException;
	boolean isCompatibleWith(DataSheetModel model) throws IOException;
	void update(String name, DataField dateReference) throws IOException;
	
	AggregatedModel EMPTY = new AggregatedModel() {
		
		@Override
		public String tag() throws IOException {
			return StringUtils.EMPTY;
		}
		
		@Override
		public Long ownerId() throws IOException {
			return null;
		}
		
		@Override
		public Long lastModifierId() throws IOException {
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			return null;
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
		public Long creatorId() throws IOException {
			return null;
		}
		
		@Override
		public LocalDateTime creationDate() throws IOException {
			return null;
		}
		
		@Override
		public DataModel model() throws IOException {
			return null;
		}

		@Override
		public ModelFilters filters() throws IOException {
			throw new UnsupportedOperationException("#filters");
		}

		@Override
		public FormularDataFields formulars() throws IOException {
			throw new UnsupportedOperationException("#formulars");
		}

		@Override
		public ParamDataFields params() throws IOException {
			throw new UnsupportedOperationException("#params");
		}

		@Override
		public Base base() {
			return Base.EMPTY;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			throw new UnsupportedOperationException("#listOf");
		}

		@Override
		public List<DataSheetModel> compatibleModels() throws IOException {
			throw new UnsupportedOperationException("#compatibleModels");
		}

		@Override
		public List<DataFieldCompatibility> compatibilityOf(DataSheetModel model) throws IOException {
			throw new UnsupportedOperationException("#compatibilityOf");
		}

		@Override
		public boolean isCompatibleWith(DataSheetModel model) throws IOException {
			return false;
		}

		@Override
		public String name() throws IOException {
			return null;
		}

		@Override
		public boolean isTemplate() throws IOException {
			return false;
		}

		@Override
		public void update(String name, DataField dateReference) throws IOException {
			throw new UnsupportedOperationException("#update");
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
		public void templating(boolean template) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public EditableDataFields baseFields() throws IOException {
			// TODO Auto-generated method stub
			return null;
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
		public DataSheetModel coreModel() throws IOException {
			// TODO Auto-generated method stub
			return null;
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

		@Override
		public Activity coreActivity() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataField dateReference() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
