package com.supervisor.takes;

import java.util.logging.Level;

import javax.json.JsonArray;
import javax.json.JsonObject;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.Request;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;

import com.minlessika.rq.RqJson;
import com.minlessika.rq.RqJsonBase;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkFormularExpressionOrganize extends TkBaseWrap {

	public TkFormularExpressionOrganize(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					
					final Request gReq = new RqGreedy(req);
					
					Long modelId = Long.parseLong(new RqHref.Smart(gReq).single("model"));
					Long formularId = Long.parseLong(new RqHref.Smart(gReq).single("formular"));		
					AggregatedModel model = module.aggregatedModels().get(modelId);
					FormularDataField formular = model.formulars().get(formularId);
						
					final RqJson rqJson = new RqJsonBase(gReq);					
					JsonArray locations = rqJson.payload().asJsonArray();
					for (JsonObject myLoc : locations.getValuesAs(JsonObject.class)) {
						
						Long id = myLoc.getJsonNumber("id").longValue();			
						FormularExpression expr = formular.expressions().get(id);	
						
						final int no = myLoc.getInt("no");
						
						expr.order(no); 
					}
								
					return new RsForward(
						new RsFlash(
								"Les expressions ont été organisées avec succès !",
			                Level.FINE
			            ),
			            String.format("/collect/aggregated-model/formular/edit?id=%s&model=%s", formular.id(), model.id())
				    );
				}
		);
	}
}
