package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;
import com.supervisor.sdk.metadata.Relation;
import org.apache.commons.lang.StringUtils;

@Recordable(
		name="supervisor_formular_condition", 
		label="Condition d'une formule",
		comodel=FormularDataField.class		
)
public interface FormularCondition extends com.supervisor.sdk.datasource.Recordable {
	
	@Field(
		label="Paramètre", 
		rel= Relation.MANY2ONE
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
		public UUID id() {
			return null;
		}

		@Override
		public LocalDateTime creationDate() throws IOException {
			
			return null;
		}

		@Override
		public UUID creatorId() throws IOException {
			
			return null;
		}

		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}

		@Override
		public UUID lastModifierId() throws IOException {
			
			return null;
		}

		@Override
		public UUID ownerId() throws IOException {
			
			return null;
		}

		@Override
		public String tag() throws IOException {
			
			return null;
		}

		@Override
		public Base base() {
			
			return null;
		}

		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
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
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}
	};
}
