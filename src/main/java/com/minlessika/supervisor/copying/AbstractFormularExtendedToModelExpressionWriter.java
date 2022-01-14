package com.minlessika.supervisor.copying;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.Writer;

public abstract class AbstractFormularExtendedToModelExpressionWriter implements Writer<FormularExtendedToModelExpression> {
	
	protected final FormularDataField targetFormular;
	protected final FormularExtendedToModelExpression source;
	protected final Map<Long, DataModel> dataModelMappings;
	
	public AbstractFormularExtendedToModelExpressionWriter(final FormularDataField targetFormular, final FormularExtendedToModelExpression source, final Map<Long, DataModel> dataModelMappings) {
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
