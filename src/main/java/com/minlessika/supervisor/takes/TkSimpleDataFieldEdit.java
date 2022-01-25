package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.supervisor.domain.SimpleDataField;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeDataField;
import com.minlessika.supervisor.xe.XeDataFieldType;
import com.minlessika.supervisor.xe.XeDataSheetModel;
import com.minlessika.supervisor.xe.XeSimpleDataField;
import com.minlessika.supervisor.xe.XeSupervisor;

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
		
		Long modelId = Long.parseLong(new RqHref.Smart(req).single(MODEL));
		Long tableModelId = Long.parseLong(new RqHref.Smart(req).single("table_model", "0"));
		Long tableId = Long.parseLong(new RqHref.Smart(req).single("table", "0"));
		
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeDataFieldType());
		content.add(new XeDataSheetModel(MODEL, model));
		
		if(tableId > 0) {
			final DataSheetModel tableModel = module.dataSheetModels().get(tableModelId);
			content.add(new XeDataField("table", tableModel.fields().get(tableId)));
			content.add(new XeAppend("table_model_id", tableModelId.toString()));
		}	
		
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		Long modelId = Long.parseLong(new RqHref.Smart(req).single(MODEL));
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);
		SimpleDataField item = model.fields().simples().get(id);
		return new XeSimpleDataField("item", item);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeSimpleDataField(dir);
	}	
}
