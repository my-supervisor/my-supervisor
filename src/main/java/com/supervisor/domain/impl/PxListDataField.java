package com.supervisor.domain.impl;

import com.supervisor.domain.DataDomainDefinition;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.ListDataFieldSources;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Ordering;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.pgsql.PgSimpleOrdering;
import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.BiRequest;
import com.supervisor.domain.bi.BiResult;
import com.supervisor.domain.bi.impl.BiDataFieldColumn;
import com.supervisor.domain.bi.impl.BiRequestImpl;
import com.supervisor.domain.bi.impl.PgDataWarehouse;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class PxListDataField extends PxEditableDataField implements ListDataField {

	private final Record<ListDataField> listRecord;
	
	public PxListDataField(Record<DataField> record) throws IOException {
		super(record);
		
		this.listRecord = record.listOf(ListDataField.class).get(record.id());
	}

	@Override
	protected void checkStyle(Record<DataField> record) throws IOException {
		if(record.valueOf(DataField::style) != DataFieldStyle.LIST)
			throw new IllegalArgumentException("Field isn't a list !");
	}
	
	@Override
	public OrderDirection orderDirection() throws IOException {
		return listRecord.valueOf(ListDataField::orderDirection);
	}

	@Override
	public BiResult values(String filter, long start, int limit) throws IOException {
		
		if(sources().isEmpty())
			return BiResult.EMPTY;
		
		final DataField orderField = orderField();
		final Ordering ordering = new PgSimpleOrdering(StringUtils.EMPTY, orderDirection(), Arrays.asList(String.format("\"%s\"", orderField.code())));
		
		final BiRequest request = new BiRequestImpl(filter, BiPeriod.EMPTY, start, limit, ordering);
		for (ListDataFieldSource source : sources().actives().items()) {
			final DataModel model = source.model();
			final Map<DataField, DataLinkOperator> columns = new LinkedHashMap<>();
			columns.put(source.fieldToDisplay(), DataLinkOperator.NONE);
			columns.put(source.orderField(), DataLinkOperator.NONE);
			columns.put(source.model().fields().get("REF"), DataLinkOperator.NONE);	
			request.selects()
			       .add(
		    		   model, 
		    		   columns, 
		    		   Arrays.asList(source.fieldToDisplay()), 
		    		   new ParamsImpl(
		    				   model.fields()
		    				        .params()
		    				        .items()
		    		   ),
		    		   DataDomainDefinition.ALL_PERIOD,
		    		   Ordering.EMPTY,
		    		   0L,
		    		   0
			    	);
		}
		
		request.columns().add(new BiDataFieldColumn(fieldToDisplay()));
		request.columns().add(new BiDataFieldColumn(sources().first().model().fields().get("REF")));
		
		return new PgDataWarehouse(base()).query(request); 
	}

	@Override
	public ListDataFieldSources sources() throws IOException {
		return new PxListDataFieldSources(this);
	}

	@Override
	public int limit() throws IOException {
		return listRecord.valueOf(ListDataField::limit);
	}

	@Override
	public BiResult values(String filter, long start) throws IOException {
		return values(filter, start, limit());
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {
		throw new UnsupportedOperationException("ListDataField::update");
	}
	
	@Override
	public void update(OrderDirection orderDirection, int limit) throws IOException {
		
		listRecord.entryOf(ListDataField::orderDirection, orderDirection)
			  .entryOf(ListDataField::limit, limit)
			  .update();
	}

	@Override
	public DataField fieldToDisplay() throws IOException {		
		return sources().first().fieldToDisplay();
	}

	@Override
	public DataField orderField() throws IOException {
		return sources().first().orderField();
	}

	@Override
	public BiResult value(String reference) throws IOException {
		
		if(sources().isEmpty())
			return BiResult.EMPTY;
		
		final BiRequest request = new BiRequestImpl(reference, BiPeriod.EMPTY, 1, 1, Ordering.EMPTY);
		for (ListDataFieldSource source : sources().actives().items()) {
			final DataModel model = source.model();
			final Map<DataField, DataLinkOperator> columns = new LinkedHashMap<>();
			columns.put(source.fieldToDisplay(), DataLinkOperator.NONE);
			columns.put(source.orderField(), DataLinkOperator.NONE);
			request.selects()
			       .add(
			    		   model, 
			    		   columns, 
			    		   Arrays.asList(source.model().fields().get("REF")), 
			    		   new ParamsImpl(
			    				   model.fields()
			    				        .params()
			    				        .items()
			    		   ),
			    		   DataDomainDefinition.ALL_PERIOD,
			    		   Ordering.EMPTY,
			    		   0L,
			    		   0
			    	);
		}
		
		return new PgDataWarehouse(base()).query(request);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public boolean mustEditOnce() throws IOException {
		return listRecord.valueOf(ListDataField::mustEditOnce);
	}

	@Override
	public void makeMustEditOnce(boolean mustEditOnce) throws IOException {
		listRecord.entryOf(ListDataField::mustEditOnce, mustEditOnce)
		      .update();
	}

	@Override
	public boolean isReadOnly() throws IOException {
		return listRecord.valueOf(ListDataField::isReadOnly);
	}

	@Override
	public void makeReadOnly(boolean readOnly) throws IOException {
		listRecord.entryOf(ListDataField::isReadOnly, readOnly)
	          .update();
	}

	@Override
	public boolean canBeUpdated() throws IOException {
		return listRecord.valueOf(ListDataField::canBeUpdated);
	}

	@Override
	public void makeCanBeUpdated(boolean update) throws IOException {
		listRecord.entryOf(ListDataField::canBeUpdated, update)
        	  .update();
	}

	@Override
	public BiResult values() throws IOException {
		
		if(sources().isEmpty())
			return BiResult.EMPTY;
		
		final DataField orderField = orderField();
		final Ordering ordering = new PgSimpleOrdering(StringUtils.EMPTY, orderDirection(), Arrays.asList(String.format("\"%s\"", orderField.code()))); 
		
		final BiRequest request = new BiRequestImpl(StringUtils.EMPTY, BiPeriod.EMPTY, 0, 0, ordering);
		for (ListDataFieldSource source : sources().actives().items()) {
			final DataModel model = source.model();			
			final Map<DataField, DataLinkOperator> columns = new LinkedHashMap<>();
			columns.put(source.fieldToDisplay(), DataLinkOperator.NONE);
			columns.put(source.orderField(), DataLinkOperator.NONE);
			columns.put(source.model().fields().get("REF"), DataLinkOperator.NONE);	
			request.selects()
			       .add(
		    		   model, 
		    		   columns, 
		    		   Arrays.asList(source.fieldToDisplay()), 
		    		   new ParamsImpl(
		    				   model.fields()
		    				        .params()
		    				        .items()
		    		   ),
		    		   DataDomainDefinition.ALL_PERIOD,
		    		   Ordering.EMPTY,
		    		   0L,
		    		   0
			    	);
		}
		
		request.columns().add(new BiDataFieldColumn(fieldToDisplay()));
		request.columns().add(new BiDataFieldColumn(sources().first().model().fields().get("REF")));
		
		return new PgDataWarehouse(base()).query(request);
	}

	@Override
	public void update(String code, String name, DataFieldType type, String description) throws IOException {
		super.update(code, name, type, DataFieldStyle.LIST, description); 
	}

	@Override
	public boolean isBasedOn(DataModel model) throws IOException {
		return sources().isBasedOn(model);
	}
	
	@Override
	public boolean isStrictBasedOn(DataModel model) throws IOException {
		return sources().isStrictBasedOn(model);
	}
}
