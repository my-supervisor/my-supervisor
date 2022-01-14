package com.minlessika.supervisor.indicator.impl;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorDynamicNumberParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicParams;
import com.minlessika.supervisor.indicator.IndicatorDynamicStringParam;
import com.minlessika.supervisor.indicator.IndicatorTypeDynamicParam;

public final class PgIndicatorDynamicParams extends DomainRecordables<IndicatorDynamicParam, IndicatorDynamicParams> implements IndicatorDynamicParams  {

	private final Indicator indicator;
	
	public PgIndicatorDynamicParams(final Indicator indicator) throws IOException {
		this(viewSource(indicator), indicator);
	}
	
	public PgIndicatorDynamicParams(final RecordSet<IndicatorDynamicParam> source, final Indicator indicator) throws IOException {
		super(PxIndicatorDynamicParam.class, source);

		this.indicator = indicator;
		this.source = source.where(IndicatorDynamicParam::indicator, indicator.id())
				            .orderBy(IndicatorDynamicParam::order);
	}
	
	@Override
	protected IndicatorDynamicParams domainSetOf(final RecordSet<IndicatorDynamicParam> source) throws IOException {
		return new PgIndicatorDynamicParams(source, indicator);
	}

	private static RecordSet<IndicatorDynamicParam> viewSource(final Indicator indicator) throws IOException{
		Table table = new TableImpl(IndicatorDynamicParam.class);
		
		String viewScript = String.format("(\r\n" + 
											"	select src.*, target.code, target.no \r\n" + 
					                        "   from %s as src \r\n" + 
											"	left join %s as target on target.id = src.origin_id \r\n" +
										  ") as %s",
										table.name(),
										new TableImpl(IndicatorTypeDynamicParam.class).name(),										
										table.name()
							);
		
		return indicator.base()
				        .select(IndicatorDynamicParam.class, viewScript);
	}
	
	@Override
	public IndicatorDynamicParam put(IndicatorTypeDynamicParam origin, AggregateFunc aggregateFunc)
			throws IOException {
		
		IndicatorDynamicParam param;
		
		if(where(IndicatorDynamicParam::origin, origin.id()).any()) {
			param = where(IndicatorDynamicParam::origin, origin.id()).first();
			param.update(aggregateFunc); 
		}else {
			
			if(!origin.indicatorType().id().equals(indicator.type().id()))
				throw new IllegalArgumentException("Le paramètre dynamique n'est pas du même type que l'indicateur !");
			
			Record<IndicatorDynamicParam> record = source.entryOf(IndicatorDynamicParam::indicator, indicator.id())
													    .entryOf(IndicatorDynamicParam::origin, origin.id())
													    .entryOf(IndicatorDynamicParam::aggregateFunc, aggregateFunc)
													    .add();
			
			switch (origin.type()) {
				case NUMBER:
					RecordSet<IndicatorDynamicNumberParam> numberSource = source.of(IndicatorDynamicNumberParam.class);
					numberSource.entryOf(IndicatorDynamicNumberParam::id, record.id())
								.entryOf(IndicatorDynamicNumberParam::precision, 0)
								.entryOf(IndicatorDynamicNumberParam::applyThousandsSeparator, true)
								.add();
					break;
				case STRING:
					RecordSet<IndicatorDynamicStringParam> stringSource = source.of(IndicatorDynamicStringParam.class);
					stringSource.entryOf(IndicatorDynamicStringParam::id, record.id())
								.entryOf(IndicatorDynamicStringParam::markup, StringUtils.EMPTY)
								.add();
					break;
				default:
					break;
			}
			
			param = new PxIndicatorDynamicParam(record);
		}
		
		return param;
	}

	@Override
	public IndicatorDynamicParam get(String code) throws IOException {
		return where(IndicatorDynamicParam::code, code).first();
	}

	@Override
	public boolean isValidCode(String code) throws IOException {
		return where(IndicatorDynamicParam::code, code).any();
	}
	
}
