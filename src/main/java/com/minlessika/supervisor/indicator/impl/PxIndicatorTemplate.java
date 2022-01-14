package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;
import java.util.List;

import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.TemplateState;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorTemplate;

public final class PxIndicatorTemplate extends IndicatorWrap implements IndicatorTemplate {

	private final Record<IndicatorTemplate> record;
	
	public PxIndicatorTemplate(final Record<IndicatorTemplate> record) throws IOException {
		this(
				new IndicatorBase(
						record.of(
								Indicator.class, 
								record.id()
						)
				)
		); 
	}
	
	public PxIndicatorTemplate(final Indicator origin) throws IOException {
		super(origin);
		
		if(!origin.isTemplate())
			throw new IllegalArgumentException("Cet indicateur n'est pas un modèle !");
		
		this.record = origin.base()
				            .select(
				            		IndicatorTemplate.class, 
				            		origin.id()
				            );
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(IndicatorTemplate::name);
	}

	@Override
	public TemplateState state() throws IOException {
		return record.valueOf(IndicatorTemplate::state);
	}

	@Override
	public void update(String name, String description) throws IOException {
		
		record.isRequired(IndicatorTemplate::name, name);
		
		record.entryOf(IndicatorTemplate::name, name)
		      .entryOf(IndicatorTemplate::description, description)
		      .update();
	}

	@Override
	public List<String> tags() throws IOException {
		return record.valueOf(IndicatorTemplate::tags);
	}

	@Override
	public void tags(List<String> tags) throws IOException {
		record.entryOf(IndicatorTemplate::tags, tags)
    	  	  .update();
	}
}
