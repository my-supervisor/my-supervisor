package com.supervisor.takes;

import com.supervisor.domain.Access;
import com.supervisor.domain.impl.PxAllAccesses;
import com.supervisor.xe.XeAccess;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

public final class TkAccess extends TkBaseWrap {

	public TkAccess(final Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
				    XeSource xeAccesses = new XeAccess(new PxAllAccesses(base).orderBy(Access::name));
				    
					return new RsPage(
                            "/xsl/access.xsl",
							req,
							base,
							()-> new Sticky<>(
								new XeAppend("menu", "access"),
								xeAccesses
							)
					);
				}
		);
	}	
}
