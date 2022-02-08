package com.supervisor.billing.impl;

import com.supervisor.domain.Currency;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class CurrencyWrap implements Currency {

	private final Currency origin;
	
	public CurrencyWrap(final Currency origin) {
		this.origin = origin;
	}
	
	@Override
	public UUID id() {
		return origin.id();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return origin.creationDate();
	}

	@Override
	public UUID creatorId() throws IOException {
		return origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return origin.lastModificationDate();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return origin.lastModifierId();
	}

	@Override
	public UUID ownerId() throws IOException {
		return origin.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return origin.tag();
	}

	@Override
	public Base base() {
		return origin.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return origin.listOf(clazz);
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return origin.listOf(clazz, viewScript);
	}

	@Override
	public String code() throws IOException {
		return origin.code();
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public String symbol() throws IOException {
		return origin.symbol();
	}

	@Override
	public boolean after() throws IOException {
		return origin.after();
	}

	@Override
	public int precision() throws IOException {
		return origin.precision();
	}

	@Override
	public double convert(double amount, Currency currency) throws IOException {
		return origin.convert(amount, currency);
	}

	@Override
	public void update(String code, String name, String symbol, boolean after, int precision) throws IOException {
		origin.update(code, name, symbol, after, precision); 
	}

	@Override
	public String toString(double amount) throws IOException {
		return origin.toString(amount);
	}
}
