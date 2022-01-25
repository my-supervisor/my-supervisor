package com.minlessika.supervisor.takes;

import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkNoActivity extends TkBaseWrap {

	public TkNoActivity(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);				
				    XeSource xeSupervisor = new XeSupervisor(module);    
			   
					return new RsPage(
							"/xsl/no_activity.xsl",
							req, 
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "home"),
									xeSupervisor
							)
					);
				}
		);
	}	
}
