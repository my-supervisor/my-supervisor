package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.domain.FormularExtendedToModelSources;

public final class PgFormularExtendedToModelSources extends DomainRecordables<FormularExtendedToModelSource, FormularExtendedToModelSources> implements FormularExtendedToModelSources {

	private final FormularExtendedToModelExpression expr;
	
	public PgFormularExtendedToModelSources(final FormularExtendedToModelExpression expr) throws IOException {
		this(viewSource(expr), expr);
	}
	
	public PgFormularExtendedToModelSources(final RecordSet<FormularExtendedToModelSource> source, final FormularExtendedToModelExpression expr) throws IOException {
		super(PxFormularExtendedToModelSource.class, source);
		
		this.expr = expr;
		this.source = source.where(FormularExtendedToModelSource::expr, expr.id())
						    .orderBy(FormularExtendedToModelSource::id);
	}

	private static RecordSet<FormularExtendedToModelSource> viewSource(final FormularExtendedToModelExpression expr) throws IOException {
		
		final Table table = new TableImpl(FormularExtendedToModelSource.class);
		
		String viewScript = String.format(
								"(\r\n" + 
								"	select src.* \r\n" + 
				                "   from %s as src \r\n" + 
							    ") as %s",
								table.name(),									
								table.name()
							);
		
		return expr.base()
				   .select(FormularExtendedToModelSource.class, viewScript);
	}
	
	@Override
	protected FormularExtendedToModelSources domainSetOf(final RecordSet<FormularExtendedToModelSource> source) throws IOException {
		return new PgFormularExtendedToModelSources(source, expr);
	}

	@Override
	public FormularExtendedToModelSources actives() throws IOException {
		return where(FormularExtendedToModelSource::active, true);
	}

	@Override
	public FormularExtendedToModelSource add(DataSheetModel model, EditableDataField modelField, Comparator comparator, DataField reference, EditableDataField fieldToExtend) throws IOException {
		
		source.mustCheckThisCondition(
			model.fields().contains(modelField), 
			String.format("Le champ %s doit appartenir au modèle %s !", modelField.name(), model.name())
		);
		
		source.mustCheckThisCondition(
			model.fields().contains(fieldToExtend), 
			String.format("Le champ %s doit appartenir au modèle %s !", fieldToExtend.name(), model.name())
		);
		
		final AggregatedModel expressionModel = expr.formular().model();
		
		source.mustCheckThisCondition(
			expressionModel.fields().contains(reference), 
			String.format("Le champ %s doit appartenir au modèle %s !", reference.name(), expressionModel.name())
		);
		
		source.mustCheckThisCondition(
			!isStrictBasedOn(model), 
			String.format("Le modele %s a déjà été utilisé !", model.name())
		);
		
		final Activity expressionActivity = expressionModel.activity();
		final boolean isExternalModel = !expressionActivity.equals(model.activity());
		
		Record<FormularExtendedToModelSource> record = source.entryOf(FormularExtendedToModelSource::expr, expr.id())
															 .entryOf(FormularExtendedToModelSource::model, model.id())															 
															 .entryOf(FormularExtendedToModelSource::modelField, modelField.id())
															 .entryOf(FormularExtendedToModelSource::comparator, comparator)
															 .entryOf(FormularExtendedToModelSource::reference, reference.id())
															 .entryOf(FormularExtendedToModelSource::interacting, isExternalModel)
															 .entryOf(FormularExtendedToModelSource::active, true)
															 .entryOf(FormularExtendedToModelSource::fieldToExtend, fieldToExtend.id())
						      								 .add();

		return domainOf(record);
	}

	@Override
	public boolean isBasedOn(DataModel model) throws IOException {
		
		for (FormularExtendedToModelSource src : items()) {
			if(src.isBasedOn(model)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public FormularExtendedToModelSource get(DataSheetModel model) throws IOException {
		
		for (FormularExtendedToModelSource src : items()) {
			if(src.isBasedOn(model)) {
				return src;
			}
		}
		
		throw new IllegalArgumentException("Extended model source hasn't been found !");
	}

	@Override
	public boolean isStrictBasedOn(DataModel model) throws IOException {
		
		for (FormularExtendedToModelSource src : items()) {
			if(src.isStrictBasedOn(model)) {
				return true;
			}
		}
		
		return false;
	}
}
