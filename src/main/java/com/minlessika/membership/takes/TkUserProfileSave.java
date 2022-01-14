package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Address;
import com.minlessika.membership.domain.Country;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.Language;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.domain.impl.UserAdmin;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import java.util.TimeZone;
import java.util.logging.Level;

public final class TkUserProfileSave extends TkBaseWrap {

	public TkUserProfileSave(Base base) {
		super(
				base,
				req -> {
					final Membership module = new DmMembership(base, req);
					final User user = new RqUser(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					
					final String name = form.single("name");
					final String photo = form.single("photo");
					final TimeZone timeZone = TimeZone.getTimeZone(form.single("time_zone_id"));
					final String email = user.address().email();
					final Language preferredLanguage = module.languages().get(Long.parseLong(form.single("preferred_language_id")));
					final Currency preferredCurrency = module.currencies().get(Long.parseLong(form.single("preferred_currency_id")));
					final Country country = module.countries().get(Long.parseLong(form.single("country_id")));		
					final String addressLine1 = form.single("address_line1");
					final String addressLine2 = form.single("address_line2", StringUtils.EMPTY);
					final String phone1 = form.single("phone1");
					final String phone2 = form.single("phone2", StringUtils.EMPTY);
					final String city = form.single("city");
					final String stateOrProvince = form.single("state_or_province");
					final String company = form.single("company", StringUtils.EMPTY);
					
					user.update(name, photo, user.isCompany());
					final Address address = user.address();
					address.defineAddress(addressLine1, addressLine2, email);
					address.definePhone(phone1, phone2);
					address.defineCompany(company);
					address.defineLocation(city, stateOrProvince, country);
					user.changePreferredLanguage(preferredLanguage);
					user.changePreferredCurrency(preferredCurrency);
					user.changeTimeZone(timeZone);
					
					final boolean userComeFromUserAdminCountry = new UserAdmin(base).address().country().equals(country);
					user.billingAddress().changeVATStatus(userComeFromUserAdminCountry);
					
					final String msg = "Your profile has been updated with success !";
					
					return new RsForward(
			            	new RsFlash(
				                msg,
				                Level.FINE
				            ),
				            "/home"
					    );
				}
		);
	}
}
