package com.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import com.supervisor.sdk.utils.OptUUID;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.UserAggregatedModels;
import com.supervisor.domain.UserDataModels;
import com.supervisor.domain.impl.DataFieldSortingOnDependency;
import com.supervisor.domain.impl.PxSupervisor;
import com.supervisor.xe.XeActivity;
import com.supervisor.xe.XeAggregatedModel;
import com.supervisor.xe.XeComparator;
import com.supervisor.xe.XeDataField;
import com.supervisor.xe.XeDataFieldType;
import com.supervisor.xe.XeDataModel;
import com.supervisor.xe.XeEditableDataField;
import com.supervisor.xe.XeRuleFilter;
import com.supervisor.xe.XeRuleFormular;
import com.supervisor.xe.XeRuleParam;
import com.supervisor.xe.XeSupervisor;

public final class TkAggregatedModelEdit extends TkForm {

	public TkAggregatedModelEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/aggregated_model_edit.xsl";
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		final Supervisor module = new PxSupervisor(base, req);
		final Activities activities = module.activities().ownActivities().where(Activity::isTemplate, false);
		final UserDataModels models = module.dataModels().where(DataModel::interacting, false).where(DataModel::isTemplate, false);
		
		return new XeChain(
			new XeDataModel(models.items()),
			new XeActivity("user_activities", activities) 
		);
	}
	
	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
				
		final List<XeSource> content = new ArrayList<>();
		content.add(new XeAppend("menu", "collect"));
		
		content.add(new XeSupervisor(module));
		content.add(new XeComparator());
		content.add(itemToShow);
		content.add(new XeDataFieldType());
		
		return content;
	}

	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		final UserAggregatedModels items = module.aggregatedModels();
		final AggregatedModel item = items.get(id.value());

		return new XeChain(
			new XeAggregatedModel("item", item),
			new XeRuleFilter(item.filters()),
			new XeDataField(
				"date_datafields", 
				item.fields().where(DataField::type, DataFieldType.DATE).items(), 
				item
			),
			new XeEditableDataField(item.baseFields()),
			new XeRuleFormular(
				new DataFieldSortingOnDependency(item.formulars().items().stream().map(c -> (DataField)c).collect(Collectors.toList()))
					.items()
					.stream()
					.map(c -> (FormularDataField)c)
					.collect(Collectors.toList())
			),
			new XeRuleParam(item.params())
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
