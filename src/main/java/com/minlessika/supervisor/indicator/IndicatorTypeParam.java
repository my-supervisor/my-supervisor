package com.minlessika.supervisor.indicator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.domain.DataFieldType;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_indicator_type_param", 
		label="Paramètre d'un type d'indicateur"
)
public interface IndicatorTypeParam extends Recordable {
	
	final String NB_OF_PERIODICITY = "NB_OF_PERIODICITY";
	final String PERIODICITY_UNIT = "PERIODICITY_UNIT";
	final String PERIODICITY_REF = "PERIODICITY_REF";
	
	@Field(label="Catégorie")
	IndicatorTypeParamCategory category() throws IOException;
	
	@Field(label="Ordre", name="no")
	int order() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Code")
	String code() throws IOException;
	
	@Field(label="Type")
	DataFieldType type() throws IOException;
	
	@Field(
		label="Type d'indicateur", 
		rel=Relation.MANY2ONE
	)
	IndicatorType indicatorType() throws IOException;
	
	IndicatorTypeParam EMPTY = new IndicatorTypeParam() {
		
		@Override
		public String tag() throws IOException {
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
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
		public DataFieldType type() throws IOException {
			return null;
		}
		
		@Override
		public String name() throws IOException {
			return null;
		}
		
		@Override
		public IndicatorType indicatorType() throws IOException {
			return null;
		}
		
		@Override
		public String code() throws IOException {
			return null;
		}

		@Override
		public Base base() {
			return Base.EMPTY;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			return null;
		}

		@Override
		public IndicatorTypeParamCategory category() throws IOException {
			return IndicatorTypeParamCategory.NONE;
		}

		@Override
		public int order() throws IOException {
			return 0;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {

			return null;
		}
	};
}
