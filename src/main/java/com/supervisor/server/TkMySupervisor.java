package com.supervisor.server;

import java.time.LocalDateTime;
import java.util.Map;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.SqlTxn;
import com.supervisor.sdk.takes.TkBaseWrap;
import com.supervisor.sdk.translation.I18n;
import org.takes.Take;

import com.supervisor.billing.PlannedTask;
import com.supervisor.billing.PlannedTasks;
import com.supervisor.billing.PurchaseOrder;
import com.supervisor.domain.PlanSubscriptionContract;
import com.supervisor.domain.Profile;
import com.supervisor.domain.User;
import com.supervisor.takes.RqUser;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

/**
 * @todo #32:30min Fix internal error while requesting login page.
 *  We see an internal error when we want to show login page.
 *  Fix it and enable login and logout.
 */
public final class TkMySupervisor extends TkBaseWrap {

	public TkMySupervisor(final Base base, final Take take) {
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
