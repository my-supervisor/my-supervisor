package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Language;
import com.minlessika.membership.domain.RegistrationRequest;
import com.minlessika.membership.domain.RegistrationRequestStatus;
import com.minlessika.membership.domain.RegistrationRequests;
import com.minlessika.membership.domain.Users;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.secure.EncryptedWord;
import com.minlessika.sdk.secure.EncryptedWordImpl;

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
