package com.supervisor.billing.impl;

import com.supervisor.billing.Order;
import com.supervisor.billing.OrderType;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.billing.PurchaseOrderState;
import com.supervisor.billing.PurchaseOrders;
import com.supervisor.domain.Currencies;
import com.supervisor.domain.Currency;
import com.supervisor.domain.Person;
import com.supervisor.domain.Sequence;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.PairEuroXof;
import com.supervisor.domain.impl.PxCurrencies;
import com.supervisor.domain.impl.PxSequences;
import com.supervisor.domain.impl.UserAdmin;
import com.supervisor.domain.impl.UserOf;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public final class PxPurchaseOrders extends DomainRecordables<PurchaseOrder, PurchaseOrders> implements PurchaseOrders {

	public PxPurchaseOrders(RecordSet<PurchaseOrder> source) {
		super(PxPurchaseOrder.class, source);
	}

	@Override
	public PurchaseOrder add(Person customer, String description) throws IOException {
		final User user = new UserOf(this);
		final UUID currencyId;
		if(user.preferredCurrency().code().equals("XOF")) {
			currencyId = user.preferredCurrency().id();
		}else {
			currencyId = Currency.EUR_ID;
		}
		
		final Currencies currencies = new PxCurrencies(source.of(Currency.class));
		return add(customer, currencies.get(currencyId), description);
		
	}

	@Override
	public PurchaseOrder add(Person customer, Currency currency, String description) throws IOException {
		if(!customer.billingAddress().isComplete())
			throw new IllegalArgumentException("You must complete your profile in order to continue !");
		
		final double exchangeRate;
		
		if(currency.code().equals("XOF")) {
			exchangeRate = 1;			
		}else {
			exchangeRate = new PairEuroXof(base()).invert().rate();
		}
		
		final Sequence sequence = new PxSequences(source.of(Sequence.class)).get("PO");
		final User supplier = new UserAdmin(base());
		Record<Order> recOrder = source.of(Order.class)									   
									   .entryOf(Order::publishingDate, LocalDate.now())
									   .entryOf(Order::customer, customer.id())
									   .entryOf(Order::customerAddress, customer.billingAddress().id())
									   .entryOf(Order::supplier, supplier.id())									   
									   .entryOf(Order::supplierAddress, supplier.billingAddress().id())
									   .entryOf(Order::baseCurrency, 1L) // Xof
									   .entryOf(Order::currency, currency.id())
									   .entryOf(Order::exchangeRate, exchangeRate)
									   .entryOf(Order::amount, 0)
									   .entryOf(Order::vatAmount, 0)
									   .entryOf(Order::totalAmount, 0)
									   .entryOf(Order::description, description)
									   .entryOf(Order::reference, sequence.generate())
									   .entryOf(Order::type, OrderType.PURCHASE_ORDER)
									   .add();

		Record<PurchaseOrder> record = source.entryOf(PurchaseOrder::id, recOrder.id())
				                             .entryOf(PurchaseOrder::state, PurchaseOrderState.PENDING)
				                             .add();
		
		return domainOf(record);
	}

}
