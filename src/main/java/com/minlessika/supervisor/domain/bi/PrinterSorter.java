package com.minlessika.supervisor.domain.bi;

import java.io.IOException;
import java.util.List;

public interface PrinterSorter {
	List<PrinterRow> items() throws IOException;
}
