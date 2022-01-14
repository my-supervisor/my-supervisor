package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.Arrays;

import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.copying.templating.ActivityTemplating;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.ActivityTemplateSubscription;
import com.minlessika.supervisor.domain.ActivityTemplates;

public final class PgActivityTemplates extends DomainRecordables<ActivityTemplate, ActivityTemplates> implements ActivityTemplates {

	private final User user;
	
	public PgActivityTemplates(final User user) throws IOException {
		super(PxActivityTemplate.class, viewSource(user));
		
		this.user = user;
	}
	
	private PgActivityTemplates(final RecordSet<ActivityTemplate> source, final User user) throws IOException {
		super(PxActivityTemplate.class, source);
		
		this.user = user;
	}
	
	private static RecordSet<ActivityTemplate> viewSource(final User user) throws IOException{
		Table table = new TableImpl(ActivityTemplate.class);
		
		String viewScript = String.format("(\r\n" + 
											"	select DISTINCT ON (src.id) src.* \r\n" + 
					                        "   from %s as src \r\n" + 
											"	left join %s as target on target.template_id = src.id \r\n" +
											"   where (src.owner_id=%s or target.user_id=%s) and src.is_template=true" + 
										  ") as %s",
										table.name(),
										new TableImpl(ActivityTemplateSubscription.class).name(),										
										user.id(),
										user.id(),
										table.name()
							);
		
		return user.base()
				   .select(ActivityTemplate.class, viewScript);
	}

	@Override
	protected ActivityTemplates domainSetOf(final RecordSet<ActivityTemplate> source) throws IOException {
		return new PgActivityTemplates(source, user);
	}
	
	@Override
	public ActivityTemplate add(Activity activity) throws IOException {
		
		Activity newActivity = new ActivityTemplating(user, activity).copy();
		ActivityTemplate template = new PxActivityTemplate(newActivity);
		
		// créer la release	par défaut
		template.releases().add(activity, "1.0.0.0", template.description());
		
		return template;
	}
	
	@Override
	public void remove(ActivityTemplate item) throws IOException {

		// détacher les activités filles pour éviter de les supprimer automatiquement
		base().update(
				  String.format(
						"update %s \r\n"
					  + "set template_src_id = NULL \r\n"
					  + "where template_src_id = ?",
					  new TableImpl(Activity.class).name()
				  ),			  
				  Arrays.asList(item.id())
		    );
		
		item.indicators().remove();
		
		super.remove(item);
	}
}
