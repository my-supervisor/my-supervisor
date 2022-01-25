package com.supervisor.domain.bi.impl;

import java.io.IOException;
import com.supervisor.domain.bi.Printer;

public final class PgViewPrinter implements Printer {

	private final Printer coreView;
	private final Printer select;	
	
	public PgViewPrinter(final Printer coreView, final Printer select) {
		this.coreView = coreView;
		this.select = select;
	}
	
	@Override
	public String toText() throws IOException {

		return String.format(
				  "select *, %s \r\n"
				+ "from (%s) as sheet \r\n",
			  	select.toText(),
			  	coreView.toText()
	    );
	}

}
