package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplates;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivityTemplate;
import com.supervisor.xe.XeSupervisor;

public final class TkActivityTemplate extends TkBaseWrap {

	public TkActivityTemplate(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					ActivityTemplates myItems = module.activityTemplates()
							                           .orderBy(ActivityTemplate::id, OrderDirection.DESC);
					
					final XeSource xeSource;
					if(myItems.any())
						xeSource = new XeActivityTemplate(myItems);
					else
						xeSource = XeSource.EMPTY;
					
					XeSource xeSupervisor = new XeSupervisor(module);
					
					return new RsPage(
							"/xsl/activity_template.xsl",
							req, 
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "activities"),
									new XeAppend("submenu", "template"),
									xeSource,
									xeSupervisor
							)
					);
				}
		);
	}	
}
