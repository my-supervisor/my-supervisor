package com.minlessika.membership.takes;

import com.minlessika.membership.domain.User;
import com.minlessika.rq.RqJson;
import com.minlessika.rq.RqJsonBase;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.report.ReportFormat;
import com.minlessika.sdk.report.birt.BirtReport;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rs.RsWithBody;

import javax.json.JsonObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public abstract class TkBaseReport implements Take {

	protected final Base base;
	
	public TkBaseReport(final Base base) {
		this.base = base;
	}
	
	@Override
	public Response act(Request req) throws IOException {
			
		final User user = new RqUser(base, req);
		
		final RqJson rqJson = new RqJsonBase(new RqGreedy(req));					
		JsonObject reqObj = rqJson.payload().asJsonObject();
		ReportFormat format = ReportFormat.valueOf(reqObj.getString("format"));					
	    
		Map<String, Object> context = context(req, reqObj);
		context.put(Locale.class.getSimpleName(), user.locale());
		
		final ByteArrayOutputStream output = new ByteArrayOutputStream();					
		new BirtReport(name(req), context).render(format, output);
		
		return new RsWithBody(output.toByteArray());
	}
	
	protected abstract String name(Request req) throws IOException;	
	protected abstract Map<String, Object> context(Request req, JsonObject parameters) throws IOException;
}
