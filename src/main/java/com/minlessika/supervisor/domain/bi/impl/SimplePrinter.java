package com.minlessika.supervisor.domain.bi.impl;

import java.io.IOException;

import com.minlessika.supervisor.domain.bi.Printer;

public final class SimplePrinter implements Printer {

	private final String text;
	
	public SimplePrinter(final String text) {
		this.text = text;
	}
	
	@Override
	public String toText() throws IOException {
		return text;
	}

}
