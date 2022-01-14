package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.Invoice;
import com.minlessika.membership.billing.Invoices;
import com.minlessika.membership.billing.OrderLine;
import com.minlessika.membership.billing.Product;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.billing.SubscriptionContract;
import com.minlessika.membership.billing.impl.PxInvoices;
import com.minlessika.membership.domain.PlanSubscriptionContract;
import com.minlessika.membership.domain.PlanSubscriptionContracts;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.sdk.datasource.comparators.Matchers;

import java.io.IOException;
import java.time.LocalDate;

public final class PgPlanSubscriptionContracts extends DomainRecordables<PlanSubscriptionContract, PlanSubscriptionContracts> implements PlanSubscriptionContracts {
	
	private final User user;
	
	public PgPlanSubscriptionContracts(final User user) throws IOException {
		this(viewSource(user), user); 
	}
	
	public PgPlanSubscriptionContracts(final RecordSet<PlanSubscriptionContract> source, final User user) throws IOException {
		super(
				PxPlanSubscriptionContract.class, 
				source.where(PlanSubscriptionContract::subscriber, user.id())
		);
		
		this.user = user;
	}

	@Override
	protected PlanSubscriptionContracts domainSetOf(final RecordSet<PlanSubscriptionContract> source) throws IOException {
		return new PgPlanSubscriptionContracts(source, user);
	}
	
	private static RecordSet<PlanSubscriptionContract> viewSource(final User user) throws IOException{
		Table table = new TableImpl(PlanSubscriptionContract.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select src.*, target.subscriber_id, target.purchase_order_id, target.invoice_id, target.begin_date, target.end_date \r\n" + 
				                        "   from %s as src \r\n" + 
										"	left join %s as target on target.id = src.id \r\n" + 
										") as %s",
							table.name(),
							new TableImpl(SubscriptionContract.class).name(),
							table.name()
				);
		
		return user.listOf(PlanSubscriptionContract.class, viewScript);
	}
	
	@Override
	public boolean hasValidContract() throws IOException {
		return currentContract() != PlanSubscriptionContract.EMPTY;			
	}

	@Override
	public PlanSubscriptionContract currentContract() throws IOException {
		
		LocalDate date = LocalDate.now();
		PlanSubscriptionContracts contracts = this.where(PlanSubscriptionContract::beginDate, Matchers.lessOrEqualsTo(date))
											      .where(PlanSubscriptionContract::endDate, Matchers.greaterOrEqualsTo(date));
		if(contracts.any())
			return contracts.first();
		
		return PlanSubscriptionContract.EMPTY;
	}

	@Override
	public PlanSubscriptionContract subscribe(PurchaseOrder order) throws IOException {
		
		OrderLine line = order.lines().first();
		Product product = line.product();		
		
		Invoice invoice = Invoice.EMPTY;
		Invoices invoicesOfOrder = new PxInvoices(source.of(Invoice.class)).where(Invoice::purchaseOrder, order.id());
		if(invoicesOfOrder.any())
			invoice = invoicesOfOrder.first();
		
		PlanSubscriptionContract currentContract = currentContract();
		final LocalDate beginDate;
		
		if(currentContract == PlanSubscriptionContract.EMPTY) {
			beginDate = LocalDate.now();			
		}else {
			beginDate = currentContract.endDate().plusDays(1);
		}
		
		final LocalDate endDate = beginDate.plusMonths(line.quantity());
		
		Record<SubscriptionContract> recordBase = source.of(SubscriptionContract.class)
														.entryOf(SubscriptionContract::subscriber, user.id())
														.entryOf(SubscriptionContract::purchaseOrder, order.id())
														.entryOf(SubscriptionContract::invoice, invoice.id())
														.entryOf(SubscriptionContract::beginDate, beginDate)
														.entryOf(SubscriptionContract::endDate, endDate)
														.add();
				
		Record<PlanSubscriptionContract> record = source.entryOf(PlanSubscriptionContract::id, recordBase.id())
														.entryOf(PlanSubscriptionContract::plan, product.id())
														.add();
		
		return new PxPlanSubscriptionContract(record);
	}

}
