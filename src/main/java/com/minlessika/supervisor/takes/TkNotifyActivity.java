package com.minlessika.supervisor.takes;

import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsEmpty;

import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkNotifyActivity extends TkBaseWrap {

	public TkNotifyActivity(final Base base) {
		super(
				base,
				req -> {
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));	
					final Long ownerId = Long.parseLong(form.single("owner_id"));
					final Iterable<String> modelIds = form.param("model_id");
					
					final RecordSet<User> source = base.select(User.class);
					final User owner = new DmUser(source.get(ownerId)); // do action as owner
					
					final Supervisor module = new PxSupervisor(base, owner);
					
					for (String modelId : modelIds) {
						final DataSheetModel model = module.dataSheetModels().get(Long.parseLong(modelId));
						module.activityNotification().publish(model);
					}					
					
					return new RsEmpty();
				}
		);
	}	
}
