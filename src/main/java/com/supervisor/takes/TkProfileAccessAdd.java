package com.supervisor.takes;

import com.supervisor.domain.Access;
import com.supervisor.domain.Profile;
import com.supervisor.domain.impl.PxAllProfiles;
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
import java.util.UUID;
import java.util.logging.Level;

public final class TkProfileAccessAdd extends TkBaseWrap {

	public TkProfileAccessAdd(Base base) {
		super(
				base,
				req -> {
					new RqAdminAuth(base, req);
					
					final UUID profileId = UUID.fromString(new RqHref.Smart(req).single("profile"));
					final Profile profile = new PxAllProfiles(base).get(profileId);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));			

					// ajouter les nouveaux droits d'accès
					int nbOfAccessesToTreat = getValuesOfRow("access_id", form).size();
					for (int i = 0; i < nbOfAccessesToTreat; i++) {
						Boolean isChecked = Boolean.parseBoolean(getRowValueAt("access_checked", form, i));
						if(!isChecked)
							continue;
						
						UUID accessId = UUID.fromString(getRowValueAt("access_id", form, i));
						Access access = profile.exceptedAccesses().get(accessId);
						
						profile.accesses().add(access);
					}
					
					return new RsForward(
						new RsFlash(
							"Ajout de droit d'accès effectué avec succès !",
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
