package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.indicator.BasePeriodicity;
import com.supervisor.indicator.Indicators;
import com.supervisor.interaction.Interactions;

@com.supervisor.sdk.metadata.Recordable(
	name="supervisor_activity", 
	label="Activité"
)
public interface Activity extends com.supervisor.sdk.datasource.Recordable {
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Description")
	String description() throws IOException;
	
	@Field(label="Afficher par défaut ?")
	boolean defaultShown() throws IOException;
	
	@Field(label="Version")
	String version() throws IOException;
	
	@Field(
		label="Application propriétaire", 
		rel= Relation.MANY2ONE
	)
	Application appOwner() throws IOException;
	
	@Field(label="Est un modèle ?")
	boolean isTemplate() throws IOException;
	
	boolean isUpToDate() throws IOException;
	
	@Field(
		label="Modèle source", 
		rel=Relation.MANY2ONE,
		isMandatory=false
	)
	ActivityTemplate templateSrc() throws IOException;
	
	@Field(
		label="Périodicité", 
		rel=Relation.MANY2ONE
	)
	BasePeriodicity periodicity() throws IOException;
	
	Indicators indicators() throws IOException;
	ActivityParams params() throws IOException;
	
	DataSheetModels forms() throws IOException;
	DataSheetModels formsOf(User user) throws IOException;
	List<DataSheetModel> primaryForms() throws IOException;
	DataModels dataModels() throws IOException;
	AggregatedModels aggregatedModels() throws IOException;
	Interactions interactions() throws IOException;
	List<Activity> actors() throws IOException;
	List<Activity> receivers() throws IOException;
	boolean interactsWith(Activity activity) throws IOException;
	void manageBy(Application app) throws IOException;
	
	User owner() throws IOException;
	
	void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException;
	void update(String name, String description) throws IOException;	
	void setDefaultShown() throws IOException;
	
	Sharing sharing() throws IOException;
	
	Activity EMPTY = new Activity() {
		
		@Override
		public String tag() throws IOException {
			
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
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
			
			return null;
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
		public Base base() {
			
			return null;
		}
		
		@Override
		public void update(String name, String description) throws IOException {
			
			
		}
		
		@Override
		public void setDefaultShown() throws IOException {
			
			
		}
		
		@Override
		public ActivityParams params() throws IOException {
			
			return null;
		}
		
		@Override
		public User owner() throws IOException {
			
			return null;
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
		public Indicators indicators() throws IOException {
			
			return null;
		}
		
		@Override
		public DataSheetModels formsOf(User user) throws IOException {
			
			return null;
		}
		
		@Override
		public DataSheetModels forms() throws IOException {
			
			return null;
		}
		
		@Override
		public String description() throws IOException {
			
			return null;
		}
		
		@Override
		public boolean defaultShown() throws IOException {
			
			return false;
		}

		@Override
		public ActivityTemplate templateSrc() throws IOException {
			return ActivityTemplate.EMPTY;
		}

		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}

		@Override
		public BasePeriodicity periodicity() throws IOException {
			
			return null;
		}

		@Override
		public void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException {
			
			
		}

		@Override
		public String version() throws IOException {
			
			return null;
		}

		@Override
		public boolean isUpToDate() throws IOException {
			
			return false;
		}

		@Override
		public Interactions interactions() throws IOException {
			return null;
		}

		@Override
		public List<Activity> actors() throws IOException {
			return null;
		}

		@Override
		public List<Activity> receivers() throws IOException {
			return null;
		}

		@Override
		public DataModels dataModels() throws IOException {
			
			return null;
		}

		@Override
		public AggregatedModels aggregatedModels() throws IOException {
			
			return null;
		}

		@Override
		public Sharing sharing() throws IOException {
			
			return null;
		}

		@Override
		public List<DataSheetModel> primaryForms() throws IOException {
			
			return null;
		}

		@Override
		public boolean interactsWith(Activity activity) throws IOException {
			
			return false;
		}

		@Override
		public Application appOwner() throws IOException {
			return Application.EMPTY;
		}

		@Override
		public void manageBy(Application app) throws IOException {
			
			
		}
	};
}
