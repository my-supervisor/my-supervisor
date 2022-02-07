package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.ActivityTemplatesPublished;
import com.supervisor.domain.TemplateAvailability;
import com.supervisor.domain.TemplateLicense;
import com.supervisor.domain.TemplateState;

public final class PgActivityTemplatesPublished extends DomainRecordables<ActivityTemplatePublished, ActivityTemplatesPublished> implements ActivityTemplatesPublished {

	private final User user;
	
	public PgActivityTemplatesPublished(final User user) throws IOException {
		super(PxActivityTemplatePublished.class, viewSource(user));
		
		this.user = user;
	}
	
	private PgActivityTemplatesPublished(final RecordSet<ActivityTemplatePublished> source, final User user) throws IOException {
		super(PxActivityTemplatePublished.class, source);
		
		this.user = user;
	}
	
	@Override
	protected ActivityTemplatesPublished domainSetOf(final RecordSet<ActivityTemplatePublished> source) throws IOException {
		return new PgActivityTemplatesPublished(source, user);
	}
	
	@Override
	protected ActivityTemplatePublished domainOf(final Record<ActivityTemplatePublished> record) throws IOException {
		return new PxActivityTemplatePublished(
				new PxActivityTemplate(
						record.of(ActivityTemplate.class, record.id())
				)
		);			
	}
		
	private static RecordSet<ActivityTemplatePublished> viewSource(final User user) throws IOException{
		Table table = new TableImpl(ActivityTemplatePublished.class);
		
		String viewScript = String.format("(\r\n" + 
											"	select src.*, target.name, target.tags, target.state, target.license, target.designer_id \r\n"+ 
					                        "   from %s as src\r\n" + 
											"	left join %s as target on target.id = src.id\r\n" + 
										  ") as %s",
							table.name(),
							new TableImpl(ActivityTemplate.class).name(),
							table.name()
				);
		
		return user.base().select(ActivityTemplatePublished.class, viewScript);
	}
	
	@Override
	public ActivityTemplatePublished publish(ActivityTemplate template, String icon) throws IOException {
		
		source.isRequired(ActivityTemplatePublished::icon, icon);
		
		if(template.designer().id() != base().currentUserId())
			throw new IllegalArgumentException("Vous ne pouvez pas publier une activité dont vous n'êtes pas le concepteur !");
		
		Record<ActivityTemplatePublished> record = source.entryOf(ActivityTemplatePublished::id, template.id())
				 .entryOf(ActivityTemplatePublished::icon, icon)
				 .entryOf(ActivityTemplatePublished::nbViews, 0)
				 .entryOf(ActivityTemplatePublished::nbLikes, 0)
				 .entryOf(ActivityTemplatePublished::nbPaid, 0)
				 .entryOf(ActivityTemplatePublished::nbSubscriptions, 0)
				 .entryOf(ActivityTemplatePublished::score, 0)
				 .entryOf(ActivityTemplatePublished::price, 0)
				 .entryOf(ActivityTemplatePublished::availability, TemplateAvailability.FREE)
				 .entryOf(ActivityTemplatePublished::profile, new UserOf(this).profile().id())
				 .add();

		Record<ActivityTemplate> recordTpl = record.of(ActivityTemplate.class, record.id());
		recordTpl.entryOf(ActivityTemplate::license, TemplateLicense.AGPL_3)
				 .entryOf(ActivityTemplate::state, TemplateState.PUBLISHED)
		         .update();
		
		return domainOf(record);
	}
}
