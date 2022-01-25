package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.impl.PxAllProfiles;
import com.minlessika.membership.xe.XeProfile;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

public final class TkProfile extends TkBaseWrap {

	public TkProfile(final Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);

				    XeSource xeProfiles = new XeProfile(new PxAllProfiles(base).orderBy(Profile::module, Profile::name));
				    
					return new RsPage(
                            "/xsl/profile.xsl",
							req,
							base,
							()-> new Sticky<>(
								new XeAppend("menu", "profile"),
								xeProfiles
							)
					);
				}
		);
	}	
}
