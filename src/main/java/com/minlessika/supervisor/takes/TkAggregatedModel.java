package com.minlessika.supervisor.takes;

import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.UserAggregatedModels;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeAggregatedModel;
import com.minlessika.supervisor.xe.XeSupervisor;

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
							"/com/supervisor/xsl/aggregated_model.xsl",
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
