/*
 * Copyright (c) 2018-2022 Minlessika
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.supervisor.sdk.takes;

import com.supervisor.sdk.datasource.Base;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;

/**
 * Take that wraps another take.
 *
 * @since 1.0
 */
public abstract class TkBaseWrap implements Take {

	/**
     * Original take.
     */
    private final Take origin;

    /**
     * Data source.
     */
	private final Base base;

    /**
     * Ctor.
     * @param base Data source
     * @param take Original take
     */
    public TkBaseWrap(final Base base, final Take take) {
        this.base = base;
    	this.origin = take;
    }

    @Override
    public final Response act(final Request req) throws Exception {
    	return this.origin.act(req);   	
    }

}
