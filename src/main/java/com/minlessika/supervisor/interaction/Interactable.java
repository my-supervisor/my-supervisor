package com.minlessika.supervisor.interaction;

import java.io.IOException;

public interface Interactable {
	boolean active() throws IOException;
	boolean interacting() throws IOException;
	void activate(boolean active) throws IOException;
}
