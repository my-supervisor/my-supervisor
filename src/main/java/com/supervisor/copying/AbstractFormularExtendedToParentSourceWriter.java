package com.supervisor.copying;

import java.io.IOException;

import com.supervisor.domain.Activity;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.Writer;

public abstract class AbstractFormularExtendedToParentSourceWriter implements Writer<FormularExtendedToParentSource> {
	
	protected final FormularExtendedToParentExpression targetExpr;
	protected final Activity targetListSourceModelActivity;
	protected final ListDataFieldSource targetListDataSource;
	protected final FormularExtendedToParentSource source;

	public AbstractFormularExtendedToParentSourceWriter(final FormularExtendedToParentExpression targetExpr, final Activity targetListSourceModelActivity, final FormularExtendedToParentSource source) {
		this(targetExpr, targetListSourceModelActivity, ListDataFieldSource.EMPTY, source);
	}
	
	public AbstractFormularExtendedToParentSourceWriter(final FormularExtendedToParentExpression targetExpr, final ListDataFieldSource targetListDataSource, final FormularExtendedToParentSource source) throws IOException {
		this(targetExpr, targetListDataSource.model().activity(), targetListDataSource, source);
	}
	
	private AbstractFormularExtendedToParentSourceWriter(final FormularExtendedToParentExpression targetExpr, final Activity targetListSourceModelActivity, final ListDataFieldSource targetListDataSource, final FormularExtendedToParentSource source) {
		this.targetExpr = targetExpr;
		this.targetListSourceModelActivity = targetListSourceModelActivity;
		this.targetListDataSource = targetListDataSource;
		this.source = source;
	}

	protected abstract ListDataFieldSource targetListSourceOf(ListDataFieldSource sourceListDataFieldSource) throws IOException;
	
	private FormularExtendedToParentSource copyBaseOf(FormularExtendedToParentSource source) throws IOException {
		
		final FormularExtendedToParentSource copy;
		
		final ListDataFieldSource newListSource;
		if(targetListDataSource == ListDataFieldSource.EMPTY) {
			newListSource = targetListSourceOf(source.listSource());
		} else {
			newListSource = targetListDataSource;
		}
		
		final DataModel newParentModel = newListSource.model();
		final EditableDataField newField = newParentModel.fields().editables().get(source.field().code());
		if(targetExpr.isBasedOn(newParentModel)) {
			copy = targetExpr.sources().get(newListSource);
			copy.update(newField);
		} else {			
			copy = targetExpr.sources().add(newListSource, newField);
		}
	
		return copy;
	}

	@Override
	public FormularExtendedToParentSource copy() throws IOException {
		return copyBaseOf(source);
	}
}
