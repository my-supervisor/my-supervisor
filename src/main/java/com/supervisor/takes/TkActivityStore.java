package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeSupervisor;

public final class TkActivityStore extends TkBaseWrap {

	public TkActivityStore(final Base base) {
		super(
				base, 
				req ->{
					final Supervisor module = new PxSupervisor(base, req);
					XeSource xeSupervisor = new XeSupervisor(module);
					
					return new RsPage(
							"/xsl/activity_store.xsl",
							req,
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "store"),
									new XeAppend("submenu", "activity"),
									xeSupervisor
							)
					);
				}
		);
	}	
}
