package com.minlessika.supervisor.copying;

import java.io.IOException;

import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;
import com.minlessika.supervisor.domain.Writer;

public abstract class AbstractFormularExtendedToModelSourceWriter implements Writer<FormularExtendedToModelSource> {
	
	protected final FormularExtendedToModelExpression targetExpr;
	protected final DataSheetModel targetSourceModel;
	protected final FormularExtendedToModelSource source;

	public AbstractFormularExtendedToModelSourceWriter(final FormularExtendedToModelExpression targetExpr, final DataSheetModel targetSourceModel, final FormularExtendedToModelSource source) {
		this.targetExpr = targetExpr;
		this.targetSourceModel = targetSourceModel;
		this.source = source;
	}
	
	private FormularExtendedToModelSource copyBaseOf(FormularExtendedToModelSource source) throws IOException {
		
		final FormularExtendedToModelSource copy;
		
		final EditableDataField modelField = targetSourceModel.editableFields().get(source.modelField().code());
		final DataField reference = targetExpr.formular().model().fields().get(source.reference().code());
		final EditableDataField fieldToExtend = targetSourceModel.editableFields().get(source.fieldToExtend().code());
		copy = targetExpr.sources().add(targetSourceModel, modelField, source.comparator(), reference, fieldToExtend);
	
		return copy;
	}

	@Override
	public FormularExtendedToModelSource copy() throws IOException {
		return copyBaseOf(source);
	}
}
