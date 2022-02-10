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

import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeDataField;
import com.supervisor.xe.XeDataFieldStyle;
import com.supervisor.xe.XeDataFieldType;
import com.supervisor.xe.XeDataSheetModel;
import com.supervisor.xe.XeEditableDataField;
import com.supervisor.xe.XeSupervisor;
import com.supervisor.xe.XeTableDataField;

public final class TkDataFieldTableEdit extends TkForm {

	public TkDataFieldTableEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/data_table_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeDataFieldType());
		content.add(new XeDataFieldStyle());
		content.add(new XeDataSheetModel("model", model));
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);
		final TableDataField item = (TableDataField)model.fields().get(id.get());
		return new XeChain(
				new XeTableDataField("item", item),
				new XeEditableDataField(item.columns())
		);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(id.isPresent()) {
		    return new XeChain(
				new XeDataField(dir),
				preItemDataToShow(id, req)
			);
		} else				
		    return new XeDataField(dir);	
	}	
}
