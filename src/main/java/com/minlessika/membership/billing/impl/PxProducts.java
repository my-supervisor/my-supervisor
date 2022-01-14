package com.minlessika.membership.billing.impl;

import com.minlessika.membership.billing.Product;
import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.billing.Products;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxProducts extends DomainRecordables<Product, Products> implements Products {

	private final ProductCatalog catalog;
	
	public PxProducts(final ProductCatalog catalog) throws IOException {
		this(catalog.listOf(Product.class), catalog);
	}
	
	public PxProducts(final RecordSet<Product> source, final ProductCatalog catalog) {
		super(ProductBase.class, source);
		
		this.catalog = catalog;
	}

	@Override
	protected Products domainSetOf(final RecordSet<Product> source) throws IOException {
		return new PxProducts(source, catalog);
	}
	
	@Override
	public Product add(String name, String reference, double price) throws IOException {
		
		source.isRequired(Product::name, name);
		source.isRequired(Product::reference, reference);
		
		Record<Product> record = source.entryOf(Product::name, name)
									   .entryOf(Product::catalog, catalog.id())
								       .entryOf(Product::reference, reference)
								       .entryOf(Product::price, price)
								       .entryOf(Product::enabled, true)
								       .add();
		
		return domainOf(record);
	}
}
