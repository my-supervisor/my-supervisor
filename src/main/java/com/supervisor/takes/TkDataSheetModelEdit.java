package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.Activity;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheetModels;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivity;
import com.supervisor.xe.XeDataSheetModel;
import com.supervisor.xe.XeEditableDataField;
import com.supervisor.xe.XeSupervisor;

public final class TkDataSheetModelEdit extends TkForm {

	public TkDataSheetModelEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/data_sheet_model_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		content.add(new XeActivity("own_activities", module.activities().ownActivities().where(Activity::isTemplate, false)));
		return content;
	}

	@Override
	protected XeSource newItemToShow(Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		DataSheetModels myItems = module.dataSheetModels(); 
		DataSheetModel item = myItems.get(id);		
		return new XeChain(
				new XeEditableDataField(item.fields().editables()),
				new XeDataSheetModel("item", item)				
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		XeSource xeSource = new XeDataSheetModel(dir);
		if(id > 0) {
			final Supervisor module = new PxSupervisor(base, req);
			DataSheetModels myItems = module.dataSheetModels(); 
			DataSheetModel item = myItems.get(id);
			
			xeSource = new XeChain(
							new XeEditableDataField(item.fields().editables()),
							xeSource
					   );
		}		
		
		return xeSource;
	}	
}
