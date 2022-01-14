package com.minlessika.supervisor.copying;

import java.io.IOException;
import java.util.Map;

import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.FormularCaseExpression;
import com.minlessika.supervisor.domain.FormularCondition;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularExtendedToChildExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularSimpleExpression;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.WhenCase;
import com.minlessika.supervisor.domain.Writer;
import com.minlessika.supervisor.domain.impl.SimpleFormularExpressionArgCopyImpl;

public abstract class AbstractFormularDataFieldWriter implements Writer<FormularDataField> {

	protected final FormularDataField source;
	protected final AggregatedModel targetModel;
	protected final Map<Long, DataModel> dataModelMappings;
	
	public AbstractFormularDataFieldWriter(final AggregatedModel targetModel, final FormularDataField source, final Map<Long, DataModel> dataModelMappings) {
		this.source = source;
		this.targetModel = targetModel;
		this.dataModelMappings = dataModelMappings;
	}
	
	protected abstract DataSheetModel targetModelOf(DataSheetModel sourceModel) throws IOException;
	protected abstract void copyFormularExtendedToParentExpressionOf(FormularDataField targetFormular, FormularExtendedToParentExpression source) throws IOException;
	protected abstract void copyFormularExtendedToModelExpressionOf(FormularDataField targetFormular, FormularExtendedToModelExpression source) throws IOException;
	
	@Override
	public FormularDataField copy() throws IOException {
		
		final FormularDataField copy = targetModel.formulars().add(source.code(), source.name(), source.type());
		
		final FormularCondition condition = source.condition();
		if(condition != FormularCondition.EMPTY) {
			final ParamDataField newParam = targetModel.params().where(ParamDataField::code, condition.param().code()).first();
			copy.addCondition(newParam, condition.comparator(), condition.value(), condition.defaultValue());
		}
		
		for (FormularExpression expr : source.expressions().items()) {
			switch (expr.type()) {
				case SIMPLE:
					final FormularSimpleExpression simpleExpr = (FormularSimpleExpression)expr;
					final FormularSimpleExpression newSimpleExpr = copy.expressions().add(simpleExpr.func());
					newSimpleExpr.order(expr.no());
					for (ExpressionArg arg : simpleExpr.arguments().items()) {
						new SimpleFormularExpressionArgCopyImpl(newSimpleExpr, arg).execute();
					}
					break;
				case CASE:
					final FormularCaseExpression caseExpr = (FormularCaseExpression)expr;
					final FormularCaseExpression newCaseExpr = copy.expressions().addCase();
					newCaseExpr.order(expr.no());
					new CaseFormularDefaultExpressionArgCopyImpl(newCaseExpr, caseExpr.defaultValue()).execute();
					for (WhenCase when : caseExpr.cases().items()) {
						new CaseFormularWhenExpressionArgCopyImpl(newCaseExpr, when).execute();
					}
					break;
				case EXTENDED_TO_PARENT:
					copyFormularExtendedToParentExpressionOf(copy, (FormularExtendedToParentExpression)expr);
					break;
				case EXTENDED_TO_MODEL:
					copyFormularExtendedToModelExpressionOf(copy, (FormularExtendedToModelExpression)expr);
					break;
				case EXTENDED_TO_CHILD:
					final FormularExtendedToChildExpression extChildExpr = (FormularExtendedToChildExpression)expr;
					final EditableDataField child = extChildExpr.child();
					final DataSheetModel newChildModel = targetModelOf(child.model());
					final EditableDataField newChild = newChildModel.scalarEditableFields().get(child.code());
					final FormularExtendedToChildExpression newExtChildExpr = copy.expressions().addChildExtension();
					newExtChildExpr.update(newChild, extChildExpr.aggregate());
					break;
				default:
					throw new IllegalArgumentException(String.format("FormularDataFieldWriter : expression type %s not supported !", expr.type().toString()));
			}						
		}
		
		return copy;
	}

}
