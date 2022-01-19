package com.minlessika.membership.domain;

import com.minlessika.membership.billing.Invoice;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.billing.SubscriptionContract;
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
		name="membership_plan_subscription_contract", 
		label="Contrat d'abonnement d'un plan",
		comodel=SubscriptionContract.class
)
public interface PlanSubscriptionContract extends SubscriptionContract {

	@Field(
		label="Plan",
		rel=Relation.MANY2ONE
	)
	Plan plan() throws IOException;
	
	PlanSubscriptionContract EMPTY = new PlanSubscriptionContract() {
		
		
		@Override
		public String tag() throws IOException {
			
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
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
		public Person subscriber() throws IOException {
			
			return null;
		}
		
		@Override
		public PurchaseOrder purchaseOrder() throws IOException {
			
			return null;
		}
		
		@Override
		public Invoice invoice() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDate endDate() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDate beginDate() throws IOException {
			
			return null;
		}
		
		@Override
		public Plan plan() throws IOException {
			
			return null;
		}
	};
}
