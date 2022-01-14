package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.FormularExtendedToParentSources;
import com.minlessika.supervisor.domain.ListDataFieldSource;

public final class PgFormularExtendedToParentSources extends DomainRecordables<FormularExtendedToParentSource, FormularExtendedToParentSources> implements FormularExtendedToParentSources {

	private final FormularExtendedToParentExpression expr;
	
	public PgFormularExtendedToParentSources(final FormularExtendedToParentExpression expr) throws IOException {
		this(viewSource(expr), expr);
	}
	
	public PgFormularExtendedToParentSources(final RecordSet<FormularExtendedToParentSource> source, final FormularExtendedToParentExpression expr) throws IOException {
		super(PxFormularExtendedToParentSource.class, source);
		
		this.expr = expr;
		this.source = source.where(FormularExtendedToParentSource::expr, expr.id())
						    .orderBy(FormularExtendedToParentSource::id);
	}

	private static RecordSet<FormularExtendedToParentSource> viewSource(final FormularExtendedToParentExpression expr) throws IOException {
		
		final Table table = new TableImpl(FormularExtendedToParentSource.class);
		
		String viewScript = String.format(
								"(\r\n" + 
								"	select src.*, target.active, target.model_id \r\n" + 
				                "   from %s as src \r\n" + 
								"	left join %s as target on target.id = src.list_source_id \r\n" +
							    ") as %s",
								table.name(),
								new TableImpl(ListDataFieldSource.class).name(),										
								table.name()
							);
		
		return expr.base()
				   .select(FormularExtendedToParentSource.class, viewScript);
	}
	
	@Override
	protected FormularExtendedToParentSources domainSetOf(final RecordSet<FormularExtendedToParentSource> source) throws IOException {
		return new PgFormularExtendedToParentSources(source, expr);
	}

	@Override
	public FormularExtendedToParentSources actives() throws IOException {
		return where(FormularExtendedToParentSource::active, true);
	}

	@Override
	public FormularExtendedToParentSource add(ListDataFieldSource listSource, EditableDataField field) throws IOException {
		
		final DataModel listSourceModel = listSource.model();
		
		source.mustCheckThisCondition(
			listSourceModel.fields().contains(field), 
			String.format("Le champ %s doit appartenir au modèle %s !", field.name(), listSourceModel.name())
		);
		
		source.mustCheckThisCondition(
		    expr.reference().sources().contains(listSource), 
			String.format("Le modèle %s n'est pas une source de la référence %s !", listSourceModel.name(), expr.reference().name())
		);
			
		source.mustCheckThisCondition(
		    !isBasedOn(listSourceModel), 
			String.format("Le modèle %s a déjà été utilisé dans cette expression !", listSourceModel.name())
		);
		
		Record<FormularExtendedToParentSource> record = source.entryOf(FormularExtendedToParentSource::listSource, listSource.id())
															  .entryOf(FormularExtendedToParentSource::expr, expr.id())
															  .entryOf(FormularExtendedToParentSource::field, field.id())
						      								  .add();

		return domainOf(record);
	}

	@Override
	public boolean isBasedOn(DataModel model) throws IOException {
		for (FormularExtendedToParentSource src : items()) {
			if(src.isBasedOn(model)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public FormularExtendedToParentSource get(ListDataFieldSource source) throws IOException {
		final DataModel sourceModel = source.model();
		for (FormularExtendedToParentSource src : items()) {
			if(src.isBasedOn(sourceModel)) {
				return src;
			}
		}
		
		throw new IllegalArgumentException("Extended parent source hasn't been found !");
	}
}
