package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Product;
import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.billing.Products;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.impl.PxCurrency;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;

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
