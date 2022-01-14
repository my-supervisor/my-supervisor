package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.datasource.comparators.Comparator;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_formular_data_field", 
	label="Champ de données formule",
	comodel=DataField.class
)
public interface FormularDataField extends DataField {
	
	@Field(
		label="Modèle agrégé", 
		rel=Relation.MANY2ONE,
		ignore=true
	)
	AggregatedModel model() throws IOException;
	
	FormularCondition condition() throws IOException;
	void addCondition(ParamDataField param, Comparator comparator, String value, double defaultValue) throws IOException;
	void removeCondition() throws IOException;
	
	FormularExpression mainExpression() throws IOException;	
	FormularExpressions expressions() throws IOException;
	FormularExpressions previousExpressions(FormularExpression expr) throws IOException;
	
	void update(String code, String name, DataFieldType type) throws IOException;
	
	FormularDataField EMPTY = new FormularDataField() {

		@Override
		public String code() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String name() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String description() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataFieldType type() throws IOException {
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
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FormularCondition condition() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addCondition(ParamDataField param, Comparator comparator, String value, double defaultValue)
				throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeCondition() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public FormularExpression mainExpression() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FormularExpressions expressions() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public FormularExpressions previousExpressions(FormularExpression expr) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataFieldDependencies dependencies() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataFieldStyle style() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
				throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update(String code, String name, DataFieldType type) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public AggregatedModel model() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
}
