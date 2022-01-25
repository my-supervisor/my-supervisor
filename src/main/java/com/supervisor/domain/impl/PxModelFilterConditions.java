package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.ModelFilter;
import com.supervisor.domain.ModelFilterCondition;
import com.supervisor.domain.ModelFilterConditions;
import com.supervisor.domain.ParamDataField;

public final class PxModelFilterConditions extends DomainRecordables<ModelFilterCondition, ModelFilterConditions> implements ModelFilterConditions {
	
	private final ModelFilter filter;
	
	public PxModelFilterConditions(final RecordSet<ModelFilterCondition> source, final ModelFilter filter) throws IOException {
		super(PxModelFilterCondition.class, source);
		
		this.filter = filter;
		this.source = source.where(ModelFilterCondition::filter, filter.id())
				            .orderBy(ModelFilterCondition::id);
	}
	
	@Override
	protected ModelFilterConditions domainSetOf(final RecordSet<ModelFilterCondition> source) throws IOException {
		return new PxModelFilterConditions(source, filter);
	}
	
	@Override
	public ModelFilterCondition add(DataField field, Comparator comparator, String value) throws IOException {
		
		source.isRequired(ModelFilterCondition::value, value); 
		
		source.mustCheckThisCondition(
				filter.model().fields().contains(field), 
				String.format("Mappage de champs : le champ %s n'existe pas dans le modèle !", field.name())
		);
		
		source.mustCheckThisCondition(
				!(comparator == null || comparator == Comparator.NONE), 
				"Vous devez spécifier un comparateur !"
		); 
		
		DataFieldType dataType = field.type();
		List<ParamDataField> params = filter.model().params().items();
		String adaptedValue = PxModelFilterCondition.adaptValue(dataType, value, params);
		PxModelFilterCondition.validateType(dataType, adaptedValue, params);
		
		Record<ModelFilterCondition> record = source.entryOf(ModelFilterCondition::filter, filter.id())
												   .entryOf(ModelFilterCondition::field, field.id())
												   .entryOf(ModelFilterCondition::comparator, comparator)
												   .entryOf(ModelFilterCondition::value, adaptedValue)
												   .add();
		
		return domainOf(record);
	}	
}
