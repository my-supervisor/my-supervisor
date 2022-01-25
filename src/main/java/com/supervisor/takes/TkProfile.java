package com.supervisor.takes;

import com.supervisor.domain.Profile;
import com.supervisor.domain.impl.PxAllProfiles;
import com.supervisor.xe.XeProfile;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
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
