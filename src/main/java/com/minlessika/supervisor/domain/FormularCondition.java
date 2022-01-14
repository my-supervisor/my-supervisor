package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_formular_condition", 
		label="Condition d'une formule",
		comodel=FormularDataField.class		
)
public interface FormularCondition extends Recordable {
	
	@Field(
		label="Paramètre", 
		rel=Relation.MANY2ONE
	)
	ParamDataField param() throws IOException;
	
	@Field(label="Comparateur")
	Comparator comparator() throws IOException;
	
	@Field(label="Valeur")
	String value() throws IOException;
	
	@Field(label="Valeur par défaut")
	double defaultValue() throws IOException;
	
	boolean isTrue() throws IOException;
	
	FormularCondition EMPTY = new FormularCondition() {
		
		@Override
		public String value() throws IOException {
			return StringUtils.EMPTY;
		}
		
		@Override
		public ParamDataField param() throws IOException {
			return ParamDataField.EMPTY;
		}
		
		@Override
		public Comparator comparator() throws IOException {
			return Comparator.NONE;
		}

		@Override
		public Long id() {
			return 0L;
		}

		@Override
		public UUID guid() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LocalDateTime creationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long creatorId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long lastModifierId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long ownerId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String tag() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Base base() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isTrue() throws IOException {
			return true;
		}

		@Override
		public double defaultValue() throws IOException {
			return 0;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
