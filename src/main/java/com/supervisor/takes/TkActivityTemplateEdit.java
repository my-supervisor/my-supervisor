package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.utils.ListOfUniqueRecord;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.ActivityTemplates;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivity;
import com.supervisor.xe.XeActivityParam;
import com.supervisor.xe.XeActivityTemplate;
import com.supervisor.xe.XeActivityTemplateRelease;
import com.supervisor.xe.XePeriodicity;
import com.supervisor.xe.XeRuleParam;
import com.supervisor.xe.XeSupervisor;

public final class TkActivityTemplateEdit extends TkForm {

	public TkActivityTemplateEdit(Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/activity_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		List<XeSource> content = new ArrayList<>();
		content.add(new XePeriodicity());
		content.add(new XeAppend("menu", "activities"));
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(OptUUID id, Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		ActivityTemplates myActivities = module.activityTemplates();
		ActivityTemplate act = myActivities.get(id.value());
		
		if(new RqUser(base, req).notOwn(act)) {
			throw new IllegalArgumentException("Vous ne pouvez pas éditer une activité partagée !");
		}
		
		List<ParamDataField> params = new ListOfUniqueRecord<>();
		for (AggregatedModel model : act.aggregatedModels().items()) {
			params.addAll(model.params().items());
		}
		
		params.addAll(act.params().items());
		
		return new XeChain(
				new XeActivityTemplate("item", act),
				new XeRuleParam(params),
				new XeActivityParam(act.params()),
				new XeActivityTemplateRelease(act.releases()) 
		);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		if(id.isPresent()) {
			
			final Supervisor module = new PxSupervisor(base, req);
			ActivityTemplates myActivities = module.activityTemplates();
			ActivityTemplate act = myActivities.get(id.value());
			
			if(new RqUser(base, req).notOwn(act)) {
				throw new IllegalArgumentException("Vous ne pouvez pas éditer une activité partagée !");
			}
			
			XeSource xeRuleParams = XeSource.EMPTY;
			for (AggregatedModel model : act.aggregatedModels().items()) {
				xeRuleParams = new XeChain(
									xeRuleParams,
									new XeRuleParam(model.params())
							   );
			}
			
			return new XeChain(
					new XeActivityTemplate(dir),
					xeRuleParams,
					new XeActivityParam(act.params()),
					new XeActivityTemplateRelease(act.releases()) 
			);
			
		}else {
			return new XeActivity(dir);
		}	
	}
}
