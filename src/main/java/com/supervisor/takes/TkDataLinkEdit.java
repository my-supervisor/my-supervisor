package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.utils.OptUUID;
import org.apache.commons.lang.StringUtils;
import org.takes.Request;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.Activity;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.UserDataModels;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.indicator.Indicator;
import com.supervisor.xe.XeDataDomainDefinition;
import com.supervisor.xe.XeDataField;
import com.supervisor.xe.XeDataLink;
import com.supervisor.xe.XeDataLinkOperator;
import com.supervisor.xe.XeDataLinkParam;
import com.supervisor.xe.XeDataModel;
import com.supervisor.xe.XeIndicatorSetting;
import com.supervisor.xe.XeMappedField;
import com.supervisor.xe.XeRuleParam;
import com.supervisor.xe.XeSupervisor;

public final class TkDataLinkEdit extends TkForm {

	public TkDataLinkEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/data_link_edit.xsl";
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
		
		final UUID indicId = UUID.fromString(new RqHref.Smart(req).single("indic"));

		final String source = new RqHref.Smart(req).single("source");
		final UUID activityId = UUID.fromString(StringUtils.remove(source, "activity"));
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
		final UUID indicId = UUID.fromString(new RqHref.Smart(req).single("indic"));

		final String source = new RqHref.Smart(req).single("source");
		final UUID activityId = UUID.fromString(StringUtils.remove(source, "activity"));
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
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		final UUID indicId = UUID.fromString(new RqHref.Smart(req).single("indic"));

		final String source = new RqHref.Smart(req).single("source");
		final UUID activityId = UUID.fromString(StringUtils.remove(source, "activity"));
		final Activity activity = module.activities().get(activityId);
		Indicator indic = activity.indicators().get(indicId);		
		DataLink item = indic.links().get(id.value());
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
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		if(id.isEmpty())
			return newItemToShow(req);
		else
			return preItemDataToShow(id, req);
	}	
}
