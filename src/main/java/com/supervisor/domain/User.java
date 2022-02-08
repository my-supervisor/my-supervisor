package com.supervisor.domain;

import com.supervisor.billing.BillingAddress;
import com.supervisor.billing.PlannedTasks;
import com.supervisor.billing.UserPaymentRequests;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.sdk.time.TimePrinter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

@com.supervisor.sdk.metadata.Recordable(
		name="membership_user", 
		label="Utilisateur",
		comodel=Person.class
)
public interface User extends Person {

	UUID ADMIN_ID = UUID.fromString("8c996d5d-ed4f-4fc2-8bea-4fcd4c9ef625");
	UUID ANONYMOUS_ID = UUID.fromString("337b67c9-7232-436f-8978-30968b969b33");

	@Field(label="Mot de passe")
	String password() throws IOException;
	
	@Field(label="Salt")
	String salt() throws IOException;
	
	@Field(label="Actif")
	boolean active() throws IOException;
	
	PlanSubscriptionContracts contracts() throws IOException;
	
	void activate(boolean active) throws IOException;
	
	boolean own(Object item) throws IOException;
	boolean notOwn(Object item) throws IOException;
	
	void changePassword(final String currentPwd, final String newPwd, final String newConfirmedPwd) throws IOException;

	PlannedTasks plannedTasks() throws IOException;
	UserPaymentRequests paymentRequests() throws IOException;
	
	TimePrinter humanDateOf(LocalDate date) throws IOException;
	TimePrinter humanDateOf(LocalDateTime date) throws IOException;
	TimePrinter humanDateOf(LocalDate date, FormatStyle style) throws IOException;
	TimePrinter humanDateOf(LocalDateTime date, FormatStyle style) throws IOException;
	TimePrinter prettyTimeOf(LocalDateTime date) throws IOException;

	@Field(
		label="Profil",
		rel= Relation.MANY2ONE
	)
	Profile profile() throws IOException;
	
	boolean isAnonymous() throws IOException;
	boolean isAdmin() throws IOException;
	
	void changeProfile(Profile newProfile) throws IOException;
	
	User EMPTY = new User() {
		
		@Override
		public String tag() throws IOException {
			return null;
		}
		
		@Override
		public UUID ownerId() throws IOException {
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			return null;
		}
		
		@Override
		public UUID lastModifierId() throws IOException {
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			return null;
		}
		
		@Override
		public UUID id() {
			return null;
		}
		
		@Override
		public UUID creatorId() throws IOException {
			return null;
		}
		
		@Override
		public LocalDateTime creationDate() throws IOException {
			return null;
		}
		
		@Override
		public Base base() {
			return Base.EMPTY;
		}
		
		@Override
		public void update(String name, String photo, boolean isCompany) throws IOException {
			throw new UnsupportedOperationException();

		}
		
		@Override
		public String photo() throws IOException {
			return null;
		}
		
		@Override
		public String name() throws IOException {
			return null;
		}
		
		@Override
		public boolean isCompany() throws IOException {
			return false;
		}
		
		@Override
		public String salt() throws IOException {
			return null;
		}
		
		@Override
		public String password() throws IOException {
			return null;
		}
		
		@Override
		public boolean own(Object item) throws IOException {
			return false;
		}
		
		@Override
		public boolean notOwn(Object item) throws IOException {
			return false;
		}
		
		@Override
		public void changePassword(String currentPwd, String newPwd, String newConfirmedPwd) throws IOException {
			throw new UnsupportedOperationException();

		}
		
		@Override
		public boolean active() throws IOException {
			return false;
		}
		
		@Override
		public void activate(boolean active) throws IOException {
			throw new UnsupportedOperationException();

		}

		@Override
		public TimeZone timeZone() throws IOException {
			return null;
		}

		@Override
		public void changeTimeZone(TimeZone timeZone) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Language preferredLanguage() throws IOException {
			return null;
		}

		@Override
		public String timeZoneId() throws IOException {
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {

			return null;
		}

		@Override
		public Locale locale() throws IOException {

			return null;
		}

		@Override
		public PlanSubscriptionContracts contracts() throws IOException {

			return null;
		}

		@Override
		public TimePrinter humanDateOf(LocalDate date) throws IOException {

			return null;
		}

		@Override
		public TimePrinter humanDateOf(LocalDateTime date) throws IOException {

			return null;
		}

		@Override
		public TimePrinter prettyTimeOf(LocalDateTime date) throws IOException {

			return null;
		}

		@Override
		public TimePrinter humanDateOf(LocalDate date, FormatStyle style) throws IOException {

			return null;
		}

		@Override
		public TimePrinter humanDateOf(LocalDateTime date, FormatStyle style) throws IOException {

			return null;
		}

		@Override
		public Address address() throws IOException {

			return null;
		}

		@Override
		public BillingAddress billingAddress() throws IOException {

			return null;
		}

		@Override
		public Currency preferredCurrency() throws IOException {

			return null;
		}

		@Override
		public void changePreferredCurrency(Currency currency) throws IOException {

			
		}

		@Override
		public void changePreferredLanguage(Language language) throws IOException {

			
		}

		@Override
		public PlannedTasks plannedTasks() throws IOException {

			return null;
		}

		@Override
		public UserPaymentRequests paymentRequests() throws IOException {

			return null;
		}

		@Override
		public boolean isAnonymous() throws IOException {

			return false;
		}

		@Override
		public boolean isAdmin() throws IOException {

			return false;
		}

		@Override
		public void changeProfile(Profile newProfile) throws IOException {

			
		}

		@Override
		public Profile profile() throws IOException {

			return null;
		}
	};
}
