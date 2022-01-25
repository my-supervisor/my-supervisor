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
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.ParamDataField;

public final class PgEditableDataFieldDependencies extends DomainRecordables<DataField, DataFieldDependencies> implements DataFieldDependencies {

	private final EditableDataField editableDataField;
	
	public PgEditableDataFieldDependencies(final EditableDataField editableDataField) throws IOException {
		this(viewSource(editableDataField), editableDataField);
	}

	private PgEditableDataFieldDependencies(final RecordSet<DataField> source, final EditableDataField editableDataField) {
		super(PxDataField.class, source);
		
		this.editableDataField = editableDataField;
	}
	
	@Override
	protected DataFieldDependencies domainSetOf(final RecordSet<DataField> source) throws IOException {
		return new PgEditableDataFieldDependencies(source, editableDataField);
	}
	
	@Override
	protected DataField domainOf(final Record<DataField> record) throws IOException {
		return TypedDataField.convert(record);
	}
	
	private static RecordSet<DataField> viewSource(final EditableDataField editableDataField) throws IOException{
		
		Table table = new TableImpl(DataField.class);
		
		String viewScript;
		
		if(editableDataField.style() == DataFieldStyle.LIST) {
			viewScript = String.format(					
							"(\r\n" + 
						    "	select DISTINCT ON (src.id) src.* \r\n" + 
		                    "   from %s as src \r\n" + 
						    "   where src.id in (\r\n" +
						    "		select field_to_display_id \r\n" + 
						    "       from %s list_source \r\n " + 
						    "       where list_source.field_id = %s \r\n" +
						    "   ) \r\n" + 
						    "   or src.id in (\r\n" +
						    "		select order_field_id \r\n" + 
						    "       from %s list_source \r\n " + 
						    "       where list_source.field_id = %s \r\n" +
						    "   ) \r\n" + 
							") as %s \r\n",
							table.name(),
							new TableImpl(ListDataFieldSource.class).name(),
							editableDataField.id(),
							new TableImpl(ListDataFieldSource.class).name(),
							editableDataField.id(),
							table.name()
						);
		} else {
			viewScript = String.format(					
							"(\r\n" + 
						    "	select src.* \r\n" + 
		                    "   from %s as src \r\n" + 
						    "   where src.id = -1 \r\n" +
							") as %s \r\n",
							table.name(),
							table.name()
						 );
		}
		
		return editableDataField.base()
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
