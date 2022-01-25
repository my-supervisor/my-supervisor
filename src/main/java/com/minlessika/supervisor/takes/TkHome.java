package com.minlessika.supervisor.takes;

import java.util.ArrayList;
import java.util.List;

import org.cactoos.iterable.Sticky;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Activities;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeActivity;
import com.minlessika.supervisor.xe.XeDataSheetModel;
import com.minlessika.supervisor.xe.XeIndicator;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkHome extends TkBaseWrap {

	public TkHome(final Base base) {
		super(
				base, 
				req -> {
					
					final Supervisor module = new PxSupervisor(base, req);
					final Activities myActivities = module.activities();
					module.user().applications().add(Supervisor.NAME);
					
					if(myActivities.isEmpty()) {
						return new RsForward("/no-activity");
					}else {
						Smart params = new RqHref.Smart(req);
						
						final Long id = Long.parseLong(params.single("activity", "0"));
						final Activity act;
						
						if(id > 0) {
							// prendre l'activité sélectionné par l'utilisateur
							act = myActivities.get(id);								
						}else {
							// afficher une activité par défaut
							Activities ownActivities = myActivities.ownActivities();
							if(ownActivities.any()) {
								// prendre l'activité par défaut s'il y'en a une
								Activities activityDefault = ownActivities.where(Activity::defaultShown, true); 
								
								if(activityDefault.any()) {
									act = activityDefault.first();
								}else {
									act = ownActivities.first();
									act.setDefaultShown();
								}
							} else {
								act = myActivities.first(); // c'est donc une activité partagée qui sera affichée
							}
						}
						
					    XeSource xeSupervisor = new XeSupervisor(module);
					    XeSource xeActivitiesLinked = new XeActivity("activities_linked", act.actors(), 0);
					    final List<DataSheetModel> forms = new ArrayList<>();
					    for (DataSheetModel m : act.formsOf(new UserOf(act)).where(DataSheetModel::activity, act.id()).items()) {
							if(!m.isTable()) {
								forms.add(m);
							}
						}
					    
					    XeSource xeModels = new XeDataSheetModel(forms); 
					    XeSource xeActivitySelected = new XeActivity("activity_selected", act);
					    XeSource xeTotalInteraction = new XeAppend("total_interactions", Long.toString(act.interactions().items().size()));
					    XeSource xeActiveInteraction = new XeAppend("active_interactions", Long.toString(act.interactions().actives().size()));
					    XeSource xeIndicators = new XeIndicator(act.indicators());				   
					    XeSource wsBare = new XeAppend("ws_bare", base.wsServer().bare(req)); 					    
					    
					    return new RsPage(
								"/xsl/home.xsl",
								req, 
								base,
								()-> new Sticky<>(
										new XeAppend("menu", "home"),
										wsBare,
										xeSupervisor,
										xeActivitiesLinked,
										xeActivitySelected,
										xeIndicators,
										xeModels,
										xeTotalInteraction,
										xeActiveInteraction
								)
						);
					}					
				}
		);
	}	
}
