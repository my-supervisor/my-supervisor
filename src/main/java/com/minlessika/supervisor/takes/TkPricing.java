package com.minlessika.supervisor.takes;

import java.util.ArrayList;
import java.util.List;

import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeSource;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.PlanFeature;
import com.minlessika.membership.domain.Plans;
import com.minlessika.membership.xe.XePlan;
import com.minlessika.membership.xe.XePlanFeature;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeActivity;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkPricing extends TkBaseWrap {

	public TkPricing(final Base base) {
		super(
				base, 
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final Membership membership = module.membership();
					final Plans plans = membership.plans().where(Plan::enabled, true);
					
					final XeSource xePlans = new XePlan(plans.items());
					
					List<PlanFeature> features = new ArrayList<>();
					for (Plan plan : plans.items()) {
						features.addAll(plan.features().items());
					}
					
					final XeSource xePlanFeatures = new XePlanFeature(features);
					final XeSource xeMyActivities = new XeActivity(module.activities());
					
					XeSource xeSupervisor = new XeSupervisor(module);
					
					return new RsPage(
							"/com/supervisor/xsl/pricing.xsl",
							req,
							base,
							()-> new Sticky<>(
									xePlans,
									xePlanFeatures,
									xeMyActivities,
									xeSupervisor									
							)
					);
				}
		);
	}	
}
