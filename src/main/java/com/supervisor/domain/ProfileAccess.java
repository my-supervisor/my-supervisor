package com.supervisor.domain;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@Recordable(
		name="profile_access",
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
