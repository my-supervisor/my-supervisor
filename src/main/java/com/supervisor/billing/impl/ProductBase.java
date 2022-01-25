package com.supervisor.billing.impl;

import com.supervisor.billing.Product;
import com.supervisor.billing.ProductCatalog;
import com.supervisor.domain.Currency;
import com.supervisor.domain.CurrencyPair;
import com.supervisor.domain.User;
import com.supervisor.domain.impl.CurrencyPairBase;
import com.supervisor.domain.impl.PairEuroXof;
import com.supervisor.domain.impl.UserOf;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.utils.logging.Logger;
import com.supervisor.sdk.utils.logging.MLogger;

import java.io.IOException;

public class ProductBase extends DomainRecordable<Product> implements Product {
	
	private static final Logger logger = new MLogger(ProductBase.class);
	
	private final Record<Product> record;
	
	public ProductBase(final Record<Product> record) throws IOException {
		super(record);
		
		this.record = record;		
	}
	
	private CurrencyPair pair() throws IOException {
		
		User user = new UserOf(this);
		if(user.preferredCurrency().code().equals("XOF")) {
			Currency currency = catalog().currency();
			return new CurrencyPairBase(currency, currency, 1);
		}else {
			return new PairEuroXof(base()).invert(); // XOF -> EURO
		}
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Product::name);
	}

	@Override
	public String reference() throws IOException {
		return record.valueOf(Product::reference);
	}

	@Override
	public double price() throws IOException {
		double price = record.valueOf(Product::price);
		CurrencyPair pair = pair();
		if(pair.rate() == 1)
			return price;
		else
			return pair.convert(price);
	}

	@Override
	public boolean enabled() throws IOException {
		return record.valueOf(Product::enabled);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(Product::description);
	}

	@Override
	public void evaluate(double price) throws IOException {
		
		record.entryOf(Product::price, price)
		      .update();
	}

	@Override
	public void enable(boolean enabled) throws IOException {
		record.entryOf(Product::enabled, enabled)
	          .update();
	}

	@Override
	public void describe(String description) throws IOException {
		record.entryOf(Product::description, description)
              .update();
	}
	
	@Override
	public String toString() {
		try {
			return pair().counterParty().toString(price());
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public ProductCatalog catalog() throws IOException {
		Record<ProductCatalog> rec = record.of(Product::catalog);
		return new PxProductCatalog(rec);
	}
}
