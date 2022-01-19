package com.minlessika.membership.billing;

import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.Person;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@com.minlessika.sdk.metadata.Recordable(
		name="billing_invoice", 
		label="Facture",
		comodel=Order.class
)
public interface Invoice extends Order {

	@Field(label="Etat")
	InvoiceState state() throws IOException;
	
	@Field(
		label="Bon de commande",
		rel=Relation.MANY2ONE
	)
	PurchaseOrder purchaseOrder() throws IOException;
	
	@Field(label="Date d'échéance")
	LocalDate dueDate() throws IOException;
	
	OrderLines lines() throws IOException;
	
	Invoice EMPTY = new Invoice() {
		
		@Override
		public String tag() throws IOException {
			
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
			return null;
		}
		
		@Override
		public Long lastModifierId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public Long id() {
			
			return null;
		}
		
		@Override
		public UUID guid() throws IOException {
			
			return null;
		}
		
		@Override
		public Long creatorId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime creationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public Base base() {
			
			return null;
		}
		
		@Override
		public void update(LocalDate publishingDate) throws IOException {
			
			
		}
		
		@Override
		public String reference() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDate publishingDate() throws IOException {
			
			return null;
		}
		
		@Override
		public String description() throws IOException {
			
			return null;
		}
		
		@Override
		public void describe(String description) throws IOException {
			
			
		}
		
		@Override
		public PurchaseOrder purchaseOrder() throws IOException {
			
			return null;
		}
		
		@Override
		public OrderLines lines() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDate dueDate() throws IOException {
			
			return null;
		}

		@Override
		public Person supplier() throws IOException {
			
			return null;
		}

		@Override
		public Person customer() throws IOException {
			
			return null;
		}

		@Override
		public double amount() throws IOException {
			
			return 0;
		}

		@Override
		public double vatAmount() throws IOException {
			
			return 0;
		}

		@Override
		public double totalAmount() throws IOException {
			
			return 0;
		}

		@Override
		public OrderTaxes taxes() throws IOException {
			
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}

		@Override
		public void calculate() throws IOException {
			
			
		}

		@Override
		public Currency baseCurrency() throws IOException {
			
			return null;
		}

		@Override
		public Currency currency() throws IOException {
			
			return null;
		}

		@Override
		public double exchangeRate() throws IOException {
			
			return 0;
		}

		@Override
		public InvoiceState state() throws IOException {
			
			return null;
		}

		@Override
		public OrderType type() throws IOException {
			
			return null;
		}

		@Override
		public PaymentReceipts receipts() throws IOException {
			
			return null;
		}

		@Override
		public BillingAddress supplierAddress() throws IOException {
			
			return null;
		}

		@Override
		public BillingAddress customerAddress() throws IOException {
			
			return null;
		}

		@Override
		public PaymentRequests requests() throws IOException {
			
			return null;
		}
	};
}
