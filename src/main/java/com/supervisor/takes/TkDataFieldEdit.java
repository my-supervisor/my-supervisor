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
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeDataField;
import com.supervisor.xe.XeDataFieldStyle;
import com.supervisor.xe.XeDataFieldType;
import com.supervisor.xe.XeDataSheetModel;
import com.supervisor.xe.XeSupervisor;
import com.supervisor.xe.XeTableDataField;

public final class TkDataFieldEdit extends TkForm {

	public TkDataFieldEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/supervisor/xsl/data_field_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		OptUUID tableId = new OptUUID(new RqHref.Smart(req).single("table", "0"));
		
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeDataFieldType());
		content.add(new XeDataFieldStyle());
		content.add(new XeDataSheetModel("model", model));
		
		if(tableId.isPresent())
			content.add(new XeTableDataField("table", model.fields().tables().get(tableId.get())));
		
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		final Supervisor module = new PxSupervisor(base, req);
		final DataSheetModel model = module.dataSheetModels().get(modelId);
		final SimpleDataField item = (SimpleDataField)model.fields().get(id.get());
		return new XeDataField("item", item);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeDataField(dir);
	}	
}
