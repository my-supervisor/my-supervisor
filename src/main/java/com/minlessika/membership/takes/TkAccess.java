package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.impl.PxAllAccesses;
import com.minlessika.membership.xe.XeAccess;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
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
