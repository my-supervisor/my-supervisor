package com.minlessika.supervisor.server;

import java.util.Arrays;

import javax.sql.DataSource;

import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.Fork;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtCli;

import com.minlessika.core.takes.BddVersion;
import com.minlessika.core.takes.PgBddVersion;
import com.minlessika.core.takes.PgBddVersions;
import com.minlessika.core.takes.TkApp;
import com.minlessika.membership.domain.Profile;
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
import com.minlessika.sdk.pgsql.SettingData;
import com.minlessika.sdk.pgsql.metadata.PgFieldOfMethod;
import com.minlessika.sdk.pgsql.statement.PgCreateColumnStatement;
import com.minlessika.sdk.pgsql.statement.PgDropColumnStatement;
import com.minlessika.sdk.pgsql.statement.PgDropTableStatement;
import com.minlessika.sdk.pgsql.statement.PgUpdateSchemaStatement;
import com.minlessika.sdk.websockets.WebSocketNettosphere;
import com.minlessika.sdk.websockets.WebSocketServer;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataDomainDefinition;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataLinkOperator;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.MappedDataField;
import com.minlessika.supervisor.domain.SimpleDataField;
import com.minlessika.supervisor.domain.Supervisor;

public final class Main {
	
    public static void main(final String... args) throws Exception {
    	    	    
    	AppInfo appInfo = new AppInfoImpl(Supervisor.NAME, args);
    	BaseCredentials credentials = new PgBaseCredentials(args);
		DataSources dataSourceFactory = new PgHikariDataSourceFactory(credentials);
		DataSource dataSource = dataSourceFactory.create();
		
		WebSocketServer wsServer = new WebSocketNettosphere(
										Arrays.asList(ActivityRealtime.class), 
										args
								   );
		wsServer.start();
		
		Base base = new PgBase(dataSource, appInfo, wsServer);
    	
		Fork fork = new SqlTxn(base)
					    .call(
							  (final Base myBase) -> {
									
								  if(new PgBddVersions(myBase).contains(Supervisor.NAME)) {
									  
									  BddVersion bddVersion = new PgBddVersion(base, Supervisor.NAME);			
									  if(!bddVersion.productRange().equals(Supervisor.PRODUCT_RANGE))
										  throw new IllegalArgumentException("La gamme n'est pas compatible à celle du produit installée !");
									
									  if(bddVersion.isNewThan(Supervisor.REV))
										  throw new IllegalArgumentException("La base de données installée est plus récente que le produit !");																									
									
									  if(bddVersion.isOldThan(Supervisor.REV)) {
										// Mettre à niveau ne manière incrémentale
										
										if(bddVersion.isOldThan("1.1.0.9210")) {
											// mise à jour des versions < 1.1.0.9210
											new PgDropColumnStatement(myBase, "supervisor_indicator_general_setting", "license").execute();
											new PgDropColumnStatement(myBase, "supervisor_indicator_general_setting", "designer_id").execute();
										}
										
										if(bddVersion.isOldThan("1.1.0.9502")) {
											// mise à jour des versions < 1.1.0.9502
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(SimpleDataField.class, SimpleDataField::mustEditOnce), false).execute();
											
											new PgUpdateSchemaStatement(
													myBase, 
													"update supervisor_data_field \r\n"
												  + "set must_edit_once = true, \r\n"
												  + "is_read_only = false \r\n"
												  + "where code = 'REF' and user_scope = 'SYSTEM' \r\n"
										    ).execute();
										}
										
										if(bddVersion.isOldThan("1.1.0.9503")) {
											// mise à jour des versions < 1.1.0.9503
											new PgDropTableStatement(myBase, "supervisor_indicator_template_like").execute();											
											new PgDropTableStatement(myBase, "supervisor_indicator_template_subscription").execute();
											new PgDropTableStatement(myBase, "supervisor_indicator_template_view").execute();
											new PgDropTableStatement(myBase, "supervisor_indicator_template_published").execute();
										}
										
										if(bddVersion.isOldThan("1.1.0.9600")) {
											// mise à jour des versions < 1.1.0.9600
											
											new PgUpdateSchemaStatement(
													myBase, 
													"DO $$ DECLARE\r\n" + 
													"    r RECORD;\r\n" + 
													"BEGIN\r\n" + 
													"    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public' and tablename LIKE 'supervisor_%') LOOP\r\n" + 
													"        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';\r\n" + 
													"    END LOOP;\r\n" + 
													"END $$;"
										    ).execute();
										}
										
										if(bddVersion.isOldThan("1.1.0.9720")) {
											// mise à jour des versions < 1.1.0.9720
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(MappedDataField.class, MappedDataField::operator), DataLinkOperator.NONE.name()).execute();
										}
										
										if(bddVersion.isOldThan("1.1.0.9900")) {
											// mise à jour des versions < 1.1.0.9900
											
											// create extended parent table
											new PgUpdateSchemaStatement(
												myBase, 
												new PgBaseScheme().scriptOf(FormularExtendedToParentSource.class)
										    ).execute();
											
											// load data to table
											new PgUpdateSchemaStatement(
												myBase, 
												"insert into supervisor_formular_extended_to_parent_source ( \r\n" + 
												"	expr_id, list_source_id, field_id, guid, creation_date, creator_id, last_modification_date, last_modifier_id, owner_id, tag \r\n" + 
												")	\r\n" + 
												"select expression_id, list_source_id, parent_id, guid, creation_date, creator_id, last_modification_date, last_modifier_id, owner_id, tag from ( \r\n" + 
												"SELECT dtarg.*, arg.expression_id, arg.type, arg.no, dt.style, ltdt.id as list_source_id, dtarg.field_id as reference_id, dt_parent.field_id as parent_id FROM supervisor_data_field_expression_arg dtarg \r\n" + 
												"left join supervisor_expression_arg arg on arg.id = dtarg.id \r\n" + 
												"left join supervisor_data_field dt on dt.id = dtarg.field_id \r\n" + 
												"left join ( \r\n" + 
												"	select * from  ( \r\n" + 
												"	SELECT dtarg.*, arg.expression_id, arg.type, arg.no, dt.style, dt.model_id FROM supervisor_data_field_expression_arg dtarg \r\n" + 
												"	left join supervisor_expression_arg arg on arg.id = dtarg.id \r\n" + 
												"	left join supervisor_data_field dt on dt.id = dtarg.field_id \r\n" + 
												"	WHERE arg.expression_id IN ( \r\n" + 
												"		SELECT id FROM supervisor_formular_expression WHERE type = 'EXTENDED_TO_PARENT' \r\n" + 
												"	) AND arg.no = 2 \r\n" + 
												"	) as dt_parent	\r\n" + 
												") as dt_parent on dt_parent.expression_id = arg.expression_id \r\n" + 
												"left join supervisor_list_data_field_source ltdt on ltdt.model_id = dt_parent.model_id \r\n" + 
												"WHERE arg.expression_id IN ( \r\n" + 
												"	SELECT id FROM supervisor_formular_expression WHERE type = 'EXTENDED_TO_PARENT' \r\n" + 
												") AND arg.no = 1	\r\n" + 
												") as dt_reference\r\n" + 
												"where list_source_id IS NOT NULL"
										    ).execute();											
											
											// remove unused argument
											new PgUpdateSchemaStatement(
												myBase, 
												"DELETE FROM supervisor_expression_arg \r\n" + 
												"WHERE expression_id IN ( \r\n" +
												"	SELECT id FROM supervisor_formular_expression WHERE type = 'EXTENDED_TO_PARENT' \r\n" +
												") AND no = 2"
										    ).execute();		
										}
										
										if(bddVersion.isOldThan("1.1.1.0001")) {
											// mise à jour des versions < 1.1.1.0001
											
											// change constraint on model_id pass from data_sheet_model to data_model index
											new PgUpdateSchemaStatement(
													myBase, 
													"ALTER TABLE supervisor_aggregated_model DROP CONSTRAINT supervisor_aggregated_model_model_id_fkey;"
										    ).execute();
											
											new PgUpdateSchemaStatement(
													myBase, 
													"ALTER TABLE supervisor_aggregated_model ADD CONSTRAINT supervisor_aggregated_model_model_id_fkey FOREIGN KEY (model_id) \r\n" + 
													"REFERENCES supervisor_data_model (id) MATCH SIMPLE \r\n" + 
													"ON UPDATE NO ACTION ON DELETE CASCADE"
										    ).execute();
											
											// add core column and copy all model_id values in it
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(AggregatedModel.class, AggregatedModel::coreModel), 1L).execute();
											
											new PgUpdateSchemaStatement(
													myBase, 
													"update supervisor_aggregated_model \r\n"
												  + "set core_model_id = model_id"
										    ).execute();
										}
										
										if(bddVersion.isOldThan("1.1.1.0010")) {
											// mise à jour des versions < 1.1.1.0010

											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(DataModel.class, DataModel::interacting), false).execute();
										}
										
										if(bddVersion.isOldThan("1.1.1.0030")) {
											// mise à jour des versions < 1.1.1.0030

											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(AggregatedModel.class, AggregatedModel::dateReference), 2).execute();
											
											// set default date reference to document date field
											new PgUpdateSchemaStatement(
													myBase, 
													"update supervisor_aggregated_model as amodel \r\n" + 
													"set date_reference_id = field.id \r\n" + 
													"from supervisor_data_model as model \r\n" + 
													"left join supervisor_data_field as field on field.model_id = model.id \r\n" + 
													"where field.code = 'DATE' and  amodel.core_model_id = model.id"
										    ).execute();
										}
										
										if(bddVersion.isOldThan("1.1.1.0040")) {
											// mise à jour des versions < 1.1.1.0040
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(DataLink.class, DataLink::dataDomainDefinition), DataDomainDefinition.DURING_PERIOD.name()).execute();
										}
										
										if(bddVersion.isOldThan("1.1.1.0060")) {
											// mise à jour des versions < 1.1.1.0060
											new PgCreateColumnStatement(myBase, new PgFieldOfMethod(Activity.class, Activity::appOwner), 1).execute();
											
											new PgUpdateSchemaStatement(
													myBase, 
													"update supervisor_activity as act \r\n" + 
													"set app_owner_id = app.id \r\n" + 
													"from base_application as app \r\n" + 
													"where app.user_id = act.owner_id and app.module = 'supervisor'"
										    ).execute();
										}
										
										if(bddVersion.isOldThan("1.1.1.0080")) {
											// mise à jour des versions < 1.1.1.0080
											
											new PgUpdateSchemaStatement(
												myBase, 
												"update membership_access set module = 'supervisor' where id > 3 \r\n"
										    ).execute();
											
											// assign PRIVILEGE, PRO, GOLD, CLASSIC to supervisor
											new PgUpdateSchemaStatement(
													myBase, 
													"update membership_profile \r\n" + 
													"set module = 'supervisor' \r\n" + 
													"where creator_id = 3"
										    ).execute();
											
											// create default profiles
											final BaseScheme scheme = new PgBaseScheme();
											new SettingData(scheme, Profile.class, base).execute();
											
											// remove all access rights of admin profile
											new PgUpdateSchemaStatement(
													myBase, 
													"delete from membership_profile_access \r\n" + 
													"where profile_id = 1"
										    ).execute();
											
											// assign all access rights from profile 3 to USER_SUPERVISOR
											new PgUpdateSchemaStatement(
													myBase, 
													"update membership_profile_access \r\n" + 
													"set profile_id = (select id from membership_profile where tag = 'USER_SUPERVISOR') \r\n" +
													"where profile_id = 3"
										    ).execute();
											
											// set hierachic between profiles
											// parent of PRIVILEGE is ADMIN
											new PgUpdateSchemaStatement(
													myBase, 
													"update membership_profile \r\n" + 
													"set parent_id = (select id from membership_profile where code = 'ADMIN_SUPERVISOR') \r\n" +
													"where code = 'PRIVILEGE'"
										    ).execute();
											
											// parent of USER_SUPERVISOR is CLASSIC
											new PgUpdateSchemaStatement(
													myBase, 
													"update membership_profile \r\n" + 
													"set parent_id = (select id from membership_profile where code = 'CLASSIC') \r\n" +
													"where code = 'USER_SUPERVISOR'"
										    ).execute();
											
											// parent of ANONYMOUS_SUPERVISOR is USER_SUPERVISOR
											new PgUpdateSchemaStatement(
													myBase, 
													"update membership_profile \r\n" + 
													"set parent_id = (select id from membership_profile where code = 'USER_SUPERVISOR') \r\n" +
													"where code = 'ANONYMOUS_SUPERVISOR'"
										    ).execute();
											
											// set admin user to admin profile
											new PgUpdateSchemaStatement(
												myBase, 
												"update base_application set profile_id = (select id from membership_profile where tag='ADMIN_SUPERVISOR') where user_id = 1 and module='supervisor' \r\n"
										    ).execute();
											
											// set other users to user profile
											new PgUpdateSchemaStatement(
												myBase, 
												"update base_application set profile_id = (select id from membership_profile where tag='USER_SUPERVISOR') where user_id <> 1 and module='supervisor' \r\n"
										    ).execute();
										}
										
										if(bddVersion.isOldThan("1.1.1.0085")) {
											// mise à jour des versions < 1.1.1.0085

											new PgUpdateSchemaStatement(
													myBase, 
													"update membership_plan \r\n" + 
													"set profile_id = 10 \r\n" + 
													"where profile_id = 3 \r\n"
										    ).execute();
											
											new PgUpdateSchemaStatement(
													myBase, 
													"update supervisor_activity_template_published \r\n" + 
													"set profile_id = 10 \r\n" + 
													"where profile_id = 3 \r\n"
										    ).execute();
										}
										
										// mettre à jour la version de la base de données
										bddVersion.update(Supervisor.REV, Supervisor.RELEASE_DATE, Supervisor.NOTES);
									  }	
								  }								  						  
									
								  return new FkChain(
										new FkSupervisor(myBase)
								  );
							}
						 );
    	
        new FtCli(
        		new TkApp(
        				new FkRegex(
        					".+", 
        					new TkSupervisor(
        							base, 
        							new TkFork(fork))
        					), 
        				base
        		), 
        		args
        ).start(Exit.NEVER);
	}
}
