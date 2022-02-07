package com.supervisor.domain.impl;

import com.supervisor.domain.Profile;
import com.supervisor.domain.Profiles;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.comparators.Matchers;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public final class PxProfiles extends DomainRecordables<Profile, Profiles> implements Profiles {
	
	public PxProfiles(final Base base) throws IOException {
		this(base.select(Profile.class));
	}
	
	public PxProfiles(final RecordSet<Profile> source) throws IOException {
		super(PxProfile.class, source);
	}

	@Override
	protected Profiles domainSetOf(final RecordSet<Profile> source) throws IOException {
		return new PxProfiles(source);
	}
	
	@Override
	public Profile add(String code, String name) throws IOException {
		
		source.isRequired(Profile::name, name);	
		source.isRequired(Profile::code, code);
		
		source.mustBeUnique(Profile::code, code.toUpperCase());
		
		Record<Profile> record = source.entryOf(Profile::name, name)
				  					   .entryOf(Profile::code, code.toUpperCase())
						      		   .add();
		
		return domainOf(record);
	}

	@Override
	public Profile admin() throws IOException {
		return where(Profile::code, Profile.ADMIN).first();
	}
	
	@Override
	public void remove(Profile item) throws IOException {
		
		if(!contains(item))
			throw new IllegalArgumentException("Profile doesn't belong to List of profiles !");
		
		// le profil ne doit pas être un profil prédéfini
		if(StringUtils.isNotBlank(item.tag()))
			throw new IllegalArgumentException("Vous ne pouvez pas supprimer un profil prédéfini !");
		
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
		return where(Profile::code, Profile.ANONYMOUS).first();
	}

	@Override
	public Profile simpleUser() throws IOException {
		return where(Profile::code, Profile.USER).first();
	}
}
