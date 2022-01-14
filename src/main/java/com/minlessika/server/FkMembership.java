package com.minlessika.server;

import com.minlessika.core.takes.BddVersion;
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
import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.AccessParam;
import com.minlessika.membership.domain.Address;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Country;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.Event;
import com.minlessika.membership.domain.Language;
import com.minlessika.membership.domain.Person;
import com.minlessika.membership.domain.Plan;
import com.minlessika.membership.domain.PlanFeature;
import com.minlessika.membership.domain.PlanSubscriptionContract;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.ProfileAccess;
import com.minlessika.membership.domain.ProfileAccessParam;
import com.minlessika.membership.domain.RegistrationRequest;
import com.minlessika.membership.domain.Sequence;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmMembership;
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
import com.minlessika.membership.takes.TkHome;
import com.minlessika.membership.takes.TkHub2CancelTransaction;
import com.minlessika.membership.takes.TkHub2OpenTransaction;
import com.minlessika.membership.takes.TkHub2TransactionCallback;
import com.minlessika.membership.takes.TkIndex;
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
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.FkModule;
import com.minlessika.sdk.takes.FkModuleMimes;
import com.minlessika.secure.TkAnonymous;
import com.minlessika.tk.TkPrevious;
import org.takes.facets.auth.TkSecure;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkConsumes;

import java.io.IOException;

public final class FkMembership extends FkModule {
	
	@SuppressWarnings("unchecked")
	public FkMembership(final Base base) throws IOException {
		super(
			base,
			new FkModuleMimes(
				  new DmMembership(base), 
				  new FkChain(
					  new FkRegex(
						  "/", 
						  new TkIndex(base)
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
		              new FkRegex("/home", 
	            		  new TkSecure(new TkPrevious(new TkHome(base)))
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
			Application.class
		);
	}
}
