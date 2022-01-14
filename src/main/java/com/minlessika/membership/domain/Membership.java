package com.minlessika.membership.domain;

import com.minlessika.membership.billing.AllPaymentReceipts;
import com.minlessika.membership.billing.Invoices;
import com.minlessika.membership.billing.PaymentMethods;
import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.billing.PurchaseOrders;
import com.minlessika.membership.billing.Taxes;
import com.minlessika.membership.billing.UserPaymentReceipts;
import com.minlessika.membership.billing.UserPaymentRequests;
import com.minlessika.sdk.datasource.Module;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.TimeZone;

public interface Membership extends Module {
	
	public static final String NAME = "com.membership";
	
	/**
     * Revision.
     */
    public static final String REV = "0.0.1.1040";
    public static final LocalDate RELEASE_DATE = LocalDate.of(2019, 12, 03);
    public static final String PRODUCT_RANGE = "SAAS"; 
    public static final String NOTES = StringUtils.EMPTY;
    
	RegistrationRequests registrationRequests() throws IOException;
	Persons contacts() throws IOException;
	Users members() throws IOException;
	Accesses accesses() throws IOException;
	Profiles profiles() throws IOException;
	Users users() throws IOException;
	ProductCatalog planCatalog() throws IOException;
	ProductCatalog softwareEngineeringServiceCatalog() throws IOException;
	Plans plans() throws IOException;
	
	Currencies currencies() throws IOException;
	Countries countries() throws IOException;
	Languages languages() throws IOException;
	PaymentMethods paymentMethods() throws IOException;
	UserPaymentRequests paymentRequests() throws IOException;
	UserPaymentReceipts paymentReceipts() throws IOException;
	AllPaymentReceipts allPaymentReceipts() throws IOException;
	
	Taxes taxes() throws IOException;
	List<TimeZone> timeZones() throws IOException;
	
	PurchaseOrders purchaseOrders() throws IOException;
	Invoices invoices() throws IOException;
	
	Sequences sequences() throws IOException;	
	
	User user();
}
