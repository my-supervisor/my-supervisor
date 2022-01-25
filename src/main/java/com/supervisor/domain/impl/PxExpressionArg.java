package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.domain.EditableDataFieldArg;
import com.supervisor.domain.DataFieldArg;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataFieldValue;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionArgType;
import com.supervisor.domain.ExpressionValueArg;
import com.supervisor.domain.FormularArg;
import com.supervisor.domain.FormularFunc;
import com.supervisor.domain.ParamArg;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExpressionType;
import com.supervisor.domain.FormularSimpleExpression;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.ValueArg;
import com.supervisor.domain.WhenCase;

public final class PxExpressionArg extends DomainRecordable<ExpressionArg> implements ExpressionArg {

	public PxExpressionArg(Record<ExpressionArg> record) throws IOException {
		super(record);
	}

	@Override
	public String name() throws IOException {
		throw new UnsupportedOperationException("#name");
	}

	@Override
	public FormularExpression expression() throws IOException {		
		Record<FormularExpression> rec = record.of(ExpressionArg::expression);
		return new FormularExpressionTypingImpl(rec).expression();
	}

	@Override
	public int no() throws IOException {
		return record.valueOf(ExpressionArg::no);
	}

	@Override
	public ExpressionArgType type() throws IOException {
		return record.valueOf(ExpressionArg::type);
	}

	private void validate(ExpressionArgType argType) throws IOException {
		
		final FormularExpressionType type = expression().type();
		switch (type) {
			case SIMPLE:
				final FormularSimpleExpression simpleExpr = (FormularSimpleExpression)expression();
				final FormularFunc func = simpleExpr.func();
				if((func == FormularFunc.FIRST || func == FormularFunc.PREVIOUS || func == FormularFunc.NEXT) && argType != ExpressionArgType.DATA_FIELD)
					throw new IllegalArgumentException(String.format("Le fonction %s ne reçoit en argument qu'un champ de données !", func.toString()));
				break;
			case CASE:
				final FormularCaseExpression caseExpr = (FormularCaseExpression)expression();
				if(no() == 2 && caseExpr.cases().where(WhenCase::rightArg, id()).any()) {
					final WhenCase wcase = caseExpr.cases().where(WhenCase::rightArg, id()).first();
					final Comparator comparator = wcase.comparator();
					
					if(
							(comparator == Comparator.CONTAINS || comparator == Comparator.ENDS_WITH || comparator == Comparator.STARTS_WITH)
						&&  (argType != ExpressionArgType.VALUE && argType != ExpressionArgType.PARAMETER)
					) {
						throw new IllegalArgumentException("Une valeur ou un paramètre de type chaine de caractère est exigée pour cet opérateur !");		
					}
				}
				break;
			case EXTENDED_TO_PARENT:	
			case EXTENDED_TO_CHILD:
				if(argType != ExpressionArgType.DATA_FIELD) {
					throw new IllegalArgumentException("Une expression étendue ne peut recevoir qu'un champ de données en argument !");
				}
				break;
			case EXTENDED_TO_MODEL:	
				break;
		default:
			throw new IllegalArgumentException(String.format("Validation argument d'expression : %s n'est pas pris en charge !", type.toString()));
		}
	}
	
	@Override
	public void update(String value, DataFieldType valueType) throws IOException {
						
		validate(ExpressionArgType.VALUE);
		
		RecordSet<ValueArg> source = record.listOf(ValueArg.class);
		
		source.isRequired(ValueArg::value, value);
		source.mustCheckThisCondition(
				!(valueType == DataFieldType.NONE || valueType == DataFieldType.TABLE), 
				"Vous devez renseigner le type !"
		);
		
		DataFieldValue dataValue = new DataFieldValueImpl(valueType, value);
		
		if(type() == ExpressionArgType.VALUE) {
			Record<ValueArg> rec = source.get(id());			
			dataValue.validate();
			
			rec.entryOf(ValueArg::value, dataValue.cleaned())
			   .entryOf(ValueArg::valueType, valueType)
			   .update();
		}else {
			source.entryOf(ValueArg::id, id())
				  .entryOf(ValueArg::value, dataValue.cleaned())
				  .entryOf(ValueArg::valueType, valueType)
				  .add();
			
			record.entryOf(ExpressionArg::type, ExpressionArgType.VALUE)
      	  		  .update();
			
			// remove other instance	
			record.listOf(ExpressionValueArg.class).remove(id());
			record.listOf(DataFieldArg.class).remove(id());
		}	
	}

	@Override
	public void update(EditableDataField field) throws IOException {
		
		validate(ExpressionArgType.DATA_FIELD);
		
		RecordSet<EditableDataFieldArg> source = record.listOf(EditableDataFieldArg.class);
		
		source.mustCheckThisCondition(
			expression().type() == FormularExpressionType.EXTENDED_TO_PARENT || expression().type() == FormularExpressionType.EXTENDED_TO_CHILD || expression().formular().model().fields().contains(field), 
			"Le champ de données n'appartient pas au modèle de base !"
		);
		
		if(type() == ExpressionArgType.DATA_FIELD) {
			Record<EditableDataFieldArg> rec = source.get(id());			
			
			rec.entryOf(EditableDataFieldArg::field, field.id())
		       .update();
		}else {
			source.entryOf(EditableDataFieldArg::id, id())
				  .entryOf(EditableDataFieldArg::field, field.id())
				  .add();
			
			record.entryOf(ExpressionArg::type, ExpressionArgType.DATA_FIELD)
	  		      .update();
			
			// remove other instance	
			record.listOf(ValueArg.class).remove(id());
			record.listOf(ExpressionValueArg.class).remove(id());
		}		
	}

	@Override
	public void update(ParamDataField param) throws IOException {
		
		validate(ExpressionArgType.PARAMETER);
		
		RecordSet<ParamArg> source = record.listOf(ParamArg.class);
		
		source.mustCheckThisCondition(
				(expression().formular().model()).params().contains(param), 
				"Le paramètre n'appartient pas au modèle agrégé !"
		);
		
		if(type() == ExpressionArgType.PARAMETER) {
			Record<ParamArg> rec = source.get(id());			
			
			rec.entryOf(ParamArg::field, param.id())
		       .update();
		}else {
			source.entryOf(ParamArg::id, id())
				  .entryOf(ParamArg::field, param.id())
				  .add();
			
			record.entryOf(ExpressionArg::type, ExpressionArgType.PARAMETER)
	  		      .update();
			
			// remove other instance	
			record.listOf(ValueArg.class).remove(id());
			record.listOf(ExpressionValueArg.class).remove(id());
		}		
	}

	@Override
	public void update(FormularExpression expression) throws IOException {
		
		validate(ExpressionArgType.EXPRESSION);
		
		RecordSet<ExpressionValueArg> source = record.listOf(ExpressionValueArg.class);
		
		source.mustCheckThisCondition(
				expression().formular().expressions().contains(expression), 
				"L'expression n'appartient pas à la formule !"
		);
		
		source.mustCheckThisCondition(
				! expression().id().equals(expression.id()), 
				"Une expression ne peut pas se prendre en argument !"
		);
		
		if(type() == ExpressionArgType.EXPRESSION) {
			Record<ExpressionValueArg> rec = source.get(id());			
			
			rec.entryOf(ExpressionValueArg::targetExpr, expression.id())
		       .update();
		}else {
			source.entryOf(ExpressionValueArg::id, id())
				  .entryOf(ExpressionValueArg::targetExpr, expression.id())
				  .add();
			
			record.entryOf(ExpressionArg::type, ExpressionArgType.EXPRESSION)
	  		  	  .update();
			
			// remove other instance	
			record.listOf(ValueArg.class).remove(id());
			record.listOf(DataFieldArg.class).remove(id());
		}		
	}

	@Override
	public void update(FormularDataField formular) throws IOException {
		
		validate(ExpressionArgType.FORMULAR);
		
		RecordSet<FormularArg> source = record.listOf(FormularArg.class);
		
		source.mustCheckThisCondition(
				(expression().formular().model()).formulars().contains(formular), 
				"La formule n'appartient pas au modèle agrégé !"
		);
		
		if(type() == ExpressionArgType.FORMULAR) {
			Record<FormularArg> rec = source.get(id());			
			
			rec.entryOf(FormularArg::field, formular.id())
		       .update();
		}else {
			source.entryOf(FormularArg::id, id())
				  .entryOf(FormularArg::field, formular.id())
				  .add();
			
			record.entryOf(ExpressionArg::type, ExpressionArgType.FORMULAR)
	  		  	  .update();
			
			// remove other instance	
			record.listOf(ValueArg.class).remove(id());
			record.listOf(ExpressionValueArg.class).remove(id());
		}		
	}

}
