package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.RsJson;

import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.bi.BiResult;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeListDataFieldValueJson;

public final class TkListDataFieldValueSearchable extends TkBaseWrap {

	public TkListDataFieldValueSearchable(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					Smart params = new RqHref.Smart(req);
		
					final Long fieldId = Long.parseLong(params.single("field"));
					final Long modelId = Long.parseLong(params.single("model"));
					final String filter = params.single("filter", "");
					final Long page = Long.parseLong(params.single("page"));
					final int itemsPerPage = Integer.parseInt(params.single("itemsPerPage"));
					
					final DataSheetModel model = module.dataSheetModels().get(modelId);
					final ListDataField field = (ListDataField)model.fields().get(fieldId);
					final Long start = itemsPerPage * (page - 1) + 1;
					final int limit = itemsPerPage;
					
					final BiResult result = field.values(filter, start, limit);
		
					return new RsJson(
							new XeListDataFieldValueJson(field.fieldToDisplay().code(), result)
					);
				}
		);
	}	
}
