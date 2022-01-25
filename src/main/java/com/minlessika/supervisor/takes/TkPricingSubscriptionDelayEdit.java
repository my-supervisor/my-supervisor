package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.xe.XePlan;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkPricingSubscriptionDelayEdit extends TkForm {

	public TkPricingSubscriptionDelayEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/plan_subscription_delay_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		Long planId = Long.parseLong(new RqHref.Smart(req).single("plan"));
		Plan plan = module.membership().plans().get(planId);
		XeSource xePlan = new XePlan(plan);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeSupervisor(module));
		content.add(xePlan);
		content.add(new XeAppend("currency", plan.catalog().currency().code()));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return new XeAppend(
				"item", 
				new XeAppend("delay", "12")
		);
	}
	
	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		return XeSource.EMPTY;
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeDirectives(dir);
	}	
}
