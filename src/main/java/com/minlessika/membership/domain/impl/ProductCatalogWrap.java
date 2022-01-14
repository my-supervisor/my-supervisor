package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.billing.Products;
import com.minlessika.membership.domain.Currency;
import com.minlessika.sdk.datasource.DomainRecordable;

import java.io.IOException;

public class ProductCatalogWrap extends DomainRecordable<ProductCatalog> implements ProductCatalog {

	private final ProductCatalog origin;
	
	public ProductCatalogWrap(final ProductCatalog origin) throws IOException {
		super(origin.listOf(ProductCatalog.class).get(origin.id()));
		
		this.origin = origin;
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public Currency currency() throws IOException {
		return origin.currency();
	}

	@Override
	public Products products() throws IOException {
		return origin.products();
	}
}
