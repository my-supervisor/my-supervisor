package com.supervisor.sdk.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.Recordable;
import org.takes.facets.fork.FkWrap;
import org.takes.facets.fork.Fork;

import java.io.IOException;

public class FkModule extends FkWrap {
	
	public FkModule(final Fork fork) throws IOException {
		super(fork);
	}
	
	@SuppressWarnings("unchecked")
	public FkModule(final Base base, final Fork fork, final Class<? extends Recordable>... domains) throws IOException {
		super(fork);
		
		initialize(base, domains);				
	}
	
	@SuppressWarnings("unchecked")
	private void initialize(final Base myBase, final Class<? extends Recordable>... domains) throws IOException {

		for (Class<? extends Recordable> domain : domains) {						
			myBase.register(domain);						
		}
		
		// Générer les tables requises
		myBase.createScheme();
	}
}
