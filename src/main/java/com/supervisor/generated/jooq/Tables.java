/*
 * This file is generated by jOOQ.
 */
package com.supervisor.generated.jooq;


import com.supervisor.generated.jooq.tables.Access;
import com.supervisor.generated.jooq.tables.AccessParam;
import com.supervisor.generated.jooq.tables.Activity;
import com.supervisor.generated.jooq.tables.ActivityParam;
import com.supervisor.generated.jooq.tables.ActivityTemplateLike;
import com.supervisor.generated.jooq.tables.ActivityTemplatePublished;
import com.supervisor.generated.jooq.tables.ActivityTemplateRelease;
import com.supervisor.generated.jooq.tables.ActivityTemplateSubscription;
import com.supervisor.generated.jooq.tables.ActivityTemplateView;
import com.supervisor.generated.jooq.tables.Address;
import com.supervisor.generated.jooq.tables.AggregatedModel;
import com.supervisor.generated.jooq.tables.AggregatedModelShared;
import com.supervisor.generated.jooq.tables.BillingAddress;
import com.supervisor.generated.jooq.tables.ChartCamembertSetting;
import com.supervisor.generated.jooq.tables.Country;
import com.supervisor.generated.jooq.tables.Currency;
import com.supervisor.generated.jooq.tables.DataField;
import com.supervisor.generated.jooq.tables.DataFieldExpressionArg;
import com.supervisor.generated.jooq.tables.DataFieldOfSheet;
import com.supervisor.generated.jooq.tables.DataLink;
import com.supervisor.generated.jooq.tables.DataLinkParam;
import com.supervisor.generated.jooq.tables.DataLinkShared;
import com.supervisor.generated.jooq.tables.DataModel;
import com.supervisor.generated.jooq.tables.DataSheet;
import com.supervisor.generated.jooq.tables.DataSheetModel;
import com.supervisor.generated.jooq.tables.DataSheetShared;
import com.supervisor.generated.jooq.tables.Databasechangelog;
import com.supervisor.generated.jooq.tables.Databasechangeloglock;
import com.supervisor.generated.jooq.tables.DynamicTable_2Col;
import com.supervisor.generated.jooq.tables.EditableDataField;
import com.supervisor.generated.jooq.tables.Event;
import com.supervisor.generated.jooq.tables.ExpressionArg;
import com.supervisor.generated.jooq.tables.ExpressionValueArg;
import com.supervisor.generated.jooq.tables.FormularCaseExpression;
import com.supervisor.generated.jooq.tables.FormularCondition;
import com.supervisor.generated.jooq.tables.FormularDataField;
import com.supervisor.generated.jooq.tables.FormularExpression;
import com.supervisor.generated.jooq.tables.FormularExtendedToModelSource;
import com.supervisor.generated.jooq.tables.FormularExtendedToParentSource;
import com.supervisor.generated.jooq.tables.FormularSimpleExpression;
import com.supervisor.generated.jooq.tables.GaugeSetting;
import com.supervisor.generated.jooq.tables.GaugeZone;
import com.supervisor.generated.jooq.tables.GoalNumberSetting;
import com.supervisor.generated.jooq.tables.IndicatorDynamicNumberParam;
import com.supervisor.generated.jooq.tables.IndicatorDynamicParam;
import com.supervisor.generated.jooq.tables.IndicatorDynamicStringParam;
import com.supervisor.generated.jooq.tables.IndicatorGeneralSetting;
import com.supervisor.generated.jooq.tables.IndicatorStaticParam;
import com.supervisor.generated.jooq.tables.IndicatorType;
import com.supervisor.generated.jooq.tables.IndicatorTypeParam;
import com.supervisor.generated.jooq.tables.Invoice;
import com.supervisor.generated.jooq.tables.Language;
import com.supervisor.generated.jooq.tables.ListDataFieldSource;
import com.supervisor.generated.jooq.tables.ListDataFieldSourceShared;
import com.supervisor.generated.jooq.tables.MappedDataField;
import com.supervisor.generated.jooq.tables.ModelFilter;
import com.supervisor.generated.jooq.tables.ModelFilterCondition;
import com.supervisor.generated.jooq.tables.NumberOrientedSetting;
import com.supervisor.generated.jooq.tables.Order;
import com.supervisor.generated.jooq.tables.OrderLine;
import com.supervisor.generated.jooq.tables.OrderTax;
import com.supervisor.generated.jooq.tables.ParamDataField;
import com.supervisor.generated.jooq.tables.PaymentMethod;
import com.supervisor.generated.jooq.tables.PaymentReceipt;
import com.supervisor.generated.jooq.tables.PaymentRequest;
import com.supervisor.generated.jooq.tables.Periodicity;
import com.supervisor.generated.jooq.tables.Person;
import com.supervisor.generated.jooq.tables.Plan;
import com.supervisor.generated.jooq.tables.PlanFeature;
import com.supervisor.generated.jooq.tables.PlanSubscriptionContract;
import com.supervisor.generated.jooq.tables.PlannedTask;
import com.supervisor.generated.jooq.tables.Product;
import com.supervisor.generated.jooq.tables.ProductCatalog;
import com.supervisor.generated.jooq.tables.Profile;
import com.supervisor.generated.jooq.tables.ProfileAccess;
import com.supervisor.generated.jooq.tables.ProfileAccessParam;
import com.supervisor.generated.jooq.tables.PurchaseOrder;
import com.supervisor.generated.jooq.tables.Recordable;
import com.supervisor.generated.jooq.tables.RegistrationRequest;
import com.supervisor.generated.jooq.tables.Sequence;
import com.supervisor.generated.jooq.tables.SharedResource;
import com.supervisor.generated.jooq.tables.SubscriptionContract;
import com.supervisor.generated.jooq.tables.Tax;
import com.supervisor.generated.jooq.tables.User;
import com.supervisor.generated.jooq.tables.ValueExpressionArg;
import com.supervisor.generated.jooq.tables.WhenCase;


/**
 * Convenience access to all tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.access</code>.
     */
    public static final Access ACCESS = Access.ACCESS;

    /**
     * The table <code>public.access_param</code>.
     */
    public static final AccessParam ACCESS_PARAM = AccessParam.ACCESS_PARAM;

    /**
     * The table <code>public.activity</code>.
     */
    public static final Activity ACTIVITY = Activity.ACTIVITY;

    /**
     * The table <code>public.activity_param</code>.
     */
    public static final ActivityParam ACTIVITY_PARAM = ActivityParam.ACTIVITY_PARAM;

    /**
     * The table <code>public.activity_template_like</code>.
     */
    public static final ActivityTemplateLike ACTIVITY_TEMPLATE_LIKE = ActivityTemplateLike.ACTIVITY_TEMPLATE_LIKE;

    /**
     * The table <code>public.activity_template_published</code>.
     */
    public static final ActivityTemplatePublished ACTIVITY_TEMPLATE_PUBLISHED = ActivityTemplatePublished.ACTIVITY_TEMPLATE_PUBLISHED;

    /**
     * The table <code>public.activity_template_release</code>.
     */
    public static final ActivityTemplateRelease ACTIVITY_TEMPLATE_RELEASE = ActivityTemplateRelease.ACTIVITY_TEMPLATE_RELEASE;

    /**
     * The table <code>public.activity_template_subscription</code>.
     */
    public static final ActivityTemplateSubscription ACTIVITY_TEMPLATE_SUBSCRIPTION = ActivityTemplateSubscription.ACTIVITY_TEMPLATE_SUBSCRIPTION;

    /**
     * The table <code>public.activity_template_view</code>.
     */
    public static final ActivityTemplateView ACTIVITY_TEMPLATE_VIEW = ActivityTemplateView.ACTIVITY_TEMPLATE_VIEW;

    /**
     * The table <code>public.address</code>.
     */
    public static final Address ADDRESS = Address.ADDRESS;

    /**
     * The table <code>public.aggregated_model</code>.
     */
    public static final AggregatedModel AGGREGATED_MODEL = AggregatedModel.AGGREGATED_MODEL;

    /**
     * The table <code>public.aggregated_model_shared</code>.
     */
    public static final AggregatedModelShared AGGREGATED_MODEL_SHARED = AggregatedModelShared.AGGREGATED_MODEL_SHARED;

    /**
     * The table <code>public.billing_address</code>.
     */
    public static final BillingAddress BILLING_ADDRESS = BillingAddress.BILLING_ADDRESS;

    /**
     * The table <code>public.chart_camembert_setting</code>.
     */
    public static final ChartCamembertSetting CHART_CAMEMBERT_SETTING = ChartCamembertSetting.CHART_CAMEMBERT_SETTING;

    /**
     * The table <code>public.country</code>.
     */
    public static final Country COUNTRY = Country.COUNTRY;

    /**
     * The table <code>public.currency</code>.
     */
    public static final Currency CURRENCY = Currency.CURRENCY;

    /**
     * The table <code>public.data_field</code>.
     */
    public static final DataField DATA_FIELD = DataField.DATA_FIELD;

    /**
     * The table <code>public.data_field_expression_arg</code>.
     */
    public static final DataFieldExpressionArg DATA_FIELD_EXPRESSION_ARG = DataFieldExpressionArg.DATA_FIELD_EXPRESSION_ARG;

    /**
     * The table <code>public.data_field_of_sheet</code>.
     */
    public static final DataFieldOfSheet DATA_FIELD_OF_SHEET = DataFieldOfSheet.DATA_FIELD_OF_SHEET;

    /**
     * The table <code>public.data_link</code>.
     */
    public static final DataLink DATA_LINK = DataLink.DATA_LINK;

    /**
     * The table <code>public.data_link_param</code>.
     */
    public static final DataLinkParam DATA_LINK_PARAM = DataLinkParam.DATA_LINK_PARAM;

    /**
     * The table <code>public.data_link_shared</code>.
     */
    public static final DataLinkShared DATA_LINK_SHARED = DataLinkShared.DATA_LINK_SHARED;

    /**
     * The table <code>public.data_model</code>.
     */
    public static final DataModel DATA_MODEL = DataModel.DATA_MODEL;

    /**
     * The table <code>public.data_sheet</code>.
     */
    public static final DataSheet DATA_SHEET = DataSheet.DATA_SHEET;

    /**
     * The table <code>public.data_sheet_model</code>.
     */
    public static final DataSheetModel DATA_SHEET_MODEL = DataSheetModel.DATA_SHEET_MODEL;

    /**
     * The table <code>public.data_sheet_shared</code>.
     */
    public static final DataSheetShared DATA_SHEET_SHARED = DataSheetShared.DATA_SHEET_SHARED;

    /**
     * The table <code>public.databasechangelog</code>.
     */
    public static final Databasechangelog DATABASECHANGELOG = Databasechangelog.DATABASECHANGELOG;

    /**
     * The table <code>public.databasechangeloglock</code>.
     */
    public static final Databasechangeloglock DATABASECHANGELOGLOCK = Databasechangeloglock.DATABASECHANGELOGLOCK;

    /**
     * The table <code>public.dynamic_table_2_col</code>.
     */
    public static final DynamicTable_2Col DYNAMIC_TABLE_2_COL = DynamicTable_2Col.DYNAMIC_TABLE_2_COL;

    /**
     * The table <code>public.editable_data_field</code>.
     */
    public static final EditableDataField EDITABLE_DATA_FIELD = EditableDataField.EDITABLE_DATA_FIELD;

    /**
     * The table <code>public.event</code>.
     */
    public static final Event EVENT = Event.EVENT;

    /**
     * The table <code>public.expression_arg</code>.
     */
    public static final ExpressionArg EXPRESSION_ARG = ExpressionArg.EXPRESSION_ARG;

    /**
     * The table <code>public.expression_value_arg</code>.
     */
    public static final ExpressionValueArg EXPRESSION_VALUE_ARG = ExpressionValueArg.EXPRESSION_VALUE_ARG;

    /**
     * The table <code>public.formular_case_expression</code>.
     */
    public static final FormularCaseExpression FORMULAR_CASE_EXPRESSION = FormularCaseExpression.FORMULAR_CASE_EXPRESSION;

    /**
     * The table <code>public.formular_condition</code>.
     */
    public static final FormularCondition FORMULAR_CONDITION = FormularCondition.FORMULAR_CONDITION;

    /**
     * The table <code>public.formular_data_field</code>.
     */
    public static final FormularDataField FORMULAR_DATA_FIELD = FormularDataField.FORMULAR_DATA_FIELD;

    /**
     * The table <code>public.formular_expression</code>.
     */
    public static final FormularExpression FORMULAR_EXPRESSION = FormularExpression.FORMULAR_EXPRESSION;

    /**
     * The table <code>public.formular_extended_to_model_source</code>.
     */
    public static final FormularExtendedToModelSource FORMULAR_EXTENDED_TO_MODEL_SOURCE = FormularExtendedToModelSource.FORMULAR_EXTENDED_TO_MODEL_SOURCE;

    /**
     * The table <code>public.formular_extended_to_parent_source</code>.
     */
    public static final FormularExtendedToParentSource FORMULAR_EXTENDED_TO_PARENT_SOURCE = FormularExtendedToParentSource.FORMULAR_EXTENDED_TO_PARENT_SOURCE;

    /**
     * The table <code>public.formular_simple_expression</code>.
     */
    public static final FormularSimpleExpression FORMULAR_SIMPLE_EXPRESSION = FormularSimpleExpression.FORMULAR_SIMPLE_EXPRESSION;

    /**
     * The table <code>public.gauge_setting</code>.
     */
    public static final GaugeSetting GAUGE_SETTING = GaugeSetting.GAUGE_SETTING;

    /**
     * The table <code>public.gauge_zone</code>.
     */
    public static final GaugeZone GAUGE_ZONE = GaugeZone.GAUGE_ZONE;

    /**
     * The table <code>public.goal_number_setting</code>.
     */
    public static final GoalNumberSetting GOAL_NUMBER_SETTING = GoalNumberSetting.GOAL_NUMBER_SETTING;

    /**
     * The table <code>public.indicator_dynamic_number_param</code>.
     */
    public static final IndicatorDynamicNumberParam INDICATOR_DYNAMIC_NUMBER_PARAM = IndicatorDynamicNumberParam.INDICATOR_DYNAMIC_NUMBER_PARAM;

    /**
     * The table <code>public.indicator_dynamic_param</code>.
     */
    public static final IndicatorDynamicParam INDICATOR_DYNAMIC_PARAM = IndicatorDynamicParam.INDICATOR_DYNAMIC_PARAM;

    /**
     * The table <code>public.indicator_dynamic_string_param</code>.
     */
    public static final IndicatorDynamicStringParam INDICATOR_DYNAMIC_STRING_PARAM = IndicatorDynamicStringParam.INDICATOR_DYNAMIC_STRING_PARAM;

    /**
     * The table <code>public.indicator_general_setting</code>.
     */
    public static final IndicatorGeneralSetting INDICATOR_GENERAL_SETTING = IndicatorGeneralSetting.INDICATOR_GENERAL_SETTING;

    /**
     * The table <code>public.indicator_static_param</code>.
     */
    public static final IndicatorStaticParam INDICATOR_STATIC_PARAM = IndicatorStaticParam.INDICATOR_STATIC_PARAM;

    /**
     * The table <code>public.indicator_type</code>.
     */
    public static final IndicatorType INDICATOR_TYPE = IndicatorType.INDICATOR_TYPE;

    /**
     * The table <code>public.indicator_type_param</code>.
     */
    public static final IndicatorTypeParam INDICATOR_TYPE_PARAM = IndicatorTypeParam.INDICATOR_TYPE_PARAM;

    /**
     * The table <code>public.invoice</code>.
     */
    public static final Invoice INVOICE = Invoice.INVOICE;

    /**
     * The table <code>public.language</code>.
     */
    public static final Language LANGUAGE = Language.LANGUAGE;

    /**
     * The table <code>public.list_data_field_source</code>.
     */
    public static final ListDataFieldSource LIST_DATA_FIELD_SOURCE = ListDataFieldSource.LIST_DATA_FIELD_SOURCE;

    /**
     * The table <code>public.list_data_field_source_shared</code>.
     */
    public static final ListDataFieldSourceShared LIST_DATA_FIELD_SOURCE_SHARED = ListDataFieldSourceShared.LIST_DATA_FIELD_SOURCE_SHARED;

    /**
     * The table <code>public.mapped_data_field</code>.
     */
    public static final MappedDataField MAPPED_DATA_FIELD = MappedDataField.MAPPED_DATA_FIELD;

    /**
     * The table <code>public.model_filter</code>.
     */
    public static final ModelFilter MODEL_FILTER = ModelFilter.MODEL_FILTER;

    /**
     * The table <code>public.model_filter_condition</code>.
     */
    public static final ModelFilterCondition MODEL_FILTER_CONDITION = ModelFilterCondition.MODEL_FILTER_CONDITION;

    /**
     * The table <code>public.number_oriented_setting</code>.
     */
    public static final NumberOrientedSetting NUMBER_ORIENTED_SETTING = NumberOrientedSetting.NUMBER_ORIENTED_SETTING;

    /**
     * The table <code>public.order</code>.
     */
    public static final Order ORDER = Order.ORDER;

    /**
     * The table <code>public.order_line</code>.
     */
    public static final OrderLine ORDER_LINE = OrderLine.ORDER_LINE;

    /**
     * The table <code>public.order_tax</code>.
     */
    public static final OrderTax ORDER_TAX = OrderTax.ORDER_TAX;

    /**
     * The table <code>public.param_data_field</code>.
     */
    public static final ParamDataField PARAM_DATA_FIELD = ParamDataField.PARAM_DATA_FIELD;

    /**
     * The table <code>public.payment_method</code>.
     */
    public static final PaymentMethod PAYMENT_METHOD = PaymentMethod.PAYMENT_METHOD;

    /**
     * The table <code>public.payment_receipt</code>.
     */
    public static final PaymentReceipt PAYMENT_RECEIPT = PaymentReceipt.PAYMENT_RECEIPT;

    /**
     * The table <code>public.payment_request</code>.
     */
    public static final PaymentRequest PAYMENT_REQUEST = PaymentRequest.PAYMENT_REQUEST;

    /**
     * The table <code>public.periodicity</code>.
     */
    public static final Periodicity PERIODICITY = Periodicity.PERIODICITY;

    /**
     * The table <code>public.person</code>.
     */
    public static final Person PERSON = Person.PERSON;

    /**
     * The table <code>public.plan</code>.
     */
    public static final Plan PLAN = Plan.PLAN;

    /**
     * The table <code>public.plan_feature</code>.
     */
    public static final PlanFeature PLAN_FEATURE = PlanFeature.PLAN_FEATURE;

    /**
     * The table <code>public.plan_subscription_contract</code>.
     */
    public static final PlanSubscriptionContract PLAN_SUBSCRIPTION_CONTRACT = PlanSubscriptionContract.PLAN_SUBSCRIPTION_CONTRACT;

    /**
     * The table <code>public.planned_task</code>.
     */
    public static final PlannedTask PLANNED_TASK = PlannedTask.PLANNED_TASK;

    /**
     * The table <code>public.product</code>.
     */
    public static final Product PRODUCT = Product.PRODUCT;

    /**
     * The table <code>public.product_catalog</code>.
     */
    public static final ProductCatalog PRODUCT_CATALOG = ProductCatalog.PRODUCT_CATALOG;

    /**
     * The table <code>public.profile</code>.
     */
    public static final Profile PROFILE = Profile.PROFILE;

    /**
     * The table <code>public.profile_access</code>.
     */
    public static final ProfileAccess PROFILE_ACCESS = ProfileAccess.PROFILE_ACCESS;

    /**
     * The table <code>public.profile_access_param</code>.
     */
    public static final ProfileAccessParam PROFILE_ACCESS_PARAM = ProfileAccessParam.PROFILE_ACCESS_PARAM;

    /**
     * The table <code>public.purchase_order</code>.
     */
    public static final PurchaseOrder PURCHASE_ORDER = PurchaseOrder.PURCHASE_ORDER;

    /**
     * The table <code>public.recordable</code>.
     */
    public static final Recordable RECORDABLE = Recordable.RECORDABLE;

    /**
     * The table <code>public.registration_request</code>.
     */
    public static final RegistrationRequest REGISTRATION_REQUEST = RegistrationRequest.REGISTRATION_REQUEST;

    /**
     * The table <code>public.sequence</code>.
     */
    public static final Sequence SEQUENCE = Sequence.SEQUENCE;

    /**
     * The table <code>public.shared_resource</code>.
     */
    public static final SharedResource SHARED_RESOURCE = SharedResource.SHARED_RESOURCE;

    /**
     * The table <code>public.subscription_contract</code>.
     */
    public static final SubscriptionContract SUBSCRIPTION_CONTRACT = SubscriptionContract.SUBSCRIPTION_CONTRACT;

    /**
     * The table <code>public.tax</code>.
     */
    public static final Tax TAX = Tax.TAX;

    /**
     * The table <code>public.user</code>.
     */
    public static final User USER = User.USER;

    /**
     * The table <code>public.value_expression_arg</code>.
     */
    public static final ValueExpressionArg VALUE_EXPRESSION_ARG = ValueExpressionArg.VALUE_EXPRESSION_ARG;

    /**
     * The table <code>public.when_case</code>.
     */
    public static final WhenCase WHEN_CASE = WhenCase.WHEN_CASE;
}
