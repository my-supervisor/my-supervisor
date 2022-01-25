package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XeUserProfile;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
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
