package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.Product;
import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.Plans;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;

import java.io.IOException;

public final class PgPlans extends DomainRecordables<Plan, Plans> implements Plans {

	private final ProductCatalog catalog;
	
	public PgPlans(final ProductCatalog catalog) throws IOException {
		this(viewSource(new UserOf(catalog.currency())), catalog);
	}
	
	public PgPlans(final RecordSet<Plan> source, final ProductCatalog catalog) throws IOException {
		super(PxPlan.class, source);
		
		this.catalog = catalog;
		this.source = source.orderBy(Plan::order);
	}

	@Override
	protected Plans domainSetOf(final RecordSet<Plan> source) throws IOException {
		return new PgPlans(source, catalog);
	}
	
	@Override
	protected Plan domainOf(final Long id) throws IOException {
		Product origin = catalog.products().get(id);
		return new PxPlan(origin);					
	}

	private static RecordSet<Plan> viewSource(final User user) throws IOException{
		Table table = new TableImpl(Plan.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select src.*, target.enabled, target.name, target.reference, target.price \r\n" + 
				                        "   from %s as src \r\n" + 
										"	left join %s as target on target.id = src.id \r\n" + 
										") as %s",
							table.name(),
							new TableImpl(Product.class).name(),
							table.name()
				);
		
		return user.listOf(Plan.class, viewScript);
	}
	
	@Override
	public Plan add(String reference, Profile profile, double price) throws IOException {
		
		Product product = catalog.products().add(profile.name(), reference, price);
		
		Record<Plan> record = source.entryOf(Plan::id, product.id())
				                    .entryOf(Plan::profile, profile.id())
	                                .entryOf(Plan::preferred, false)
	                                .entryOf(Plan::order, isEmpty() ? 1 : last().order() + 1)
	                                .add();
		
		return domainOf(record);
	}
}
