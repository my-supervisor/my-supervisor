package com.minlessika.supervisor.copying.templating;

import java.io.IOException;

import com.minlessika.supervisor.copying.AbstractListDataFieldSourceWriter;
import com.minlessika.supervisor.data.sharing.ListDataFieldSourceSharing;
import com.minlessika.supervisor.data.sharing.ListDataFieldSourceUniqueSharing;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;

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
