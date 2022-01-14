package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Products extends DomainSet<Product, Products> {
	Product add(String name, String reference, double price) throws IOException;
}
