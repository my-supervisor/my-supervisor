package com.minlessika.supervisor.interaction;

import java.io.IOException;
import java.util.List;

public interface Interactables {
	List<Interactable> items() throws IOException;
	boolean areAllActives() throws IOException;
	boolean isEmpty() throws IOException;
}
