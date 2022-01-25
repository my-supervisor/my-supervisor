package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.comparators.Matchers;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.EditableDataFieldArg;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionValueArg;
import com.minlessika.supervisor.domain.FormularArg;
import com.minlessika.supervisor.domain.ParamArg;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.ValueArg;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeDataFieldArg;
import com.minlessika.supervisor.xe.XeDataFieldType;
import com.minlessika.supervisor.xe.XeEditableDataField;
import com.minlessika.supervisor.xe.XeExpressionArg;
import com.minlessika.supervisor.xe.XeExpressionArgType;
import com.minlessika.supervisor.xe.XeExpressionValueArg;
import com.minlessika.supervisor.xe.XeFormularArg;
import com.minlessika.supervisor.xe.XeFormularExpression;
import com.minlessika.supervisor.xe.XeParamArg;
import com.minlessika.supervisor.xe.XeRuleFormular;
import com.minlessika.supervisor.xe.XeRuleParam;
import com.minlessika.supervisor.xe.XeSupervisor;
import com.minlessika.supervisor.xe.XeValueArg;

public final class TkExpressionArgEdit extends TkForm {

	public TkExpressionArgEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/expression_arg_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model")); 
		AggregatedModel model = module.aggregatedModels()
									 .get(modelId);
		
		Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		FormularDataField formular = model.formulars().get(formularId);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));
		content.add(new XeDataFieldType());
		content.add(new XeExpressionArgType());
		content.add(new XeEditableDataField(model.fields().editables().where(EditableDataField::type, Matchers.notEqualsTo(DataFieldType.TABLE))));
		content.add(new XeRuleParam(model.params()));
		content.add(new XeRuleFormular(model.formulars()));
		content.add(new XeFormularExpression(formular.expressions().items()));
		content.add(new XeAppend("formular_id", formular.id().toString()));
		content.add(new XeAppend("model_id", model.id().toString()));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		final Smart href = new RqHref.Smart(req);
		
		Long modelId = Long.parseLong(href.single("model"));
		AggregatedModel model = module.aggregatedModels().get(modelId); 
		
		Long formularId = Long.parseLong(href.single("formular"));
		FormularDataField formular = model.formulars().get(formularId);
		
		Long expressionId = Long.parseLong(href.single("expression"));
				
		FormularExpression expression = formular.expressions().get(expressionId);
		final ExpressionArg item = expression.arguments().get(id);
		
		String typeId = href.single("type");
		
		XeSource content =  new XeChain(
									new XeExpressionArg("item", item),
									new XeAppend("type_id", typeId)
							);
		
		switch (item.type()) {
			case VALUE:
				content = new XeChain(
							content, 
							new XeValueArg("arg", (ValueArg)item)
						  );
				break;
			case DATA_FIELD:
				content = new XeChain(
							content, 
							new XeDataFieldArg("arg", (EditableDataFieldArg)item)
						  );
				break;
			case PARAMETER:
				content = new XeChain(
							content, 
							new XeParamArg("arg", (ParamArg)item)
						  );
				break;
			case FORMULAR:
				content = new XeChain(
							content, 
							new XeFormularArg("arg", (FormularArg)item)
						  );
				break;
			case EXPRESSION:
				content = new XeChain(
							content, 
							new XeExpressionValueArg("arg", (ExpressionValueArg)item)
						  );
				break;
			default:
				break;
		}
		
		return content;
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeSource() {
		            @Override
		            public Iterable<Directive> toXembly() throws IOException {
		                return dir;
		            }
		       };
	}	
}
