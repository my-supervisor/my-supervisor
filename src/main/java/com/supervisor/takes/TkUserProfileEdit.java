package com.supervisor.takes;

import com.supervisor.domain.Currency;
import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.utils.OptUUID;
import com.supervisor.xe.XeCountry;
import com.supervisor.xe.XeCurrency;
import com.supervisor.xe.XeLanguage;
import com.supervisor.xe.XeMembership;
import com.supervisor.xe.XeTimeZone;
import com.supervisor.xe.XeUserProfile;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkUserProfileEdit extends TkForm {

	public TkUserProfileEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/user_profile_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {

		final Membership module = new DmMembership(base, req);
		
		List<Currency> currencies = new ArrayList<>();
		for (Currency currency : module.currencies().items()) {
			if(currency.code().equals("EUR") || currency.code().equals("XOF")) {
				currencies.add(currency);
			}
		}
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeMembership(module));
		content.add(new XeCountry(module.countries()));
		content.add(new XeLanguage(module.languages()));
		content.add(new XeTimeZone(module.timeZones()));
		content.add(new XeCurrency(currencies));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {

		final User user = new RqUser(base, req);
		XeSource xeProfile = new XeUserProfile("item", user);
		
		return new XeChain(
				xeProfile
		);
	}
	
	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		return XeSource.EMPTY;
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeUserProfile(dir);
	}	
}
