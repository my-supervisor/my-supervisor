package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorStaticParam;
import com.minlessika.supervisor.indicator.IndicatorStaticParams;
import com.minlessika.supervisor.indicator.IndicatorTypeStaticParam;

public final class PgIndicatorStaticParams extends DomainRecordables<IndicatorStaticParam, IndicatorStaticParams> implements IndicatorStaticParams {

	private final Indicator indicator;
	
	public PgIndicatorStaticParams(final Indicator indicator) throws IOException {
		this(viewSource(indicator), indicator);
	}
	
	public PgIndicatorStaticParams(final RecordSet<IndicatorStaticParam> source, final Indicator indicator) throws IOException {
		super(PxIndicatorStaticParam.class, source);

		this.indicator = indicator;
		this.source = source.where(IndicatorStaticParam::indicator, indicator.id())
				            .orderBy(IndicatorStaticParam::order);
	}
	
	@Override
	protected IndicatorStaticParams domainSetOf(final RecordSet<IndicatorStaticParam> source) throws IOException {
		return new PgIndicatorStaticParams(source, indicator);
	}
	
	private static RecordSet<IndicatorStaticParam> viewSource(final Indicator indicator) throws IOException{
		Table table = new TableImpl(IndicatorStaticParam.class);
		
		String viewScript = String.format("(\r\n" + 
											"	select src.*, target.code, target.no \r\n" + 
					                        "   from %s as src \r\n" + 
											"	left join %s as target on target.id = src.origin_id \r\n" +
										  ") as %s",
										table.name(),
										new TableImpl(IndicatorTypeStaticParam.class).name(),										
										table.name()
							);
		
		return indicator.base()
				        .select(IndicatorStaticParam.class, viewScript);
	}

	@Override
	public IndicatorStaticParam put(IndicatorTypeStaticParam origin, String value) throws IOException {
				
		IndicatorStaticParam param;
		
		if(where(IndicatorStaticParam::origin, origin.id()).any()) {
			param = where(IndicatorStaticParam::origin, origin.id()).first();			
		}else {
			
			if(!origin.indicatorType().id().equals(indicator.type().id()))
				throw new IllegalArgumentException("Le paramètre statique n'est pas du même type que l'indicateur !");
			
			source.isRequired(IndicatorStaticParam::value, value);
			
			Record<IndicatorStaticParam> record = source.entryOf(IndicatorStaticParam::indicator, indicator.id())
													    .entryOf(IndicatorStaticParam::origin, origin.id())
													    .entryOf(IndicatorStaticParam::value, value)
													    .add();
			
			param = new PxIndicatorStaticParam(record);
		}
		
		return param;
	}

	@Override
	public IndicatorStaticParam get(String code) throws IOException {
		return where(IndicatorStaticParam::code, code).first();
	}

	@Override
	public boolean isValidCode(String code) throws IOException {
		return where(IndicatorStaticParam::code, code).any();
	}	
}
