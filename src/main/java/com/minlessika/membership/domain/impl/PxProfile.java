package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Accesses;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.ProfileAccesses;
import com.minlessika.sdk.datasource.DomainRecordable;
import com.minlessika.sdk.datasource.Record;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public final class PxProfile extends DomainRecordable<Profile> implements Profile {
	
	public PxProfile(final Record<Profile> source) throws IOException {
		super(source);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(Profile::name);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(Profile::code);
	}

	@Override
	public ProfileAccesses accesses() throws IOException {
		
		return new PgProfileAccesses(this);
	}

	@Override
	public void update(String code, String name) throws IOException {
		
		record.isRequired(Profile::name, name);	
		record.isRequired(Profile::code, code);
		
		record.mustBeUnique(Profile::code, code.toUpperCase());
		
		record.entryOf(Profile::name, name)
			  .entryOf(Profile::code, code.toUpperCase())
			  .update();
	}
	
	@Override
	public boolean hasAccess(String code) throws IOException {
		return accesses().hasAccess(code);
	}

	@Override
	public boolean hasAccess(String code, String value) throws IOException {
		return accesses().hasAccess(code, value);
	}

	@Override
	public void validateAccessibility(String code) throws IOException {
		accesses().validateAccessibility(code); 
	}

	@Override
	public void validateAccessibility(String code, String value) throws IOException {
		accesses().validateAccessibility(code, value);
	}

	@Override
	public Accesses exceptedAccesses() throws IOException {
		return accesses().exceptedAccesses();
	}

	@Override
	public Profile parent() throws IOException {
		
		Long profileId = record.valueOf(Profile::parent);
		if(profileId == null)
			return Profile.EMPTY;
		
		Record<Profile> rec = record.of(Profile::parent);
		return new PxProfile(rec);
	}

	@Override
	public void changeParent(Profile profile) throws IOException {
		
		record.mustCheckThisCondition(
				!id().equals(profile.id()), 
				"Le profil ne peut pas être son propre parent !"
		);
		
		record.entryOf(Profile::parent, profile.id())
		      .update();
	}

	@Override
	public boolean isUpperOrEqualTo(Profile profile) throws IOException {
		
		if(profile == Profile.EMPTY || id().equals(profile.id()))
			return true;
		
		Profile parent = profile.parent();
		if(parent == Profile.EMPTY)
			return false;
		
		return isUpperOrEqualTo(parent);
	}

	@Override
	public String module() throws IOException {
		return record.valueOf(Profile::module);
	}

	@Override
	public boolean isAnonymous() throws IOException {
		return StringUtils.isNotBlank(tag()) && tag().startsWith(ANONYMOUS_TAG);
	}

	@Override
	public boolean isSimpleUser() throws IOException {
		return StringUtils.isNotBlank(tag()) && tag().startsWith(USER_TAG);
	}
	
	@Override
	public boolean isAdmin() throws IOException {
		return StringUtils.isNotBlank(tag()) && tag().startsWith(ADMIN_TAG);
	}
}
