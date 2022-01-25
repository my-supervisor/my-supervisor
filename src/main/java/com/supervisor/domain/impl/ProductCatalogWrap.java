package com.supervisor.domain.impl;

import com.supervisor.billing.ProductCatalog;
import com.supervisor.billing.Products;
import com.supervisor.domain.Currency;
import com.supervisor.sdk.datasource.DomainRecordable;

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
