package com.minlessika.server;

import com.minlessika.core.takes.BddVersion;
import com.minlessika.core.takes.PgBddVersion;
import com.minlessika.core.takes.TkApp;
import com.minlessika.membership.billing.BillingAddress;
import com.minlessika.membership.billing.PaymentReceipt;
import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.Address;
import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Person;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.RegistrationRequest;
import com.minlessika.sdk.app.info.AppInfo;
import com.minlessika.sdk.app.info.AppInfoImpl;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.BaseCredentials;
import com.minlessika.sdk.datasource.BaseScheme;
import com.minlessika.sdk.datasource.DataSources;
import com.minlessika.sdk.datasource.SqlTxn;
import com.minlessika.sdk.pgsql.PgBase;
import com.minlessika.sdk.pgsql.PgBaseCredentials;
import com.minlessika.sdk.pgsql.PgBaseScheme;
import com.minlessika.sdk.pgsql.PgHikariDataSourceFactory;
import com.minlessika.sdk.pgsql.metadata.PgFieldOfMethod;
import com.minlessika.sdk.pgsql.statement.PgCreateColumnStatement;
import com.minlessika.sdk.pgsql.statement.PgCreateDatabase;
import com.minlessika.sdk.pgsql.statement.PgDatabaseExistsStatement;
import com.minlessika.sdk.pgsql.statement.PgDropColumnStatement;
import com.minlessika.sdk.pgsql.statement.PgDropTableStatement;
import com.minlessika.sdk.pgsql.statement.PgUpdateSchemaStatement;
import org.apache.commons.lang.StringUtils;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.Fork;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtCli;

import javax.sql.DataSource;

public final class Main {
	
	public static void main(final String... args) throws Exception {
				
		AppInfo appInfo = new AppInfoImpl(Membership.NAME, args);
		BaseCredentials credentials = new PgBaseCredentials(args);
		DataSources dataSourceFactory = new PgHikariDataSourceFactory(credentials);
		
		final DataSource sysSource = dataSourceFactory.create("postgres"); 		
		final Base baseSys = new PgBase(sysSource);	
		
		final boolean databaseExists = new PgDatabaseExistsStatement(baseSys, credentials.basename()).exists();
		if(!databaseExists)
			new PgCreateDatabase(baseSys, credentials.basename()).execute();
		
		DataSource source = dataSourceFactory.create();
		Base base = new PgBase(source, appInfo);
		
		Fork fork = new SqlTxn(base).call(
							  (final Base myBase) -> {
										
								if(databaseExists) {
									BddVersion bddVersion = new PgBddVersion(myBase, Membership.NAME);			
									if(!bddVersion.productRange().equals(Membership.PRODUCT_RANGE))
										throw new IllegalArgumentException("La gamme n'est pas compatible à celle du produit installée !");
									
									if(bddVersion.isNewThan(Membership.REV))
										throw new IllegalArgumentException("La base de données installée est plus récente que le produit !");																															
									
									if(bddVersion.isOldThan(Membership.REV)) {
										// Mettre à niveau ne manière incrémentale
										BaseScheme scheme = new PgBaseScheme();
										
										if(bddVersion.isOldThan("0.0.1.0003")) {
											// mise à jour des versions < 0.0.1.0003
											new PgDropTableStatement(myBase, "billing_mobile_money_payment_receipt").execute();
											new PgDropTableStatement(myBase, "billing_order_line").execute();
											new PgDropTableStatement(myBase, "billing_order_tax").execute();
											new PgDropTableStatement(myBase, "billing_payment_receipt").execute();
											new PgDropTableStatement(myBase, "membership_plan_subscription_contract").execute();
											new PgDropTableStatement(myBase, "billing_subscription_contract").execute();
											new PgDropTableStatement(myBase, "billing_invoice").execute();
											new PgDropTableStatement(myBase, "billing_purchase_order").execute();									
											new PgDropTableStatement(myBase, "billing_order").execute();
											new PgDropTableStatement(myBase, "base_sequence").execute();
											
											new PgUpdateSchemaStatement(myBase, scheme.scriptOf(Address.class)).execute();				
											new PgUpdateSchemaStatement(
													myBase, 
													"insert into base_address \r\n"
												  + "(email, country_id, guid, creation_date, creator_id, last_modification_date, last_modifier_id, owner_id, tag) \r\n"
												  + "(\r\n"
												  + "	select email, country_id, guid, creation_date, creator_id, last_modification_date, last_modifier_id, owner_id, tag \r\n"
												  + "   from base_person \r\n"
												  + ");"
										    ).execute();
											
											new PgUpdateSchemaStatement(myBase, scheme.scriptOf(BillingAddress.class)).execute();
											new PgUpdateSchemaStatement(
													myBase, 
													"insert into billing_address \r\n"
												  + "(id, submitted_to_vat, guid, creation_date, creator_id, last_modification_date, last_modifier_id, owner_id, tag) \r\n"
												  + "(\r\n"
												  + "	select id, true, guid, creation_date, creator_id, last_modification_date, last_modifier_id, owner_id, tag \r\n"
												  + "   from base_address \r\n"
												  + ");"
										    ).execute();
											
											new PgDropColumnStatement(myBase, "base_person", "email").execute();
											new PgDropColumnStatement(myBase, "base_person", "country_id").execute();
											
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(Person.class, Person::preferredCurrency), 1L).execute(); // XOF by default
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(Person.class, Person::address), 1L).execute();
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(Person.class, Person::billingAddress), 1L).execute();
											
											// set address and billing_address of each person
											new PgUpdateSchemaStatement(
													myBase, 
													"update base_person as pers \r\n"
												  + "set address_id = addr.id, \r\n"
												  + "billing_address_id = addr.id \r\n"
												  + "from base_address as addr \r\n"
												  + "where pers.guid = addr.guid \r\n"
										    ).execute();
											
											new PgUpdateSchemaStatement(
													myBase, 
													"delete from base_version where tag='WEBSITE_VERSION'"
										    ).execute();
											
											new PgUpdateSchemaStatement(
													myBase, 
													"update base_application \r\n" + 
													"set tag = 'ADMIN_SUPERVISOR' \r\n" + 
													"where user_id=1 and module='supervisor'"
										    ).execute();																					
										}
										
										if(bddVersion.isOldThan("0.0.1.0004")) {
											// mise à jour des versions < 0.0.1.0004
											new PgDropTableStatement(myBase, "billing_mobile_money_payment_receipt").execute();
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(PaymentReceipt.class, PaymentReceipt::metadata), StringUtils.EMPTY).execute();																					
										}
										
										if(bddVersion.isOldThan("0.0.1.1010")) {
											
											// mise à jour des versions < 0.0.1.1010
											new PgUpdateSchemaStatement(
												myBase, 
												"delete from base_currency where code='USD'"
										    ).execute();
											
											// rename MTN Mobile money to Momo
											new PgUpdateSchemaStatement(
												myBase, 
												"update billing_payment_method set name='Momo' where tag='MOMO'"
										    ).execute();
											
											// rename MOOV Mobile Money (Flooz) to Flooz
											new PgUpdateSchemaStatement(
												myBase, 
												"update billing_payment_method set name='Flooz' where tag='FLOOZ'"
										    ).execute();
											
											// rename Orange Money to Orange Money (we ever know)
											new PgUpdateSchemaStatement(
												myBase, 
												"update billing_payment_method set name='Orange Money' where tag='OM'"
										    ).execute();
											
											// rename Orange Money to Orange Money (we ever know)
											new PgUpdateSchemaStatement(
												myBase, 
												"update billing_payment_method set name='Orange Money' where tag='OM'"
										    ).execute();
										}
										
										if(bddVersion.isOldThan("0.0.1.1021")) {									
											// mise à jour des versions < 0.0.1.1021
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(RegistrationRequest.class, RegistrationRequest::preferredLanguage), 1L).execute();
										}
										
										if(bddVersion.isOldThan("0.0.1.1030")) {									
											// mise à jour des versions < 0.0.1.1030
											
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(Access.class, Access::module), "no-module").execute();
											new PgUpdateSchemaStatement(
												myBase, 
												"update membership_access set module = 'membership' where id  <= 3 \r\n"
										    ).execute();
											
											new PgDropColumnStatement(myBase, "membership_user", "profile_id").execute();
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(Profile.class, Profile::module), "com.membership").execute();
											
											// set all user profiles to profile user (3)
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(Application.class, Application::profile), 3L).execute();
																						
											// change profile Gratuit to profil Utilisateur
											new PgUpdateSchemaStatement(
												myBase, 
												"update membership_profile set code = 'USER_MEMBERSHIP', name='Utilisateur', tag='USER_MEMBERSHIP', parent_id = 1 where id = 3 \r\n"
										    ).execute();	
											
											new PgUpdateSchemaStatement(
												myBase, 
												"update membership_profile set code = 'ADMIN_MEMBERSHIP', tag='ADMIN_MEMBERSHIP' where id = 1 \r\n"
										    ).execute();
											
											new PgUpdateSchemaStatement(
												myBase, 
												"update membership_profile set code = 'ANONYMOUS_MEMBERSHIP', tag='ANONYMOUS_MEMBERSHIP' where id = 2 \r\n"
										    ).execute();
											
											new PgUpdateSchemaStatement(
												myBase, 
												"insert into base_application(user_id, module, profile_id, guid, creation_date, creator_id, last_modification_date, last_modifier_id, owner_id) \r\n" + 
												"select id, 'membership', 3, guid, now(), 1, now(), 1, id \r\n" +
												"from membership_user \r\n"
										    ).execute();
											
											new PgUpdateSchemaStatement(
												myBase, 
												"update base_application set tag='ADMIN_MEMBERSHIP', profile_id=1 where user_id=1 and module='membership' \r\n"
										    ).execute();
											
											new PgUpdateSchemaStatement(
												myBase, 
												"delete from base_application where user_id=2 or (user_id=1 and tag is NULL)"
										    ).execute();
											
											// set admin user to admin profile
											new PgUpdateSchemaStatement(
												myBase, 
												"update base_application set profile_id = (select id from membership_profile where tag='ADMIN_MEMBERSHIP') where user_id = 1 and module='membership' \r\n"
										    ).execute();
											
											// set other users to user profile
											new PgUpdateSchemaStatement(
												myBase, 
												"update base_application set profile_id = (select id from membership_profile where tag='USER_MEMBERSHIP') where user_id <> 1 and module='membership' \r\n"
										    ).execute();
										}
										
										// mettre à jour la version de la base de données
										bddVersion.update(Membership.REV, Membership.RELEASE_DATE, Membership.NOTES);
									}
								}								
								
								return new FkChain(
									new FkMembership(myBase)
								);
							}
						 );
		
	    new FtCli(
    		new TkApp(
				new FkRegex(
					".+", 
					new TkMembership(
							base, 
							new TkFork(fork))
					), 
				base
    		), 
    		args
	    ).start(Exit.NEVER);
	}
}
