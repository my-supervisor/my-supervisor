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
	comodel=Activity.class
)
public interface ActivityTemplate extends Activity {
	
	@Field(label="Tags", isMandatory=false)
	List<String> tags() throws IOException;
	
	@Field(label="Etat")
	TemplateState state() throws IOException;
	
	@Field(label="Licence")
	TemplateLicense license() throws IOException;
	
	@Field(
		label="Concepteur",
		rel= Relation.MANY2ONE
	)
	User designer() throws IOException;
	
	ActivityTemplateReleases releases() throws IOException;
	
	void tags(List<String> tags) throws IOException;
	
	Activity generate(ActivityTemplateRequest request) throws IOException;
	void changeDesigner(User newDesigner) throws IOException;
	
	ActivityTemplate EMPTY = new ActivityTemplate() {
		
		@Override
		public String tag() throws IOException {
			return null;
		}
		
		@Override
		public UUID ownerId() throws IOException {
			return null;
		}
		
		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			throw new UnsupportedOperationException("#listOf");
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
		public Base base() {
			return Base.EMPTY;
		}
		
		@Override
		public void update(String name, String description) throws IOException {
			throw new UnsupportedOperationException("#update");
		}
		
		@Override
		public void setDefaultShown() throws IOException {
			throw new UnsupportedOperationException("#setDefaultShown");
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
			throw new UnsupportedOperationException("#listOf");
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
		public List<String> tags() throws IOException {
			throw new UnsupportedOperationException("#tags");
		}

		@Override
		public TemplateState state() throws IOException {
			return TemplateState.NONE;
		}

		@Override
		public TemplateLicense license() throws IOException {
			return TemplateLicense.NONE;
		}

		@Override
		public User designer() throws IOException {
			return User.EMPTY;
		}

		@Override
		public void tags(List<String> tags) throws IOException {
			throw new UnsupportedOperationException("#tags");
		}

		@Override
		public Activity generate(ActivityTemplateRequest request) throws IOException {
			throw new UnsupportedOperationException("#generate");
		}

		@Override
		public DataSheetModels forms() throws IOException {
			throw new UnsupportedOperationException("#forms");
		}
		
		@Override
		public DataSheetModels formsOf(User user) throws IOException {
			throw new UnsupportedOperationException("#formsOf");
		}

		@Override
		public ActivityParams params() throws IOException {
			throw new UnsupportedOperationException("#params");
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
		public ActivityTemplateReleases releases() throws IOException {

			return null;
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
		public void changeDesigner(User newDesigner) throws IOException {
			throw new UnsupportedOperationException("#changeDesigner");
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

	};
}
