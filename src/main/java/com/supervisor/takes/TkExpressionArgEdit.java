package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.EditableDataFieldArg;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionValueArg;
import com.supervisor.domain.FormularArg;
import com.supervisor.domain.ParamArg;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.ValueArg;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeDataFieldArg;
import com.supervisor.xe.XeDataFieldType;
import com.supervisor.xe.XeEditableDataField;
import com.supervisor.xe.XeExpressionArg;
import com.supervisor.xe.XeExpressionArgType;
import com.supervisor.xe.XeExpressionValueArg;
import com.supervisor.xe.XeFormularArg;
import com.supervisor.xe.XeFormularExpression;
import com.supervisor.xe.XeParamArg;
import com.supervisor.xe.XeRuleFormular;
import com.supervisor.xe.XeRuleParam;
import com.supervisor.xe.XeSupervisor;
import com.supervisor.xe.XeValueArg;

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

		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		AggregatedModel model = module.aggregatedModels()
									 .get(modelId);
		
		UUID formularId = UUID.fromString(new RqHref.Smart(req).single("formular"));
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
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		final Smart href = new RqHref.Smart(req);
		
		UUID modelId = UUID.fromString(href.single("model"));
		AggregatedModel model = module.aggregatedModels().get(modelId); 
		
		UUID formularId = UUID.fromString(href.single("formular"));
		FormularDataField formular = model.formulars().get(formularId);
		
		UUID expressionId = UUID.fromString(href.single("expression"));
				
		FormularExpression expression = formular.expressions().get(expressionId);
		final ExpressionArg item = expression.arguments().get(id.get());
		
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
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeSource() {
		            @Override
		            public Iterable<Directive> toXembly() throws IOException {
		                return dir;
		            }
		       };
	}	
}
