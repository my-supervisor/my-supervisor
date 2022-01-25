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
import com.supervisor.indicator.DynamicTable2Col;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.xe.XeActivity;
import com.supervisor.xe.XeDataLink;
import com.supervisor.xe.XeDynamicTable2ColSetting;
import com.supervisor.xe.XeIndicatorDynamicParam;
import com.supervisor.xe.XeIndicatorType;
import com.supervisor.xe.XeOrderingMode;
import com.supervisor.xe.XePeriodicity;
import com.supervisor.xe.XeSupervisor;

public final class TkDynamicTable2ColSettingEdit extends TkForm {

	public TkDynamicTable2ColSettingEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/indicators/dynamic_table_2_col_setting_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "activities"));
		content.add(new XeAppend("chart_name", module.indicatorTypes().indicatorType(IndicatorType.DYNAMIC_TABLE_2_COL).name()));
		content.add(new XePeriodicity());
		content.add(new XeOrderingMode());
		content.add(new XeSupervisor(module));
		content.add(itemToShow);
		content.add(new XeAppend("cancel_url", IndicatorSource.url(req)));
		content.add(new XeAppend("source", new RqHref.Smart(req).single("source")));
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		final String source = new RqHref.Smart(req).single("source");
		final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
		final Activity activity = module.activities().get(activityId);
		final DynamicTable2Col indic = (DynamicTable2Col)activity.indicators().get(id);
						
		return new XeChain(
				new XeDynamicTable2ColSetting("item", indic),
				new XeActivity(activity),
				new XeDataLink(indic.links()),
				new XeIndicatorDynamicParam(indic.dynamicParams())
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		
		String source = new RqHref.Smart(req).single("source");
		final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
		final Activity activity = module.activities().get(activityId);
			
		XeSource xeDataRule = XeSource.EMPTY;
		XeSource xeParam = XeSource.EMPTY;
		if(id > 0) {
			final DynamicTable2Col indic = (DynamicTable2Col)activity.indicators().get(id);
			xeDataRule = new XeDataLink(indic.links());
			xeParam = new XeIndicatorDynamicParam(indic.dynamicParams());
		}
	
		return new XeChain(
				new XeDynamicTable2ColSetting(dir),
				new XeActivity(activity),
				xeDataRule,
				xeParam
		);
	}	
	
	@Override
	protected XeSource newItemToShow(Request req) throws IOException {
		
		Long activityId = Long.parseLong(new RqHref.Smart(req).single("activity", "0"));
		if(activityId == 0)
			throw new IllegalArgumentException("Vous devez spécifier l'activité pour lequel vous créer cet indicateur !");
		
		final Supervisor module = new PxSupervisor(base, req);
		
		Activity activity = module.activities().get(activityId);
		
		return new XeChain(
				new XeIndicatorType(module.indicatorTypes().indicatorType(IndicatorType.DYNAMIC_TABLE_2_COL)), 
				new XeActivity(activity)
		);
	}
}
