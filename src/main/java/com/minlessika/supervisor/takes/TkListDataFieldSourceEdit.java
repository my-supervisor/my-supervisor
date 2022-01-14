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
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.UserDataModels;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeDataField;
import com.minlessika.supervisor.xe.XeDataModel;
import com.minlessika.supervisor.xe.XeDataSheetModel;
import com.minlessika.supervisor.xe.XeListDataField;
import com.minlessika.supervisor.xe.XeListDataFieldSource;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkListDataFieldSourceEdit extends TkForm {

	private static final String MODEL = "model";
	
	public TkListDataFieldSourceEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/supervisor/xsl/list_data_field_source_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		Long modelId = Long.parseLong(new RqHref.Smart(req).single(MODEL));
		Long fieldId = Long.parseLong(new RqHref.Smart(req).single("field"));
		Long tableId = Long.parseLong(new RqHref.Smart(req).single("table", "0"));
		Long tableModelId = Long.parseLong(new RqHref.Smart(req).single("table_model", "0"));
		
		final Supervisor module = new PxSupervisor(base, req);
		final DataSheetModel model = module.dataSheetModels().get(modelId);
		final ListDataField field = (ListDataField)model.fields().get(fieldId);		
		final UserDataModels models = module.dataModels().where(DataModel::interacting, false).where(DataModel::isTemplate, false);
		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeDataModel(models.items()));
		content.add(new XeListDataField("field", field));
		XeSource xeModelFields = XeSource.EMPTY;
		for (DataModel m : models.items()) {
			xeModelFields = new XeChain(
								xeModelFields, 
								new XeDataField(m.fields(), m)
							);
		}
		
		content.add(xeModelFields);
		content.add(new XeDataSheetModel(MODEL, model));
		
		if(tableId > 0) {		
			final DataSheetModel tableModel = module.dataSheetModels().get(tableModelId);
			final TableDataField table = (TableDataField)tableModel.fields().get(tableId);
			content.add(new XeDataField("table", table));
		}			
		
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}
	
	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Long modelId = Long.parseLong(new RqHref.Smart(req).single(MODEL));
		Long fieldId = Long.parseLong(new RqHref.Smart(req).single("field"));
		
		final Supervisor module = new PxSupervisor(base, req);
		final DataSheetModel model = module.dataSheetModels().get(modelId);
		final ListDataField list = (ListDataField)model.fields().get(fieldId);
		final ListDataFieldSource item = list.sources().get(id);
		return new XeListDataFieldSource("item", item);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeListDataFieldSource(dir);
	}	
}
