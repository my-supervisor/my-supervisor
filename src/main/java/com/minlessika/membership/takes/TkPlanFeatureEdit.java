package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.PlanFeature;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.xe.XePlan;
import com.minlessika.membership.xe.XePlanFeature;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkPlanFeatureEdit extends TkForm {


	public TkPlanFeatureEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/membership/xsl/plan_feature_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		final Membership module = new DmMembership(base, req);
		final Long planId = Long.parseLong(new RqHref.Smart(req).single("plan"));
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
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		final Membership module = new DmMembership(base, req);
		final Long planId = Long.parseLong(new RqHref.Smart(req).single("plan"));
		final Plan plan = module.plans().get(planId);
		final PlanFeature item = plan.features().get(id);
		
		XeSource xePlanFeature = new XePlanFeature("item", item);
		return new XeChain(
				xePlanFeature
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XePlanFeature(dir);
	}
}
