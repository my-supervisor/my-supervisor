package com.supervisor.takes;

import com.supervisor.domain.impl.DmUser;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsEmpty;

import com.supervisor.domain.User;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

import java.util.UUID;

public final class TkNotifyActivity extends TkBaseWrap {

	public TkNotifyActivity(final Base base) {
		super(
				base,
				req -> {
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));	
					final UUID ownerId = UUID.fromString(form.single("owner_id"));
					final Iterable<String> modelIds = form.param("model_id");
					
					final RecordSet<User> source = base.select(User.class);
					final User owner = new DmUser(source.get(ownerId)); // do action as owner
					
					final Supervisor module = new PxSupervisor(base, owner);
					
					for (String modelId : modelIds) {
						final DataSheetModel model = module.dataSheetModels().get(UUID.fromString(modelId));
						module.activityNotification().publish(model);
					}					
					
					return new RsEmpty();
				}
		);
	}	
}
