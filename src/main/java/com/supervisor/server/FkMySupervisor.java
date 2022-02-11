/*
 * Copyright (c) 2018-2022 Minlessika
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.supervisor.server;

import com.supervisor.sdk.secure.Recaptcha;
import com.supervisor.takes.TkAccess;
import com.supervisor.takes.TkAccessEdit;
import com.supervisor.takes.TkAccessSave;
import com.supervisor.takes.TkActivityBaseOnTemplateEdit;
import com.supervisor.takes.*;
import com.supervisor.takes.TkActivityNextPeriod;
import com.supervisor.takes.TkActivityPeriodAtDate;
import com.supervisor.takes.TkActivityPreviousPeriod;
import com.supervisor.takes.TkActivitySave;
import com.supervisor.takes.TkActivityShowDefault;
import com.supervisor.takes.TkActivityTemplate;
import com.supervisor.takes.TkActivityTemplateDelete;
import com.supervisor.takes.TkActivityTemplateEdit;
import com.supervisor.takes.TkActivityTemplateGenerate;
import com.supervisor.takes.TkActivityTemplateReleaseEdit;
import com.supervisor.takes.TkActivityTemplateReleaseSave;
import com.supervisor.takes.TkActivityUpdateApply;
import com.supervisor.takes.TkActivityUpdateEdit;
import com.supervisor.takes.TkAdmin;
import com.supervisor.takes.TkAdminChangePasswordEdit;
import com.supervisor.takes.TkAdminChangePasswordSave;
import com.supervisor.takes.TkBeginMobileTransaction;
import com.supervisor.takes.TkCinetPayCancelTransaction;
import com.supervisor.takes.TkCinetPayOpenTransaction;
import com.supervisor.takes.TkCinetPayTransactionCallback;
import com.supervisor.takes.TkCinetPayTransactionReturn;
import com.supervisor.takes.TkConfirmRegistering;
import com.supervisor.takes.TkContact;
import com.supervisor.takes.TkFeedbackEdit;
import com.supervisor.takes.TkFloozTransactionCallback;
import com.supervisor.takes.TkHome;
import com.supervisor.takes.TkHub2CancelTransaction;
import com.supervisor.takes.TkHub2OpenTransaction;
import com.supervisor.takes.TkHub2TransactionCallback;
import com.supervisor.takes.TkIndex;
import com.supervisor.takes.TkInteraction;
import com.supervisor.takes.TkInteractionActivate;
import com.supervisor.takes.TkLangEn;
import com.supervisor.takes.TkLangFr;
import com.supervisor.takes.TkLogin;
import com.supervisor.takes.TkMobileMoneyPaymentEdit;
import com.supervisor.takes.TkMobileMoneyPaymentFinalize;
import com.supervisor.takes.TkMomoTransactionCallback;
import com.supervisor.takes.TkNoActivity;
import com.supervisor.takes.TkOmOpenTransaction;
import com.supervisor.takes.TkOmTransactionCallback;
import com.supervisor.takes.TkOmTransactionError;
import com.supervisor.takes.TkOmTransactionSuccess;
import com.supervisor.takes.TkPasswordEdit;
import com.supervisor.takes.TkPasswordForgotten;
import com.supervisor.takes.TkPasswordForgottenChanged;
import com.supervisor.takes.TkPasswordSave;
import com.supervisor.takes.TkPayTestBuy;
import com.supervisor.takes.TkPaymentMethod;
import com.supervisor.takes.TkPaymentMethodEdit;
import com.supervisor.takes.TkPaymentMethodSave;
import com.supervisor.takes.TkPaymentRequestCancel;
import com.supervisor.takes.TkPaymentRequestEdit;
import com.supervisor.takes.TkPlan;
import com.supervisor.takes.TkPlanDelete;
import com.supervisor.takes.TkPlanEdit;
import com.supervisor.takes.TkPlanFeatureDelete;
import com.supervisor.takes.TkPlanFeatureEdit;
import com.supervisor.takes.TkPlanFeatureSave;
import com.supervisor.takes.TkPlanSave;
import com.supervisor.takes.TkProfile;
import com.supervisor.takes.TkProfileAccessAdd;
import com.supervisor.takes.TkProfileAccessDelete;
import com.supervisor.takes.TkProfileAccessEdit;
import com.supervisor.takes.TkProfileAccessNotAdded;
import com.supervisor.takes.TkProfileAccessSave;
import com.supervisor.takes.TkProfileDelete;
import com.supervisor.takes.TkProfileEdit;
import com.supervisor.takes.TkProfileSave;
import com.supervisor.takes.TkPurchaseOrderCancel;
import com.supervisor.takes.TkPurchaseOrderEdit;
import com.supervisor.takes.TkRegister;
import com.supervisor.takes.TkRegistration;
import com.supervisor.takes.TkRequestPasswordResetting;
import com.supervisor.takes.TkResetPassword;
import com.supervisor.takes.TkSendContact;
import com.supervisor.takes.TkSendFeedback;
import com.supervisor.takes.TkSignin;
import com.supervisor.takes.TkTax;
import com.supervisor.takes.TkTaxEdit;
import com.supervisor.takes.TkTaxSave;
import com.supervisor.takes.TkUser;
import com.supervisor.takes.TkUserActivate;
import com.supervisor.takes.TkUserDelete;
import com.supervisor.takes.TkUserEdit;
import com.supervisor.takes.TkUserProfileEdit;
import com.supervisor.takes.TkUserProfileSave;
import com.supervisor.takes.TkUserSave;
import com.minlessika.secure.TkAnonymous;
import com.supervisor.sdk.datasource.Base;
import com.minlessika.tk.TkPrevious;
import org.takes.facets.auth.TkSecure;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;
import org.takes.facets.fork.TkConsumes;

/**
 * My server front.
 *
 * @since 1.0
 */
public final class FkMySupervisor extends FkWrap {

	/**
	 * Ctor.
	 *
	 * @param base Data source
	 * @param recaptcha Recaptcha
	 */
	@SuppressWarnings("unchecked")
	public FkMySupervisor(final Base base, Recaptcha recaptcha) {
		super(
			new FkModuleMimes(
				new FkChain(
					new FkRegex(
						"/",
						new TkAnonymous(new TkIndex())
					),
					new FkRegex(
						"/contacts",
						new TkAnonymous(new TkContact(recaptcha))
					),
					new FkRegex(
						"/contacts/send",
						new TkAnonymous(new TkSendContact(base, recaptcha))
					),
					new FkRegex("/registration",
						new TkAnonymous(new TkRegistration(recaptcha))
					),
					new FkRegex("/register",
						new TkAnonymous(new TkRegister(base, recaptcha))
					),
					new FkRegex("/login",
						new TkAnonymous(new TkLogin(recaptcha))
					),
					new FkRegex("/signin",
						new TkAnonymous(new TkSignin(base, recaptcha))
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
						new TkSecure(new TkStore(base))
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
			)
		);
	}
}
