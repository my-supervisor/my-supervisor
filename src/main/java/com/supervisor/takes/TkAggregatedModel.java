package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.UserAggregatedModels;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeAggregatedModel;
import com.supervisor.xe.XeSupervisor;

public final class TkAggregatedModel extends TkBaseWrap {

	public TkAggregatedModel(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final UserAggregatedModels myItems = module.aggregatedModels().where(AggregatedModel::isTemplate, false);  
					
					final XeSource xeSource;
					if(myItems.any())
						xeSource = new XeAggregatedModel(myItems.items());
					else
						xeSource = XeSource.EMPTY;
					
					XeSource xeSupervisor = new XeSupervisor(module);
					
					return new RsPage(
							"/xsl/aggregated_model.xsl",
							req, 
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "collect"),
									new XeAppend("submenu", "aggregated_model"),
									xeSource,
									xeSupervisor
							)
					);
				}
		);
	}	
}
