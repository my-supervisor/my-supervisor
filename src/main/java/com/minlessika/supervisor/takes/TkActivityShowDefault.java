package com.minlessika.supervisor.takes;

import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Activities;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkActivityShowDefault extends TkBaseWrap {

	public TkActivityShowDefault(Base base) {
		super(
				base, 
				(req)->{
					final Supervisor module = new PxSupervisor(base, req);
					final Activities myActivities = module.activities();
					Smart params = new RqHref.Smart(req);			
					final Long id = Long.parseLong(params.single("id", "0"));
					
					Activity act = myActivities.get(id);
					act.setDefaultShown();
								
					return new RsForward(
						new RsFlash(
			                String.format("L'activité %s a été modifiée avec succès !", act.name())
			            ),
			            "/home?activity="+id
				    );
				}
		);
	}	
}
