package com.minlessika.supervisor.takes;

import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkBaseWrap;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.bi.AggregateFunc;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorDynamicNumberParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicStringParam;
import com.minlessika.supervisor.indicator.impl.PxIndicatorDynamicNumberParam;
import com.minlessika.supervisor.indicator.impl.PxIndicatorDynamicStringParam;

public final class TkIndicatorDynamicParamSave extends TkBaseWrap {

	public TkIndicatorDynamicParamSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
						
					final Long indicatorId = Long.parseLong(form.single("indicator_id"));		
					final String source = new RqHref.Smart(req).single("source");
					final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
					final Activity activity = module.activities().get(activityId);
					Indicator indicator = activity.indicators().get(indicatorId);		
					
					final Long id = Long.parseLong(new RqHref.Smart(req).single("id"));
					IndicatorDynamicParam itemSaved = indicator.dynamicParams().get(id);
					
					switch (itemSaved.type()) {
						case NUMBER:
							final int precision = Integer.parseInt(form.single("precision"));
							final boolean applyThousandsSeparator = Boolean.parseBoolean(form.single("apply_thousands_separator"));
							final AggregateFunc func = AggregateFunc.valueOf(form.single("aggregate_func_id"));
							
							IndicatorDynamicNumberParam numberParam = new PxIndicatorDynamicNumberParam(itemSaved);
							numberParam.update(func); 
							numberParam.update(precision, applyThousandsSeparator); 
							break;
						case STRING:
							final String markup = form.single("markup", StringUtils.EMPTY);
							IndicatorDynamicStringParam stringParam = new PxIndicatorDynamicStringParam(itemSaved);
							stringParam.update(markup);
							break;
						default:
							break;
					}
					
					return new RsForward(
						new RsFlash(
							String.format("Le paramètre %s a été modifié avec succès !", itemSaved.name()),
			                Level.FINE
			            ),
						String.format("/%s-setting/edit?id=%s&shortname=%s&source=%s", indicator.type().shortName(), indicator.id(), indicator.type().shortName(), source)
				    );
				}
		);
	}
}
