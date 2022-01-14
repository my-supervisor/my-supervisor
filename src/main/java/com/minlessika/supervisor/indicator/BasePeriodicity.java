package com.minlessika.supervisor.indicator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.time.Periodicity;
import com.minlessika.sdk.time.PeriodicityUnit;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_periodicity", 
		label="Périodicité"
)
public interface BasePeriodicity extends Periodicity, Recordable {

	BasePeriodicity EMPTY = new BasePeriodicity() {
		
		@Override
		public String tag() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long lastModifierId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long id() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public UUID guid() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Long creatorId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public LocalDateTime creationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Base base() {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public LocalDate end(LocalDate date) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public LocalDate begin(LocalDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean closeInterval() {
			// TODO Auto-generated method stub
			return false;
		}
	};
}
