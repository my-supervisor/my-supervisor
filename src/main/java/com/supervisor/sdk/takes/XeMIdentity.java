/*
 * Copyright (c) 2018-2022 Minlessika
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.supervisor.sdk.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.ResultStatement;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directives;

/**
 * XeSource to collect user info.
 *
 * @since 1.0
 */
public final class XeMIdentity extends XeWrap {

	/**
	 * Profile tag.
	 */
	private static final String PROFILE_TAG = "profile_tag";

	/**
	 * Ctor.
	 * @param req Request
	 * @param base Data source
	 * @throws IOException If fails
	 */
	public XeMIdentity(final Request req, final Base base) throws IOException {
		super(transform(req, base));
	}

	/**
	 * Transform user info to XeSource.
	 * @param req Request
	 * @param base Data source
	 * @return XeSource
	 * @throws IOException If fails
	 */
	private static XeSource transform(
		final Request req, final Base base
	) throws IOException {
		final Identity identity = new RqAuth(req).identity();
		final XeSource result;
		if(identity == Identity.ANONYMOUS) {
			result = XeSource.EMPTY;
		} else {
			final UUID id = UUID.fromString(identity.properties().get("id"));
			final List<ResultStatement> results = base.query(
				String.join(
					System.lineSeparator(),
					"select",
					"pers.photo, pers.name, addr.email, pers.is_company",
					"from base_person as pers",
					"left join base_address as addr on addr.id = pers.address_id",
					"where pers.id=?"
				),
				Arrays.asList(id)
			);
			final Map<String, Object> data = results.get(0).data();
			final List<ResultStatement> resultsProfile = base.query(
				String.join(
					System.lineSeparator(),
					"select",
					"prof.id as profile_id, prof.name as profile, prof.code as profile_tag",
					"from membership_user as app_user",
					"left join membership_profile as prof on prof.id = app_user.profile_id",
					"where app_user.id=?"
				),
				Arrays.asList(id)
			);
			final Map<String, Object> profile;
			if(resultsProfile.isEmpty()) {
				profile = new LinkedHashMap<>();
				profile.put("profile_id", 0);
				profile.put("profile", StringUtils.EMPTY);
				profile.put(XeMIdentity.PROFILE_TAG, StringUtils.EMPTY);
			} else {
				profile = resultsProfile.get(0).data();
			}
			result = new XeDirectives(
				new Directives()
					.add("identity")
					.add("id").set(id).up()
					.add("name").set(data.get("name")).up()
					.add("email").set(data.get("email")).up()
					.add("profile").set(profile.get("profile")).up()
					.add("profile_id").set(profile.get("profile_id")).up()
					.add(XeMIdentity.PROFILE_TAG).set(
						profile.get(XeMIdentity.PROFILE_TAG)
					).up()
					.add("is_admin").set("ADMIN".equals(profile.get(PROFILE_TAG))).up()
					.add("is_anonymous").set("ANONYMOUS".equals(profile.get(PROFILE_TAG))).up()
					.add("is_simple_user").set("USER".equals(profile.get(PROFILE_TAG))).up()
					.add("is_company").set(data.get("is_company")).up()
					.add("photo").set(data.get("photo")).up()
					.up()
			);
		}
		return result;
	}

}
