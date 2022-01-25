package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.Optional;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.User;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.Resource;
import com.supervisor.domain.ResourceType;
import com.supervisor.domain.Resources;
import com.supervisor.domain.Sharing;
import com.supervisor.domain.Supervisor;

public final class PxSharing extends DomainRecordables<SharedResource, Sharing> implements Sharing {
	
	public PxSharing(final RecordSet<SharedResource> source) throws IOException {
		super(PxSharedResource.class, source);
		
		this.source = source.where(SharedResource::ownerId, base().currentUserId());
	}

	@Override
	public SharedResource share(Long resourceId, ResourceType type, User subscriber) throws IOException {
		
		User follower = new DmUser(
							source.of(User.class)
							      .get(base().currentUserId())
					    );
		
		// vérifier l'existence de la ressource
		Optional<Resource> res = resources().resourceIfPresent(type, resourceId);
		
		Long count;
		switch (type) {
			case DATA_SHEET_MODEL:
				if(!res.isPresent())
					throw new IllegalArgumentException("Le modèle de feuille de données à partager n'existe pas !");
				
				count = source.where(SharedResource::type, ResourceType.DATA_SHEET_MODEL).count();
				follower.profileOf(Supervisor.NAME).validateAccessibility("SHARE_DATA_SHEET_MODEL", String.format("%s", count + 1));
				
				break;
			case ACTIVITY:
				if(!res.isPresent())
					throw new IllegalArgumentException("L'activité à partager n'existe pas !");
				
				count = source.where(SharedResource::type, ResourceType.ACTIVITY).count();
				follower.profileOf(Supervisor.NAME).validateAccessibility("SHARE_ACTIVITY", String.format("%s", count + 1));
				break;
			case INDICATOR:
				if(!res.isPresent())
					throw new IllegalArgumentException("L'indicateur à partager n'existe pas !");
				break;
			default:
				break;
		}
		
		// vérifier que la ressource n'est pas déjà partagée
		Optional<SharedResource> resource = resource(resourceId, type, subscriber);
		if(resource.isPresent())
			return resource.get();
		
		Record<SharedResource> record = source.entryOf(SharedResource::type, type)
									          .entryOf(SharedResource::subscriber, subscriber.id())
									          .entryOf(SharedResource::resourceId, resourceId)
									          .add();

		return domainOf(record);
	}

	@Override
	public void unsubscribe(SharedResource resource) throws IOException {
		remove(resource);
	}

	@Override
	public Optional<SharedResource> resource(Long resourceId, ResourceType type, User subscriber) throws IOException {
		
		Sharing resourceToSearch =  where(SharedResource::resourceId, resourceId)
				                   .where(SharedResource::type, type)
				                   .where(SharedResource::subscriber, subscriber.id());
		
		if(resourceToSearch.isEmpty())
			return Optional.empty();
		else
			return Optional.of(resourceToSearch.first());
	}
	
	private Resources resources() throws IOException {
		return new ResourcesImpl(source, new UserOf(this));
	}

	@Override
	public SharedResource share(Long resourceId, ResourceType type, String email) throws IOException {
		
		if(StringUtils.isBlank(email))
			throw new IllegalArgumentException("Vous devez spécifier une adresse email !");
		
		Optional<User> user = new PgUsers(new UserOf(this)).userOf(email);
		if(!user.isPresent())
			throw new IllegalArgumentException(String.format("L'adresse email %s n'est pas liée à un utilisateur !", email));
		
		if(user.get().id() == base().currentUserId())
			throw new IllegalArgumentException("Vous êtes déjà le propriétaire de la ressource à partager !");
		
		return share(resourceId, type, user.get());
	}
}
