package com.supervisor.copying.generating;

import java.io.IOException;

import com.supervisor.copying.AbstractListDataFieldSourceWriter;
import com.supervisor.sharing.ListDataFieldSourceSharing;
import com.supervisor.sharing.ListDataFieldSourceUniqueSharing;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;

public final class ListDataFieldSourceGenerating extends AbstractListDataFieldSourceWriter {

	public ListDataFieldSourceGenerating(final ListDataFieldSource source, final ListDataFieldSource target) throws IOException {
		super(source, target);
	}

	public ListDataFieldSourceGenerating(final ListDataField targetListField, final DataModel targetModel, final ListDataFieldSource source) throws IOException {
		super(targetListField, targetModel, source);
	}
	
	@Override
	public ListDataFieldSource copy() throws IOException {
		final ListDataFieldSource copy = super.copy();
		if(target == ListDataFieldSource.EMPTY) {
			if(copy.interacting() && !targetListField.model().activity().interactsWith(copy.model().activity())) {
				copy.activate(false);
			}			
		}
		new ListDataFieldSourceUniqueSharing(source, copy.model().activity(), copy.activity()).linkTo(copy);
		return copy;
	}

	@Override
	protected ListDataFieldSource findTarget(ListDataField targetListField, DataModel targetModel, ListDataFieldSource source, ListDataFieldSource target) throws IOException {
		if(target == ListDataFieldSource.EMPTY) {
			ListDataFieldSourceSharing sharing = new ListDataFieldSourceUniqueSharing(source, targetModel.activity(), targetListField.model().activity());
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
