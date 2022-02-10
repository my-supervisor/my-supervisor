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

import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.UserDataModels;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeDataField;
import com.supervisor.xe.XeDataModel;
import com.supervisor.xe.XeDataSheetModel;
import com.supervisor.xe.XeListDataField;
import com.supervisor.xe.XeListDataFieldSource;
import com.supervisor.xe.XeSupervisor;

public final class TkListDataFieldSourceEdit extends TkForm {

	private static final String MODEL = "model";
	
	public TkListDataFieldSourceEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/list_data_field_source_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single(MODEL));
		UUID fieldId = UUID.fromString(new RqHref.Smart(req).single("field"));
		OptUUID tableId = new OptUUID(new RqHref.Smart(req).single("table", "0"));
		OptUUID tableModelId = new OptUUID(new RqHref.Smart(req).single("table_model", "0"));
		
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
		
		if(tableId.isPresent()) {
			final DataSheetModel tableModel = module.dataSheetModels().get(tableModelId.get());
			final TableDataField table = (TableDataField)tableModel.fields().get(tableId.get());
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
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final UUID modelId = UUID.fromString(new RqHref.Smart(req).single(MODEL));
		UUID fieldId = UUID.fromString(new RqHref.Smart(req).single("field"));
		
		final Supervisor module = new PxSupervisor(base, req);
		final DataSheetModel model = module.dataSheetModels().get(modelId);
		final ListDataField list = (ListDataField)model.fields().get(fieldId);
		final ListDataFieldSource item = list.sources().get(id.get());
		return new XeListDataFieldSource("item", item);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeListDataFieldSource(dir);
	}	
}
