package com.minlessika.supervisor.takes;

import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.DataSheetModels;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeDataSheetModel;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkDataSheetModel extends TkBaseWrap {

	public TkDataSheetModel(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					DataSheetModels myItems = module.dataSheetModels().where(DataSheetModel::isTemplate, false); 
					
					final XeSource xeSource;
					if(myItems.any())
						xeSource = new XeDataSheetModel(myItems);
					else
						xeSource = XeSource.EMPTY;
					
					XeSource xeSupervisor = new XeSupervisor(module);
					
					return new RsPage(
							"/xsl/data_sheet_model.xsl",
							req, 
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "collect"),
									new XeAppend("submenu", "data_sheet_model"),
									xeSource,
									xeSupervisor
							)
					);
				}
		);
	}	
}
