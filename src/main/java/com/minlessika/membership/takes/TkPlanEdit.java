package com.minlessika.membership.takes;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.impl.DmMembership;
import com.minlessika.membership.domain.impl.PxProfiles;
import com.minlessika.membership.xe.XePlan;
import com.minlessika.membership.xe.XePlanFeature;
import com.minlessika.membership.xe.XeProfile;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkPlanEdit extends TkForm {


	public TkPlanEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/membership/xsl/plan_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeProfile(new PxProfiles(base, "supervisor")));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		final Membership module = new DmMembership(base, req);
		final Plan plan = module.plans().get(id);
		
		XeSource xePlan = new XePlan("item", plan);
		XeSource xeFeatures = new XePlanFeature(plan.features().items());
		return new XeChain(
				xePlan,
				xeFeatures
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XePlanFeature(dir);
	}
}
