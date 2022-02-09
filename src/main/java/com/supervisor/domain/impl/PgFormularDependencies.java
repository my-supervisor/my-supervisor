package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.datasource.conditions.pgsql.ConditionOperator;
import com.supervisor.sdk.datasource.conditions.pgsql.PgFilter;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldArg;
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.domain.ParamDataField;

public final class PgFormularDependencies extends DomainRecordables<DataField, DataFieldDependencies> implements DataFieldDependencies {

	private final FormularDataField formular;
	
	public PgFormularDependencies(final FormularDataField formular) throws IOException {
		this(viewSource(formular), formular);
	}

	private PgFormularDependencies(final RecordSet<DataField> source, final FormularDataField formular) {
		super(PxDataField.class, source);
		
		this.formular = formular;
	}
	
	@Override
	protected DataFieldDependencies domainSetOf(final RecordSet<DataField> source) throws IOException {
		return new PgFormularDependencies(source, formular);
	}
	
	@Override
	protected DataField domainOf(final Record<DataField> record) throws IOException {
		return TypedDataField.convert(record);
	}
	
	private static RecordSet<DataField> viewSource(final FormularDataField formular) throws IOException{
		Table table = new TableImpl(DataField.class);
		Table exprTable = new TableImpl(FormularExpression.class);
		Table sourceTable = new TableImpl(FormularExtendedToModelSource.class);
		
		String viewScript = String.format(
								"(\r\n" + 
							    "	select DISTINCT ON (src.id) src.* \r\n" + 
			                    "   from %s as src \r\n" + 
							    "   where src.id in (\r\n" +
							    "		select data_arg.field_id \r\n" + 
							    "       from %s data_arg \r\n " + 
							    "       left join %s arg on arg.id = data_arg.id \r\n" + 
							    "       left join %s expr on expr.id = arg.expression_id \r\n" +
							    "       where expr.formular_id = '%s'::uuid \r\n" +
							    "   ) \r\n" + 
							    "   or src.id in (\r\n" +
							    "		select model_field_id \r\n" + 
							    "       from %s source \r\n " + 
							    "       left join %s expr on expr.id = source.expr_id \r\n" +
							    "       where expr.formular_id = '%s'::uid \r\n" +
							    "   ) \r\n" + 
							    "   or src.id in (\r\n" +
							    "		select reference_id \r\n" + 
							    "       from %s source \r\n " + 
							    "       left join %s expr on expr.id = source.expr_id \r\n" +
							    "       where expr.formular_id = '%s'::uuid \r\n" +
							    "   ) \r\n" + 
							    "   or src.id in (\r\n" +
							    "		select field_to_extend_id \r\n" + 
							    "       from %s source \r\n " + 
							    "       left join %s expr on expr.id = source.expr_id \r\n" +
							    "       where expr.formular_id = '%s'::uuid \r\n" +
							    "   ) \r\n" + 
								") as %s \r\n",
								table.name(),
								new TableImpl(DataFieldArg.class).name(),
								new TableImpl(ExpressionArg.class).name(),
								new TableImpl(FormularExpression.class).name(),
								formular.id(),
								sourceTable.name(),
								exprTable.name(),
								formular.id(),
								sourceTable.name(),
								exprTable.name(),
								formular.id(),
								sourceTable.name(),
								exprTable.name(),
								formular.id(),
								table.name()
							);
		
		return formular.base()
				       .select(DataField.class, viewScript);
	}

	@Override
	public List<EditableDataField> editables() throws IOException {
		return where(
					new PgFilter<EditableDataField>(EditableDataField.class, ConditionOperator.OR)
				        .add(DataField::style, Matchers.equalsTo(DataFieldStyle.SIMPLE))
				        .add(DataField::style, Matchers.equalsTo(DataFieldStyle.LIST))
			   )
			   .items()
			   .stream()
			   .map(c -> (EditableDataField)c)
			   .collect(Collectors.toList());
	}

	@Override
	public List<FormularDataField> formulars() throws IOException {
		return where(DataField::style, DataFieldStyle.FORMULAR)
				   .items()
				   .stream()
				   .map(c -> (FormularDataField)c)
				   .collect(Collectors.toList());
	}

	@Override
	public List<ParamDataField> params() throws IOException {
		return where(DataField::style, DataFieldStyle.PARAMETER)
				   .items()
				   .stream()
				   .map(c -> (ParamDataField)c)
				   .collect(Collectors.toList());
	}
}
