package com.minlessika.supervisor.domain.bi.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.minlessika.supervisor.domain.bi.*;
import org.apache.commons.lang.StringUtils;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.Ordering;
import com.minlessika.sdk.datasource.ResultStatement;
import com.minlessika.sdk.datasource.comparators.Matchers;
import com.minlessika.sdk.datasource.conditions.Condition;
import com.minlessika.sdk.datasource.conditions.Filter;
import com.minlessika.sdk.datasource.conditions.pgsql.ConditionOperator;
import com.minlessika.sdk.datasource.conditions.pgsql.PgFilter;
import com.minlessika.sdk.datasource.conditions.pgsql.PgSimpleCondition;
import com.minlessika.sdk.utils.logging.Logger;
import com.minlessika.sdk.utils.logging.MLogger;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.Params;
import com.minlessika.supervisor.domain.impl.PgFilterOfModel;
import com.minlessika.supervisor.domain.impl.PgTypeOfDataFieldType;
import com.minlessika.supervisor.domain.impl.PxAggregatedModel;

public final class PgDataWarehouse implements DataWarehouse {

	private static final Logger logger = new MLogger(PgDataWarehouse.class);
	
	private final Base base;
	
	public PgDataWarehouse(final Base base) {
		this.base = base;
	}
	
	public static String aggregateColumn(String name, DataFieldType type, AggregateFunc func) {
		
		final String agg;
		
		switch (func) {
			case SUM:
				agg = columnText("SUM", name, type);
				break;
			case COUNT:
				agg = columnText("COUNT", name, type);
				break;
			case MAX:
				agg = columnText("MAX", name, type);
				break;	
			case MIN:
				agg = columnText("MIN", name, type);
				break;
			case AVG:
				agg = columnText("AVG", name, type);
				break;
			case FIRST_VALUE:
			case LAST_VALUE:
				if(type == DataFieldType.NONE) {
					agg = String.format("%s", name);
				} else {
					agg = String.format("%s::%s", name, new PgTypeOfDataFieldType(type).name());
				}	
				break;
			default:
				agg = name;
				break;
		}
		
		return agg;
	}
	
	private static String columnText(String function, String name, DataFieldType type) {
		if(type == DataFieldType.NONE) {
			return String.format("%s(%s)", function, name);
		} else {
			return String.format("%s(%s::%s)", function, name, new PgTypeOfDataFieldType(type).name());
		}	
	}

	class ScriptResult {
    	private final String script;
    	private final List<Object> parameters;
    	
    	public ScriptResult(final String script, final List<Object> parameters) {
    		this.script = script;
    		this.parameters = parameters;
    	}
    	
    	public String script() {
    		return script;
    	}
    	
    	public List<Object> parameters(){
    		return parameters;
    	}
    }

	private ScriptResult scriptOf(BiRequest request) throws IOException {
		
		String script = StringUtils.EMPTY;
		List<Object> parameters = new ArrayList<>();
		
		for (BiSelect select : request.selects().items()) {
			
			final DataModel model = select.view();

			final DataField dateReference;
			if(model.type() == DataModelType.DATA_SHEET_MODEL) {
				dateReference = model.fields().get("DATE");
			} else {
				dateReference = ((AggregatedModel)model).dateReference();
			}
			
			final BiPeriod period = select.request().period();
			final Condition periodCondition;
			if(period == BiPeriod.EMPTY) {
				periodCondition = Condition.EMPTY;
			} else {
				Filter<DataFieldOfSheet> filterPeriod = new PgFilter<>(DataFieldOfSheet.class);
				
				switch (select.dataDomainDefinition()) {
					case DURING_PERIOD:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.between(period.begin(), period.end())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case BEGIN_PERIOD_TO_NOW:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.between(period.begin(), request.period().today())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case NOW_TO_END_PERIOD:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.between(request.period().today(), period.end())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case BEFORE_PERIOD:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.lessThan(period.begin())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case AFTER_PERIOD:											
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.greatThan(period.end())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case BEFORE_NOW:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.lessThan(request.period().today())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case AFTER_NOW:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.greatThan(request.period().today())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case BEFORE_TO_NOW:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.lessOrEqualsTo(request.period().today())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case NOW_TO_AFTER:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.greaterOrEqualsTo(request.period().today())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case BEFORE_AND_AFTER_PERIOD:
						Filter<DataFieldOfSheet> orFilterPeriod = new PgFilter<>(DataFieldOfSheet.class, ConditionOperator.OR);
						
						orFilterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.lessThan(period.begin())
								)
							);
						
						orFilterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.greatThan(period.end())
								)
							);
						
						periodCondition = orFilterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case BEFORE_AND_DURING_PERIOD:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.lessOrEqualsTo(period.end())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					case DURING_PERIOD_AND_AFTER:
						filterPeriod.add(
								new PgSimpleCondition(
										String.format("sheet.\"%s\"", dateReference.code()),
										DataSheet.class, DataSheet::date, 
										Matchers.greaterOrEqualsTo(period.begin())
								)
							);
						periodCondition = filterPeriod.condition();
						parameters.addAll(periodCondition.parameters());
						break;
					default:
						// ALL_PERIOD
						periodCondition = Condition.EMPTY;
						break;
				}				
			}
			
			Condition otherConditionScript = Condition.EMPTY;
			if(model.type() == DataModelType.AGGREGATED_MODEL) {
				final AggregatedModel aModel = new PxAggregatedModel(model);
				final Params params = select.params();
				final LocalDate today = select.request().period().today();
				final Filter<DataFieldOfSheet> otherFilters = new PgFilter<>(DataFieldOfSheet.class);
				otherFilters.append(
			    	new PgFilterOfModel(aModel.filters(), params, period)
			    );
							
				otherConditionScript = otherFilters.condition();
				parameters.addAll(otherConditionScript.parameters());
			}
						
			String subScript = new PgModelViewPrinter(select).toText();
			if(periodCondition != Condition.EMPTY) {
				subScript = String.format(
						"select * from \r\n"
					  + "(%s) as sheet \r\n"
					  + "%s \r\n",
					    subScript,
					    String.format("where %s", periodCondition.toScritpt())
			    );
			}
			
			if(otherConditionScript != Condition.EMPTY) {
				subScript = String.format(
						"select * from \r\n"
					  + "(%s) as sheet \r\n"
					  + "%s \r\n",
					    subScript,
					    String.format("where %s", otherConditionScript.toScritpt())
			    );
			}
			
			final Ordering ordering = select.ordering();
			if(ordering != Ordering.EMPTY) {
				subScript = String.format(
						    "%s \r\n"
						  + "%s", 				
						    subScript,
							ordering.script()
						 );
			}
			
			if(select.start() > 0) {
				subScript = String.format("%s \r\n offset %d", subScript, select.start() - 1);
			}
			
			if(select.limit() > 0) {
				subScript = String.format("%s \r\n limit %d", subScript, select.limit());
			}
			
			subScript = String.format(
							"( \r\n" +
							"	select %s from (\r\n" +
							"	%s \r\n" +
							"   ) as sheet \r\n" +
							") \r\n", 
							new PgSelectPrinter(select).toText(),
							subScript
						);
			
			if(StringUtils.isBlank(script))
				script = subScript;
			else
				script = String.format("%s UNION ALL %s", script, subScript);
		}
		
		script = String.format(
						"select * \r\n"
					  + "from (\r\n"
					  + "%s \r\n"
					  + ") as sheet \r\n",
					  script
				 );
		
		if(request.columns().canGroup()) {
			script = String.format(
							"select %s \r\n"
						  + "from (%s) as sheet \r\n"
						  + "%s",
						  new PgColumnsPrinter(request.columns()).toText(),
						  script,
						  new PgGroupPrinter(request.columns()).toText()
					 );
		}
		
		final Ordering ordering = request.ordering();
		if(ordering != Ordering.EMPTY) {
			script = String.format(
					    "%s \r\n"
					  + "%s", 				
						script,
						ordering.script()
					 );
		}

		return new ScriptResult(script, parameters);
	}

	@Override
	public BiResult query(BiRequest request) throws IOException {
		
		if(request == BiRequest.EMPTY || request.selects().items().isEmpty())
			return BiResult.EMPTY;
		
		final ScriptResult script = scriptOf(request);
		final String shortScript = script.script();
		String fullScript = shortScript;
		if(request.start() > 0) {
			fullScript = String.format("%s \r\n offset %d", fullScript, request.start() - 1);
		}
		
		if(request.limit() > 0) {
			fullScript = String.format("%s \r\n limit %d", fullScript, request.limit());
		} else if (request.columns().items().size() == 1){
			final BiColumn column = request.columns().items().get(0);
			if(column.aggregate() == AggregateFunc.FIRST_VALUE || column.aggregate() == AggregateFunc.LAST_VALUE) {
				fullScript = String.format(
					"%s FETCH FIRST 1 ROWS ONLY", 
					fullScript
				);
			}
		}
		
		logger.debug(fullScript);
		String paramStr = StringUtils.EMPTY;
		for (Object object : script.parameters()) {
			paramStr = String.format("%s %s", paramStr, object);
		}
		
		logger.debug(paramStr);
		
		List<ResultStatement> rows = base.query(fullScript, script.parameters());
		
		final long count = Long.parseLong(
									base.query(String.format("select count(*) as nb from (%s) as sheet", shortScript), script.parameters())
						            .get(0)
						            .data()
						            .get("nb")
						            .toString()
						    );
		
		final BiResult result = new BiResultImpl(count);
		
		for (ResultStatement res : rows) {
			final BiRow row = result.addRow();
			
			for (Entry<String, Object> data : res.data().entrySet()) {
				row.addCell(data.getKey(), data.getValue());
			}
		}
		
		return result;	
	}
}
