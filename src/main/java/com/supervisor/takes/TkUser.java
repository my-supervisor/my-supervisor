package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.xe.XeUserProfile;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

public final class TkUser extends TkBaseWrap {

	public TkUser(final Base base) {
		super(
				base, 
				req -> {
					
					new RqAdminAuth(base, req);
					
					Membership module = new DmMembership(base, req);
				    XeSource xeUsers = new XeUserProfile(module.users().orderBy(User::name));
				    
					return new RsPage(
                            "/xsl/user.xsl",
							req,
							base,
							()-> new Sticky<>(
								new XeAppend("menu", "user"),
								xeUsers
							)
					);
				}
		);
	}	
}
