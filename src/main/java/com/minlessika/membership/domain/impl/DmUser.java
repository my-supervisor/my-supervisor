package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.BillingAddress;
import com.minlessika.membership.billing.PlannedTasks;
import com.minlessika.membership.billing.UserPaymentRequests;
import com.minlessika.membership.billing.impl.PgUserPaymentRequests;
import com.minlessika.membership.billing.impl.PxPlannedTasks;
import com.minlessika.membership.domain.Address;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Applications;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.Language;
import com.minlessika.membership.domain.Person;
import com.minlessika.membership.domain.PlanSubscriptionContracts;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.utils.UserHumanDateOf;
import com.minlessika.membership.utils.UserPrettyTimeOf;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.secure.EncryptedWord;
import com.minlessika.sdk.secure.EncryptedWordImpl;
import com.minlessika.sdk.time.TimePrinter;
import com.minlessika.sdk.utils.logging.Logger;
import com.minlessika.sdk.utils.logging.MLogger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

public final class DmUser extends DomainRecordable<User> implements User {

	private static final Logger logger = new MLogger(DmUser.class);
	
	private final Person origin;
	
	public DmUser(final Base base, long userId) throws IOException {
		super(base.select(User.class, userId));
		this.origin = new DmPerson(base.select(Person.class, userId));
	}
	
	public DmUser(final Record<User> source) throws IOException {
		super(source);
		
		this.origin = new DmPerson(source.of(Person.class, source.id())); 
	}

	@Override
	public String password() throws IOException {
		return record.valueOf(User::password);
	}

	@Override
	public void changePassword(String currentPwd, String newPwd, String newConfirmedPwd) throws IOException {	
		
		EncryptedWord encryptedPassword = new EncryptedWordImpl(password(), salt(), true);
		
		// Vérifier que l'actuel mot de passe est exact.
		record.mustCheckThisCondition(
				encryptedPassword.verify(currentPwd, true),  
				"L'ancien mot de passe ne correspond pas au mot de passe spécifié !"
		);
		
		record.isRequired(User::password, newPwd);	
		
		record.mustCheckThisCondition(
				newPwd.equals(newConfirmedPwd), 
				"Le nouveau mot de passe n'est pas confirmé !"
		);
		
		EncryptedWord securedPassword = new EncryptedWordImpl(newPwd);
		
		record.entryOf(User::password, securedPassword.value())
			  .entryOf(User::salt, securedPassword.salt())
		      .update();
	}

	@Override
	public boolean own(Object item) throws IOException {
		Method m;
		Long ownerId;
		
		try {
			m = item.getClass().getMethod("ownerId");
			ownerId = (Long)m.invoke(item);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.error(e);
			throw new IOException(e);
		}
		
		return ownerId == record.id();
	}

	@Override
	public boolean notOwn(Object item) throws IOException {
		return !own(item);
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public boolean isCompany() throws IOException {
		return origin.isCompany();
	}

	@Override
	public void update(String name, String email, final boolean isCompany) throws IOException {
		origin.update(name, email, isCompany);
	}

	@Override
	public boolean active() throws IOException {
		return record.valueOf(User::active);
	}

	@Override
	public void activate(boolean active) throws IOException {
		record.entryOf(User::active, active)
		      .update();
	}

	@Override
	public String salt() throws IOException {
		return record.valueOf(User::salt);
	}

	@Override
	public String photo() throws IOException {
		return origin.photo();
	}

	@Override
	public TimeZone timeZone() throws IOException {
		return origin.timeZone();
	}

	@Override
	public void changeTimeZone(TimeZone timeZone) throws IOException {
		origin.changeTimeZone(timeZone); 
	}

	@Override
	public Language preferredLanguage() throws IOException {
		return origin.preferredLanguage();
	}

	@Override
	public void changePreferredLanguage(Language language) throws IOException {
		origin.changePreferredLanguage(language); 
	}
	
	@Override
	public String timeZoneId() throws IOException {
		return origin.timeZoneId();
	}

	@Override
	public Currency preferredCurrency() throws IOException {
		return origin.preferredCurrency();
	}

	@Override
	public Locale locale() throws IOException {
		return origin.locale();
	}

	@Override
	public PlanSubscriptionContracts contracts() throws IOException {
		return new PgPlanSubscriptionContracts(this);
	}

	@Override
	public TimePrinter humanDateOf(LocalDate date) throws IOException {
		return new UserHumanDateOf(date, this);
	}

	@Override
	public TimePrinter humanDateOf(LocalDateTime date) throws IOException {
		return new UserHumanDateOf(date, this);
	}

	@Override
	public TimePrinter prettyTimeOf(LocalDateTime date) throws IOException {
		return new UserPrettyTimeOf(date, this);
	}

	@Override
	public TimePrinter humanDateOf(LocalDate date, FormatStyle style) throws IOException {
		return new UserHumanDateOf(date, style, this);
	}

	@Override
	public TimePrinter humanDateOf(LocalDateTime date, FormatStyle style) throws IOException {
		return new UserHumanDateOf(date, style, this);
	}

	@Override
	public Applications applications() throws IOException {
		return new PxApplications(this);
	}

	@Override
	public Address address() throws IOException {
		return origin.address();
	}

	@Override
	public void changePreferredCurrency(Currency currency) throws IOException {
		origin.changePreferredCurrency(currency); 
	}

	@Override
	public BillingAddress billingAddress() throws IOException {
		return origin.billingAddress();
	}

	@Override
	public PlannedTasks plannedTasks() throws IOException {
		return new PxPlannedTasks(this);
	}

	@Override
	public UserPaymentRequests paymentRequests() throws IOException {
		return new PgUserPaymentRequests(this);
	}

	@Override
	public Application currentApp() throws IOException {
		
		if(isAnonymous())
			return new AnonymousUserApplication(base());
		else
			return applications().current();
	}

	@Override
	public Profile currentProfile() throws IOException {
		return currentApp().profile();
	}

	@Override
	public boolean isAnonymous() throws IOException {
		return id().equals(2L);
	}

	@Override
	public boolean isAdmin() throws IOException {
		return id().equals(1L);
	}

	@Override
	public void changeProfile(Profile newProfile) throws IOException {
		currentApp().changeProfile(newProfile);
	}

	@Override
	public Profile profileOf(String module) throws IOException {
		if(!applications().has(module))
			applications().add(module);
		
		return applications().get(module).profile();
	}
}
