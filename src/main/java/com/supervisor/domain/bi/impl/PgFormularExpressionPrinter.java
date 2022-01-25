package com.supervisor.domain.bi.impl;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldArg;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.EditableDataFieldArg;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionValueArg;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.FormularCondition;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExpressionType;
import com.supervisor.domain.FormularExtendedToChildExpression;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.FormularFunc;
import com.supervisor.domain.FormularSimpleExpression;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.Params;
import com.supervisor.domain.User;
import com.supervisor.domain.ValueArg;
import com.supervisor.domain.WhenCase;
import com.supervisor.domain.impl.OwnerOf;
import com.supervisor.domain.impl.UserOf;
import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.metadata.BaseType;
import com.supervisor.sdk.pgsql.PgRecordSet;
import com.supervisor.domain.bi.AggregateFunc;
import com.supervisor.domain.bi.BiPeriod;
import com.supervisor.domain.bi.FormularExpressionPrinter;
import com.supervisor.domain.impl.DefaultDataFieldValue;
import com.supervisor.domain.impl.PgDataSheets;
import com.supervisor.domain.impl.PgTypeOfDataFieldType;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public final class PgFormularExpressionPrinter implements FormularExpressionPrinter {

	private final FormularExpression expr;

	/**
	 * Period.
	 */
	private final BiPeriod period;

	private final Params params;
	private final List<Object> parameters;
	
	public PgFormularExpressionPrinter(
		final FormularExpression expr,
		final Params params,
		final BiPeriod period
	) {
		this.expr = expr;
		this.period = period;
		this.params = params;
		this.parameters = new ArrayList<>();
	}
	
	@Override
	public String toText() throws IOException {

		parameters.clear();
				
		String script = StringUtils.EMPTY;
		
		FormularExpression mainExpr = expr.formular().mainExpression();
		FormularCondition condition = expr.formular().condition();
		
		if(expr.id().equals(mainExpr.id()) && !condition.isTrue()) {
			return String.format("%s", condition.defaultValue());
		}
		final LocalDate today = period.today();
		final FormularExpressionType type = expr.type();
		switch (type) {
			case SIMPLE:
				FormularSimpleExpression simpleExpr = (FormularSimpleExpression)expr;
				List<String> argScripts = new ArrayList<>();
				
				switch (simpleExpr.func()) {
					case FIRST:
						script = firstFuncExprScript(simpleExpr.arguments().first());
						break;
					case PREVIOUS:
						script = previousFuncExprScript(simpleExpr.arguments().first());
						break;
					case NEXT:
						script = nextFuncExprScript(simpleExpr.arguments().first());
						break;
					case DAYS_OF_PERIOD:
						script = daysOfPeriodFuncExprScript(simpleExpr.arg(1), simpleExpr.arg(2));
						break;
					case NOW_DATE:
						script = String.format("'%s'", today.format(DateTimeFormatter.ISO_LOCAL_DATE));
						break;
					case BEGIN_OF_PERIOD:
						script = String.format("'%s'", period.begin().format(DateTimeFormatter.ISO_LOCAL_DATE));
						break;
					case END_OF_PERIOD:
						script = String.format("'%s'", period.end().format(DateTimeFormatter.ISO_LOCAL_DATE));
						break;
					case BEGIN_OF_MONTH:
						script = String.format("'%s'", LocalDate.of(today.getYear(), today.getMonth(), 01).format(DateTimeFormatter.ISO_LOCAL_DATE));
						break;
					case END_OF_MONTH:
						script = String.format("'%s'", LocalDate.of(today.getYear(), today.getMonth(), today.lengthOfMonth()).format(DateTimeFormatter.ISO_LOCAL_DATE));
						break;
					case BEGIN_OF_YEAR:
						script = String.format("'%s'", LocalDate.of(today.getYear(), 01, 01).format(DateTimeFormatter.ISO_LOCAL_DATE));
						break;
					case END_OF_YEAR:
						script = String.format("'%s'", LocalDate.of(today.getYear(), 12, 31).format(DateTimeFormatter.ISO_LOCAL_DATE));
						break;
					default:
						for (ExpressionArg arg : simpleExpr.arguments().items()) {
							argScripts.add(argScript(arg, true));
						}
						script =  script(simpleExpr.func(), argScripts);
						break;
				}
				break;
			case CASE:
				FormularCaseExpression caseExpr = (FormularCaseExpression)expr;
				
				String whenScript = StringUtils.EMPTY;
				for (WhenCase when : caseExpr.cases().items()) {
					if(StringUtils.isBlank(whenScript))
						whenScript = whenCaseScript(when);
					else
						whenScript = String.format(
										  "%s \r\n"
										+ "%s",
										  whenScript,
										  whenCaseScript(when)
									  );
				}
				
				script = String.format(
							  "case \r\n"
							+ "%s \r\n"
							+ "else %s \r\n"
							+ "end", 
							whenScript,
							argScript(caseExpr.defaultValue(), true) 
						);
				break;
			case EXTENDED_TO_PARENT:
				final FormularExtendedToParentExpression extExpr = (FormularExtendedToParentExpression)expr;
				final ListDataField reference = extExpr.reference();
				final String none = new DefaultDataFieldValue(extExpr.formular().type()).stringValue();
				script = none;
				for (FormularExtendedToParentSource extSource : extExpr.sources().actives().items()) {
					final EditableDataField field = extSource.field();
					String subScript = String.format(
											  "select dvalue.value \r\n"
											+ "from supervisor_data_field_of_sheet as dvalue \r\n"
											+ "where dvalue.sheet_id in ( \r\n"
											+ "		select sheet_to_refer_id \r\n"
											+ "		from  supervisor_data_field_of_sheet as dfs \r\n"
											+ "		left join supervisor_data_field as df on df.id = dfs.origin_field_id \r\n"
											+ "		where df.code='%s' and dfs.sheet_id = sheet.id \r\n"
											+ ") and dvalue.origin_field_id = %s \r\n", 
											reference.code(),
											field.id()
									   );
					
					if(script.equals(none)) {
						script = subScript;
					} else {
						script = String.format("%s UNION ALL %s", script, subScript);
					}
				}
				break;
			case EXTENDED_TO_MODEL:
				final FormularExtendedToModelExpression extModelExpr = (FormularExtendedToModelExpression)expr;
				for (FormularExtendedToModelSource extSource : extModelExpr.sources().actives().items()) {
					final EditableDataField modelField = extSource.modelField();
					final EditableDataField fieldToExtend = extSource.fieldToExtend();
					final DataField ref = extSource.reference();
					
					BaseType fType = new PgTypeOfDataFieldType(modelField.type());
					BaseType fieldToExtendType = new PgTypeOfDataFieldType(fieldToExtend.type());
					final String clause = PgRecordSet.sqlClauseOf(String.format("value::%s", fType.name()), extSource.comparator(), String.format("sheet.\"%s\"", ref.code()));
					
					final String subScript = String.format(
													  "select %s \r\n"
													+ "from supervisor_data_field_of_sheet \r\n"
													+ "where sheet_id in ( \r\n"
													+ "		select sheet_id \r\n"
													+ "		from  supervisor_data_field_of_sheet \r\n"
													+ "		where origin_field_id=%s and %s \r\n"
													+ ") and origin_field_id = %s \r\n", 
													String.format("value::%s", fieldToExtendType.name()),
													modelField.id(),
													clause,
													fieldToExtend.id()
											 );
					
					if(StringUtils.isBlank(script)) {
						script = subScript;
					} else {
						script = String.format("%s UNION ALL %s", script, subScript);
					}										
				}
				
				final ExpressionArg defaultValue = extModelExpr.defaultValue();
				final String defaultValueScript = argScript(defaultValue, true);
				
				if(StringUtils.isBlank(script)) {
					script = defaultValueScript;
				} else {
					final AggregateFunc aggregate = extModelExpr.aggregate();
					if(aggregate == AggregateFunc.FIRST_VALUE || aggregate == AggregateFunc.LAST_VALUE) {						
						if(aggregate == AggregateFunc.FIRST_VALUE) {
							script = String.format(
									"select value \r\n"
								  + "from ( \r\n"
								  + "	%s \r\n"
								  + ") as my_table "
								  + "order by value asc FETCH FIRST 1 ROWS ONLY \r\n", 
								  	script
								);
						} else {
							script = String.format(
									"select value \r\n"
								  + "from ( \r\n"
								  + "	%s \r\n"
								  + ") as my_table "
								  + "order by value desc FETCH FIRST 1 ROWS ONLY \r\n", 
								  	script
								);
						}
						
					} else {
						script = String.format(
								"select %s as value \r\n"
							  + "from ( \r\n"
							  + "	%s \r\n"
							  + ") as my_table \r\n", 
							  	PgDataWarehouse.aggregateColumn(
									"value", 
									DataFieldType.NONE,
									aggregate
								),
							  	script
							);
					}
										
					script = String.format(
								  "COALESCE ((%s), %s) \r\n",
								  script,
								  defaultValueScript
							 );
				}				
				break;
			case EXTENDED_TO_CHILD:
				final FormularExtendedToChildExpression extChildExpr = (FormularExtendedToChildExpression)expr;
				final EditableDataField child = extChildExpr.child();
				script = String.format(
						    "select COALESCE(%s, %s) \r\n"
						  + "from supervisor_data_field_of_sheet as dvalue \r\n"
						  + "left join supervisor_data_field_of_sheet as dkey on dkey.sheet_id = dvalue.sheet_id \r\n"
						  + "where dkey.sheet_to_refer_id = sheet.id and dvalue.origin_field_id = %s \r\n", 
							PgDataWarehouse.aggregateColumn("dvalue.value", child.type(), extChildExpr.aggregate()),
							new DefaultDataFieldValue(child.type()).stringValue(),
							child.id()
						);								
				break;
			default:
				throw new IllegalArgumentException(String.format("Formular printer : %s is not supported !", type.toString()));
		}
		
		return script;
	}
	
	private String whenCaseScript(WhenCase when) throws IOException {
		
		String clause = PgRecordSet.sqlClauseOf(argScript(when.leftArg(), true), when.comparator(), argScript(when.rightArg(), true));	
		return String.format("when %s then %s", clause, argScript(when.result(), true));
	}
	
	private String firstFuncExprScript(ExpressionArg arg) throws IOException {
		
		final String script;
		
		// prendre la première valeur de la période
		final DataSheets sheetsOfPeriod;
		final DataSheetModel model = expr.formular().model().coreModel();
		final User user = new UserOf(expr);
		if(user.notOwn(expr)) {
			// tableau de bord partagé. prendre les feuilles du propriétaire
			sheetsOfPeriod = new PgDataSheets(new OwnerOf(expr)).where(DataSheet::date, Matchers.between(period.begin(), period.end()))
					                                            .where(DataSheet::model, model.id())
					   										    .orderBy(DataSheet::date, DataSheet::id);
		}else {
			// tableau de bord partagé. prendre les feuilles de l'utilisateur
			sheetsOfPeriod = new PgDataSheets(user).where(DataSheet::date, Matchers.between(period.begin(), period.end()))
												   .where(DataSheet::model, model.id())
					   							   .orderBy(DataSheet::date, DataSheet::id);
		}			
		
		final EditableDataField field = ((EditableDataFieldArg)arg).field();
		final BaseType aType = new PgTypeOfDataFieldType(field.type());
		
		if(sheetsOfPeriod.isEmpty()) {
			switch (field.type()) {
				case BOOLEAN:
					script = String.format("%s::%s", "false", aType.name());
					break;
				case DATE:
					script = String.format("%s::%s", period.begin().format(DateTimeFormatter.ISO_LOCAL_DATE), aType.name());
					break;
				case STRING:
					script = String.format("%s::%s", StringUtils.EMPTY, aType.name());
					break;
				case NUMBER:
					script = String.format("%s::%s", 0, aType.name());
					break;
				default:
					throw new IllegalArgumentException("Le type du champ de données n'est pas pris en charge !");
			}
			 
		}
		else {
			
			String firstValue = sheetsOfPeriod.first()
								              .fields()
								              .where(DataFieldOfSheet::code, field.code())
								              .first()
								              .value();
							
			script = String.format("%s::%s", firstValue, aType.name());
		}
		
		return script;
	}
	
	private String previousFuncExprScript(ExpressionArg arg) throws IOException {		
		EditableDataField field = ((EditableDataFieldArg)arg).field();
		return String.format("lead(\"%s\") OVER (ORDER BY date DESC, id DESC)", field.code());
	}
	
	private String nextFuncExprScript(ExpressionArg arg) throws IOException {		
		EditableDataField field = ((EditableDataFieldArg)arg).field();
		return String.format("lag(\"%s\") OVER (ORDER BY date DESC, id DESC)", field.code());
	}
	
	private String daysOfPeriodFuncExprScript(ExpressionArg from, ExpressionArg to) throws IOException {		
		return String.format("extract(day from (%s::timestamp without time zone - %s::timestamp without time zone))", argScript(to, false), argScript(from, false));
	}
	
	private String argValueTyped(String value, DataFieldType type, boolean withType) {
		BaseType pType = new PgTypeOfDataFieldType(type);
		if(type == DataFieldType.STRING || type == DataFieldType.DATE) {
			if(withType)
				return String.format("'%s'::%s", value, pType.name());
			else
				return String.format("'%s'", value);
		}else {
			if(withType)
				return String.format("%s::%s", value, pType.name());
			else
				return String.format("%s", value);
		}
	}
	
	private String argScript(final ExpressionArg arg, final boolean withType) throws IOException {
		
		String argScript;
		
		switch (arg.type()) {
			case DATA_FIELD:
			case PARAMETER:
			case FORMULAR:
				DataFieldArg dArg = (DataFieldArg)arg;
				DataField field = dArg.field();
				BaseType bType = new PgTypeOfDataFieldType(field.type());
				if(withType)
					argScript = String.format("\"%s\"::%s", field.code(), bType.name());
				else
					argScript = String.format("\"%s\"", field.code());				
				break;
			case EXPRESSION:
				ExpressionValueArg eArg = (ExpressionValueArg)arg;
				argScript = String.format(
					"(%s)",
					new PgFormularExpressionPrinter(
						eArg.targetExpr(),
						params,
						period
					).toText()
				);
				break;
			case VALUE:
				ValueArg vArg = (ValueArg)arg;
				argScript = argValueTyped(vArg.value(), vArg.valueType(), withType);				
				break;
			default:
				throw new IllegalArgumentException(String.format("Expression %s : l'argument %s n'est pas valide !", expr.tag(), arg));
		}

		return argScript;
	}
	
	private String script(FormularFunc func, List<String> argScripts) {
		
		String script = StringUtils.EMPTY;
		
		switch (func) {
			case IDEM:
				script = String.format("%s", argScripts.get(0));
				break;
			case ADD:
				script = String.format("%s + %s", argScripts.get(0), argScripts.get(1));
				break;
			case SUBSTRUCT:
				script = String.format("%s - %s", argScripts.get(0), argScripts.get(1));
				break;
			case MULTIPLY:
				script = String.format("%s * %s", argScripts.get(0), argScripts.get(1));
				break;
			case DIVIDE:
				script = String.format("%s / %s", argScripts.get(0), argScripts.get(1));
				break;
			case ECART:
				script = String.format("ABS(%s - %s)", argScripts.get(0), argScripts.get(1));
				break;
			case MIN:
				script = String.format("LEAST(%s, %s)", argScripts.get(0), argScripts.get(1));
				break;
			case MAX:
				script = String.format("GREATEST(%s, %s)", argScripts.get(0), argScripts.get(1));
				break;
			case TRUNC:
				script = String.format("TRUNC((%s)::numeric, (%s)::int)", argScripts.get(0), argScripts.get(1));
				break;
			case ROUND:
				script = String.format("ROUND((%s)::numeric, (%s)::int)", argScripts.get(0), argScripts.get(1));
				break;
			case ROUND_UP:
				script = String.format("CEIL((%s)::numeric)", argScripts.get(0));
				break;
			case ROUND_DOWN:
				script = String.format("FLOOR((%s)::numeric)", argScripts.get(0));
				break;
			case ROUND_TO_NEAREST_VALUE:
				script = String.format("ROUND((%s/%s)::numeric, 0)*%s", argScripts.get(0), argScripts.get(1), argScripts.get(1));
				break;
			case ROUND_TO_DOWN_VALUE:
				String kernel1 = String.format("ROUND((%s/%s)::numeric, 0)*%s", argScripts.get(0), argScripts.get(1), argScripts.get(1));
				script = String.format("case when mod((%s)::int,(%s)::int) > (%s)::int/2 then (%s)::int - (%s)::int \r\n"
									 + "else (%s)::int end", 
									 argScripts.get(0), 
									 argScripts.get(1), 
									 argScripts.get(1),
									 kernel1,
									 argScripts.get(1),
									 kernel1);
				break;
			case ROUND_TO_UP_VALUE:
				String kernel2 = String.format("ROUND((%s/%s)::numeric, 0)*%s", argScripts.get(0), argScripts.get(1), argScripts.get(1));
				script = String.format("case when mod((%s)::int,(%s)::int) > (%s)::int/2 then (%s)::int \r\n"
									 + "else (%s)::int + (%s)::int end", 
									 argScripts.get(0), 
									 argScripts.get(1), 
									 argScripts.get(1),
									 kernel2,
									 kernel2,
									 argScripts.get(1));
				break;
			case CONCAT:
				script = String.format("CONCAT((%s)::character varying, (%s)::character varying)", argScripts.get(0), argScripts.get(1));
				break;
			case CONCAT_WITH_BLANK:
				script = String.format("CONCAT((%s)::character varying, ' ', (%s)::character varying)", argScripts.get(0), argScripts.get(1));
				break;
			case CONCAT_WITH_SEPARATOR:
				script = String.format("CONCAT((%s)::character varying, (%s)::character varying, (%s)::character varying)", argScripts.get(0), argScripts.get(2), argScripts.get(1));
				break;
			case CONCAT_WITH_SEPARATOR_SPACED:
				script = String.format("CONCAT((%s)::character varying, ' ', (%s)::character varying, ' ', (%s)::character varying)", argScripts.get(0), argScripts.get(2), argScripts.get(1));
				break;
			case CONCAT3:
				script = String.format("CONCAT((%s)::character varying, (%s)::character varying, (%s)::character varying)", argScripts.get(0), argScripts.get(1), argScripts.get(2));
				break;
			default:
				break;
		}
		
		return script;
	}

	@Override
	public FormularExpression expression() {
		return expr;
	}
}
