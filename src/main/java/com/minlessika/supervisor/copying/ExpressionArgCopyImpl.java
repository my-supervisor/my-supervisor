package com.minlessika.supervisor.copying;

import java.io.IOException;

import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.EditableDataFieldArg;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionArgCopy;
import com.minlessika.supervisor.domain.ExpressionValueArg;
import com.minlessika.supervisor.domain.FormularArg;
import com.minlessika.supervisor.domain.ParamArg;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.ValueArg;

public final class ExpressionArgCopyImpl implements ExpressionArgCopy {

	private final ExpressionArg source;
	private final ExpressionArg target;
	
	public ExpressionArgCopyImpl(final ExpressionArg source, final ExpressionArg target) {
		this.source = source;
		this.target = target;
	}
	
	@Override
	public void execute() throws IOException {
		
		FormularExpression expr = target.expression();
		
		switch (source.type()) {
			case DATA_FIELD:
				EditableDataField field = (EditableDataField)expr.formular().model().model().fields().get(((EditableDataFieldArg)source).field().code());
				target.update(field);
				break;
			case EXPRESSION:										
				FormularExpression tExpr = expr.formular().expressions().where(FormularExpression::no, ((ExpressionValueArg)source).targetExpr().no()).first();
				target.update(tExpr);
				break;
			case FORMULAR:										
				FormularDataField tformular = expr.formular().model().formulars().where(FormularDataField::code, ((FormularArg)source).field().code()).first();
				target.update(tformular);
				break;
			case PARAMETER:
				ParamDataField tparam = expr.formular().model().params().where(ParamDataField::code, ((ParamArg)source).field().code()).first();
				target.update(tparam);
				break;
			case VALUE:
				ValueArg vArg = (ValueArg)source;
				target.update(vArg.value(), vArg.valueType());
				break;
			default:
				throw new IllegalArgumentException("La copie de ce type d'expression n'est pas pris en charge !");
		}
	}

}
