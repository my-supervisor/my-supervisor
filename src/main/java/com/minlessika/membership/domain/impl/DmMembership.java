package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.AllPaymentReceipts;
import com.minlessika.membership.billing.Invoice;
import com.minlessika.membership.billing.Invoices;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentMethods;
import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.billing.PurchaseOrders;
import com.minlessika.membership.billing.Tax;
import com.minlessika.membership.billing.Taxes;
import com.minlessika.membership.billing.UserPaymentReceipts;
import com.minlessika.membership.billing.UserPaymentRequests;
import com.minlessika.membership.billing.impl.PgAllPaymentReceipts;
import com.minlessika.membership.billing.impl.PgUserPaymentReceipts;
import com.minlessika.membership.billing.impl.PgUserPaymentRequests;
import com.minlessika.membership.billing.impl.PxInvoices;
import com.minlessika.membership.billing.impl.PxPaymentMethods;
import com.minlessika.membership.billing.impl.PxPurchaseOrders;
import com.minlessika.membership.billing.impl.PxTaxes;
import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.Accesses;
import com.minlessika.membership.domain.Countries;
import com.minlessika.membership.domain.Country;
import com.minlessika.membership.domain.Currencies;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.Language;
import com.minlessika.membership.domain.Languages;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Person;
import com.minlessika.membership.domain.Persons;
import com.minlessika.membership.domain.Plans;
import com.minlessika.membership.domain.Profiles;
import com.minlessika.membership.domain.RegistrationRequest;
import com.minlessika.membership.domain.RegistrationRequests;
import com.minlessika.membership.domain.Sequence;
import com.minlessika.membership.domain.Sequences;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.Users;
import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BasicModule;
import com.minlessika.sdk.datasource.RecordSet;
import org.takes.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public final class DmMembership extends BasicModule implements Membership {

	private final RecordSet<User> source;
	private final User user;
	private final Base base;
	
	public DmMembership(final Base base) throws IOException {
		this(
			base, 
			new DmUser(base.select(User.class, 2L))
		);
	}
	
	public DmMembership(final Base base, final Request req) throws IOException {
		this(base, new RqUser(base, req));
	}
	
	public DmMembership(final Base base, final User user) throws IOException {
		super(Membership.NAME, base.appInfo());
		this.base = base;
		this.base.changeUser(user.id());
		this.source = this.base.select(User.class);
		this.user = new DmUser(this.source.get(user.id()));
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
		return new PxAccesses(
			source.of(Access.class),
			NAME
		);
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
		return new PxProfiles(base, NAME);
	}
}
