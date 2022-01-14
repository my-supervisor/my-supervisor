package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Application;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.Profiles;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.comparators.Matchers;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public final class PxProfiles extends DomainRecordables<Profile, Profiles> implements Profiles {
	
	private final String module;
	
	public PxProfiles(final Base base, final String module) throws IOException {
		this(base.select(Profile.class), module);
	}
	
	public PxProfiles(final RecordSet<Profile> source, final String module) throws IOException {
		super(PxProfile.class, source);
		
		this.module = module;
		this.source = source.where(Profile::module, module);
	}

	@Override
	protected Profiles domainSetOf(final RecordSet<Profile> source) throws IOException {
		return new PxProfiles(source, module);
	}
	
	@Override
	public Profile add(String code, String name) throws IOException {
		
		source.isRequired(Profile::name, name);	
		source.isRequired(Profile::code, code);
		
		source.mustBeUnique(Profile::code, code.toUpperCase());
		
		Record<Profile> record = source.entryOf(Profile::name, name)
				  					   .entryOf(Profile::code, code.toUpperCase())
				  					   .entryOf(Profile::module, module)
						      		   .add();
		
		return domainOf(record);
	}

	@Override
	public Profile admin() throws IOException {
		return where(Profile::tag, Matchers.startsWith(Profile.ADMIN_TAG)).first();
	}
	
	@Override
	public void remove(Profile item) throws IOException {
		
		if(!contains(item))
			throw new IllegalArgumentException("Profile doesn't belong to List of profiles !");
		
		// le profil ne doit pas être un profil prédéfini
		if(StringUtils.isNotBlank(item.tag()))
			throw new IllegalArgumentException("Vous ne pouvez pas supprimer un profil prédéfini !");
		
		// le profil ne doit pas avoir été utilisé
		final User user = new UserOf(this);
		if(user.applications().where(Application::profile, item.id()).any())
			throw new IllegalArgumentException(String.format("Le profil %s est déjà utilisé !", item.name()));
		
		super.remove(item);
	}

	@Override
	public Profile get(String code) throws IOException {
		return where(Profile::code, code).first();
	}

	@Override
	public Profile getByTag(String tag) throws IOException {
		return where(Profile::tag, tag).first();
	}

	@Override
	public Profile anonymous() throws IOException {
		return where(Profile::tag, Matchers.startsWith(Profile.ANONYMOUS_TAG)).first();
	}

	@Override
	public Profile simpleUser() throws IOException {
		return where(Profile::tag, Matchers.startsWith(Profile.USER_TAG)).first();
	}
}
