package com.supervisor.sdk.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.ResultStatement;
import org.apache.commons.lang.StringUtils;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class XeMIdentity extends XeWrap {

	public XeMIdentity(final Request req, final Base base) throws IOException {
		super(transform(req, base));
	}
	
	private static XeSource transform(final Request req, final Base base) throws IOException {
		if(base == Base.EMPTY)
    		return XeSource.EMPTY;
		
		final Identity identity = new RqAuth(req).identity();
		
		final XeSource result;
		if(identity == Identity.ANONYMOUS) {
			final Long id = 2L;
			result = XeSource.EMPTY;
		} else {
			final Long id = Long.parseLong(identity.properties().get("id"));
			final List<ResultStatement> results = base.query(
					"select "
							+ "pers.photo, pers.name, addr.email, pers.is_company, pers.photo "
							+ "from base_person as pers "
							+ "left join base_address as addr on addr.id = pers.address_id "
							+ "where pers.id=?",
					Arrays.asList(id)
			);
			final Map<String, Object> data = results.get(0).data();

			final String module = base.appInfo().currentModule();
			final List<ResultStatement> resultsProfile = base.query(
					"select "
							+ "prof.id as profile_id, prof.name as profile, prof.tag as profile_tag "
							+ "from base_application as app "
							+ "left join membership_profile as prof on prof.id = app.profile_id "
							+ "where app.user_id=? and app.module = ?",
					Arrays.asList(id, module)
			);

			final String PROFILE_TAG = "profile_tag";

			final Map<String, Object> dataProfile;
			if(resultsProfile.isEmpty()) {
				dataProfile = new LinkedHashMap<>();
				dataProfile.put("profile_id", 0);
				dataProfile.put("profile", StringUtils.EMPTY);
				dataProfile.put(PROFILE_TAG, StringUtils.EMPTY);
			} else {
				dataProfile = resultsProfile.get(0).data();
			}

			result = new XeSource() {

				@Override
				public Iterable<Directive> toXembly() throws IOException {
					return new Directives()
							.add("identity")
							.add("id").set(id).up()
							.add("name").set(data.get("name")).up()
							.add("email").set(data.get("email")).up()
							.add("profile").set(dataProfile.get("profile")).up()
							.add("profile_id").set(dataProfile.get("profile_id")).up()
							.add(PROFILE_TAG).set(dataProfile.get(PROFILE_TAG)).up()
							.add("is_admin").set(String.format("ADMIN_%s", module.toUpperCase()).equals(dataProfile.get(PROFILE_TAG))).up()
							.add("is_anonymous").set(String.format("ANONYMOUS_%s", module.toUpperCase()).equals(dataProfile.get(PROFILE_TAG))).up()
							.add("is_simple_user").set(String.format("USER_%s", module.toUpperCase()).equals(dataProfile.get(PROFILE_TAG))).up()
							.add("is_company").set(data.get("is_company")).up()
							.add("photo").set(data.get("photo")).up()
							.up();
				}
			};
		}
		return result;
	}

}
