package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import com.supervisor.domain.CommentsScored;
import com.supervisor.domain.Profile;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.domain.ActivityTemplateReleases;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityParams;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplateLike;
import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.ActivityTemplateRequest;
import com.supervisor.domain.ActivityTemplateSubscription;
import com.supervisor.domain.ActivityTemplateView;
import com.supervisor.domain.AggregatedModels;
import com.supervisor.domain.DataModels;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheetModels;
import com.supervisor.domain.Sharing;
import com.supervisor.domain.TemplateAvailability;
import com.supervisor.domain.TemplateLicense;
import com.supervisor.domain.TemplateState;
import com.supervisor.indicator.BasePeriodicity;
import com.supervisor.indicator.Indicators;
import com.supervisor.interaction.Interactions;

public final class PxActivityTemplatePublished extends DomainRecordable<ActivityTemplatePublished> implements ActivityTemplatePublished {
	
	private final ActivityTemplate origin;
	
	public PxActivityTemplatePublished(final ActivityTemplate origin) throws IOException {
		super(origin.listOf(ActivityTemplatePublished.class).get(origin.id()));
		
		this.origin = origin;
	}
	
	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public List<String> tags() throws IOException {
		return origin.tags();
	}

	@Override
	public TemplateState state() throws IOException {
		return origin.state();
	}

	@Override
	public TemplateLicense license() throws IOException {
		return origin.license();
	}

	@Override
	public User designer() throws IOException {
		return origin.designer();
	}

	@Override
	public void update(String name, String description) throws IOException {
		origin.update(name, description); 
	}

	@Override
	public void tags(List<String> tags) throws IOException {
		origin.tags(tags); 
	}

	@Override
	public boolean isTemplate() throws IOException {
		return origin.isTemplate();
	}

	@Override
	public String description() throws IOException {
		return origin.description();
	}

	@Override
	public String icon() throws IOException {
		return record.valueOf(ActivityTemplatePublished::icon);
	}

	@Override
	public Long nbViews() throws IOException {
		return record.valueOf(ActivityTemplatePublished::nbViews);
	}

	@Override
	public Long nbLikes() throws IOException {
		return record.valueOf(ActivityTemplatePublished::nbLikes);
	}

	@Override
	public Long nbPaid() throws IOException {
		return record.valueOf(ActivityTemplatePublished::nbPaid);
	}

	@Override
	public Long nbSubscriptions() throws IOException {
		return record.valueOf(ActivityTemplatePublished::nbSubscriptions);
	}

	@Override
	public double score() throws IOException {
		return record.valueOf(ActivityTemplatePublished::score);
	}

	@Override
	public double price() throws IOException {
		return record.valueOf(ActivityTemplatePublished::price);
	}

	@Override
	public TemplateAvailability availability() throws IOException {
		return record.valueOf(ActivityTemplatePublished::availability);
	}

	@Override
	public CommentsScored advices() throws IOException {
		
		return null;
	}

	@Override
	public void defineIcon(String icon) throws IOException {
		record.entryOf(ActivityTemplatePublished::icon, icon)
		      .update();
	}

	@Override
	public void changeLicense(TemplateLicense license) throws IOException {
		record.entryOf(ActivityTemplatePublished::license, license)
	      .update();
	}

	@Override
	public void changePrice(double amount) throws IOException {
		record.entryOf(ActivityTemplatePublished::price, amount)
	      	.update();
	}

	@Override
	public void makeObsolete() throws IOException {
		Record<ActivityTemplate> recordTpl = record.listOf(ActivityTemplate.class).get(record.id());
		recordTpl.entryOf(ActivityTemplate::state, TemplateState.OBSOLETE)
	      	  	 .update();
	}

	@Override
	public void makeAvailable() throws IOException {
		Record<ActivityTemplate> recordTpl = record.listOf(ActivityTemplate.class).get(record.id());
		recordTpl.entryOf(ActivityTemplate::state, TemplateState.PUBLISHED)
	      	  .update();
	}

	@Override
	public void like() throws IOException {
		
		User user = new UserOf(this);
		
		if(likedBy(user))
			return;
		
		record.listOf(ActivityTemplateLike.class)
		      .entryOf(ActivityTemplateLike::user, user.id())
		      .entryOf(ActivityTemplateLike::template, id())
		      .add();
		
		record.entryOf(ActivityTemplatePublished::nbLikes, nbLikes() + 1)
		      .update();
	}

	@Override
	public void view() throws IOException {
		User user = new UserOf(this);
		
		if(viewedBy(user))
			return;
		
		record.listOf(ActivityTemplateView.class)
		      .entryOf(ActivityTemplateView::user, user.id())
		      .entryOf(ActivityTemplateView::template, id())
		      .add();
		
		record.entryOf(ActivityTemplatePublished::nbViews, nbViews() + 1)
	      	  .update();
	}

	@Override
	public void pay() throws IOException {
		
		
	}

	@Override
	public void subscribe() throws IOException {
		
		User user = new UserOf(this);
		
		// vérifier que l'utilisateur n'est pas le concepteur de l'activité
		if(user.own(this))
			throw new IllegalArgumentException("Vous êtes le concepteur de cette activité !");
		
		RecordSet<ActivityTemplateSubscription> subscriptions = record.listOf(ActivityTemplateSubscription.class);
		
		// vérifier que l'utilisateur ne s'est pas déjà abonné à l'activité
		if(
			subscriptions.where(ActivityTemplateSubscription::user, user.id())
				         .where(ActivityTemplateSubscription::template, id())
				         .any()
		  )
			throw new IllegalArgumentException("Vous êtes déjà abonné à cette activité !");
		
		subscriptions.entryOf(ActivityTemplateSubscription::user, user.id())
	      			 .entryOf(ActivityTemplateSubscription::template, id())
      			 	 .add();
	
		record.entryOf(ActivityTemplatePublished::nbSubscriptions, nbSubscriptions() + 1)
    	  	  .update();
	}

	@Override
	public boolean likedBy(User user) throws IOException {
		return record.listOf(ActivityTemplateLike.class)
				     .where(ActivityTemplateLike::user, user.id())
				     .where(ActivityTemplateLike::template, id())
				     .any();
	}

	@Override
	public boolean viewedBy(User user) throws IOException {
		return record.listOf(ActivityTemplateView.class)
				     .where(ActivityTemplateView::user, user.id())
				     .where(ActivityTemplateView::template, id())
				     .any();
	}

	@Override
	public Activity generate(ActivityTemplateRequest request) throws IOException {
		return origin.generate(request);
	}

	@Override
	public boolean defaultShown() throws IOException {
		return origin.defaultShown();
	}

	@Override
	public Indicators indicators() throws IOException {
		return origin.indicators();
	}

	@Override
	public User owner() throws IOException {
		return origin.owner();
	}

	@Override
	public void setDefaultShown() throws IOException {
		origin.setDefaultShown();
	}

	@Override
	public DataSheetModels forms() throws IOException {
		return origin.forms();
	}

	@Override
	public DataSheetModels formsOf(User user) throws IOException {
		return origin.formsOf(user);
	}

	@Override
	public AggregatedModels aggregatedModels() throws IOException {
		return origin.aggregatedModels();
	}

	@Override
	public ActivityParams params() throws IOException {
		return origin.params();
	}

	@Override
	public ActivityTemplate templateSrc() throws IOException {
		return origin.templateSrc();
	}

	@Override
	public BasePeriodicity periodicity() throws IOException {
		return origin.periodicity();
	}

	@Override
	public void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException {
		origin.periodicity(number, unit, reference, closeInterval);
	}

	@Override
	public Profile profile() throws IOException {
		Record<Profile> rec = record.of(ActivityTemplatePublished::profile);
		return new PxProfile(rec);
	}

	@Override
	public void changeProfile(Profile profile) throws IOException {
		
		record.mustCheckThisCondition(
				profile != Profile.EMPTY, 
				"Vous devez renseigner un profil !" 
		);
		
		record.entryOf(ActivityTemplatePublished::profile, profile.id())
		      .update(); 
	}

	@Override
	public ActivityTemplateReleases releases() throws IOException {
		return origin.releases();
	}

	@Override
	public String version() throws IOException {
		return origin.version();
	}

	@Override
	public boolean isUpToDate() throws IOException {
		return origin.isUpToDate();
	}

	@Override
	public Interactions interactions() throws IOException {
		return origin.interactions();
	}

	@Override
	public List<Activity> actors() throws IOException {
		return origin.actors();
	}

	@Override
	public List<Activity> receivers() throws IOException {
		return origin.receivers();
	}

	@Override
	public void changeDesigner(User newDesigner) throws IOException {
		origin.changeDesigner(newDesigner); 
	}

	@Override
	public DataModels dataModels() throws IOException {
		return origin.dataModels();
	}

	@Override
	public Sharing sharing() throws IOException {
		throw new UnsupportedOperationException("PxActivityTemplatePublished#sharing"); 
	}

	@Override
	public List<DataSheetModel> primaryForms() throws IOException {
		return origin.primaryForms();
	}

	@Override
	public boolean interactsWith(Activity activity) throws IOException {
		return origin.interactsWith(activity);
	}

}
