package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.AccessParam;
import com.minlessika.membership.domain.Accesses;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.ProfileAccess;
import com.minlessika.membership.domain.ProfileAccessParam;
import com.minlessika.membership.domain.ProfileAccesses;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;

import java.io.IOException;

public final class PgProfileAccesses extends DomainRecordables<ProfileAccess, ProfileAccesses> implements ProfileAccesses {

	private final Profile profile;
	
	public PgProfileAccesses(final Profile profile) throws IOException {
		super(
				PxProfileAccess.class, 
				viewSource(profile)
		);
		
		this.profile = profile;
	}
	
	private PgProfileAccesses(final RecordSet<ProfileAccess> source, final Profile profile) throws IOException {
		super(
				PxProfileAccess.class, 
				source
		);
		
		this.profile = profile;
	}
	
	private static RecordSet<ProfileAccess> viewSource(final Profile profile) throws IOException{
		Table table = new TableImpl(ProfileAccess.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select dest.*, src.name, src.code from %s as dest\r\n" + 
										"	left join %s as src on src.id = dest.access_inherited_id\r\n" + 
										") as %s",
							table.name(),
							new TableImpl(Access.class).name(),
							table.name()
				);
		
		return profile.base()
				      .select(ProfileAccess.class, viewScript)
				      .where(ProfileAccess::profile, profile.id());
	}
	
	@Override
	protected ProfileAccesses domainSetOf(final RecordSet<ProfileAccess> source) throws IOException {
		return new PgProfileAccesses(source, profile);
	}

	@Override
	public ProfileAccess add(Access access) throws IOException {
		
		// vérifier qu'il n'a pas déjà été ajouter à ce profil
		ProfileAccesses accessAlreadyExists = where(ProfileAccess::accessInherited, access.id());
		if(accessAlreadyExists.any())
			return accessAlreadyExists.first();
		
		source.mustBeUnique(ProfileAccess::accessInherited, access.id(), ProfileAccess::profile, profile.id());
		
		Record<ProfileAccess> record = source.entryOf(ProfileAccess::profile, profile.id())
				  					         .entryOf(ProfileAccess::accessInherited, access.id())
				  					         .add();
		
		ProfileAccess profileAccess = domainOf(record);
		
		// Ajouter les paramètres du droit d'accès
		for (AccessParam param : access.parameters().items()) {
			profileAccess.parameterValues().put(param, param.defaultValue());
		}
		
		return profileAccess;
	}
	
	@Override
	public boolean hasAccess(String code) throws IOException {
		
		if(profile.isAdmin())
			return true;
		
		return where(ProfileAccess::code, code).any();
	}

	@Override
	public boolean hasAccess(String code, String value) throws IOException {

		if(profile.isAdmin())
			return true;
		
		if(!hasAccess(code))
			return false;
	
		ProfileAccess profileAccess = where(ProfileAccess::code, code).first();
		ProfileAccessParam param = profileAccess.parameterValues().first();
		
		switch (param.quantifier()) {
			case MIN:
				return Double.parseDouble(param.value()) <= Double.parseDouble(value);
			case MAX:
				return Double.parseDouble(param.value()) >= Double.parseDouble(value);
			default:
				switch (param.valueType()) {
					case NUMBER:
						return Double.parseDouble(param.value()) == Double.parseDouble(value);
					case BOOLEAN:
						return Boolean.parseBoolean(param.value()) == Boolean.parseBoolean(value);
					default:
						return param.value() == value;
				}				
		}
	}

	@Override
	public void validateAccessibility(String code) throws IOException {
		
		if(!hasAccess(code)) {
			Access access = new PxAccesses(source.of(Access.class), profile.module()).where(Access::code, code).first();
			throw new IllegalArgumentException(String.format("Vous ne pouvez pas %s !", access.name()));
		}		
	}

	@Override
	public void validateAccessibility(String code, String value) throws IOException {
				
		if(!hasAccess(code, value)) {
			ProfileAccess access = where(ProfileAccess::code, code).first();
			ProfileAccessParam param = access.parameterValues().first();
			
			throw new IllegalArgumentException(String.format(param.message(), param.value()));
		}
	}

	@Override
	public Accesses exceptedAccesses() throws IOException {
		
		Table table = new TableImpl(Access.class);
		
		String viewScript = String.format(
								"(\r\n" + 
								"	select * from %s \r\n" + 
								"	where id NOT IN (select access_inherited_id from %s where profile_id=%s) \r\n" + 
								") as %s",
								table.name(),
								new TableImpl(ProfileAccess.class).name(),
								profile.id(),
								table.name()
							);
		
		return new PxAccesses(source.of(Access.class, viewScript), profile.module());
	}	
}
