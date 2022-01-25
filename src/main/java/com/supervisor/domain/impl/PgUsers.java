package com.supervisor.domain.impl;

import com.supervisor.domain.Address;
import com.supervisor.domain.Person;
import com.supervisor.domain.Persons;
import com.supervisor.domain.User;
import com.supervisor.domain.Users;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.sdk.datasource.comparators.Matchers;
import com.supervisor.sdk.datasource.conditions.Filter;
import com.supervisor.sdk.datasource.conditions.pgsql.PgFilter;
import com.supervisor.sdk.secure.EncryptedWord;
import com.supervisor.sdk.secure.EncryptedWordImpl;
import com.supervisor.sdk.translation.I18n;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Optional;

public final class PgUsers extends DomainRecordables<User, Users> implements Users {

	private final String ADMIN_TAG = "ADMIN";
	private final String ANONYMOUS_TAG = "ANONYMOUS";
	
	private final User user;
	
	public PgUsers(final User user) throws IOException {
		super(DmUser.class, viewSource(user));
		
		this.user = user;
	}
	
	private PgUsers(final RecordSet<User> source, final User user) {
		super(DmUser.class, source);
		
		this.user = user;
	}
	
	@Override
	protected Users domainSetOf(final RecordSet<User> source) throws IOException {
		return new PgUsers(source, user);
	}

	private static RecordSet<User> viewSource(final User user) throws IOException{
		Table table = new TableImpl(User.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select tuser.*, pers.name, pers.is_company, pers.photo, addr.email, addr.country_id \r\n" + 
										"	from %s as tuser\r\n" + 
										"	left join %s as pers on pers.id = tuser.id \r\n" + 
										"   left join %s as addr on addr.id = pers.address_id \r\n" + 
										") as %s",
							table.name(),
							new TableImpl(Person.class).name(),
							new TableImpl(Address.class).name(),
							table.name()
				);
		
		return user.base().select(User.class, viewScript);
	}

	@Override
	public User registerSecure(Person identity, String encryptedPassword, String salt) throws IOException {
		
		source.isRequired(User::password, encryptedPassword);	
		source.isRequired(User::salt, salt);
		
		EncryptedWord securedPassword = new EncryptedWordImpl(encryptedPassword, salt, true);
				
		Record<User> record = source.entryOf(User::id, identity.id())
								    .entryOf(User::password, securedPassword.value())
								    .entryOf(User::salt, securedPassword.salt())
								    .entryOf(User::active, false)								     
								    .add();
				
		return domainOf(record);
	}
	
	@Override
	public User registerUnSecure(Person identity, String password, String confirmedPassword) throws IOException {
		
		source.isRequired(User::password, password);	
		
		source.mustCheckThisCondition(
				password.equals(confirmedPassword), 
				I18n.localizeText("password_is_not_confirmed")
		);
		
		EncryptedWord securedPassword = new EncryptedWordImpl(password);
		
		return registerSecure(identity, securedPassword.value(), securedPassword.salt());
	}

	@Override
	public User signin(String email, String password, boolean secure) throws IOException {
		
		if(StringUtils.isBlank(email))
			throw new IllegalArgumentException(I18n.localizeText("email_address_must_be_specified"));

		source.isRequired(User::password, password);
		
		// vérifier que le mail existe
		Optional<User> optUserOfEmail = userOf(email);
		
		if(!optUserOfEmail.isPresent())
			throw new IllegalArgumentException(I18n.localizeText("email_address_has_not_been_found"));
	
		User userOfEmail = optUserOfEmail.get();
		EncryptedWord encryptedPassword = new EncryptedWordImpl(userOfEmail.password(), userOfEmail.salt(), true);
		
		if(!encryptedPassword.verify(password, secure))
			throw new IllegalArgumentException(I18n.localizeText("password_doesnt_match"));
		
		if(!userOfEmail.active())
			throw new IllegalArgumentException(I18n.localizeText("your_account_is_not_active"));
		
		return userOfEmail; 
	}
	
	@Override
	public Optional<User> userOf(String email) throws IOException {

		// vérifier que le mail existe
		Filter<Address> filter = new PgFilter<>(Address.class).add(Address::email, Matchers.equalsTo(email));	
		Users userOfEmail = where(filter);
		if(userOfEmail.isEmpty())
			return Optional.empty();
		else{
				return Optional.of(userOfEmail.first());
		}
	}
	
	@Override
	public void remove(User item) throws IOException {
		
		Persons persons = new DmPersons(source.of(Person.class));
		// vérifier que l'utilisateur n'est pas Admin ou Anonymous
		String tag = item.tag();
		if(StringUtils.isNotBlank(tag) && (tag.equals(ADMIN_TAG) || tag.equals(ANONYMOUS_TAG)))
			throw new IllegalArgumentException(I18n.localizeText("you_cant_remove_an_predefined_user")); 		
		
		// vérifier que l'utilisateur est activé
		if(item.active())
			throw new IllegalArgumentException(I18n.localizeText("you_cant_remove_an_active_user"));
		
		persons.remove(item);
		
		super.remove(item);
	}
}
