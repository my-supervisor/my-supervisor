package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.Resource;
import com.supervisor.domain.ResourceType;
import com.supervisor.domain.Resources;
import com.supervisor.domain.SharedResource;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.impl.PxIndicators;

public final class ResourcesImpl implements Resources {

	private final RecordSet<SharedResource> source;
	private final User user;
	
	public ResourcesImpl(final RecordSet<SharedResource> source, final User user) {
		this.source = source;
		this.user = user;
	}
	
	@Override
	public List<Resource> items() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<Resource> resourceIfPresent(ResourceType type, Long id) throws IOException {
		
		Resource resource = null;
		
		// vérifier l'existence de la ressource
		switch (type) {
			case DATA_SHEET_MODEL:
				DataSheetModel model = new PgDataSheetModels(user)
				                              .where(DataSheetModel::ownerId, source.base().currentUserId())
						                      .get(id);
				
				resource = new ResourceImpl(model);
				break;
			case ACTIVITY:
				Activity activity = new PgActivities(user)
										 .ownActivities()
						                 .get(id);
				
				resource = new ResourceImpl(activity);
				break;
			case INDICATOR:
				Indicator indicator = new PxIndicators(source.of(Indicator.class))
											  .where(Indicator::ownerId, source.base().currentUserId())
								              .get(id);
				
				resource = new ResourceImpl(indicator);
				break;
			default:
				break;
		}
		
		if(resource == null)
			return Optional.empty();
		else
			return Optional.of(resource);
	}

	@Override
	public Resource resource(ResourceType type, Long id) throws IOException {
		Optional<Resource> res = resourceIfPresent(type, id);
		if(!res.isPresent())
			throw new IllegalArgumentException("La resource demandée n'existe pas !");
		
		return res.get();
	}

}
