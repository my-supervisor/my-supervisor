package com.supervisor.domain.bi.impl;

import java.util.List;

import com.supervisor.domain.bi.Printer;
import com.supervisor.domain.bi.PrinterRow;

public final class PrinterRowImpl implements PrinterRow {

	private final int level;
	private final List<Printer> items;
	
	public PrinterRowImpl(final int level, final List<Printer> items) {
		this.level = level;
		this.items = items;
	}
	
	@Override
	public int level() {
		return level;
	}

	@Override
	public List<Printer> items() {
		return items;
	}
}
