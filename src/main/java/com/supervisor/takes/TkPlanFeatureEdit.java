package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.Plan;
import com.supervisor.domain.PlanFeature;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.utils.OptUUID;
import com.supervisor.xe.XePlan;
import com.supervisor.xe.XePlanFeature;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class TkPlanFeatureEdit extends TkForm {


	public TkPlanFeatureEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/plan_feature_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		final Membership module = new DmMembership(base, req);
		final UUID planId = UUID.fromString(new RqHref.Smart(req).single("plan"));
		final Plan plan = module.plans().get(planId);
		
		List<XeSource> content = new ArrayList<>();
		content.add(itemToShow);
		content.add(new XePlan(plan));
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		final Membership module = new DmMembership(base, req);
		final UUID planId = UUID.fromString(new RqHref.Smart(req).single("plan"));
		final Plan plan = module.plans().get(planId);
		final PlanFeature item = plan.features().get(id.get());
		
		XeSource xePlanFeature = new XePlanFeature("item", item);
		return new XeChain(
				xePlanFeature
		);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XePlanFeature(dir);
	}
}
