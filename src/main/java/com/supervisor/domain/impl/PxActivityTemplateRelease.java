package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.utils.Version;
import com.supervisor.sdk.utils.VersionImpl;
import com.supervisor.copying.generating.ActivityGenerating;
import com.supervisor.copying.templating.ActivityTemplating;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplateRelease;

public final class PxActivityTemplateRelease extends DomainRecordable<ActivityTemplateRelease> implements ActivityTemplateRelease {

	public PxActivityTemplateRelease(Record<ActivityTemplateRelease> record) throws IOException {
		super(record);
	}

	@Override
	public ActivityTemplate template() throws IOException {
		Record<ActivityTemplate> rec = record.of(ActivityTemplateRelease::template);
		return new PxActivityTemplate(rec);
	}

	@Override
	public String version() throws IOException {
		return record.valueOf(ActivityTemplateRelease::version);
	}

	@Override
	public String notes() throws IOException {
		return record.valueOf(ActivityTemplateRelease::notes);
	}

	@Override
	public void update(String version, String notes) throws IOException {
		
		record.isRequired(ActivityTemplateRelease::version, version);
		record.isRequired(ActivityTemplateRelease::notes, notes);
		
		Version templateVersion = new VersionImpl(template().version());
		
		if(templateVersion.isNewThan(version))
			throw new IllegalArgumentException(String.format("Vous devez spécifier une version supérieure ou égale à la version actuelle du modèle (%s) !", templateVersion.value()));
		
		record.entryOf(ActivityTemplateRelease::version, version)
		      .entryOf(ActivityTemplateRelease::notes, notes)
		      .update();
		
		// mettre à jour la version du modèle
		record.listOf(Activity.class)
		      .get(template().id())
		      .entryOf(Activity::version, version)
		      .update();
					
	}

	@Override
	public void build(Activity source) throws IOException {	
		new ActivityTemplating(source, template()).copy();
	}

	@Override
	public void applyTo(Activity target) throws IOException {
		new ActivityGenerating(template(), target).copy();
	}

}
