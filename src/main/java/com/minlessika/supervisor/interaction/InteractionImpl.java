package com.minlessika.supervisor.interaction;

import java.io.IOException;

import com.minlessika.supervisor.domain.Activity;

public final class InteractionImpl implements Interaction {

	private final Activity actor;
	private final Activity receiver;
	
	public InteractionImpl(final Activity actor, final Activity receiver) throws IOException {
		this.actor = actor;
		this.receiver = receiver;
	}

	@Override
	public Activity actor() throws IOException {
		return actor;
	}

	@Override
	public Activity receiver() throws IOException {
		return receiver;
	}

	@Override
	public Interactables interactables() throws IOException {
		return new InteractablesImpl(this);
	}

	@Override
	public boolean active() throws IOException {
		return new InteractablesImpl(this).areAllActives();
	}

	@Override
	public void activate(boolean active) throws IOException {
		
		for (Interactable rule : interactables().items()) {
			rule.activate(active);
		}
	}
}
