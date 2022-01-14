package com.minlessika.supervisor.server;

import java.time.LocalDateTime;
import java.util.Map;

import org.takes.Take;

import com.minlessika.membership.billing.PlannedTask;
import com.minlessika.membership.billing.PlannedTasks;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.domain.PlanSubscriptionContract;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.SqlTxn;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.sdk.translation.I18n;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;

public final class TkSupervisor extends TkBaseWrap {

	public TkSupervisor(final Base base, final Take take) {
		super(
				base, 
				req -> 
					new SqlTxn(base, req)
						.call((final Base myBase) -> {
							
							final Supervisor module = new PxSupervisor(myBase, req);
							final User user = new RqUser(myBase, req);
							
							if(user.isAnonymous()) {
								I18n.register(req);
							} else {
								I18n.register(user.locale());
								if(user.applications().has(Supervisor.NAME)) {									
									// 1 - perform tasks
									final PlannedTasks tasks = user.plannedTasks().tasksToExecute(LocalDateTime.now());
									
									for (PlannedTask task : tasks.items()) {
										
										if(task.metadata().containsKey(PurchaseOrder.class.getSimpleName())) {
											final Map<String, String> metadata = task.metadata();
											// perform subscriptions tasks
											// envoi du bon de commande, de la facture et du reçu au client
											
											// création de l'abonnement
											Long purchaseId = Long.parseLong(metadata.get(PurchaseOrder.class.getSimpleName()));
											PurchaseOrder order = module.membership().purchaseOrders().get(purchaseId);
											PlanSubscriptionContract contract = user.contracts().subscribe(order);
											Profile profile = contract.plan().profile();
						    				if(!profile.equals(user.currentProfile())) {
						    					user.changeProfile(profile);
						    				}
										}
										
										task.execute();
									}
								}
							}								
											
							return take.act(req);
						})
		);
	}

}
