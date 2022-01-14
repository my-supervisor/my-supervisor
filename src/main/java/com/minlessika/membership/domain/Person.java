package com.minlessika.membership.domain;

import com.minlessika.membership.billing.BillingAddress;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

@com.minlessika.sdk.metadata.Recordable(
		name="base_person", 
		label="Person"
)
public interface Person extends Recordable {
	
	@Field(label="Name")
	String name() throws IOException;
	
	@Field(label="Photo")
	String photo() throws IOException;
	
	@Field(label="Is a company ?")
	boolean isCompany() throws IOException;
	
	@Field(label="Time zone ID")
	String timeZoneId() throws IOException;
	
	TimeZone timeZone() throws IOException;
	
	@Field(
		label="Address",
		rel=Relation.MANY2ONE
	)
	Address address() throws IOException;
	
	@Field(
		label="Billing Address",
		rel=Relation.MANY2ONE
	)
	BillingAddress billingAddress() throws IOException;
	
	@Field(
		label="Preferred language",
		rel=Relation.MANY2ONE
	)
	Language preferredLanguage() throws IOException;
	
	@Field(
		label="Preferred currency",
		rel=Relation.MANY2ONE
	)
	Currency preferredCurrency() throws IOException;
	
	Locale locale() throws IOException;
	
	void update(final String name, final String photo, final boolean isCompany) throws IOException;
	void changePreferredCurrency(Currency currency) throws IOException;
	void changeTimeZone(TimeZone timeZone) throws IOException;
	void changePreferredLanguage(Language language) throws IOException;
}
