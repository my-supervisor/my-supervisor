package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
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
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeDataSheetModel;
import com.supervisor.xe.XeEditableDataField;
import com.supervisor.xe.XeFormularExtendedToParentSource;
import com.supervisor.xe.XeSupervisor;

public final class TkFormularExtendedToParentSourceEdit extends TkForm {

	public TkFormularExtendedToParentSourceEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/formular_extended_to_parent_source_edit.xsl";
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		final Long modelId = Long.parseLong(new RqHref.Smart(req).single("model")); 
		final AggregatedModel amodel = module.aggregatedModels()
									  		.get(modelId);
		final DataSheetModel model = amodel.coreModel();
		
		final Long referenceId = Long.parseLong(new RqHref.Smart(req).single("reference"));
		final ListDataField reference = model.fields().lists().get(referenceId);
		
		final Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		final FormularDataField formular = amodel.formulars().get(formularId);
		
		final Long exprId = Long.parseLong(new RqHref.Smart(req).single("expr"));
		final FormularExpression expr = formular.expressions().get(exprId);
		
		final XeSource xeParent;
		XeSource xeParentFields = XeSource.EMPTY;
		final List<DataSheetModel> parents = new ArrayList<>();
		for (DataSheetModel dataModel : model.parents().items()) {
			if(reference.isBasedOn(dataModel)) {
				parents.add(dataModel);
			}
		}

		if(parents.isEmpty()) {
			xeParent = XeSource.EMPTY;
		} else {			
			for (DataSheetModel m : parents) {
				xeParentFields = new XeChain(
									xeParentFields, 
									new XeEditableDataField(
										"parent_datafields", 
										m.scalarEditableFields().items(),
										m
									)
								 );
			}
			
			xeParent = new XeDataSheetModel(parents);
		}
		
		final List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));	
		content.add(new XeAppend("formular_id", formular.id().toString()));
		content.add(new XeAppend("formular", formular.name()));
		content.add(new XeAppend("model_id", amodel.id().toString()));
		content.add(new XeAppend("expr_id", expr.id().toString()));
		content.add(new XeAppend("reference_id", reference.id().toString()));
		content.add(xeParent);
		content.add(xeParentFields);
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		final Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
		final AggregatedModel model = module.aggregatedModels().get(modelId); 
		
		final Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		final FormularDataField formular = model.formulars().get(formularId);
		
		final Long exprId = Long.parseLong(new RqHref.Smart(req).single("expr"));
		final FormularExtendedToParentExpression expr = (FormularExtendedToParentExpression)formular.expressions().get(exprId);
		final FormularExtendedToParentSource item = expr.sources().get(id);
		
		return new XeChain(
			new XeFormularExtendedToParentSource("item", item)
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(id == 0)
			return newItemToShow(req);
		else
			return preItemDataToShow(id, req);
	}	
}
