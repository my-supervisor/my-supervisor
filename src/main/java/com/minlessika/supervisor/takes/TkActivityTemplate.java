package com.minlessika.supervisor.takes;

import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.ActivityTemplates;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeActivityTemplate;
import com.minlessika.supervisor.xe.XeSupervisor;

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
							"/com/supervisor/xsl/activity_template.xsl",
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
