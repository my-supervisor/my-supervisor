package com.supervisor.domain.impl;

import com.supervisor.billing.AllPaymentReceipts;
import com.supervisor.billing.Invoice;
import com.supervisor.billing.Invoices;
import com.supervisor.billing.PaymentMethod;
import com.supervisor.billing.PaymentMethods;
import com.supervisor.billing.ProductCatalog;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.billing.PurchaseOrders;
import com.supervisor.billing.Tax;
import com.supervisor.billing.Taxes;
import com.supervisor.billing.UserPaymentReceipts;
import com.supervisor.billing.UserPaymentRequests;
import com.supervisor.billing.impl.PgAllPaymentReceipts;
import com.supervisor.billing.impl.PgUserPaymentReceipts;
import com.supervisor.billing.impl.PgUserPaymentRequests;
import com.supervisor.billing.impl.PxInvoices;
import com.supervisor.billing.impl.PxPaymentMethods;
import com.supervisor.billing.impl.PxPurchaseOrders;
import com.supervisor.billing.impl.PxTaxes;
import com.supervisor.domain.Access;
import com.supervisor.domain.Accesses;
import com.supervisor.domain.Countries;
import com.supervisor.domain.Country;
import com.supervisor.domain.Currencies;
import com.supervisor.domain.Currency;
import com.supervisor.domain.Language;
import com.supervisor.domain.Languages;
import com.supervisor.domain.Membership;
import com.supervisor.domain.Person;
import com.supervisor.domain.Persons;
import com.supervisor.domain.Plans;
import com.supervisor.domain.Profiles;
import com.supervisor.domain.RegistrationRequest;
import com.supervisor.domain.RegistrationRequests;
import com.supervisor.domain.Sequence;
import com.supervisor.domain.Sequences;
import com.supervisor.domain.User;
import com.supervisor.domain.Users;
import com.supervisor.takes.RqUser;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import org.takes.Request;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public final class DmMembership implements Membership {

	private final RecordSet<User> source;
	private final User user;
	private final Base base;
	
	public DmMembership(final Base base) throws IOException {
		this(
			base, 
			new DmUser(base, User.ANONYMOUS_ID)
		);
	}
	
	public DmMembership(final Base base, final Request req) throws IOException {
		this(base, new RqUser(base, req));
	}
	
	public DmMembership(final Base base, final User user) throws IOException {
		this.base = base;
		this.base.changeUser(user.id());
		this.source = this.base.select(User.class);
		this.user = user;
	}
	
	@Override
	public Persons contacts() throws IOException {
		return new DmPersons(
			source.of(Person.class)
		);
	}

	@Override
	public Users members() throws IOException {
		return new PgUsers(user);
	}

	@Override
	public Accesses accesses() throws IOException {
		return new PxAccesses(source.of(Access.class));
	}

	@Override
	public RegistrationRequests registrationRequests() throws IOException {
		return new PxRegistrationRequests(
				source.of(RegistrationRequest.class)
		);
	}

	@Override
	public Users users() throws IOException {
		return new PgUsers(user);
	}

	@Override
	public Countries countries() throws IOException {
		return new PxCountries(source.of(Country.class));
	}

	@Override
	public Languages languages() throws IOException {
		return new PxLanguages(source.of(Language.class));
	}

	@Override
	public PaymentMethods paymentMethods() throws IOException {
		return new PxPaymentMethods(source.of(PaymentMethod.class));
	}

	@Override
	public Plans plans() throws IOException {
		return new PgPlans(planCatalog());
	}

	@Override
	public Currencies currencies() throws IOException {
		return new PxCurrencies(source.of(Currency.class));
	}

	@Override
	public Sequences sequences() throws IOException {
		return new PxSequences(source.of(Sequence.class));
	}

	@Override
	public Taxes taxes() throws IOException {
		return new PxTaxes(source.of(Tax.class));
	}

	@Override
	public List<TimeZone> timeZones() throws IOException {
		List<TimeZone> timeZones = new ArrayList<>();
		
		for (String id : TimeZone.getAvailableIDs()) {
			timeZones.add(TimeZone.getTimeZone(id));
		}
		
		return timeZones;
	}

	@Override
	public ProductCatalog planCatalog() throws IOException {
		return new PxPlanCatalog(user); 
	}

	@Override
	public PurchaseOrders purchaseOrders() throws IOException {
		return new PxPurchaseOrders(source.of(PurchaseOrder.class));
	}

	@Override
	public Invoices invoices() throws IOException {
		return new PxInvoices(source.of(Invoice.class));
	}

	@Override
	public User user() {
		return user;
	}

	@Override
	public ProductCatalog softwareEngineeringServiceCatalog() throws IOException {
		return new PxSoftwareEngineeringCatalog(user);
	}

	@Override
	public UserPaymentRequests paymentRequests() throws IOException {
		return new PgUserPaymentRequests(user);
	}

	@Override
	public UserPaymentReceipts paymentReceipts() throws IOException {
		return new PgUserPaymentReceipts(user);
	}

	@Override
	public AllPaymentReceipts allPaymentReceipts() throws IOException {
		return new PgAllPaymentReceipts(user);
	}

	@Override
	public Profiles profiles() throws IOException {
		return new PxProfiles(base);
	}
}
