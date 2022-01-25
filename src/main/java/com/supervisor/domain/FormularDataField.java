package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
	name="supervisor_formular_data_field", 
	label="Champ de données formule",
	comodel=DataField.class
)
public interface FormularDataField extends DataField {
	
	@Field(
		label="Modèle agrégé", 
		rel= Relation.MANY2ONE,
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
			return null;
		}

		@Override
		public String name() throws IOException {

			return null;
		}

		@Override
		public String description() throws IOException {

			return null;
		}

		@Override
		public DataFieldType type() throws IOException {

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
		public LocalDateTime creationDate() throws IOException {

			return null;
		}

		@Override
		public Long creatorId() throws IOException {

			return null;
		}

		@Override
		public LocalDateTime lastModificationDate() throws IOException {

			return null;
		}

		@Override
		public Long lastModifierId() throws IOException {

			return null;
		}

		@Override
		public Long ownerId() throws IOException {

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
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {

			return null;
		}

		@Override
		public FormularCondition condition() throws IOException {

			return null;
		}

		@Override
		public void addCondition(ParamDataField param, Comparator comparator, String value, double defaultValue)
				throws IOException {

			
		}

		@Override
		public void removeCondition() throws IOException {

			
		}

		@Override
		public FormularExpression mainExpression() throws IOException {

			return null;
		}

		@Override
		public FormularExpressions expressions() throws IOException {

			return null;
		}

		@Override
		public FormularExpressions previousExpressions(FormularExpression expr) throws IOException {

			return null;
		}

		@Override
		public DataFieldDependencies dependencies() throws IOException {

			return null;
		}

		@Override
		public DataFieldStyle style() throws IOException {

			return null;
		}

		@Override
		public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
				throws IOException {

			
		}

		@Override
		public void update(String code, String name, DataFieldType type) throws IOException {

			
		}

		@Override
		public AggregatedModel model() throws IOException {

			return null;
		}
		
	};
}
