package com.minlessika.supervisor.takes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.TkForm;
import com.minlessika.supervisor.domain.Activities;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.UserAggregatedModels;
import com.minlessika.supervisor.domain.UserDataModels;
import com.minlessika.supervisor.domain.impl.DataFieldSortingOnDependency;
import com.minlessika.supervisor.domain.impl.PxSupervisor;
import com.minlessika.supervisor.xe.XeActivity;
import com.minlessika.supervisor.xe.XeAggregatedModel;
import com.minlessika.supervisor.xe.XeComparator;
import com.minlessika.supervisor.xe.XeDataField;
import com.minlessika.supervisor.xe.XeDataFieldType;
import com.minlessika.supervisor.xe.XeDataModel;
import com.minlessika.supervisor.xe.XeEditableDataField;
import com.minlessika.supervisor.xe.XeRuleFilter;
import com.minlessika.supervisor.xe.XeRuleFormular;
import com.minlessika.supervisor.xe.XeRuleParam;
import com.minlessika.supervisor.xe.XeSupervisor;

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
	protected XeSource preItemDataToShow(final Long id, final Request req) throws IOException {
		
		final Supervisor module = new PxSupervisor(base, req);
		final UserAggregatedModels items = module.aggregatedModels();
		final AggregatedModel item = items.get(id);

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
	protected XeSource postItemDataToShow(Long id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		if(id == 0)
			return newItemToShow(req);
		else
			return preItemDataToShow(id, req);
	}	
}
