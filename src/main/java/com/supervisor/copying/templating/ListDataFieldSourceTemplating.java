package com.supervisor.copying.templating;

import java.io.IOException;

import com.supervisor.copying.AbstractListDataFieldSourceWriter;
import com.supervisor.sharing.ListDataFieldSourceSharing;
import com.supervisor.sharing.ListDataFieldSourceUniqueSharing;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;

public final class ListDataFieldSourceTemplating extends AbstractListDataFieldSourceWriter {

	public ListDataFieldSourceTemplating(final ListDataFieldSource source, final ListDataFieldSource target) throws IOException {
		super(source, target);
	}

	public ListDataFieldSourceTemplating(final ListDataField targetListField, final DataModel targetModel, final ListDataFieldSource source) throws IOException {
		super(targetListField, targetModel, source);
	}
	
	@Override
	public ListDataFieldSource copy() throws IOException {
		final ListDataFieldSource copy = super.copy();
		if(copy.interacting()) {
			copy.activate(false);
		}	
		new ListDataFieldSourceUniqueSharing(source).linkTo(copy);
		return copy;
	}
	
	@Override
	protected ListDataFieldSource findTarget(ListDataField targetListField, DataModel targetModel, ListDataFieldSource source, ListDataFieldSource target) throws IOException {
		if(target == ListDataFieldSource.EMPTY) {
			ListDataFieldSourceSharing sharing = new ListDataFieldSourceUniqueSharing(source);
			if(sharing.isEmpty())
				return ListDataFieldSource.EMPTY;
			else
				return sharing.counterpart();
		}			
		else {
			return target;
		}			
	}
}
