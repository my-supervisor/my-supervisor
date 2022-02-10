package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeFormularExtendedToParentExpression;
import com.supervisor.xe.XeFormularExtendedToParentSource;
import com.supervisor.xe.XeListDataField;
import com.supervisor.xe.XeSupervisor;

public final class TkFormularExtendedToParentExpressionEdit extends TkForm {

	public TkFormularExtendedToParentExpressionEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/formular_extended_to_parent_expression_edit.xsl";
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		final UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		final AggregatedModel amodel = module.aggregatedModels()
									  		.get(modelId);
		
		final UUID formularId = UUID.fromString(new RqHref.Smart(req).single("formular"));
		final FormularDataField formular = amodel.formulars().get(formularId);
		
		final List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));	
		content.add(new XeAppend("formular_id", formular.id().toString()));
		content.add(new XeAppend("formular", formular.name()));
		content.add(new XeAppend("model_id", amodel.id().toString()));
		content.add(itemToShow);
		
		return content;
	}
	
	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		final UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		final AggregatedModel amodel = module.aggregatedModels()
									  		.get(modelId);
		final DataSheetModel model = amodel.coreModel();
		
		return new XeListDataField(model.fields().lists().items());
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		final UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		final AggregatedModel model = module.aggregatedModels().get(modelId); 
		
		final UUID formularId = UUID.fromString(new RqHref.Smart(req).single("formular"));
		final FormularDataField formular = model.formulars().get(formularId);
		
		final FormularExtendedToParentExpression item = (FormularExtendedToParentExpression)formular.expressions().get(id.get());

		return new XeChain(
			new XeFormularExtendedToParentExpression("item", item),
			new XeFormularExtendedToParentSource(item.sources())
		);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(!id.isPresent())
			return newItemToShow(req);
		else
			return preItemDataToShow(id, req);
	}	
}
