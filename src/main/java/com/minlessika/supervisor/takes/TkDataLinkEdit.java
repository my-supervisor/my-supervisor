package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.UserDataModels;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.xe.XeDataDomainDefinition;
import com.minlessika.supervisor.xe.XeDataField;
import com.minlessika.supervisor.xe.XeDataLink;
import com.minlessika.supervisor.xe.XeDataLinkOperator;
import com.minlessika.supervisor.xe.XeDataLinkParam;
import com.minlessika.supervisor.xe.XeDataModel;
import com.minlessika.supervisor.xe.XeIndicatorSetting;
import com.minlessika.supervisor.xe.XeMappedField;
import com.minlessika.supervisor.xe.XeRuleParam;
import com.minlessika.supervisor.xe.XeSupervisor;

public final class TkDataLinkEdit extends TkForm {

	public TkDataLinkEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/com/supervisor/xsl/data_link_edit.xsl";
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		UserDataModels models = module.dataModels().where(DataModel::isTemplate, false);
		
		XeSource xeDataFields = XeSource.EMPTY;
		XeSource xeParams = XeSource.EMPTY;
		for (DataModel model : models.items()) {
			xeDataFields = new XeChain(
								xeDataFields,
								new XeDataField(model.fields(), model)
						   );
			
			xeParams = new XeChain(
					xeParams,
					new XeRuleParam(model.fields().params())
			   );
		}
		
		final Long indicId = Long.parseLong(new RqHref.Smart(req).single("indic"));

		final String source = new RqHref.Smart(req).single("source");
		final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
		final Activity activity = module.activities().get(activityId);
		final Indicator indic = activity.indicators().get(indicId);
		
		return new XeChain(
					new XeMappedField(indic.generateMappedFields()),
					new XeDataModel(models.items()),
					xeDataFields,
					xeParams
				);
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		final Long indicId = Long.parseLong(new RqHref.Smart(req).single("indic"));

		final String source = new RqHref.Smart(req).single("source");
		final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
		final Activity activity = module.activities().get(activityId);
		final Indicator indic = activity.indicators().get(indicId);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "activities"));
		
		content.add(new XeSupervisor(module));
		content.add(new XeIndicatorSetting(indic));
		content.add(new XeDataLinkOperator());
		content.add(new XeDataDomainDefinition());
		content.add(itemToShow);
		content.add(new XeAppend("source", new RqHref.Smart(req).single("source")));
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		final Long indicId = Long.parseLong(new RqHref.Smart(req).single("indic"));

		final String source = new RqHref.Smart(req).single("source");
		final Long activityId = Long.parseLong(StringUtils.remove(source, "activity"));
		final Activity activity = module.activities().get(activityId);
		Indicator indic = activity.indicators().get(indicId);		
		DataLink item = indic.links().get(id);
		DataModel model = item.model();
		
		return new XeChain(
				new XeMappedField(item.mappings()),
				new XeDataLink("item", item),
				new XeDataLinkParam(item.params()),
				new XeDataModel(Arrays.asList(model)),
				new XeDataField(model.fields(), model),
				new XeRuleParam(model.fields().params())
		);
	}

	@Override
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		if(id == 0)
			return newItemToShow(req);
		else
			return preItemDataToShow(id, req);
	}	
}