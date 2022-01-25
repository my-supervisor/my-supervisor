package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.supervisor.domain.Application;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityParams;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.AggregatedModels;
import com.supervisor.domain.DataModels;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheetModels;
import com.supervisor.domain.Sharing;
import com.supervisor.indicator.BasePeriodicity;
import com.supervisor.indicator.Indicators;
import com.supervisor.interaction.Interactions;

public class ActivityWrap implements Activity {

	private final Activity origin;
	
	public ActivityWrap(final Activity origin) {
		this.origin = origin;
	}
	
	@Override
	public Long id() {
		return origin.id();
	}

	@Override
	public UUID guid() throws IOException {
		return origin.guid();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return origin.creationDate();
	}

	@Override
	public Long creatorId() throws IOException {
		return origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return origin.lastModificationDate();
	}

	@Override
	public Long lastModifierId() throws IOException {
		return origin.lastModifierId();
	}

	@Override
	public Long ownerId() throws IOException {
		return origin.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return origin.tag();
	}

	@Override
	public String description() throws IOException {
		return origin.description();
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public Base base() {
		return origin.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return origin.listOf(clazz);
	}

	@Override
	public boolean isTemplate() throws IOException {
		return origin.isTemplate();
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
	public void update(String name, String description) throws IOException {
		origin.update(name, description); 
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
	public ActivityParams params() throws IOException {
		return origin.params();
	}

	@Override
	public ActivityTemplate templateSrc() throws IOException {
		return origin.templateSrc();
	}
	
	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return origin.listOf(clazz, viewScript);
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
	public DataModels dataModels() throws IOException {
		return origin.dataModels();
	}

	@Override
	public AggregatedModels aggregatedModels() throws IOException {
		return origin.aggregatedModels();
	}

	@Override
	public Sharing sharing() throws IOException {
		return origin.sharing();
	}

	@Override
	public List<DataSheetModel> primaryForms() throws IOException {
		return origin.primaryForms();
	}

	@Override
	public boolean interactsWith(Activity activity) throws IOException {
		return interactsWith(activity);
	}

	@Override
	public Application appOwner() throws IOException {
		return origin.appOwner();
	}

	@Override
	public void manageBy(Application app) throws IOException {
		origin.manageBy(app);
	}
}
