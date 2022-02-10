package com.supervisor.takes;

import com.supervisor.domain.Membership;
import com.supervisor.domain.Plan;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.domain.impl.PxProfiles;
import com.supervisor.sdk.utils.OptUUID;
import com.supervisor.xe.XePlan;
import com.supervisor.xe.XePlanFeature;
import com.supervisor.xe.XeProfile;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
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
		return "/xsl/plan_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		new RqAdminAuth(base, req);
		
		List<XeSource> content = new ArrayList<>();
		content.add(new XeProfile(new PxProfiles(base)));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		final Membership module = new DmMembership(base, req);
		final Plan plan = module.plans().get(id.get());
		
		XeSource xePlan = new XePlan("item", plan);
		XeSource xeFeatures = new XePlanFeature(plan.features().items());
		return new XeChain(
				xePlan,
				xeFeatures
		);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XePlanFeature(dir);
	}
}
