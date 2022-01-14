package com.minlessika.sdk.report;

import java.io.IOException;
import java.io.OutputStream;

public interface Report {	
	String name();	
	void render(ReportFormat format, OutputStream output) throws IOException;
}
