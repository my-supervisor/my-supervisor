package com.supervisor.takes;

import com.supervisor.domain.Access;
import com.supervisor.domain.AccessParam;
import com.supervisor.domain.Accesses;
import com.supervisor.domain.impl.PxAccesses;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.apache.commons.collections.IteratorUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public final class TkAccessSave extends TkBaseWrap {

	public TkAccessSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));			
					final String name = form.single("name");
					final String targetmodule = form.single("module");
					
					final Accesses accesses = new PxAccesses(base, targetmodule);
					Access item = accesses.get(id);	
					item.update(item.code(), name); 

					// sauvegarder les paramètres
					int nbOfParamsToTreat = getValuesOfRow("param_id", form).size();
					for (int i = 0; i < nbOfParamsToTreat; i++) {
						Long paramId = getRowLongValueAt("param_id", form, i);
						AccessParam param = item.parameters().get(paramId);
						
						String paramName = getRowValueAt("param_name", form, i);
						String paramDefaultValue = getRowValueAt("param_default_value", form, i);
						String paramMessage = getRowValueAt("param_message", form, i);
						
						param.update(param.code(), paramName, paramDefaultValue, param.quantifier(), paramMessage);
					}
					
					return new RsForward(
						new RsFlash(
								String.format("Le droit d'accès < %s > a été modifié avec succès !", item.name()),
			                Level.FINE
			            ),
			            "/admin/access"
				    );
				}
		);
	}
	
	@SuppressWarnings("unchecked")
	private static List<String> getValuesOfRow(final String name, RqFormSmart form) throws IOException {
		return IteratorUtils.toList((form.param(name)).iterator());
	}
	
	private static String getRowValueAt(final String name, RqFormSmart form, int index) throws IOException {
		return getValuesOfRow(name, form).get(index);
	}
	
	private static Long getRowLongValueAt(final String name, RqFormSmart form, int index) throws IOException {
		return Long.parseLong(getRowValueAt(name, form, index));
	}
}
