package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.minlessika.membership.takes.RqUser;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.sdk.utils.ListOfUniqueRecord;
import com.minlessika.supervisor.domain.Activities;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeActivity;
import com.minlessika.supervisor.xe.XeActivityParam;
import com.minlessika.supervisor.xe.XePeriodicity;
import com.minlessika.supervisor.xe.XeRuleParam;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkActivityEdit extends TkForm {

	public TkActivityEdit(Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/supervisor/xsl/activity_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(Request req, XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		List<XeSource> content = new ArrayList<>();
		content.add(new XePeriodicity());
		content.add(new XeAppend("menu", "activities"));
		content.add(new XeAppend("submenu", "new_activity"));
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(Long id, Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		Activities myActivities = module.activities();
		Activity act = myActivities.get(id);
		
		if(new RqUser(base, req).notOwn(act)) {
			throw new IllegalArgumentException("Vous ne pouvez pas éditer une activité partagée !");
		}
		
		List<ParamDataField> params = new ListOfUniqueRecord<>();		
		for (AggregatedModel model : act.aggregatedModels().items()) {
			params.addAll(model.params().items());
		}
		
		params.addAll(act.params().items());
		
		return new XeChain(
				new XeActivity("item", act),
				new XeRuleParam(params),
				new XeActivityParam(act.params()) 
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		if(id > 0) {
			
			final Supervisor module = new PxSupervisor(base, req);
			Activities myActivities = module.activities();
			Activity act = myActivities.get(id);
			
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
					new XeActivity(dir),
					xeRuleParams,
					new XeActivityParam(act.params()) 
			);
			
		}else {
			return new XeActivity(dir);
		}	
	}
}
