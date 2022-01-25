package com.supervisor.takes;

import java.util.ArrayList;
import java.util.List;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.RsPage;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.cactoos.iterable.Sticky;
import org.takes.rs.xe.XeSource;

import com.supervisor.domain.Membership;
import com.supervisor.domain.Plan;
import com.supervisor.domain.PlanFeature;
import com.supervisor.domain.Plans;
import com.supervisor.xe.XePlan;
import com.supervisor.xe.XePlanFeature;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivity;
import com.supervisor.xe.XeSupervisor;

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
							"/xsl/pricing.xsl",
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
