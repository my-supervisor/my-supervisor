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

import com.supervisor.domain.UserScope;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeDataFieldOfSheet;
import com.supervisor.xe.XeDataSheet;
import com.supervisor.xe.XeDataSheetModel;
import com.supervisor.xe.XeListDataFieldSource;
import com.supervisor.xe.XeRowOfDataFieldTable;
import com.supervisor.xe.XeSupervisor;

public final class TkDataSheetEdit extends TkForm {

	public TkDataSheetEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/data_sheet_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		content.add(new XeSupervisor(module));				
		content.add(itemToShow);		
		return content;
	}
	
	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {

		final Supervisor module = new PxSupervisor(base, req);
		
		UUID modelId = UUID.fromString(new RqHref.Smart(req).single("model"));
		DataSheetModel model = module.dataSheetModels().get(modelId);
		
		XeSource xeSheetModel = new XeDataSheetModel(model);
		XeSource xeSheetFields = new XeDataFieldOfSheet(model.generateFields());
		
		XeSource xeColumns = XeSource.EMPTY;
		XeSource xeListSources = XeSource.EMPTY;
		for (EditableDataField field : model.fields().editables().items()) {			
			if(field.type() == DataFieldType.TABLE) {
				final TableDataField table = (TableDataField)field;
				final List<DataFieldOfSheet> columns = new ArrayList<>();
				
				for (DataFieldOfSheet column : table.structure().generateFields()) {
					if(column.userScope() == UserScope.USER) {
						columns.add(column);
					}
				}
				
				xeColumns = new XeDataFieldOfSheet(
								"columns",
								true,
								columns
							);				
			} else if(field.style() == DataFieldStyle.LIST) {
				final ListDataField list = (ListDataField)field;
				xeListSources = new XeChain(
									xeListSources,
									new XeListDataFieldSource(list.sources())
							    );
				
			}
		}
		
		return new XeChain(
			xeSheetModel,
			xeSheetFields,
			xeColumns,
			xeListSources
		);
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		DataSheets myItems = module.dataSheets();
		DataSheet item = myItems.get(id.get());
				
		XeSource xeRows = XeSource.EMPTY;
		XeSource xeColumns = XeSource.EMPTY;
		for (DataFieldOfSheet field : item.fields().items()) {			
			if(field.type() == DataFieldType.TABLE) {
				final TableDataFieldOfSheet table = (TableDataFieldOfSheet)field;			
				final List<DataFieldOfSheet> columns = new ArrayList<>();
				
				for (DataFieldOfSheet column : table.structure().generateFields()) {
					if(column.userScope() == UserScope.USER) {
						columns.add(column);
					}
				}
				
				xeColumns = new XeDataFieldOfSheet(
								"columns",
								true,
								columns
							);
				
				xeRows = new XeChain(
								xeRows,
								new XeRowOfDataFieldTable(
									table.rows().items()
								) 
							);				
			}
		}
		
		return new XeChain(
				new XeDataSheet("item", item),
				new XeDataFieldOfSheet(item.fields()),
				new XeDataSheetModel(item.model()),
				xeColumns,
				xeRows
		);
	}

	@Override
	protected XeSource postItemDataToShow(final OptUUID id, final Request req, final RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(!id.isPresent()) {
			return newItemToShow(req);
		}else {
			return preItemDataToShow(id, req);
		}
	}	
}
