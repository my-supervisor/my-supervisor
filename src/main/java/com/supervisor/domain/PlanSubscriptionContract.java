package com.supervisor.domain;

import com.supervisor.billing.Invoice;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.billing.SubscriptionContract;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@com.supervisor.sdk.metadata.Recordable(
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
		public UUID ownerId() throws IOException {
			
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
		public UUID lastModifierId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public UUID id() {
			
			return null;
		}
		
		@Override
		public UUID creatorId() throws IOException {
			
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
