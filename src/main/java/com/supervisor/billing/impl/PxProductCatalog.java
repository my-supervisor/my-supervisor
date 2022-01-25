package com.supervisor.billing.impl;

import com.supervisor.billing.Product;
import com.supervisor.billing.ProductCatalog;
import com.supervisor.billing.Products;
import com.supervisor.domain.Currency;
import com.supervisor.domain.impl.PxCurrency;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;

public final class PxProductCatalog extends DomainRecordable<ProductCatalog> implements ProductCatalog {

	public PxProductCatalog(final Record<ProductCatalog> record) throws IOException {
		super(record);
	}
	
	@Override
	public Currency currency() throws IOException {
		Record<Currency> rec = record.of(ProductCatalog::currency);
		return new PxCurrency(rec);
	}

	@Override
	public Products products() throws IOException {
		return new PxProducts(this).where(Product::catalog, id()); 		
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(ProductCatalog::name);
	}

}
