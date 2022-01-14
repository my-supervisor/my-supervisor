package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.utils.logging.Logger;
import com.minlessika.sdk.utils.logging.MLogger;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.indicator.IndicatorTypeParam;
import com.minlessika.supervisor.indicator.IndicatorTypeParamCategory;
import com.minlessika.supervisor.indicator.IndicatorTypeParams;
import com.minlessika.supervisor.indicator.IndicatorType;

public final class PxIndicatorTypeParams extends DomainRecordables<IndicatorTypeParam, IndicatorTypeParams> implements IndicatorTypeParams {

	private static final Logger logger = new MLogger(PxIndicatorTypeParams.class);
	
	private final IndicatorType indicatorType;
	
	public PxIndicatorTypeParams(final IndicatorType indicatorType) throws IOException {
		this(indicatorType.listOf(IndicatorTypeParam.class), indicatorType);
	}
	
	public PxIndicatorTypeParams(final RecordSet<IndicatorTypeParam> source, final IndicatorType indicatorType) throws IOException {
		super(PxIndicatorTypeParam.class, source);

		this.indicatorType = indicatorType;
		this.source = source.where(IndicatorTypeParam::indicatorType, indicatorType.id());
	}
	
	@Override
	protected IndicatorTypeParams domainSetOf(final RecordSet<IndicatorTypeParam> source) throws IOException {
		return new PxIndicatorTypeParams(source, indicatorType);
	}

	@Override
	public IndicatorTypeParam add(int order, String name, IndicatorTypeParamCategory category, String code, DataFieldType type) throws IOException {
		
		source.isRequired(IndicatorTypeParam::code, code);
		source.isRequired(IndicatorTypeParam::name, name);
		
		source.mustCheckThisCondition(
				category != null && category != IndicatorTypeParamCategory.NONE, 
				"Vous devez définir la catégorie du paramètre !"
		);
		
		source.mustCheckThisCondition(
				type != null && type != DataFieldType.NONE, 
				"Vous devez définir le type de donnée !"
		);
		
		source.mustBeUnique(
				IndicatorTypeParam::code, code, 
				IndicatorTypeParam::indicatorType, indicatorType
		);
		
		Record<IndicatorTypeParam> record = source.entryOf(IndicatorTypeParam::name, name)
				                              .entryOf(IndicatorTypeParam::code, code)
				                              .entryOf(IndicatorTypeParam::order, order)
				                              .entryOf(IndicatorTypeParam::type, type)
				                              .entryOf(IndicatorTypeParam::category, category)
				                              .entryOf(IndicatorTypeParam::indicatorType, indicatorType.id())
				                              .add();
		
		return domainOf(record);
	}

	@Override
	public IndicatorTypeParam get(String code) throws IOException {
		
		for (IndicatorTypeParam p : items()) {
			if(p.code().equals(code)) {
				return p;
			}			
		}
		
		throw new IllegalArgumentException(
				String.format("Le paramètre de l'indicateur de type %s n'est pas présent !", indicatorType.name())
		);
	}

	@Override
	public boolean isValidCode(String code) throws IOException {
		return items().stream()
					  .anyMatch(c -> {
								try {
									return c.code().equals(code);
								} catch (IOException e) {
									logger.error(e);
								}
								return false;
					  });
	}

}
