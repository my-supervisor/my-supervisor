package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataFieldSource;

public final class PxFormularExtendedToParentSource extends DomainRecordable<FormularExtendedToParentSource> implements FormularExtendedToParentSource {

	public PxFormularExtendedToParentSource(Record<FormularExtendedToParentSource> record) {
		super(record);
	}

	@Override
	public ListDataFieldSource listSource() throws IOException {
		return new PxListDataFieldSource(record.of(FormularExtendedToParentSource::listSource));
	}

	@Override
	public EditableDataField field() throws IOException {
		final Long fieldId = record.valueOf(FormularExtendedToParentSource::field);
		return (EditableDataField)TypedDataField.convert(record.of(DataField.class, fieldId));
	}

	@Override
	public boolean active() throws IOException {
		return listSource().active();
	}

	@Override
	public void update(EditableDataField field) throws IOException {
		
		record.mustCheckThisCondition(
		    listSource().model().fields().contains(field), 
			String.format("Le champ %s doit appartenir au modèle %s !", field.name(), listSource().model().name())
		);
		
		record.entryOf(FormularExtendedToParentSource::field, field.id())
			  .update();
	}

	@Override
	public FormularExtendedToParentExpression expr() throws IOException {
		return new PxFormularExtendedToParentExpression(record.of(FormularExtendedToParentSource::expr));
	}

	@Override
	public boolean interacting() throws IOException {
		return listSource().interacting();
	}

	@Override
	public void activate(boolean active) throws IOException {
		listSource().activate(active); 
	}

	@Override
	public DataModel model() throws IOException {
		return listSource().model();
	}

	@Override
	public boolean isBasedOn(DataModel model) throws IOException {
		return listSource().isBasedOn(model);
	}

	@Override
	public boolean isStrictBasedOn(DataModel model) throws IOException {
		return listSource().isBasedOn(model);
	}

}
