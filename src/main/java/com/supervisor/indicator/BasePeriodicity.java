package com.supervisor.indicator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.time.Periodicity;
import com.supervisor.sdk.time.PeriodicityUnit;

@com.supervisor.sdk.metadata.Recordable(
		name="periodicity",
		label="Périodicité"
)
public interface BasePeriodicity extends Periodicity, com.supervisor.sdk.datasource.Recordable {

	BasePeriodicity EMPTY = new BasePeriodicity() {
		
		@Override
		public String tag() throws IOException {
			
			return null;
		}
		
		@Override
		public UUID ownerId() throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
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
		public PeriodicityUnit unit() {
			return PeriodicityUnit.NONE;
		}
		
		@Override
		public LocalDate reference() {
			return LocalDate.MIN;
		}
		
		@Override
		public int number() {
			
			return 0;
		}
		
		@Override
		public LocalDate end(LocalDate date) {
			
			return null;
		}
		
		@Override
		public LocalDate begin(LocalDate date) {
			
			return null;
		}

		@Override
		public boolean closeInterval() {
			
			return false;
		}
	};
}
