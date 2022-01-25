package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeDataSheet;
import com.supervisor.xe.XeSupervisor;

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
							"/xsl/data_sheet.xsl",
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
