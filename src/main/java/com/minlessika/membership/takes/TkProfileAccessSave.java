package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.ProfileAccess;
import com.minlessika.membership.domain.ProfileAccessParam;
import com.minlessika.membership.domain.impl.PxAllProfiles;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.apache.commons.collections.IteratorUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public final class TkProfileAccessSave extends TkBaseWrap {

	public TkProfileAccessSave(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final Long profileId = Long.parseLong(new RqHref.Smart(req).single("profile"));
					final Profile profile = new PxAllProfiles(base).get(profileId);
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					final ProfileAccess item = profile.accesses().get(id);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));			

					// sauvegarder les valeurs des paramètres
					int nbOfParamsToTreat = getValuesOfRow("param_id", form).size();
					for (int i = 0; i < nbOfParamsToTreat; i++) {
						Long paramId = getRowLongValueAt("param_id", form, i);
						ProfileAccessParam param = item.parameterValues().get(paramId);
						
						String paramValue = getRowValueAt("param_value", form, i);
						
						param.update(paramValue);
					}
					
					return new RsForward(
						new RsFlash(
								String.format("Le droit d'accès < %s > a été modifié avec succès !", item.name()),
			                Level.FINE
			            ),
			            String.format("/admin/profile/edit?id=%s", profile.id())
				    );
				}
		);
	}
	
	@SuppressWarnings("unchecked")
	private static List<String> getValuesOfRow(final String name, RqFormSmart form) throws IOException {
		return IteratorUtils.toList(form.param(name).iterator());
	}
	
	private static String getRowValueAt(final String name, RqFormSmart form, int index) throws IOException {
		return getValuesOfRow(name, form).get(index);
	}
	
	private static Long getRowLongValueAt(final String name, RqFormSmart form, int index) throws IOException {
		return Long.parseLong(getRowValueAt(name, form, index));
	}
}
