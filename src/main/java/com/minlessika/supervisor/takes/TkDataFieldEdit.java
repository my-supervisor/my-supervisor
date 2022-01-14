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
import com.minlessika.supervisor.xe.XeDataFieldStyle;
import com.minlessika.supervisor.xe.XeDataFieldType;
import com.minlessika.supervisor.xe.XeDataSheetModel;
import com.minlessika.supervisor.xe.XeSupervisor;
import com.minlessika.supervisor.xe.XeTableDataField;

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
		
		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
		Long tableId = Long.parseLong(new RqHref.Smart(req).single("table", "0"));
		
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeDataFieldType());
		content.add(new XeDataFieldStyle());
		content.add(new XeDataSheetModel("model", model));
		
		if(tableId > 0)
			content.add(new XeTableDataField("table", model.fields().tables().get(tableId)));
		
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
		final Supervisor module = new PxSupervisor(base, req);
		final DataSheetModel model = module.dataSheetModels().get(modelId);
		final SimpleDataField item = (SimpleDataField)model.fields().get(id);
		return new XeDataField("item", item);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeDataField(dir);
	}	
}
