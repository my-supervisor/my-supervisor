package com.supervisor.domain.impl;

import com.supervisor.domain.Language;
import com.supervisor.domain.Membership;
import com.supervisor.domain.Person;
import com.supervisor.domain.Persons;
import com.supervisor.domain.RegistrationRequest;
import com.supervisor.domain.RegistrationRequestStatus;
import com.supervisor.domain.User;
import com.supervisor.domain.Users;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;

import java.io.IOException;
import java.time.LocalDateTime;

public final class PxRegistrationRequest extends DomainRecordable<RegistrationRequest> implements RegistrationRequest {

	public PxRegistrationRequest(Record<RegistrationRequest> record) throws IOException {
		super(record);
	}

	@Override
	public String name() throws IOException {
		return record.valueOf(RegistrationRequest::name);
	}

	@Override
	public String email() throws IOException {
		return record.valueOf(RegistrationRequest::email);
	}

	@Override
	public String password() throws IOException {
		return record.valueOf(RegistrationRequest::password);
	}

	@Override
	public String salt() throws IOException {
		return record.valueOf(RegistrationRequest::salt);
	}

	@Override
	public LocalDateTime deadLine() throws IOException {
		return record.valueOf(RegistrationRequest::deadLine);
	}

	@Override
	public RegistrationRequestStatus status() throws IOException {
		return record.valueOf(RegistrationRequest::status);
	}

	@Override
	public User applicant() throws IOException {
		Long id = record.valueOf(RegistrationRequest::applicant);
		
		if(id == null)
			return User.EMPTY;
		else
			return new DmUser(record.of(RegistrationRequest::applicant));
	}

	@Override
	public User process() throws IOException {
		
		if(status() != RegistrationRequestStatus.PENDING)
			throw new IllegalArgumentException("La requête n'est plus valide !");
		
		Membership membership = new DmMembership(record.base(), new UserOf(this)); 
		Persons persons = membership.contacts();
		Person identity = persons.add(name(), email());
		identity.changePreferredLanguage(preferredLanguage());
		
		Users users = membership.members();
		User user = users.registerSecure(identity, password(), salt());
		
		record.entryOf(RegistrationRequest::applicant, user.id())
		      .entryOf(RegistrationRequest::status, RegistrationRequestStatus.CONFIRMED)
		      .update();
		
		return user;
	}

	@Override
	public Language preferredLanguage() throws IOException {
		return new PxLanguage(record.of(RegistrationRequest::preferredLanguage));
	}
}
