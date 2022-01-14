package com.minlessika.sdk.report;

import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class ReportAsStreaming implements StreamingOutput {

	private final Report report;
	private final ReportFormat format;
	
	public ReportAsStreaming(final Report report, final ReportFormat format) {
		this.report = report;
		this.format = format;
	}
	
	@Override
	public void write(OutputStream output) throws IOException {
		
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		report.render(format, stream);
		
		try {
			stream.writeTo(output);
		} finally
		{
			stream.close();
		}	
	}

}
