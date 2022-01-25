package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeComparator;
import com.minlessika.supervisor.xe.XeDataField;
import com.minlessika.supervisor.xe.XeDataSheetModel;
import com.minlessika.supervisor.xe.XeEditableDataField;
import com.minlessika.supervisor.xe.XeFormularExtendedToModelSource;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkFormularExtendedToModelSourceEdit extends TkForm {

	public TkFormularExtendedToModelSourceEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/formular_extended_to_model_source_edit.xsl";
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
		
		final Long formularId = Long.parseLong(new RqHref.Smart(req).single("formular"));
		final FormularDataField formular = amodel.formulars().get(formularId);
		
		final Long exprId = Long.parseLong(new RqHref.Smart(req).single("expr"));
		final FormularExpression expr = formular.expressions().get(exprId);
		
		final XeSource xeModels;
		XeSource xeModelFields = XeSource.EMPTY;
		List<DataSheetModel> models = module.dataSheetModels().where(DataSheetModel::isTemplate, amodel.isTemplate()).items();
		
		// remove from list core model of amodel
		final DataSheetModel coreModel = amodel.coreModel();
		models = models.stream().filter(c -> !c.equals(coreModel)).collect(Collectors.toList());

		// collect models fields
		if(models.isEmpty()) {
			xeModels = XeSource.EMPTY;
		} else {			
			for (DataSheetModel m : models) {
				xeModelFields = new XeChain(
									xeModelFields, 
									new XeEditableDataField(
										"model_datafields", 
										m.scalarEditableFields().items(),
										m
									)
								 );
			}
			
			xeModels = new XeDataSheetModel(models);
		}
		
		final List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));	
		content.add(new XeComparator());
		content.add(new XeAppend("formular_id", formular.id().toString()));
		content.add(new XeAppend("formular", formular.name()));
		content.add(new XeAppend("model_id", amodel.id().toString()));
		content.add(new XeAppend("expr_id", expr.id().toString()));
		
		content.add(
			new XeDataField(
				"datafield_references", 
				amodel.fields().where(DataField::type, Matchers.notEqualsTo(DataFieldType.TABLE)).items(),
				amodel
			)
		);
		
		content.add(xeModels);
		content.add(xeModelFields);
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
		final FormularExtendedToModelExpression expr = (FormularExtendedToModelExpression)formular.expressions().get(exprId);
		final FormularExtendedToModelSource item = expr.sources().get(id);
		
		return new XeChain(
			new XeFormularExtendedToModelSource("item", item)
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
