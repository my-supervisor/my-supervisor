package com.supervisor.copying;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.domain.DataModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.Writer;

public abstract class AbstractFormularExtendedToModelExpressionWriter implements Writer<FormularExtendedToModelExpression> {
	
	protected final FormularDataField targetFormular;
	protected final FormularExtendedToModelExpression source;
	protected final Map<UUID, DataModel> dataModelMappings;
	
	public AbstractFormularExtendedToModelExpressionWriter(final FormularDataField targetFormular, final FormularExtendedToModelExpression source, final Map<UUID, DataModel> dataModelMappings) {
		this.targetFormular = targetFormular;
		this.source = source;
		this.dataModelMappings = dataModelMappings;
	}

	protected abstract void copyExtendedToModelSources(FormularExtendedToModelExpression targetExpr) throws IOException;
	
	private FormularExtendedToModelExpression copyBaseOf(FormularExtendedToModelExpression source) throws IOException {
					
		final FormularExtendedToModelExpression copy = targetFormular.expressions().addModelExtension();
		new ExpressionArgCopyImpl(source.defaultValue(), copy.defaultValue()).execute();		
		copy.update(source.aggregate());
		copyExtendedToModelSources(copy);
		
		return copy;
	}

	@Override
	public FormularExtendedToModelExpression copy() throws IOException {
		return copyBaseOf(source);
	}
}
