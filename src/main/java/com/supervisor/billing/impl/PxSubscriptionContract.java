package com.supervisor.billing.impl;

import com.supervisor.billing.Invoice;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.billing.SubscriptionContract;
import com.supervisor.domain.Person;
import com.supervisor.domain.impl.DmPerson;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;
import java.time.LocalDate;

public class PxSubscriptionContract extends DomainRecordable<SubscriptionContract> implements SubscriptionContract {

	public PxSubscriptionContract(Record<SubscriptionContract> record) throws IOException {
		super(record);
	}

	@Override
	public Person subscriber() throws IOException {
		Record<Person> rec = record.of(SubscriptionContract::subscriber);
		return new DmPerson(rec);
	}

	@Override
	public PurchaseOrder purchaseOrder() throws IOException {
		Record<PurchaseOrder> rec = record.valueOf(SubscriptionContract::purchaseOrder);
		return new PxPurchaseOrder(rec);
	}

	@Override
	public Invoice invoice() throws IOException {
		
		Long invoiceId = record.valueOf(SubscriptionContract::invoice);
		if(invoiceId == null)
			return Invoice.EMPTY;
		else
			return new PxInvoice(record.of(SubscriptionContract::invoice));
	}

	@Override
	public LocalDate beginDate() throws IOException {
		return record.valueOf(SubscriptionContract::beginDate);
	}

	@Override
	public LocalDate endDate() throws IOException {
		return record.valueOf(SubscriptionContract::endDate);
	}

}
