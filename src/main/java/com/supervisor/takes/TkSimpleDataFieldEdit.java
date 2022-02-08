package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Request;
import org.takes.misc.Opt;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeDataField;
import com.supervisor.xe.XeDataFieldType;
import com.supervisor.xe.XeDataSheetModel;
import com.supervisor.xe.XeSimpleDataField;
import com.supervisor.xe.XeSupervisor;

public final class TkSimpleDataFieldEdit extends TkForm {

	private static final String MODEL = "model";
	
	public TkSimpleDataFieldEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/simple_data_field_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single(MODEL));
		OptUUID tableModelId = new OptUUID(new RqHref.Smart(req).single("table_model", "0"));
		OptUUID tableId = new OptUUID(new RqHref.Smart(req).single("table", "0"));
		
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeDataFieldType());
		content.add(new XeDataSheetModel(MODEL, model));
		
		if(tableId.isPresent()) {
			final DataSheetModel tableModel = module.dataSheetModels().get(tableModelId.value());
			content.add(new XeDataField("table", tableModel.fields().get(tableId.value())));
			content.add(new XeAppend("table_model_id", tableModelId.toString()));
		}	
		
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single(MODEL));
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);
		SimpleDataField item = model.fields().simples().get(id.value());
		return new XeSimpleDataField("item", item);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeSimpleDataField(dir);
	}	
}
