package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.sdk.utils.ListOfUniqueRecord;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityParams;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.AggregatedModels;
import com.supervisor.domain.DataModels;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheetModels;
import com.supervisor.domain.ResourceType;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.Sharing;
import com.supervisor.indicator.BasePeriodicity;
import com.supervisor.indicator.Indicators;
import com.supervisor.indicator.impl.PxBasePeriodicity;
import com.supervisor.indicator.impl.PxIndicators;
import com.supervisor.interaction.Interaction;
import com.supervisor.interaction.Interactions;
import com.supervisor.interaction.InteractionsImpl;

public final class PxActivity extends DomainRecordable<Activity> implements Activity {
	
	public PxActivity(final Record<Activity> source) throws IOException {
		super(source);
	}
	
	@Override
	public String name() throws IOException {
		return record.valueOf(Activity::name);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(Activity::description);
	}
	
	@Override
	public void update(String name, String description) throws IOException {
		
		record.isRequired(Activity::name, name);
		
		record.entryOf(Activity::name, name)
			  .entryOf(Activity::description, description)
			  .update();
	}

	@Override
	public boolean defaultShown() throws IOException {
		return record.valueOf(Activity::defaultShown);
	}

	@Override
	public void setDefaultShown() throws IOException {
		
		record.entryOf(Activity::defaultShown, true)
	    	  .update();
		
		// rendre tous les autres non affichables par défaut
		RecordSet<Activity> recordSet = record.listOf(Activity.class)
				                              .where(Activity::ownerId, base().currentUserId());

		for (Record<Activity> rec : recordSet.items()) {
			if(!rec.id().equals(record.id())) {
				rec.entryOf(Activity::defaultShown, false)
		    	  	  .update();
			}
		}
	}

	@Override
	public User owner() throws IOException {
		return new DmUser(
			record.of(User.class, ownerId())
		);
	}

	@Override
	public Indicators indicators() throws IOException {
		return new PxIndicators(this);
	}

	@Override
	public boolean isTemplate() throws IOException {
		return record.valueOf(Activity::isTemplate);
	}

	@Override
	public DataSheetModels forms() throws IOException {
		return new PgDataSheetModels(new OwnerOf(this), this);
	}
	
	@Override
	public DataSheetModels formsOf(User user) throws IOException {
		return new PgDataSheetModels(user, this);
	}

	@Override
	public ActivityParams params() throws IOException {
		return new PgActivityParams(this);
	}

	@Override
	public ActivityTemplate templateSrc() throws IOException {
		Long id = record.valueOf(Activity::templateSrc);
		if(id == null)
			return ActivityTemplate.EMPTY;
		
		Record<ActivityTemplate> recordTpl = record.of(Activity::templateSrc);
		return new PxActivityTemplate(recordTpl);	
	}

	@Override
	public BasePeriodicity periodicity() throws IOException {
		Record<BasePeriodicity> rec = record.of(Activity::periodicity);
		return new PxBasePeriodicity(rec);
	}

	@Override
	public void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException {
		
		record.mustCheckThisCondition(
				number > 0, 
				"Le nombre de la période doit être un entier positif !"
		);

		record.mustCheckThisCondition(
				!closeInterval || (unit == PeriodicityUnit.MONTHLY || unit == PeriodicityUnit.YEARLY), 
				"La ferméture de l'intervalle s'effectue avec les unités de temps Mois et Année uniquement !"
		);
		
		record.mustCheckThisCondition(
				unit != PeriodicityUnit.NONE, 
				"Vous devez spécifier la périodicité !"
		);
		
		record.of(BasePeriodicity.class, periodicity().id())
		      .entryOf(BasePeriodicity::number, number)
		      .entryOf(BasePeriodicity::unit, unit)
		      .entryOf(BasePeriodicity::reference, reference)
		      .entryOf(BasePeriodicity::closeInterval, closeInterval)
		      .update();
	}

	@Override
	public String version() throws IOException {
		return record.valueOf(Activity::version);
	}

	@Override
	public boolean isUpToDate() throws IOException {
		if(templateSrc() == ActivityTemplate.EMPTY)
			return true;
		else
			return version().equals(templateSrc().version());
	}

	@Override
	public Interactions interactions() throws IOException {
		return new InteractionsImpl(this);
	}

	@Override
	public List<Activity> actors() throws IOException {
		
		List<Activity> activities = new ListOfUniqueRecord<>();
		
		Activities activitiesPermitted = new PgActivities(new OwnerOf(this)).where(Activity::isTemplate, isTemplate());
		for (Interaction interaction : interactions().actives()) {
			Activity actor = interaction.actor();
			if(activitiesPermitted.contains(actor)) {
				activities.add(actor);
			}
		}
		
		return activities;
	}

	@Override
	public List<Activity> receivers() throws IOException {

		final List<Activity> items = new ArrayList<>();
		for (Activity activity : new PgActivities(new OwnerOf(this)).where(Activity::isTemplate, isTemplate()).items()) {
			for (Interaction interaction : activity.interactions().items()) {
				if(interaction.actor().id().equals(id())) {
					items.add(interaction.receiver());
				}
			}
		}
		
		return items;
	}

	@Override
	public DataModels dataModels() throws IOException {
		return new PgDataModels(this);
	}

	@Override
	public AggregatedModels aggregatedModels() throws IOException {
		return new PgAggregatedModels(this);
	}
	
	@Override
	public Sharing sharing() throws IOException {
		return new PxSharing(record.listOf(SharedResource.class))
					.where(SharedResource::type, ResourceType.ACTIVITY)
				    .where(SharedResource::resourceId, id());
	}

	@Override
	public List<DataSheetModel> primaryForms() throws IOException {
		final List<DataSheetModel> forms = new ArrayList<>();
		// take all source models without structures
		for (DataSheetModel form : forms().items()) {
			if(new DataSheetModelTableWrappingImpl(form).isStructure())
				continue;
			
			forms.add(form);
		}
		
		return forms;
	}

	@Override
	public boolean interactsWith(Activity activity) throws IOException {
		
		for (Activity actor : actors()) {
			if(actor.equals(activity))
				return true;
		}
		
		for (Activity receiver : receivers()) {
			if(receiver.equals(activity))
				return true;
		}
		
		return false;
	}
}
