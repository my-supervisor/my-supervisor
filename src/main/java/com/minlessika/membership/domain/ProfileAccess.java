package com.minlessika.membership.domain;

import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		name="membership_profile_access", 
		label="Droit d'accès d'un profil",
		inheritFields = false
)
public interface ProfileAccess extends Access {
	
	@Field(
		label="Profil", 
		rel=Relation.MANY2ONE
	)
	Profile profile() throws IOException;
	
	@Field(
		label="Droit d'accès hérité", 
		rel=Relation.MANY2ONE
	)
	Access accessInherited() throws IOException;
	
	ProfileAccessParams parameterValues() throws IOException;
}
