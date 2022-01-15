package com.minlessika.supervisor.server;

import java.io.IOException;

import com.minlessika.membership.billing.BillingAddress;
import com.minlessika.membership.billing.Invoice;
import com.minlessika.membership.billing.Order;
import com.minlessika.membership.billing.OrderLine;
import com.minlessika.membership.billing.OrderTax;
import com.minlessika.membership.billing.PaymentMethod;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.billing.PlannedTask;
import com.minlessika.membership.billing.Product;
import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.billing.SoftwareEngineeringService;
import com.minlessika.membership.billing.SubscriptionContract;
import com.minlessika.membership.billing.Tax;
import com.minlessika.membership.domain.Address;
import com.minlessika.membership.domain.Country;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.Event;
import com.minlessika.membership.domain.Language;
import com.minlessika.membership.domain.Person;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.PlanFeature;
import com.minlessika.membership.domain.PlanSubscriptionContract;
import com.minlessika.membership.domain.RegistrationRequest;
import com.minlessika.membership.domain.Sequence;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.takes.TkAccess;
import com.minlessika.membership.takes.TkAccessEdit;
import com.minlessika.membership.takes.TkAccessSave;
import com.minlessika.membership.takes.TkAdmin;
import com.minlessika.membership.takes.TkAdminChangePasswordEdit;
import com.minlessika.membership.takes.TkAdminChangePasswordSave;
import com.minlessika.membership.takes.TkApplicationEdit;
import com.minlessika.membership.takes.TkApplicationSave;
import com.minlessika.membership.takes.TkBeginMobileTransaction;
import com.minlessika.membership.takes.TkCinetPayCancelTransaction;
import com.minlessika.membership.takes.TkCinetPayOpenTransaction;
import com.minlessika.membership.takes.TkCinetPayTransactionCallback;
import com.minlessika.membership.takes.TkCinetPayTransactionReturn;
import com.minlessika.membership.takes.TkConfirmRegistering;
import com.minlessika.membership.takes.TkContact;
import com.minlessika.membership.takes.TkFeedbackEdit;
import com.minlessika.membership.takes.TkFloozTransactionCallback;
import com.minlessika.membership.takes.TkHub2CancelTransaction;
import com.minlessika.membership.takes.TkHub2OpenTransaction;
import com.minlessika.membership.takes.TkHub2TransactionCallback;
import com.minlessika.membership.takes.TkLangEn;
import com.minlessika.membership.takes.TkLangFr;
import com.minlessika.membership.takes.TkLogin;
import com.minlessika.membership.takes.TkMobileMoneyPaymentEdit;
import com.minlessika.membership.takes.TkMobileMoneyPaymentFinalize;
import com.minlessika.membership.takes.TkMomoTransactionCallback;
import com.minlessika.membership.takes.TkOmOpenTransaction;
import com.minlessika.membership.takes.TkOmTransactionCallback;
import com.minlessika.membership.takes.TkOmTransactionError;
import com.minlessika.membership.takes.TkOmTransactionSuccess;
import com.minlessika.membership.takes.TkPasswordEdit;
import com.minlessika.membership.takes.TkPasswordForgotten;
import com.minlessika.membership.takes.TkPasswordForgottenChanged;
import com.minlessika.membership.takes.TkPasswordSave;
import com.minlessika.membership.takes.TkPayTestBuy;
import com.minlessika.membership.takes.TkPaymentMethod;
import com.minlessika.membership.takes.TkPaymentMethodEdit;
import com.minlessika.membership.takes.TkPaymentMethodSave;
import com.minlessika.membership.takes.TkPaymentRequestCancel;
import com.minlessika.membership.takes.TkPaymentRequestEdit;
import com.minlessika.membership.takes.TkPlan;
import com.minlessika.membership.takes.TkPlanDelete;
import com.minlessika.membership.takes.TkPlanEdit;
import com.minlessika.membership.takes.TkPlanFeatureDelete;
import com.minlessika.membership.takes.TkPlanFeatureEdit;
import com.minlessika.membership.takes.TkPlanFeatureSave;
import com.minlessika.membership.takes.TkPlanSave;
import com.minlessika.membership.takes.TkProfile;
import com.minlessika.membership.takes.TkProfileAccessAdd;
import com.minlessika.membership.takes.TkProfileAccessDelete;
import com.minlessika.membership.takes.TkProfileAccessEdit;
import com.minlessika.membership.takes.TkProfileAccessNotAdded;
import com.minlessika.membership.takes.TkProfileAccessSave;
import com.minlessika.membership.takes.TkProfileDelete;
import com.minlessika.membership.takes.TkProfileEdit;
import com.minlessika.membership.takes.TkProfileSave;
import com.minlessika.membership.takes.TkPurchaseOrderCancel;
import com.minlessika.membership.takes.TkPurchaseOrderEdit;
import com.minlessika.membership.takes.TkRegister;
import com.minlessika.membership.takes.TkRegistration;
import com.minlessika.membership.takes.TkRequestPasswordResetting;
import com.minlessika.membership.takes.TkResetPassword;
import com.minlessika.membership.takes.TkSendContact;
import com.minlessika.membership.takes.TkSendFeedback;
import com.minlessika.membership.takes.TkSignin;
import com.minlessika.membership.takes.TkTax;
import com.minlessika.membership.takes.TkTaxEdit;
import com.minlessika.membership.takes.TkTaxSave;
import com.minlessika.membership.takes.TkUser;
import com.minlessika.membership.takes.TkUserActivate;
import com.minlessika.membership.takes.TkUserDelete;
import com.minlessika.membership.takes.TkUserEdit;
import com.minlessika.membership.takes.TkUserProfileEdit;
import com.minlessika.membership.takes.TkUserProfileSave;
import com.minlessika.membership.takes.TkUserSave;
import com.minlessika.secure.TkAnonymous;
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

public final class FkMySupervisor extends FkModule {
	
	@SuppressWarnings("unchecked")
	public FkMySupervisor(final Base base) throws IOException {
		super(
			base,
			new FkModuleMimes(
				  new BasicModule(Supervisor.NAME, base.appInfo()), 
				  new FkChain(
					  new FkRegex(
							  "/",
							  new com.minlessika.membership.takes.TkIndex(base)
					  ),
					  new FkRegex(
							  "/contacts",
							  new TkAnonymous(new TkContact(base))
					  ),
					  new FkRegex(
							  "/contacts/send",
							  new TkAnonymous(new TkSendContact(base))
					  ),
					  new FkRegex("/registration",
							  new TkAnonymous(new TkRegistration(base))
					  ),
					  new FkRegex("/register",
							  new TkAnonymous(new TkRegister(base))
					  ),
					  new FkRegex("/login",
							  new TkAnonymous(new TkLogin(base))
					  ),
					  new FkRegex("/signin",
							  new TkAnonymous(new TkSignin(base))
					  ),
					  new FkRegex("/password/forgotten",
							  new TkAnonymous(new TkPasswordForgotten(base))
					  ),
					  new FkRegex("/password/reset/request",
							  new TkAnonymous(new TkRequestPasswordResetting(base))
					  ),
					  new FkRegex("/password/reset",
							  new TkAnonymous(new TkResetPassword(base))
					  ),
					  new FkRegex("/password/reset/change",
							  new TkAnonymous(new TkPasswordForgottenChanged(base))
					  ),
					  new FkRegex("/register/confirm",
							  new TkAnonymous(new TkConfirmRegistering(base))
					  ),
					  new FkRegex(
							  "/user-profile/edit",
							  new TkSecure(new TkUserProfileEdit(base))
					  ),
					  new FkRegex(
							  "/user-profile/save",
							  new TkSecure(new TkUserProfileSave(base))
					  ),
					  new FkRegex(
							  "/password/edit",
							  new TkSecure(new TkPasswordEdit(base))
					  ),
					  new FkRegex(
							  "/password/save",
							  new TkSecure(new TkPasswordSave(base))
					  ),
					  new FkRegex(
							  "/feedback/edit",
							  new TkSecure(new TkFeedbackEdit(base))
					  ),
					  new FkRegex(
							  "/feedback/send",
							  new TkSecure(new TkSendFeedback(base))
					  ),
					  new FkRegex("/admin",
							  new TkSecure(new TkAdmin(base))
					  ),
					  new FkRegex("/admin/access",
							  new TkSecure(new TkAccess(base))
					  ),
					  new FkRegex("/admin/access/edit",
							  new TkSecure(new TkAccessEdit(base))
					  ),
					  new FkRegex("/admin/access/save",
							  new TkSecure(new TkAccessSave(base))
					  ),
					  new FkRegex("/admin/profile",
							  new TkSecure(new TkProfile(base))
					  ),
					  new FkRegex("/admin/profile/edit",
							  new TkSecure(new TkProfileEdit(base))
					  ),
					  new FkRegex("/admin/profile/save",
							  new TkSecure(new TkProfileSave(base))
					  ),
					  new FkRegex("/admin/profile/delete",
							  new TkSecure(new TkProfileDelete(base))
					  ),
					  new FkRegex("/admin/profile/access/edit",
							  new TkSecure(new TkProfileAccessEdit(base))
					  ),
					  new FkRegex("/admin/profile/access/save",
							  new TkSecure(new TkProfileAccessSave(base))
					  ),
					  new FkRegex("/admin/profile/access/no-added",
							  new TkSecure(new TkProfileAccessNotAdded(base))
					  ),
					  new FkRegex("/admin/profile/access/add",
							  new TkSecure(new TkProfileAccessAdd(base))
					  ),
					  new FkRegex("/admin/profile/access/delete",
							  new TkSecure(new TkProfileAccessDelete(base))
					  ),
					  new FkRegex("/admin/user",
							  new TkSecure(new TkUser(base))
					  ),
					  new FkRegex("/admin/user/edit",
							  new TkSecure(new TkUserEdit(base))
					  ),
					  new FkRegex("/admin/user/save",
							  new TkSecure(new TkUserSave(base))
					  ),
					  new FkRegex("/admin/user/delete",
							  new TkSecure(new TkUserDelete(base))
					  ),
					  new FkRegex("/admin/user/activate",
							  new TkSecure(new TkUserActivate(base))
					  ),
					  new FkRegex("/admin/user/app/edit",
							  new TkSecure(new TkApplicationEdit(base))
					  ),
					  new FkRegex("/admin/user/app/save",
							  new TkSecure(new TkApplicationSave(base))
					  ),
					  new FkRegex("/admin/user/change-password/edit",
							  new TkSecure(new TkAdminChangePasswordEdit(base))
					  ),
					  new FkRegex(
							  "/admin/user/change-password/save",
							  new TkSecure(new TkAdminChangePasswordSave(base))
					  ),
					  new FkRegex(
							  "/admin/plan",
							  new TkSecure(new TkPlan(base))
					  ),
					  new FkRegex(
							  "/admin/plan/edit",
							  new TkSecure(new TkPlanEdit(base))
					  ),
					  new FkRegex(
							  "/admin/plan/save",
							  new TkSecure(new TkPlanSave(base))
					  ),
					  new FkRegex(
							  "/admin/plan/delete",
							  new TkSecure(new TkPlanDelete(base))
					  ),
					  new FkRegex(
							  "/admin/plan/feature/edit",
							  new TkSecure(new TkPlanFeatureEdit(base))
					  ),
					  new FkRegex(
							  "/admin/plan/feature/save",
							  new TkSecure(new TkPlanFeatureSave(base))
					  ),
					  new FkRegex(
							  "/admin/plan/feature/delete",
							  new TkSecure(new TkPlanFeatureDelete(base))
					  ),
					  new FkRegex(
							  "/admin/tax",
							  new TkSecure(new TkTax(base))
					  ),
					  new FkRegex(
							  "/admin/tax/edit",
							  new TkSecure(new TkTaxEdit(base))
					  ),
					  new FkRegex(
							  "/admin/tax/save",
							  new TkSecure(new TkTaxSave(base))
					  ),
					  new FkRegex(
							  "/admin/payment-method",
							  new TkSecure(new TkPaymentMethod(base))
					  ),
					  new FkRegex(
							  "/admin/payment-method/edit",
							  new TkSecure(new TkPaymentMethodEdit(base))
					  ),
					  new FkRegex(
							  "/admin/payment-method/save",
							  new TkSecure(new TkPaymentMethodSave(base))
					  ),
					  new FkRegex(
							  "/purchase-order/edit",
							  new TkSecure(new TkPurchaseOrderEdit(base))
					  ),
					  new FkRegex(
							  "/purchase-order/cancel",
							  new TkSecure(new TkPurchaseOrderCancel(base))
					  ),
					  new FkRegex(
							  "/payment-request/edit",
							  new TkSecure(new TkPaymentRequestEdit(base))
					  ),
					  new FkRegex(
							  "/payment-request/cancel",
							  new TkSecure(new TkPaymentRequestCancel(base))
					  ),
					  new FkRegex(
							  "/payment-method/mobile-money/edit",
							  new TkSecure(new TkMobileMoneyPaymentEdit(base))
					  ),
					  new FkRegex(
							  "/payment-method/mobile-money/begin",
							  new TkSecure(
									  new TkConsumes(new TkBeginMobileTransaction(base), "application/json")
							  )
					  ),
					  new FkRegex(
							  "/payment-method/mobile-money/finalize",
							  new TkSecure(new TkMobileMoneyPaymentFinalize(base))
					  ),
					  new FkRegex(
							  "/flooz/notify",
							  new TkAnonymous(new TkFloozTransactionCallback(base))
					  ),
					  new FkRegex(
							  "/momo/notify",
							  new TkAnonymous(new TkMomoTransactionCallback(base))
					  ),
					  new FkRegex(
							  "/hub2/transaction/open",
							  new TkSecure(new TkHub2OpenTransaction(base))
					  ),
					  new FkRegex(
							  "/hub2/transaction/cancel",
							  new TkSecure(new TkHub2CancelTransaction(base))
					  ),
					  new FkRegex(
							  "/hub2/notify",
							  new TkAnonymous(new TkHub2TransactionCallback(base))
					  ),
					  new FkRegex(
							  "/om/transaction/open",
							  new TkSecure(new TkOmOpenTransaction(base))
					  ),
					  new FkRegex(
							  "/om/notify",
							  new TkOmTransactionCallback(base)
					  ),
					  new FkRegex(
							  "/payment-method/om-money/success",
							  new TkOmTransactionSuccess(base)
					  ),
					  new FkRegex(
							  "/payment-method/om-money/error",
							  new TkOmTransactionError(base)
					  ),
					  new FkRegex(
							  "/cinetpay/transaction/open",
							  new TkSecure(new TkCinetPayOpenTransaction(base))
					  ),
					  new FkRegex(
							  "/cinetpay/notify",
							  new TkCinetPayTransactionCallback(base)
					  ),
					  new FkRegex(
							  "/cinetpay/cancel",
							  new TkCinetPayCancelTransaction(base)
					  ),
					  new FkRegex(
							  "/cinetpay/return",
							  new TkCinetPayTransactionReturn(base)
					  ),
					  new FkRegex(
							  "/pay-test/buy",
							  new TkPayTestBuy(base)
					  ),
					  new FkRegex(
							  "/lang/fr",
							  new TkLangFr(base)
					  ),
					  new FkRegex(
							  "/lang/en",
							  new TkLangEn(base)
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
			Sequence.class,
			Event.class,
			Currency.class,
			Country.class,
			Language.class,
			Tax.class,
			Profile.class,
			Access.class,
			AccessParam.class,
			ProfileAccess.class,
			ProfileAccessParam.class,
			Address.class,
			BillingAddress.class,
			Person.class,
			User.class,
			Application.class,
			RegistrationRequest.class,
			PaymentMethod.class,
			ProductCatalog.class,
			Product.class,
			SoftwareEngineeringService.class,
			Order.class,
			OrderLine.class,
			OrderTax.class,
			PurchaseOrder.class,
			PaymentRequest.class,
			PlannedTask.class,
			PaymentReceipt.class,
			Invoice.class,
			SubscriptionContract.class,
			Plan.class,
			PlanFeature.class,
			PlanSubscriptionContract.class,
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
