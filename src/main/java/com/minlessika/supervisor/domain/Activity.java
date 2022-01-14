package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.sdk.time.PeriodicityUnit;
import com.minlessika.supervisor.indicator.BasePeriodicity;
import com.minlessika.supervisor.indicator.Indicators;
import com.minlessika.supervisor.interaction.Interactions;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_activity", 
	label="Activité"
)
public interface Activity extends Recordable {
	
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
		rel=Relation.MANY2ONE
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
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long lastModifierId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
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
		public Long creatorId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public LocalDateTime creationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Base base() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void update(String name, String description) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setDefaultShown() throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public ActivityParams params() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public User owner() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String name() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean isTemplate() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public Indicators indicators() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DataSheetModels formsOf(User user) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DataSheetModels forms() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String description() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean defaultShown() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public ActivityTemplate templateSrc() throws IOException {
			return ActivityTemplate.EMPTY;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BasePeriodicity periodicity() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String version() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isUpToDate() throws IOException {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AggregatedModels aggregatedModels() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Sharing sharing() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<DataSheetModel> primaryForms() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean interactsWith(Activity activity) throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Application appOwner() throws IOException {
			return Application.EMPTY;
		}

		@Override
		public void manageBy(Application app) throws IOException {
			// TODO Auto-generated method stub
			
		}
	};
}
