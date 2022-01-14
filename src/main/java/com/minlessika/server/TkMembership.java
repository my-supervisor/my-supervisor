package com.minlessika.server;

import com.minlessika.membership.billing.PlannedTask;
import com.minlessika.membership.billing.PlannedTasks;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.SqlTxn;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.sdk.translation.I18n;
import org.takes.Take;

import java.time.LocalDateTime;

public class TkMembership extends TkBaseWrap {

	public TkMembership(final Base base, final Take take) {
		super(
				base, 
				req -> 
					new SqlTxn(base, req)
						.call((final Base myBase) -> {
							
							final User user = new RqUser(myBase, req);
							
							if(user.isAnonymous()) {
								I18n.register(req);
							} else {
								I18n.register(user.locale());
								if(user.applications().has(Membership.NAME)) {
									// 1 - perform tasks
									final PlannedTasks tasks = user.plannedTasks().tasksToExecute(LocalDateTime.now());
									
									for (PlannedTask task : tasks.items()) {
										
										task.execute();
									}
								}
							}							
																
							return take.act(req);
						})
		);
	}
	
}
