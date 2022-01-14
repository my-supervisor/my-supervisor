package com.minlessika.supervisor.takes;

import org.cactoos.iterable.Sticky;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.SharedResource;
import com.minlessika.supervisor.domain.Resource;
import com.minlessika.supervisor.domain.ResourceType;
import com.minlessika.supervisor.domain.Sharing;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeResource;
import com.minlessika.supervisor.xe.XeSharedResource;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkSharing extends TkBaseWrap {

	public TkSharing(final Base base) {
		super(
				base, 
				req -> {

					final Supervisor module = new PxSupervisor(base, req);
					Sharing sharing = module.sharing();
					Smart params = new RqHref.Smart(req);
					
					ResourceType type = ResourceType.valueOf(params.single("type"));
					Long resourceId = Long.parseLong(params.single("resource"));					
					Resource resource = module.resources().resource(type, resourceId);
					
					if(new RqUser(base, req).notOwn(resource)) {
						throw new IllegalArgumentException("Vous n'êtes pas propriétaire de cette ressource !");
					}
										
					final XeSource xeSource;
					if(sharing.any())
						xeSource = new XeSharedResource(
										sharing.where(SharedResource::type, type)
											   .where(SharedResource::resourceId, resource.id())
											   .items()
								   );
					else
						xeSource = XeSource.EMPTY;
					
					XeSource xeSupervisor = new XeSupervisor(module);
					XeSource xeResource = new XeResource(resource);
					
					return new RsPage(
							"/com/supervisor/xsl/sharing.xsl",
							req,
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "collect"),
									xeResource,
									xeSource,
									xeSupervisor
							)
					);
				}
		);
	}	
}
