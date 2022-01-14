package com.minlessika.supervisor.copying;

import java.io.IOException;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.Writer;

public abstract class AbstractFormularExtendedToParentExpressionWriter implements Writer<FormularExtendedToParentExpression> {
	
	protected final FormularDataField targetFormular;
	protected final FormularExtendedToParentExpression source;
	
	public AbstractFormularExtendedToParentExpressionWriter(final FormularDataField targetFormular, final FormularExtendedToParentExpression source) {
		this.targetFormular = targetFormular;
		this.source = source;
	}

	protected abstract void copyExtendedToParentSources(FormularExtendedToParentExpression targetExpr) throws IOException;
	
	private FormularExtendedToParentExpression copyBaseOf(FormularExtendedToParentExpression source) throws IOException {
		
		final EditableDataField reference = source.reference();					
		final ListDataField newReference = targetFormular.model().model().fields().lists().get(reference.code());					
		final FormularExtendedToParentExpression copy = targetFormular.expressions().addParentExtension(newReference);
		copyExtendedToParentSources(copy);
		
		return copy;
	}

	@Override
	public FormularExtendedToParentExpression copy() throws IOException {
		return copyBaseOf(source);
	}
}
