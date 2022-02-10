package com.supervisor.takes;

import com.supervisor.domain.Address;
import com.supervisor.domain.Country;
import com.supervisor.domain.Currency;
import com.supervisor.domain.Language;
import com.supervisor.domain.Membership;
import com.supervisor.domain.Person;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.utils.OptUUID;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.Level;

public final class TkUserSave extends TkBaseWrap {

	public TkUserSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Membership module = new DmMembership(base, req);
					OptUUID id = new OptUUID(new RqHref.Smart(req).single("id", "0"));
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String name = form.single("name");
					final String photo = form.single("photo");
					final String email = form.single("email");
					final Boolean isCompany = Boolean.parseBoolean(form.single("is_company"));
					final TimeZone timeZone = TimeZone.getTimeZone(form.single("time_zone_id"));
					final Language preferredLanguage = module.languages().get(UUID.fromString(form.single("preferred_language_id")));
					final Currency preferredCurrency = module.currencies().get(UUID.fromString(form.single("preferred_currency_id")));
					final Country country = module.countries().get(UUID.fromString(form.single("country_id")));
					final String addressLine1 = form.single("address_line1");
					final String addressLine2 = form.single("address_line2", StringUtils.EMPTY);
					final String phone1 = form.single("phone1");
					final String phone2 = form.single("phone2", StringUtils.EMPTY);
					final String city = form.single("city");
					final String stateOrProvince = form.single("state_or_province");
					final String company = form.single("company", StringUtils.EMPTY);
					
					final User item;
					final String msg;
					final String ADMIN = "admin";
					
					if(id.isPresent()) {
						item = module.users().get(id.get());
						
						msg = String.format("L'utilisateur %s a été modifié avec succès !", item.name());
					} else {
						Person identity = module.contacts().add(name, email);
						item = module.users().registerUnSecure(identity, ADMIN, ADMIN);
						item.activate(true);
						msg = String.format("L'utilisateur %s a été créé avec succès avec le mot de passe par défaut < %s > !", item.name(), ADMIN);
					}
					
					item.update(name, photo, isCompany);
					final Address address = item.address();
					address.defineAddress(addressLine1, addressLine2, email);
					address.definePhone(phone1, phone2);
					address.defineCompany(company);
					address.defineLocation(city, stateOrProvince, country);
					item.changePreferredLanguage(preferredLanguage);
					item.changePreferredCurrency(preferredCurrency);
					item.changeTimeZone(timeZone);
					
					return new RsForward(
			            	new RsFlash(
				                msg,
				                Level.FINE
				            ),
				            "/admin/user"
					    );
				}
		);
	}
}
