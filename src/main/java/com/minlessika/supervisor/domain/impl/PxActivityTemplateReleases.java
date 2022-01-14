package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.utils.Version;
import com.minlessika.sdk.utils.VersionImpl;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.ActivityTemplateRelease;
import com.minlessika.supervisor.domain.ActivityTemplateReleases;

public final class PxActivityTemplateReleases extends DomainRecordables<ActivityTemplateRelease, ActivityTemplateReleases> implements ActivityTemplateReleases {

	private final ActivityTemplate template;
	
	public PxActivityTemplateReleases(final ActivityTemplate template) throws IOException {
		this(template.listOf(ActivityTemplateRelease.class), template);
	}
	
	public PxActivityTemplateReleases(final RecordSet<ActivityTemplateRelease> source, final ActivityTemplate template) throws IOException {
		super(PxActivityTemplateRelease.class, source);
		
		this.template = template;
		this.source = source.where(ActivityTemplateRelease::template, template.id())
				            .orderBy(ActivityTemplateRelease::id, OrderDirection.DESC);
	}
	
	@Override
	protected ActivityTemplateReleases domainSetOf(final RecordSet<ActivityTemplateRelease> source) throws IOException {
		return new PxActivityTemplateReleases(source, template);
	}

	@Override
	public ActivityTemplateRelease add(Activity source, String version, String notes) throws IOException {
		
		this.source.isRequired(ActivityTemplateRelease::version, version); 		
		this.source.isRequired(ActivityTemplateRelease::notes, notes);
				
		Version templateVersion = new VersionImpl(template.version());
		
		// vérifier que la version est la même pour le nouveau modèle
		if(isEmpty() && !templateVersion.isSameThan(version))
			throw new IllegalArgumentException(String.format("Vous devez spécifier la même version que la version actuelle du modèle (%s) !", templateVersion.value()));
		
		// vérifier que la version est nouvelle pour l'ancien modèle
		if(any() && !templateVersion.isOldThan(version))
			throw new IllegalArgumentException(String.format("Vous devez spécifier une version supérieure à la version actuelle du modèle (%s) !", templateVersion.value()));		
		
		Record<ActivityTemplateRelease> record = this.source.entryOf(ActivityTemplateRelease::version, version)
													   		.entryOf(ActivityTemplateRelease::notes, notes)
												   			.entryOf(ActivityTemplateRelease::template, template.id())  
												   			.add(); 
		
		ActivityTemplateRelease release = domainOf(record);
		
		if(count() > 1) {				
			release.build(source);
		}
			
		return release;
	}
}
