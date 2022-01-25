package com.supervisor.sdk.takes;

import com.supervisor.sdk.datasource.Base;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;

public class TkBaseWrap implements Take {

	/**
     * Original take.
     */
    private final Take origin;
    @SuppressWarnings("unused")
	private final Base base;

    /**
     * Ctor.
     * @param take Original take
     */
    public TkBaseWrap(final Base base, final Take take) {
        this.base = base;
    	this.origin = take;
    }

    @Override
    public Response act(final Request req) throws Exception {
    	return this.origin.act(req);   	
    }

}
