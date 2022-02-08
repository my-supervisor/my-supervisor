package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.codecs.MCodec;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.takes.XeRecaptcha;
import com.supervisor.sdk.translation.I18n;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.codecs.Codec;
import org.takes.misc.Utf8String;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TkPasswordForgottenChanged extends TkForm {

	public TkPasswordForgottenChanged(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/password_forgotten_changed.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		List<XeSource> content = new ArrayList<>();
		
		final String key = new RqHref.Smart(req).single("key");
		
		content.add(itemToShow);
		content.add(new XeAppend("key", key));
		// content.add(new XeRecaptcha(base.appInfo()));
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(OptUUID id, Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		Membership module = new DmMembership(base);
		final String key = new RqHref.Smart(req).single("key");
		
		final Codec codec = new MCodec();

		Identity idt = codec.decode(
		            new Utf8String(key).asBytes()
		       );
		
		// vérifier la validité du lien
		LocalDate expirationDate = LocalDate.parse(
										idt.properties()
										.get("expiration_date")
								   );
		
		if(expirationDate.isBefore(LocalDate.now()))
			throw new IllegalArgumentException(I18n.localizeText("the_link_has_expired"));
		
		Optional<User> optUser = module.members()
		                       .userOf(idt.properties().get("email"));
		
		if(!optUser.isPresent())
			throw new IllegalArgumentException(I18n.localizeText("this_link_is_no_long_valid"));;

		return XeSource.EMPTY;
	}
	
	protected XeSource postItemDataToShow(final Long id, final Request req, final RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return XeSource.EMPTY;
	}
}
