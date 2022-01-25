package com.supervisor.takes;

import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.supervisor.domain.ResourceType;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.Subscription;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkSharingSubscriberDelete extends TkBaseWrap {

	public TkSharingSubscriberDelete(Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					Smart params = new RqHref.Smart(req);
					
					ResourceType type = ResourceType.valueOf(params.single("type"));
					Long resourceId = Long.parseLong(params.single("resource"));
									
					Subscription itemsToDelete = module.subscription()
													   .where(SharedResource::resourceId, resourceId)
													   .where(SharedResource::type, type);
					
					String url;
					switch (type) {
						case DATA_SHEET_MODEL:
							url = "/collect/model";
							break;
						case ACTIVITY:
						case INDICATOR:
							url = "/home";
							break;
						default:
							url = "/home";
							break;
					}
					
					itemsToDelete.remove();
						
					return new RsForward(
						new RsFlash(
			                "Vous ne suivez plus cette resource !",
			                Level.FINE
			            ),
						url
				    );
				}
		);
	}
}
