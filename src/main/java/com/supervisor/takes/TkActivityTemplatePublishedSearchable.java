package com.supervisor.takes;

import java.util.Arrays;
import java.util.List;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.datasource.conditions.Filter;
import com.supervisor.sdk.datasource.conditions.pgsql.ConditionOperator;
import com.supervisor.sdk.datasource.conditions.pgsql.PgFilter;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.apache.commons.lang.StringUtils;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsJson;

import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.ActivityTemplatesPublished;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TemplateState;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivityTemplatePublishedJson;

public final class TkActivityTemplatePublishedSearchable extends TkBaseWrap {

	public TkActivityTemplatePublishedSearchable(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					Smart params = new RqHref.Smart(req);
		
					final String tagsToSearch = params.single("tags", "");
					final String facet = params.single("facet", "");
					final Long page = Long.parseLong(params.single("page"));
					final Long itemsPerPage = Long.parseLong(params.single("itemsPerPage"));
					
					List<String> tags = Arrays.asList(StringUtils.split(tagsToSearch, ','));
					ActivityTemplatesPublished templates = module.activityTemplatesPublished()
							                					  .where(ActivityTemplatePublished::state, TemplateState.PUBLISHED);
		
					switch(facet) {
						case "RECENTS":
							templates = templates.orderBy(ActivityTemplatePublished::creationDate, OrderDirection.DESC);
							break;
						case "MORE_VISITED":
							templates = templates.orderBy(ActivityTemplatePublished::nbViews, OrderDirection.DESC);
							break;
						case "MORE_USED":
							templates = templates.orderBy(ActivityTemplatePublished::nbSubscriptions, OrderDirection.DESC);
							break;
						case "POPULAR":
							templates = templates.orderBy(ActivityTemplatePublished::nbLikes, OrderDirection.DESC);
							break;
						default:
							break;
					}
					
					Filter<ActivityTemplatePublished> filter = new PgFilter<>(ActivityTemplatePublished.class, ConditionOperator.OR);
					
					for (String tag : tags) {
						filter.add(ActivityTemplatePublished::tags, Matchers.contains(tag));
						filter.add(ActivityTemplatePublished::name, Matchers.contains(tag));
					}									
					
					templates = templates.where(filter)
								         .start(itemsPerPage * (page - 1) + 1)
								         .limit(itemsPerPage);
					
					return new RsJson(
							new XeActivityTemplatePublishedJson(templates)
					);
				}
		);
	}	
}
