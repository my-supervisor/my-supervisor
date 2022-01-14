package com.minlessika.supervisor.interaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.impl.PgActivities;

public final class InteractionsImpl implements Interactions {

	private final Activity receiver;
	
	public InteractionsImpl(final Activity receiver) {
		this.receiver = receiver;
	}

	@Override
	public List<Interaction> actives() throws IOException {
		final List<Interaction> actives = new ArrayList<>();
		for (Interaction interaction : items()) {
			if(interaction.active()) {
				actives.add(interaction);
			}
		}
		
		return actives;
	}

	@Override
	public List<Interaction> items() throws IOException {
		
		final List<Interaction> items = new ArrayList<>();
		for (Activity activity : new PgActivities(new OwnerOf(receiver)).where(Activity::isTemplate, receiver.isTemplate()).items()) {
			if(activity.id().equals(receiver.id()))
				continue;
			
			final Interaction interaction = new InteractionImpl(activity, receiver);
			if(!interaction.interactables().isEmpty()) {
				items.add(interaction);
			}
		}
		
		return items;
	}

	@Override
	public Interaction getWith(Activity actor) throws IOException {
		return new InteractionImpl(actor, receiver);
	}

}
