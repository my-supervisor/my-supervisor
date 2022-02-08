package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.SharedResource;
import com.supervisor.domain.Resource;
import com.supervisor.domain.ResourceType;
import com.supervisor.domain.Sharing;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeResource;
import com.supervisor.xe.XeSharedResource;
import com.supervisor.xe.XeSupervisor;

import java.util.UUID;

public final class TkSharing extends TkBaseWrap {

	public TkSharing(final Base base) {
		super(
				base, 
				req -> {

					final Supervisor module = new PxSupervisor(base, req);
					Sharing sharing = module.sharing();
					Smart params = new RqHref.Smart(req);
					
					ResourceType type = ResourceType.valueOf(params.single("type"));
					UUID resourceId = UUID.fromString(params.single("resource"));
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
							"/xsl/sharing.xsl",
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
