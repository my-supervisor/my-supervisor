package com.supervisor.copying.cloning;

import java.io.IOException;

import com.supervisor.copying.AbstractListDataFieldSourceWriter;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;

public final class ListDataFieldSourceCloning extends AbstractListDataFieldSourceWriter {

	public ListDataFieldSourceCloning(final ListDataFieldSource source, final ListDataFieldSource target) throws IOException {
		super(source, target);
	}

	public ListDataFieldSourceCloning(final ListDataField targetListField, final DataModel targetModel, final ListDataFieldSource source) throws IOException {
		super(targetListField, targetModel, source);
	}
	
	@Override
	public ListDataFieldSource copy() throws IOException {
		final ListDataFieldSource copy = super.copy();
		copy.activate(source.active());
		return copy;
	}
	
	@Override
	protected ListDataFieldSource findTarget(ListDataField targetListField, DataModel targetModel, ListDataFieldSource source, ListDataFieldSource target) throws IOException {
		return target;		
	}
	
}
