package com.supervisor.domain.impl;

import com.supervisor.billing.Product;
import com.supervisor.billing.impl.ProductBase;
import com.supervisor.domain.Plan;
import com.supervisor.domain.PlanFeatures;
import com.supervisor.domain.Profile;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;

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
