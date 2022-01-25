package com.supervisor.copying;

import java.io.IOException;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.Writer;

public abstract class AbstractListDataFieldSourceWriter implements Writer<ListDataFieldSource> {
	
	protected final ListDataField targetListField;
	protected final DataModel targetModel;
	protected final ListDataFieldSource source;
	protected final ListDataFieldSource target;
	
	public AbstractListDataFieldSourceWriter(final ListDataFieldSource source, final ListDataFieldSource target) throws IOException {
		this(target.field(), target.model(), source, target);
	}
	
	public AbstractListDataFieldSourceWriter(final ListDataField targetListField, final DataModel targetModel, final ListDataFieldSource source) throws IOException {
		this(targetListField, targetModel, source, ListDataFieldSource.EMPTY);
	}
	
	private AbstractListDataFieldSourceWriter(final ListDataField targetListField, final DataModel targetModel, final ListDataFieldSource source, final ListDataFieldSource target) throws IOException {
		this.targetListField = targetListField;
		this.targetModel = targetModel;
		this.source = source;
		this.target = findTarget(targetListField, targetModel, source, target);
	}
	
	protected abstract ListDataFieldSource findTarget(final ListDataField targetListField, final DataModel targetModel, final ListDataFieldSource source, final ListDataFieldSource target) throws IOException;

	private ListDataFieldSource copyBaseOf(ListDataFieldSource source) throws IOException {
		
		final ListDataFieldSource copy;
		final DataField fieldToDisplay = targetModel.fields().get(source.fieldToDisplay().code());
		final DataField orderField = targetModel.fields().get(source.orderField().code());
		if(target == ListDataFieldSource.EMPTY) {
			copy = targetListField.sources().add(targetModel, fieldToDisplay, orderField);
		} else {
			copy = target;
			copy.update(targetModel, fieldToDisplay, orderField);
		}
		
		return copy;
	}

	@Override
	public ListDataFieldSource copy() throws IOException {
		return copyBaseOf(source);
	}
}
