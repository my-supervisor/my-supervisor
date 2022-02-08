package com.supervisor.domain.impl;

import com.supervisor.billing.BillingAddress;
import com.supervisor.billing.PlannedTasks;
import com.supervisor.billing.UserPaymentRequests;
import com.supervisor.domain.Address;
import com.supervisor.domain.Currency;
import com.supervisor.domain.Language;
import com.supervisor.domain.PlanSubscriptionContracts;
import com.supervisor.domain.Profile;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.time.TimePrinter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class UserWrap implements User {

	private final User origin;
	
	public UserWrap(final User origin) {
		this.origin = origin;
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
	public LocalDateTime creationDate() throws IOException {
		return origin.creationDate();
	}

	@Override
	public UUID creatorId() throws IOException {
		return origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return origin.lastModificationDate();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return origin.lastModifierId();
	}

	@Override
	public UUID ownerId() throws IOException {
		return origin.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return origin.tag();
	}

	@Override
	public UUID id() {
		return origin.id();
	}

	@Override
	public String password() throws IOException {
		return origin.password();
	}

	@Override
	public String salt() throws IOException {
		return origin.salt();
	}

	@Override
	public boolean active() throws IOException {
		return origin.active();
	}

	@Override
	public void activate(boolean active) throws IOException {
		origin.activate(active);
	}

	@Override
	public boolean own(Object item) throws IOException {
		return origin.own(item);
	}

	@Override
	public boolean notOwn(Object item) throws IOException {
		return origin.notOwn(item);
	}

	@Override
	public void changePassword(String currentPwd, String newPwd, String newConfirmedPwd) throws IOException {
		origin.changePassword(currentPwd, newPwd, newConfirmedPwd);
	}

	@Override
	public Base base() {
		return origin.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return origin.listOf(clazz);
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
	public String timeZoneId() throws IOException {
		return origin.timeZoneId();
	}
	
	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return origin.listOf(clazz, viewScript);
	}

	@Override
	public Locale locale() throws IOException {
		return origin.locale();
	}

	@Override
	public PlanSubscriptionContracts contracts() throws IOException {
		return origin.contracts();
	}

	@Override
	public TimePrinter humanDateOf(LocalDate date) throws IOException {
		return origin.humanDateOf(date);
	}

	@Override
	public TimePrinter humanDateOf(LocalDateTime date) throws IOException {
		return origin.humanDateOf(date);
	}

	@Override
	public TimePrinter humanDateOf(LocalDate date, FormatStyle style) throws IOException {
		return origin.humanDateOf(date, style);
	}

	@Override
	public TimePrinter humanDateOf(LocalDateTime date, FormatStyle style) throws IOException {
		return origin.humanDateOf(date, style);
	}

	@Override
	public TimePrinter prettyTimeOf(LocalDateTime date) throws IOException {
		return origin.prettyTimeOf(date);
	}

	@Override
	public Address address() throws IOException {
		return origin.address();
	}

	@Override
	public BillingAddress billingAddress() throws IOException {
		return origin.billingAddress();
	}

	@Override
	public Currency preferredCurrency() throws IOException {
		return origin.preferredCurrency();
	}

	@Override
	public void update(String name, String photo, boolean isCompany) throws IOException {
		origin.update(name, photo, isCompany);
	}

	@Override
	public void changePreferredCurrency(Currency currency) throws IOException {
		origin.changePreferredCurrency(currency);
	}

	@Override
	public void changePreferredLanguage(Language language) throws IOException {
		origin.changePreferredLanguage(language); 
	}

	@Override
	public PlannedTasks plannedTasks() throws IOException {
		return origin.plannedTasks();
	}

	@Override
	public UserPaymentRequests paymentRequests() throws IOException {
		return origin.paymentRequests();
	}

	@Override
	public boolean isAnonymous() throws IOException {
		return origin.isAnonymous();
	}

	@Override
	public boolean isAdmin() throws IOException {
		return origin.isAdmin();
	}

	@Override
	public void changeProfile(Profile newProfile) throws IOException {
		origin.changeProfile(newProfile);
	}

	@Override
	public Profile profile() throws IOException {
		return origin.profile();
	}
}
