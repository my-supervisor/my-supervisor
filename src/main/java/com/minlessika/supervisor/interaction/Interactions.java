package com.minlessika.supervisor.interaction;

import java.io.IOException;
import java.util.List;

import com.minlessika.supervisor.domain.Activity;

public interface Interactions {	
	Interaction getWith(Activity actor) throws IOException;
	List<Interaction> items() throws IOException;
	List<Interaction> actives() throws IOException;
}
