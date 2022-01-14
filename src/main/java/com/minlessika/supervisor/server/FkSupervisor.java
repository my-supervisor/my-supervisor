package com.minlessika.supervisor.server;

import java.io.IOException;

import org.takes.facets.auth.TkSecure;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkConsumes;

import com.minlessika.core.takes.BddVersion;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.AccessParam;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.ProfileAccess;
import com.minlessika.membership.domain.ProfileAccessParam;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BasicModule;
import com.minlessika.sdk.takes.FkModuleMimes;
import com.minlessika.sdk.takes.FkModule;
import com.minlessika.supervisor.domain.ActivityTemplateRelease;
import com.minlessika.supervisor.data.sharing.AggregatedModelShared;
import com.minlessika.supervisor.data.sharing.DataLinkShared;
import com.minlessika.supervisor.data.sharing.DataSheetShared;
import com.minlessika.supervisor.data.sharing.ListDataFieldSourceShared;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityParam;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.ActivityTemplateLike;
import com.minlessika.supervisor.domain.ActivityTemplatePublished;
import com.minlessika.supervisor.domain.ActivityTemplateSubscription;
import com.minlessika.supervisor.domain.ActivityTemplateView;
import com.minlessika.supervisor.domain.SimpleDataField;
import com.minlessika.supervisor.domain.SimpleDataFieldOfSheet;
import com.minlessika.supervisor.domain.EditableDataFieldArg;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataLinkParam;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldOfSheet;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ExpressionArg;
import com.minlessika.supervisor.domain.ExpressionValueArg;
import com.minlessika.supervisor.domain.FormularArg;
import com.minlessika.supervisor.domain.FormularCondition;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldArg;
import com.minlessika.supervisor.domain.MappedDataField;
import com.minlessika.supervisor.domain.ParamArg;
import com.minlessika.supervisor.domain.ModelFilter;
import com.minlessika.supervisor.domain.ModelFilterCondition;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularCaseExpression;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularExtendedToChildExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.FormularSimpleExpression;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.SharedResource;
import com.minlessika.supervisor.domain.Supervisor;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.TableDataFieldOfSheet;
import com.minlessika.supervisor.domain.TableDataFieldOfSheetRow;
import com.minlessika.supervisor.domain.ValueArg;
import com.minlessika.supervisor.domain.WhenCase;
import com.minlessika.supervisor.indicator.IndicatorTypeParam;
import com.minlessika.supervisor.indicator.IndicatorTypeStaticParam;
import com.minlessika.supervisor.indicator.IndicatorTemplate;
import com.minlessika.supervisor.indicator.IndicatorType;
import com.minlessika.supervisor.indicator.IndicatorTypeDynamicParam;
import com.minlessika.supervisor.indicator.BasePeriodicity;
import com.minlessika.supervisor.indicator.ChartCamembert;
import com.minlessika.supervisor.indicator.DynamicTable2Col;
import com.minlessika.supervisor.indicator.Gauge;
import com.minlessika.supervisor.indicator.GaugeZone;
import com.minlessika.supervisor.indicator.GoalNumber;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorDynamicNumberParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicStringParam;
import com.minlessika.supervisor.indicator.IndicatorStaticParam;
import com.minlessika.supervisor.indicator.NumberOriented;
import com.minlessika.supervisor.takes.*;
import com.minlessika.tk.TkPrevious;

public final class FkSupervisor extends FkModule {
	
	@SuppressWarnings("unchecked")
	public FkSupervisor(final Base base) throws IOException {
		super(
			base,
			new FkModuleMimes(
				  new BasicModule(Supervisor.NAME, base.appInfo()), 
				  new FkChain(
					  new FkRegex(
						  "/", 
						  new TkIndex(base)
					  ),
					  new FkRegex(
						  "/start", 
						  new TkStart(base)
					  ),
					  new FkRegex(
						  "/home", 
						  new TkSecure(new TkPrevious(new TkHome(base)) )
					  ),
					  new FkRegex(
						  "/no-activity", 
						  new TkSecure(new TkNoActivity(base))
					  ),
					  new FkRegex(
						  "/activity", 
						  new TkSecure(new TkActivityEdit(base))
					  ),
					  new FkRegex(
						  "/activity/period", 
						  new TkSecure(new TkActivityPeriodAtDate(base))
					  ),
					  new FkRegex(
						  "/activity/interaction", 
						  new TkSecure(new TkInteraction(base))
					  ),
					  new FkRegex(
						  "/activity/interaction/activate", 
						  new TkSecure(new TkInteractionActivate(base))
					  ),
					  new FkRegex(
						  "/activity/period/next", 
						  new TkSecure(new TkActivityNextPeriod(base))
					  ),
					  new FkRegex(
							  "/activity/period/previous", 
							  new TkSecure(new TkActivityPreviousPeriod(base))
						  ),					  
					  new FkRegex(
						  "/activity/save", 
						  new TkSecure(new TkActivitySave(base))
					  ),					  
					  new FkRegex(
						  "/activity/update/edit", 
						  new TkSecure(new TkActivityUpdateEdit(base))
					  ),					  
					  new FkRegex(
						  "/activity/update/apply", 
						  new TkSecure(new TkActivityUpdateApply(base))
					  ),
					  new FkRegex(
						  "/activity/default", 
						  new TkSecure(new TkActivityShowDefault(base))
					  ),
					  new FkRegex(
						  "/activity/delete", 
						  new TkSecure(new TkActivityDelete(base))
					  ),
					  new FkRegex(
						  "/activity/template", 
						  new TkSecure(new TkActivityTemplate(base))
					  ),
					  new FkRegex(
						  "/activity/template/edit", 
						  new TkSecure(new TkActivityTemplateEdit(base))
					  ),
					  new FkRegex(
						  "/activity/template/delete", 
						  new TkSecure(new TkActivityTemplateDelete(base))
					  ),
					  new FkRegex(
						  "/activity/template/generate", 
						  new TkSecure(new TkActivityTemplateGenerate(base))
					  ),
					  new FkRegex(
						  "/activity/template/create-activity/edit", 
						  new TkSecure(new TkActivityBaseOnTemplateEdit(base))
					  ),
					  new FkRegex(
						  "/activity/template/release/edit", 
						  new TkSecure(new TkActivityTemplateReleaseEdit(base))
					  ),
					  new FkRegex(
						  "/activity/template/release/save", 
						  new TkSecure(new TkActivityTemplateReleaseSave(base))
					  ),
					  new FkRegex(
						  "/activity/template/create-activity/save", 
						  new TkSecure(new TkActivityBaseOnTemplateSave(base))
					  ),
					  new FkRegex(
						  "/activity/template/change-designer/edit", 
						  new TkSecure(new TkActivityTemplateNewDesignerEdit(base))
					  ),
					  new FkRegex(
						  "/activity/template/change-designer/save", 
						  new TkSecure(new TkActivityTemplateNewDesignerSave(base))
					  ),
					  new FkRegex(
						  "/collect/field/edit", 
						  new TkSecure(new TkDataFieldEdit(base))
					  ),
					  new FkRegex(
						  "/collect/field/simple/edit", 
						  new TkSecure(new TkSimpleDataFieldEdit(base))
					  ),
					  new FkRegex(
						  "/collect/field/simple/save", 
						  new TkSecure(new TkSimpleDataFieldSave(base))
					  ),
					  new FkRegex(
						  "/collect/field/list/edit", 
						  new TkSecure(new TkListDataFieldEdit(base))
					  ),
					  new FkRegex(
						  "/collect/field/list/save", 
						  new TkSecure(new TkListDataFieldSave(base))
					  ),
					  new FkRegex(
						  "/collect/field/list/source/edit", 
						  new TkSecure(new TkListDataFieldSourceEdit(base))
					  ),
					  new FkRegex(
						  "/collect/field/list/source/save", 
						  new TkSecure(new TkListDataFieldSourceSave(base))
					  ),
					  new FkRegex(
						  "/collect/field/list/source/activate", 
						  new TkSecure(new TkListDataFieldSourceActivate(base))
					  ),
					  new FkRegex(
						  "/collect/field/list/source/delete", 
						  new TkSecure(new TkListDataFieldSourceDelete(base))
					  ),
					  new FkRegex(
						  "/collect/field/save", 
						  new TkSecure(new TkDataFieldSave(base))
					  ),
					  new FkRegex(
						  "/collect/field/delete", 
						  new TkSecure(new TkDataFieldDelete(base))
					  ),
					  new FkRegex(
						  "/collect/table/edit", 
						  new TkSecure(new TkDataFieldTableEdit(base))
					  ),
					  new FkRegex(
						  "/collect/table/save", 
						  new TkSecure(new TkDataFieldTableSave(base))
					  ),
					  new FkRegex(
						  "/collect/model", 
						  new TkSecure(new TkDataSheetModel(base))
					  ),
					  new FkRegex(
						  "/collect/model/edit", 
						  new TkSecure(new TkDataSheetModelEdit(base))
					  ),
					  new FkRegex(
						  "/collect/model/save", 
						  new TkSecure(new TkDataSheetModelSave(base))
					  ),
					  new FkRegex(
						  "/collect/model/delete", 
						  new TkSecure(new TkDataSheetModelDelete(base))
					  ),
					  new FkRegex(
						  "/collect/model/data/import/edit", 
						  new TkSecure(new TkImportDataSheetEdit(base))
					  ),
					  new FkRegex(
						  "/collect/model/data/import/save", 
						  new TkSecure(new TkImportDataSheetSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model", 
						  new TkSecure(new TkAggregatedModel(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/edit", 
						  new TkSecure(new TkAggregatedModelEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/save", 
						  new TkSecure(new TkAggregatedModelSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/delete", 
						  new TkSecure(new TkAggregatedModelDelete(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/edit", 
						  new TkSecure(new TkFormularEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/save", 
						  new TkSecure(new TkFormularSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/delete", 
						  new TkSecure(new TkFormularDelete(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/simple-expression/edit", 
						  new TkSecure(new TkFormularSimpleExpressionEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/simple-expression/save", 
						  new TkSecure(new TkFormularSimpleExpressionSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/case-expression/edit", 
						  new TkSecure(new TkFormularCaseExpressionEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/case-expression/save", 
						  new TkSecure(new TkFormularCaseExpressionSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/case-expression/when-case/save", 
						  new TkSecure(new TkWhenCaseSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/case-expression/when-case/comparison/edit", 
						  new TkSecure(new TkWhenCaseComparatorEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/case-expression/when-case/comparison/save", 
						  new TkSecure(new TkWhenCaseComparatorSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/case-expression/when-case/delete", 
						  new TkSecure(new TkWhenCaseDelete(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-parent-expression/edit", 
						  new TkSecure(new TkFormularExtendedToParentExpressionEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-parent-expression/save", 
						  new TkSecure(new TkFormularExtendedToParentExpressionSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-parent-expression/source/edit", 
						  new TkSecure(new TkFormularExtendedToParentSourceEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-parent-expression/source/save", 
						  new TkSecure(new TkFormularExtendedToParentSourceSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-parent-expression/source/delete", 
						  new TkSecure(new TkFormularExtendedToParentSourceDelete(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-child-expression/edit", 
						  new TkSecure(new TkFormularExtendedToChildExpressionEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-child-expression/save", 
						  new TkSecure(new TkFormularExtendedToChildExpressionSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/expression/argument/edit", 
						  new TkSecure(new TkExpressionArgEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/expression/argument/save", 
						  new TkSecure(new TkExpressionArgSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/simple-expression/transform", 
						  new TkSecure(new TkFormularSimpleExpressionTransformFunction(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/expression/delete", 
						  new TkSecure(new TkFormularExpressionDelete(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/expression/organize", 
						  new TkSecure(new TkFormularExpressionOrganize(base))
					  ),
					  new FkRegex(
						  "/collect/sheet", 
						  new TkSecure(new TkDataSheet(base))
					  ),
					  new FkRegex(
						  "/collect/sheet/edit", 
						  new TkSecure(new TkDataSheetEdit(base))
					  ),
					  new FkRegex(
						  "/collect/sheet/edit-by-ref", 
						  new TkSecure(new TkDataSheetEditByRef(base))
					  ),
					  new FkRegex(
						  "/collect/sheet/save", 
						  new TkSecure(new TkDataSheetSave(base))
					  ),
					  new FkRegex(
						  "/collect/sheet/delete", 
						  new TkSecure(new TkDataSheetDelete(base))
					  ),
					  new FkRegex(
						  "/indicator/type", 
						  new TkSecure(new TkIndicatorType(base))
					  ),					  
					  new FkRegex(
						  "/number-oriented-setting/edit", 
						  new TkSecure(new TkNumberOrientedSettingEdit(base))
					  ),
					  new FkRegex(
						  "/chart-camembert-setting/edit", 
						  new TkSecure(new TkChartCamembertSettingEdit(base))
					  ),
					  new FkRegex(
						  "/gauge-setting/edit", 
						  new TkSecure(new TkGaugeSettingEdit(base))
					  ),
					  new FkRegex(
						  "/goal-number-setting/edit", 
						  new TkSecure(new TkGoalNumberSettingEdit(base))
					  ),
					  new FkRegex(
						  "/dynamic-table-2-col-setting/edit", 
						  new TkSecure(new TkDynamicTable2ColSettingEdit(base))
					  ),
					  new FkRegex(
						  "/number-oriented-setting/save", 
						  new TkSecure(new TkNumberOrientedSettingSave(base))
					  ),
					  new FkRegex(
						  "/chart-camembert-setting/save", 
						  new TkSecure(new TkChartCamembertSettingSave(base))
					  ),
					  new FkRegex(
						  "/gauge-setting/save", 
						  new TkSecure(new TkGaugeSettingSave(base))
					  ),
					  new FkRegex(
						  "/goal-number-setting/save", 
						  new TkSecure(new TkGoalNumberSettingSave(base))
					  ),
					  new FkRegex(
						  "/dynamic-table-2-col-setting/save", 
						  new TkSecure(new TkDynamicTable2ColSettingSave(base))
					  ),	
					  new FkRegex(
						  "/indicator/delete", 
						  new TkSecure(new TkIndicatorDelete(base))
					  ),	
					  new FkRegex(
						  "/indicator/dynamic-param/edit", 
						  new TkSecure(new TkIndicatorDynamicParamEdit(base))
					  ),	
					  new FkRegex(
						  "/indicator/dynamic-param/save", 
						  new TkSecure(new TkIndicatorDynamicParamSave(base))
					  ),
					  new FkRegex(
						  "/indicator/number-oriented", 
						  new TkSecure(new TkNumberOriented(base))
					  ),
					  new FkRegex(
						  "/indicator/goal-number", 
						  new TkSecure(new TkGoalNumber(base))
					  ),
					  new FkRegex(
						  "/indicator/chart-camembert", 
						  new TkSecure(new TkChartCamembert(base))
					  ),	
					  new FkRegex(
						  "/indicator/gauge", 
						  new TkSecure(new TkGauge(base))
					  ),
					  new FkRegex(
						  "/indicator/dynamic-table-2-col", 
						  new TkSecure(new TkDynamicTable2Col(base))
					  ),
					  new FkRegex(
						  "/activity/indic/data-link/edit", 
						  new TkSecure(new TkDataLinkEdit(base))
					  ),					  
					  new FkRegex(
						  "/activity/indic/data-link/save", 
						  new TkSecure(new TkDataLinkSave(base))
					  ),					  
					  new FkRegex(
						  "/activity/indic/data-link/delete", 
						  new TkSecure(new TkDataLinkDelete(base))
					  ),					  					  					 
					  new FkRegex(
						  "/sharing", 
						  new TkSecure(new TkSharing(base))
					  ),					  
					  new FkRegex(
						  "/sharing/edit", 
						  new TkSecure(new TkSharingEdit(base))
					  ),					  
					  new FkRegex(
						  "/sharing/share", 
						  new TkSecure(new TkSharingSave(base))
					  ),					  
					  new FkRegex(
						  "/sharing/unshare", 
						  new TkSecure(new TkSharingDelete(base))
					  ),					  
					  new FkRegex(
						  "/sharing/subscriber/unshare", 
						  new TkSecure(new TkSharingSubscriberDelete(base))
					  ),					  					  					  
					  new FkRegex(
						  "/activity/organize", 
						  new TkSecure(
							  new TkConsumes(new TkActivityOrganize(base), "application/json")
						  )
					  ),	
					  new FkRegex(
						  "/list-data-field/values/search",
						  new TkSecure(new TkListDataFieldValueSearchable(base))
					  ),
					  new FkRegex(
						  "/indicator/list", 
						  new TkSecure(new TkIndicator(base))
					  ),					  
					  new FkRegex(
						  "/store",
						  new TkSecure(new TkActivityStore(base))
					  ),
					  new FkRegex(
						  "/activity/template/publish/edit",
						  new TkSecure(new TkActivityTemplatePublishedEdit(base))
					  ),
					  new FkRegex(
						  "/activity/template/publish/save",
						  new TkSecure(new TkActivityTemplatePublishedSave(base))
					  ),
					  new FkRegex(
						  "/activity/template/published/obsolete",
						  new TkSecure(new TkActivityTemplatePublishedObsolete(base))
					  ),
					  new FkRegex(
						  "/activity/template/published/available",
						  new TkSecure(new TkActivityTemplatePublishedAvailable(base))
					  ),
					  new FkRegex(
						  "/activity/template/published/search",
						  new TkSecure(new TkActivityTemplatePublishedSearchable(base))
					  ),
					  new FkRegex(
						  "/activity/template/published/visualise",
						  new TkSecure(new TkActivityTemplatePublishedVisualized(base))
					  ),
					  new FkRegex(
						  "/activity/template/published/like",
						  new TkSecure(new TkActivityTemplatePublishedLike(base))
					  ),
					  new FkRegex(
						  "/activity/template/published/download",
						  new TkSecure(new TkActivityTemplatePublishedDowload(base))
					  ),
					  new FkRegex(
						  "/pricing",
						  new TkSecure(new TkPricing(base))
					  ),
					  new FkRegex(
						  "/pricing/subscription/delay",
						  new TkSecure(new TkPricingSubscriptionDelayEdit(base))
					  ),
					  new FkRegex(
						  "/plan/subscription/order",
						  new TkSecure(new TkPlanSubscriptionOrder(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-model-expression/edit", 
						  new TkSecure(new TkFormularExtendedToModelExpressionEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-model-expression/new", 
						  new TkSecure(new TkFormularExtendedToModelExpressionNew(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-model-expression/aggregate/edit", 
						  new TkSecure(new TkFormularExtendedToModelExpressionAggregateEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-model-expression/aggregate/save", 
						  new TkSecure(new TkFormularExtendedToModelExpressionAggregateSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-model-expression/source/edit", 
						  new TkSecure(new TkFormularExtendedToModelSourceEdit(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-model-expression/source/save", 
						  new TkSecure(new TkFormularExtendedToModelSourceSave(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-model-expression/source/activate", 
						  new TkSecure(new TkFormularExtendedToModelSourceActivate(base))
					  ),
					  new FkRegex(
						  "/collect/aggregated-model/formular/extended-to-model-expression/source/delete", 
						  new TkSecure(new TkFormularExtendedToModelSourceDelete(base))
					  ),
					  new FkRegex(
						  "/activity/notify", 
						  new TkNotifyActivity(base)
					  )					  
				  )
			),
			BddVersion.class,
			Access.class,
			AccessParam.class,
			Profile.class,
			ProfileAccess.class,
			ProfileAccessParam.class,
			Application.class,
			BasePeriodicity.class,
			Activity.class,
			DataModel.class,
			DataSheetModel.class,
			DataField.class,
			EditableDataField.class,
			SimpleDataField.class,
			TableDataField.class,			
			DataSheet.class,
			DataSheetShared.class,
			DataFieldOfSheet.class,	
			SimpleDataFieldOfSheet.class,
			TableDataFieldOfSheet.class,
			TableDataFieldOfSheetRow.class,
			IndicatorType.class,
			IndicatorTypeParam.class,
			IndicatorTypeStaticParam.class,
			IndicatorTypeDynamicParam.class,			
			ActivityTemplate.class,
			ActivityTemplatePublished.class,
			ActivityTemplateRelease.class,
			ActivityTemplateSubscription.class,
			ActivityTemplateLike.class,
			ActivityTemplateView.class,
			Indicator.class,
			IndicatorStaticParam.class,
			IndicatorDynamicParam.class,
			IndicatorDynamicStringParam.class,
			IndicatorDynamicNumberParam.class,
			IndicatorTemplate.class,			
			NumberOriented.class,
			ChartCamembert.class,
			Gauge.class,
			GaugeZone.class,
			GoalNumber.class,
			DynamicTable2Col.class,
			AggregatedModel.class,
			AggregatedModelShared.class,
			ParamDataField.class,
			FormularDataField.class,
			FormularCondition.class,
			FormularExpression.class,
			ExpressionArg.class,
			DataFieldArg.class,
			ValueArg.class,
			ParamArg.class,
			ExpressionValueArg.class,
			EditableDataFieldArg.class,
			FormularArg.class,
			FormularSimpleExpression.class,
			FormularCaseExpression.class,
			FormularExtendedToParentExpression.class,
			FormularExtendedToParentSource.class,
			FormularExtendedToModelExpression.class,
			FormularExtendedToModelSource.class,
			FormularExtendedToChildExpression.class,
			WhenCase.class,
			ModelFilter.class,
			ModelFilterCondition.class,
			ListDataField.class,
			ListDataFieldSource.class,
			ListDataFieldSourceShared.class,
			ListDataFieldOfSheet.class,
			DataLink.class,
			DataLinkShared.class,	
			MappedDataField.class,
			DataLinkParam.class,
			ActivityParam.class,
			SharedResource.class
		);
	}
}
