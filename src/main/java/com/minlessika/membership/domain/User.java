package com.minlessika.membership.domain;

import com.minlessika.membership.billing.BillingAddress;
import com.minlessika.membership.billing.PlannedTasks;
import com.minlessika.membership.billing.UserPaymentRequests;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.time.TimePrinter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

@com.minlessika.sdk.metadata.Recordable(
		name="membership_user", 
		label="Utilisateur",
		comodel=Person.class
)
public interface User extends Person {

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
	
	Applications applications() throws IOException;
	Application currentApp() throws IOException;
	PlannedTasks plannedTasks() throws IOException;
	UserPaymentRequests paymentRequests() throws IOException;
	
	TimePrinter humanDateOf(LocalDate date) throws IOException;
	TimePrinter humanDateOf(LocalDateTime date) throws IOException;
	TimePrinter humanDateOf(LocalDate date, FormatStyle style) throws IOException;
	TimePrinter humanDateOf(LocalDateTime date, FormatStyle style) throws IOException;
	TimePrinter prettyTimeOf(LocalDateTime date) throws IOException;
	
	Profile currentProfile() throws IOException;
	Profile profileOf(String module) throws IOException;
	
	boolean isAnonymous() throws IOException;
	boolean isAdmin() throws IOException;
	
	void changeProfile(Profile newProfile) throws IOException;
	
	User EMPTY = new User() {
		
		@Override
		public String tag() throws IOException {
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			return null;
		}
		
		@Override
		public Long lastModifierId() throws IOException {
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			return null;
		}
		
		@Override
		public Long id() {
			return null;
		}
		
		@Override
		public UUID guid() throws IOException {
			return null;
		}
		
		@Override
		public Long creatorId() throws IOException {
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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale locale() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PlanSubscriptionContracts contracts() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TimePrinter humanDateOf(LocalDate date) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TimePrinter humanDateOf(LocalDateTime date) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TimePrinter prettyTimeOf(LocalDateTime date) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TimePrinter humanDateOf(LocalDate date, FormatStyle style) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TimePrinter humanDateOf(LocalDateTime date, FormatStyle style) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Applications applications() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Address address() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BillingAddress billingAddress() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Currency preferredCurrency() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void changePreferredCurrency(Currency currency) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void changePreferredLanguage(Language language) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PlannedTasks plannedTasks() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public UserPaymentRequests paymentRequests() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Application currentApp() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Profile currentProfile() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isAnonymous() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isAdmin() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void changeProfile(Profile newProfile) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Profile profileOf(String module) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
