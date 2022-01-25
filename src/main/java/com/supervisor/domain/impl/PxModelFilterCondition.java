package com.supervisor.domain.impl;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DateMacroArg;
import com.supervisor.domain.ModelFilter;
import com.supervisor.domain.ModelFilterCondition;
import com.supervisor.domain.ParamDataField;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.domain.bi.BiPeriod;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;

public final class PxModelFilterCondition extends DomainRecordable<ModelFilterCondition> implements ModelFilterCondition {

	public PxModelFilterCondition(final Record<ModelFilterCondition> record) throws IOException {
		super(record);
	}

	@Override
	public ModelFilter filter() throws IOException {
		Record<ModelFilter> rec = record.of(ModelFilterCondition::filter);
		return new PxModelFilter(rec);
	}

	@Override
	public DataField field() throws IOException {
		return TypedDataField.convert(
					record.of(ModelFilterCondition::field)
			   ); 
	}

	@Override
	public Comparator comparator() throws IOException {
		return record.valueOf(ModelFilterCondition::comparator);
	}

	@Override
	public String value() throws IOException {
		return record.valueOf(ModelFilterCondition::value);
	}

	@Override
	public void update(DataField field, Comparator comparator, String value) throws IOException {
		
		record.isRequired(ModelFilterCondition::value, value); 
		
		record.mustCheckThisCondition(
				filter().model().fields().contains(field), 
				String.format("Mappage de champs : le champ %s n'existe pas dans le modèle !", field.name())
		);
		
		record.mustCheckThisCondition(
				!(comparator == null || comparator == Comparator.NONE), 
				"Vous devez spécifier un comparateur !"
		); 
		
		DataFieldType dataType = field.type();
		List<ParamDataField> params = filter().model().params().items();
		String adaptedValue = adaptValue(dataType, value, params);
		validateType(dataType, adaptedValue, params);
		
		record.entryOf(ModelFilterCondition::field, field.id())
		      .entryOf(ModelFilterCondition::comparator, comparator)
			  .entryOf(ModelFilterCondition::value, adaptedValue)
			  .update();
	}
	
	public static String adaptValue(final DataFieldType type, String value, List<ParamDataField> params) throws IOException {
		String newValue = value;
		
		FilterParamArg pArg = new FilterParamArg(newValue, params);
		if(pArg.isValid()) {
			ParamDataField param = pArg.param();
			if(param.type() != type)
				throw new IllegalArgumentException(String.format("Le paramètre de filtre %s n'est pas valide !", param.name()));
			
			newValue = pArg.value();
		}else {
			newValue = new DataFieldValueImpl(type, newValue).cleaned();
		}
			
		return newValue;
	}
	
	public static void validateType(final DataFieldType type, String value, List<ParamDataField> params) throws IOException {
		
		if(StringUtils.isBlank(value))
			return;
		
		FilterParamArg pArg = new FilterParamArg(value, params);
		if(!pArg.isValid()) {
			// vérifier que c'est une date macro
			DateMacroArg dMArg = new DateMacroArgImpl(value, BiPeriod.EMPTY);
			if(!dMArg.isValid()) {
				// c'est une valeur. vérifier sa validité
				new DataFieldValueImpl(type, value).validate();
			}			
		}
	}
}
