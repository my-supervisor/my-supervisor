package com.minlessika.supervisor.takes;

import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheets;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeDataSheet;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkDataSheet extends TkBaseWrap {

	public TkDataSheet(final Base base) {
		super(
				base, 
				(req)->{
					final Supervisor module = new PxSupervisor(base, req);
					DataSheets myItems = module.dataSheets().orderBy(DataSheet::id, OrderDirection.DESC);;
					
					final XeSource xeSource;
					if(myItems.any())
						xeSource = new XeDataSheet(myItems);
					else
						xeSource = XeSource.EMPTY;
					
					XeSource xeSupervisor = new XeSupervisor(module);
					
					return new RsPage(
							"/com/supervisor/xsl/data_sheet.xsl",
							req,
							base,
							()-> new Sticky<>(
									new XeAppend("menu", "collect"),
									new XeAppend("submenu", "data_sheet"),
									xeSource,
									xeSupervisor
							)
					);
				}
		);
	}	
}
