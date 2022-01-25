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
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeDataField;
import com.minlessika.supervisor.xe.XeDataFieldStyle;
import com.minlessika.supervisor.xe.XeDataFieldType;
import com.minlessika.supervisor.xe.XeDataSheetModel;
import com.minlessika.supervisor.xe.XeEditableDataField;
import com.minlessika.supervisor.xe.XeSupervisor;
import com.minlessika.supervisor.xe.XeTableDataField;

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
		
		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
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
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		Long modelId = Long.parseLong(new RqHref.Smart(req).single("model"));
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModel model = module.dataSheetModels().get(modelId);
		final TableDataField item = (TableDataField)model.fields().get(id);
		return new XeChain(
				new XeTableDataField("item", item),
				new XeEditableDataField(item.columns())
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(id > 0) {
		    return new XeChain(
				new XeDataField(dir),
				preItemDataToShow(id, req)
			);
		} else				
		    return new XeDataField(dir);	
	}	
}
