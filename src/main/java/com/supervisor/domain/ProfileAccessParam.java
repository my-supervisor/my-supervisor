package com.supervisor.domain;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@Recordable(
		name="membership_profile_access_param", 
		label="Paramètre du droit d'accès d'un profil",
		inheritFields = false
)
public interface ProfileAccessParam extends AccessParam {
	
	Profile profile() throws IOException;
	
	@Field(
		label="Droit d'accès parent", 
		rel=Relation.MANY2ONE
	)
	ProfileAccess access() throws IOException;
		
	@Field(
		label="Paramètre hérité", 
		rel=Relation.MANY2ONE
	)
	AccessParam paramInherited() throws IOException;
	
	@Field(label="Valeur")
	String value() throws IOException;
	
	void update(String value) throws IOException;
}
