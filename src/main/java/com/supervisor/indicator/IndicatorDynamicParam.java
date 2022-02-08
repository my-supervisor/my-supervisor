package com.supervisor.indicator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.bi.AggregateFunc;

@com.supervisor.sdk.metadata.Recordable(
		name="supervisor_indicator_dynamic_param", 
		label="Paramètre dynamique d'indicateur",
		inheritFields=false
)
public interface IndicatorDynamicParam extends IndicatorTypeDynamicParam {

	@Field(
		label="Indicateur", 
		rel= Relation.MANY2ONE
	)
	Indicator indicator() throws IOException;
	
	@Field(
		label="Origine", 
		rel=Relation.MANY2ONE
	)
	IndicatorTypeDynamicParam origin() throws IOException;
	
	@Field(label="Fonction d'agrégation")
	AggregateFunc aggregateFunc() throws IOException;
	
	void update(AggregateFunc func) throws IOException;
	
	IndicatorDynamicParam EMPTY = new IndicatorDynamicParam() {
		
		@Override
		public String tag() throws IOException {
			
			return null;
		}
		
		@Override
		public UUID ownerId() throws IOException {
			
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
			return Base.EMPTY;
		}
		
		@Override
		public DataFieldType type() throws IOException {
			return DataFieldType.NONE;
		}
		
		@Override
		public int order() throws IOException {
			
			return 0;
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
		public IndicatorTypeParamCategory category() throws IOException {
			
			return null;
		}
		
		@Override
		public void update(AggregateFunc func) throws IOException {
			
			
		}
		
		@Override
		public IndicatorTypeDynamicParam origin() throws IOException {
			
			return null;
		}
		
		@Override
		public Indicator indicator() throws IOException {
			
			return null;
		}
		
		@Override
		public AggregateFunc aggregateFunc() throws IOException {
			return AggregateFunc.NONE;
		}

		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}
	};
}
