package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Product;
import com.minlessika.membership.billing.SoftwareEngineeringService;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxSoftwareEngineeringService  extends ProductBase implements SoftwareEngineeringService {

	private final Record<SoftwareEngineeringService> record;
	
	public PxSoftwareEngineeringService(final Record<SoftwareEngineeringService> record) throws IOException {
		super(record.of(Product.class, record.id()));
		
		this.record = record;
	}

	public PxSoftwareEngineeringService(final Product origin) throws IOException {
		this(recordOf(origin));
	}
	
	private static Record<SoftwareEngineeringService> recordOf(Product origin) throws IOException {
		
		RecordSet<SoftwareEngineeringService> source = origin.listOf(SoftwareEngineeringService.class);
		
		if(!source.contains(origin.id()))
			throw new IllegalArgumentException("This product isn't a software engineering service !");
		
		return source.get(origin.id()); 
	}

	@Override
	public int no() throws IOException {
		return record.valueOf(SoftwareEngineeringService::no);
	}

}
