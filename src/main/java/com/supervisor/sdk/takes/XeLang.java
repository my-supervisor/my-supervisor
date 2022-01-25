package com.supervisor.sdk.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.ResultStatement;
import com.supervisor.sdk.translation.I18n;
import org.takes.Request;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class XeLang extends XeWrap {

	public XeLang(final Request req, final Base base) throws IOException {
		super(transform(req, base));
	}
	
	private static XeSource transform(final Request req, final Base base) throws IOException {
		
		final String lang;
		if(new RqAuth(req).identity() == Identity.ANONYMOUS || base == Base.EMPTY)
    		lang = I18n.locale().getLanguage();
		else {
			Long id = Long.parseLong(new RqAuth(req).identity().properties().get("id"));
	    	List<ResultStatement> results = base.query(
								    			  "select lang.iso_code "
								    			+ "from base_person as pers "
								    			+ "left join base_language as lang on lang.id = pers.preferred_language_id "
								    			+ "where pers.id=?", 
								    			Arrays.asList(id)
								    		);
	    	Map<String, Object> data = results.get(0).data(); 
	    	lang = (String)data.get("iso_code");
		}
		
		return new XeAppend("lang", lang);
	}

}
