package com.minlessika.supervisor.interaction;

import java.io.IOException;

import com.minlessika.supervisor.domain.Activity;

public interface Interaction {
	
	Activity actor() throws IOException;
	Activity receiver() throws IOException;
	
	Interactables interactables() throws IOException;
	
	boolean active() throws IOException;	
	void activate(boolean active) throws IOException;	
}
