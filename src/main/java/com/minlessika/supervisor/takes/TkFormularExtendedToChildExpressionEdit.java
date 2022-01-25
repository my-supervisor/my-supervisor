package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.comparators.Matchers;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExtendedToChildExpression;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeAggregateFunc;
import com.minlessika.supervisor.xe.XeDataSheetModel;
import com.minlessika.supervisor.xe.XeEditableDataField;
import com.minlessika.supervisor.xe.XeFormularExtendedToChildExpression;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkFormularExtendedToChildExpressionEdit extends TkForm {

	public TkFormularExtendedToChildExpressionEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/formular_extended_to_child_expression_edit.xsl";
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
		
		final Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		final FormularDataField formular = amodel.formulars().get(formularId);
		
		XeSource xeChildren = XeSource.EMPTY;
		XeSource xeChildrenFields = XeSource.EMPTY;
		final List<DataSheetModel> children = model.children().items();
		if(!children.isEmpty()) {
			for (DataModel child : children) {
				xeChildrenFields = new XeChain(
										xeChildrenFields,
										new XeEditableDataField(
											child.fields()
											     .editables()
												 .where(EditableDataField::type, Matchers.notEqualsTo(DataFieldType.TABLE))
												 .items()
									    )
									);
			}
			
			xeChildren = new XeDataSheetModel(children);
		}
		
		final List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));
		content.add(new XeAggregateFunc());	
		content.add(new XeAppend("formular_id", formular.id().toString()));
		content.add(new XeAppend("formular", formular.name()));
		content.add(new XeAppend("model_id", amodel.id().toString()));
		content.add(xeChildren);
		content.add(xeChildrenFields);
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
		
		final FormularExtendedToChildExpression item = (FormularExtendedToChildExpression)formular.expressions().get(id);

		return new XeChain(
			new XeFormularExtendedToChildExpression("item", item)
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
