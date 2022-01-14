package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.Product;
import com.minlessika.membership.billing.impl.ProductBase;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.PlanFeatures;
import com.minlessika.membership.domain.Profile;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxPlan extends ProductBase implements Plan {

	private final Record<Plan> record;
	
	public PxPlan(final Record<Plan> record) throws IOException {
		super(record.of(Product.class, record.id()));
		
		this.record = record;
	}

	public PxPlan(final Product origin) throws IOException {
		this(recordOf(origin));
	}
	
	private static Record<Plan> recordOf(Product origin) throws IOException {
		
		RecordSet<Plan> source = origin.listOf(Plan.class);
		
		if(!source.contains(origin.id()))
			throw new IllegalArgumentException("Ce produit n'est pas un plan !");
		
		return source.get(origin.id()); 
	}
	
	@Override
	public Profile profile() throws IOException {
		Record<Profile> rec = record.of(Plan::profile);
		return new PxProfile(rec);
	}

	@Override
	public int order() throws IOException {
		return record.valueOf(Plan::order);
	}

	@Override
	public boolean preferred() throws IOException {
		return record.valueOf(Plan::preferred);
	}

	@Override
	public void organize(int order) throws IOException {
		
		record.entryOf(Plan::order, order)
	          .update();
	}

	@Override
	public void prefer(boolean preferred) throws IOException {
		
		record.entryOf(Plan::preferred, preferred)
        	  .update();
	}

	@Override
	public PlanFeatures features() throws IOException {
		return new PxPlanFeatures(this);
	}
}
