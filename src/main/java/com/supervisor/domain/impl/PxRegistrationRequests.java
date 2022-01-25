package com.supervisor.domain.impl;

import com.supervisor.domain.Language;
import com.supervisor.domain.RegistrationRequest;
import com.supervisor.domain.RegistrationRequestStatus;
import com.supervisor.domain.RegistrationRequests;
import com.supervisor.domain.Users;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.secure.EncryptedWord;
import com.supervisor.sdk.secure.EncryptedWordImpl;

import java.io.IOException;
import java.time.LocalDateTime;

public final class PxRegistrationRequests extends DomainRecordables<RegistrationRequest, RegistrationRequests> implements RegistrationRequests  {
	
	public PxRegistrationRequests(final RecordSet<RegistrationRequest> source) {
		super(PxRegistrationRequest.class, source);
	}

	@Override
	public RegistrationRequest request(String name, String email, String password, String confirmedPassword, Language preferredLanguage) throws IOException {
		
		source.isRequired(RegistrationRequest::name, name);
		source.isRequired(RegistrationRequest::email, email);
		source.isRequired(RegistrationRequest::password, password);
		
		source.mustCheckThisCondition(
				password.equals(confirmedPassword),
				"Le mot de passe n'est pas confirmé !"
		);
		
		Users members = new PgUsers(new UserOf(this)); 
		if(members.userOf(email.toLowerCase()).isPresent())
			throw new IllegalArgumentException("L'adresse mail figure déjà dans nos bases !");
			
		EncryptedWord securedPassword = new EncryptedWordImpl(password);
		
		Record<RegistrationRequest> record = source.entryOf(RegistrationRequest::name, name)
													.entryOf(RegistrationRequest::email, email.toLowerCase())
												    .entryOf(RegistrationRequest::password, securedPassword.value())
												    .entryOf(RegistrationRequest::salt, securedPassword.salt())
												    .entryOf(RegistrationRequest::deadLine, LocalDateTime.now().plusDays(3)) 
												    .entryOf(RegistrationRequest::status, RegistrationRequestStatus.PENDING)
												    .entryOf(RegistrationRequest::preferredLanguage, preferredLanguage.id()) 
												    .add();

		return domainOf(record);
	}
}
