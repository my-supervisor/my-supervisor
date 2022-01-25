package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;
import java.time.LocalDateTime;

@com.supervisor.sdk.metadata.Recordable(
	name="membership_registration_request", 
	label="Requête d'inscription"
)
public interface RegistrationRequest extends Recordable {
	
	@Field(label="Nom")
	String name() throws IOException;
	
	@Field(label="Adresse mail")
	String email() throws IOException;
	
	@Field(
		label="Langue préférée", 
		rel=Relation.MANY2ONE
	)
	Language preferredLanguage() throws IOException;
	
	@Field(label="Mot de passe")
	String password() throws IOException;
	
	@Field(label="Salt")
	String salt() throws IOException;
	
	@Field(label="Date limite")
	LocalDateTime deadLine() throws IOException;
	
	@Field(label="Statut")
	RegistrationRequestStatus status() throws IOException;
	
	@Field(
		label="Demandeur", 
		rel=Relation.MANY2ONE,
		isMandatory=false
	)
	User applicant() throws IOException;
	
	User process() throws IOException;
}
