package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.FormularFunc;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularCaseExpression;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExpressionType;
import com.supervisor.domain.FormularExpressions;
import com.supervisor.domain.FormularExtendedToChildExpression;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularSimpleExpression;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.bi.AggregateFunc;

public final class PxFormularExpressions extends DomainRecordables<FormularExpression, FormularExpressions> implements FormularExpressions {
	
	private final FormularDataField formular;
	
	public PxFormularExpressions(final FormularDataField formular) throws IOException {
		this(formular.listOf(FormularExpression.class), formular);
	}
	
	public PxFormularExpressions(final RecordSet<FormularExpression> source, final FormularDataField formular) throws IOException {
		super(PxBasicFormularExpression.class, source);
		
		this.formular = formular;
		this.source = source.where(FormularExpression::formular, formular.id())
				            .orderBy(FormularExpression::no);
	}
	
	@Override
	protected FormularExpressions domainSetOf(final RecordSet<FormularExpression> source) throws IOException {
		return new PxFormularExpressions(source, formular);
	}
	
	@Override
	protected FormularExpression domainOf(final Record<FormularExpression> record) throws IOException {
		return new FormularExpressionTypingImpl(record).expression();
	}

	@Override
	public FormularSimpleExpression add(FormularFunc func) throws IOException {
		
		source.mustCheckThisCondition(
				!(func == null || func == FormularFunc.NONE), 
				"Vous devez spécifier une fonction !"
		); 
		
		Record<FormularExpression> record = source.entryOf(FormularExpression::type, FormularExpressionType.SIMPLE)
			  									  	  .entryOf(FormularExpression::formular, formular.id())
			  									  	  .entryOf(FormularExpression::no, isEmpty() ? 1 : last().no() + 1)
			  									  	  .add();
		
		source.of(FormularSimpleExpression.class)
		      .entryOf(FormularSimpleExpression::id, record.id())
		  	  .entryOf(FormularSimpleExpression::func, func)
		  	  .add();
		
		FormularSimpleExpression item = new PxFormularSimpleExpression(record);
		
		// ajouter des arguments de type valeur
		if(func == FormularFunc.FIRST || func == FormularFunc.PREVIOUS || func == FormularFunc.NEXT) {
			item.arguments().addDataFieldArg(1);
		}else {
			for (int i = 0; i < func.nbArgs(); i++) {
				item.arguments().add(i + 1); 
			}
		}		
		
		return item;
	}

	@Override
	public FormularCaseExpression addCase() throws IOException {
		
		Record<FormularExpression> record = source.entryOf(FormularExpression::type, FormularExpressionType.CASE)
												  	  .entryOf(FormularExpression::formular, formular.id())
												  	  .entryOf(FormularExpression::no, isEmpty() ? 1 : last().no() + 1)
												  	  .add();
		
		source.of(FormularCaseExpression.class)
		      .entryOf(FormularCaseExpression::id, record.id())
		  	  .add();
		
		FormularCaseExpression item = new PxFormularCaseExpression(record);
		item.arguments().add(0);
		
		return item;
	}
	
	@Override
	public FormularExtendedToParentExpression addParentExtension(ListDataField reference) throws IOException {
		
		Record<FormularExpression> record = source.entryOf(FormularExpression::type, FormularExpressionType.EXTENDED_TO_PARENT)
											  	  .entryOf(FormularExpression::formular, formular.id())
											  	  .entryOf(FormularExpression::no, isEmpty() ? 1 : last().no() + 1)
											  	  .add();
		
		final FormularExtendedToParentExpression item = new PxFormularExtendedToParentExpression(record);
		ExpressionArg arg = item.arguments().add(1);
		arg.update(reference); 
		
		return item;
	}
	
	@Override
	public FormularExtendedToChildExpression addChildExtension() throws IOException {
		
		Record<FormularExpression> record = source.entryOf(FormularExpression::type, FormularExpressionType.EXTENDED_TO_CHILD)
												  	  .entryOf(FormularExpression::formular, formular.id())
												  	  .entryOf(FormularExpression::no, isEmpty() ? 1 : last().no() + 1)
												  	  .add();

		source.of(FormularExtendedToChildExpression.class)
			  .get(record.id())
			  .entryOf(FormularExtendedToChildExpression::aggregate, AggregateFunc.COUNT)
			  .update();
		
		final FormularExtendedToChildExpression item = new PxFormularExtendedToChildExpression(record);
		item.arguments().add(1);
		
		return item;
	}
	
	@Override
	public void remove(FormularExpression item) throws IOException{
		
		switch (item.type()) {
			case SIMPLE:
			case EXTENDED_TO_PARENT:
			case EXTENDED_TO_MODEL:
			case EXTENDED_TO_CHILD:
				item.arguments().remove();
				break;
			case CASE:
			    FormularCaseExpression caseItem = (FormularCaseExpression)item;
			    final UUID defaultArgId = caseItem.defaultValue().id();
			    caseItem.cases().remove();			    
			    source.of(ExpressionArg.class).remove(defaultArgId); 
				break;
			default:
				throw new UnsupportedOperationException(String.format("Remove formular expression %s : type %s not supported !", item.name(), item.type().toString())); 
		}
		
		super.remove(item);
	}

	@Override
	public FormularExtendedToModelExpression addModelExtension() throws IOException {
		
		Record<FormularExpression> record = source.entryOf(FormularExpression::type, FormularExpressionType.EXTENDED_TO_MODEL)
			  	  .entryOf(FormularExpression::formular, formular.id())
			  	  .entryOf(FormularExpression::no, isEmpty() ? 1 : last().no() + 1)
			  	  .add();

		source.of(FormularExtendedToModelExpression.class)
			  .get(record.id())
			  .entryOf(FormularExtendedToModelExpression::aggregate, AggregateFunc.SUM)
			  .update();
		
		final FormularExtendedToModelExpression item = new PxFormularExtendedToModelExpression(record);
		item.arguments().add(0);
		
		return item;
	}
}
