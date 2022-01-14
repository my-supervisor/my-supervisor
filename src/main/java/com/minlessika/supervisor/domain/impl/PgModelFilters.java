package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.ModelFilter;
import com.minlessika.supervisor.domain.ModelFilters;

public final class PgModelFilters extends DomainRecordables<ModelFilter, ModelFilters> implements ModelFilters {
	
	private final AggregatedModel model;
	
	public PgModelFilters(final AggregatedModel model) throws IOException {
		this(viewSource(model), model);
	}
	
	public PgModelFilters(final RecordSet<ModelFilter> source, final AggregatedModel model) throws IOException {
		super(PxModelFilter.class, source);
		
		this.model = model;
		this.source = source.orderBy(ModelFilter::model, ModelFilter::id, OrderDirection.ASC);
	}
	
	private static String scriptOf(final DataModel model) {
		final Table table = new TableImpl(ModelFilter.class);
		return String.format(
					"select src.* \r\n" + 
					"from %s as src \r\n" + 
					"where src.model_id = %s",
					table.name(),									
					model.id()
			   );
	}
	
	private static RecordSet<ModelFilter> viewSource(final AggregatedModel model) throws IOException{

		String viewScript = StringUtils.EMPTY;
		DataModel currentModel = model;
		do {
			if(StringUtils.isBlank(viewScript)) {
				viewScript = scriptOf(currentModel);
			} else {
				viewScript = String.format(
								"%s \r\n" + 
								"UNION ALL \r\n" +
								"%s",
								viewScript,
								scriptOf(currentModel)
							 );
			}
			
			if(currentModel.type() == DataModelType.AGGREGATED_MODEL) {
				currentModel = ((AggregatedModel)currentModel).model();
			}
		} while(currentModel.type() == DataModelType.AGGREGATED_MODEL);			
		
		viewScript = String.format(
						"(\r\n" +
						"	%s \r\n" + 
						") as %s",
						viewScript,
						new TableImpl(ModelFilter.class).name()
					 );
		
		return model.base()
				    .select(ModelFilter.class, viewScript);
	}
	
	@Override
	protected ModelFilters domainSetOf(final RecordSet<ModelFilter> source) throws IOException {
		return new PgModelFilters(source, model);
	}
	
	@Override
	public ModelFilter add() throws IOException {
		
		Record<ModelFilter> record = source.entryOf(ModelFilter::model, model.id())
									       .add();
		
		return domainOf(record);
	}
	
	@Override
	public void remove(ModelFilter item) throws IOException {
		if(item.model().id().equals(model.id())) {
			super.remove(item);
		}
	}
}
