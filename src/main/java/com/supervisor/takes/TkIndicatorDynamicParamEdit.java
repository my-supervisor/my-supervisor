package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.apache.commons.lang.StringUtils;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParam;
import com.supervisor.xe.XeAggregateFunc;
import com.supervisor.xe.XeIndicatorDynamicParam;
import com.supervisor.xe.XeSupervisor;

public final class TkIndicatorDynamicParamEdit extends TkForm {

	public TkIndicatorDynamicParamEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/indicators/indicator_dynamic_param_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "indicators"));
		content.add(new XeAggregateFunc());
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final String source = new RqHref.Smart(req).single("source");
		final String shortName = new RqHref.Smart(req).single("short_name");
		final Long indicId = Long.parseLong(new RqHref.Smart(req).single("indic"));
		
		final Supervisor module = new PxSupervisor(base, req);
		final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
		final Activity activity = module.activities().get(activityId);
		Indicator indic = activity.indicators().get(indicId);
		IndicatorDynamicParam item = indic.dynamicParams().get(id);
				
		return new XeChain( 
			new XeAppend("short_name", shortName),
			new XeAppend("source", source),
			new XeIndicatorDynamicParam("item", item)
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeIndicatorDynamicParam(dir);
	}	
}
