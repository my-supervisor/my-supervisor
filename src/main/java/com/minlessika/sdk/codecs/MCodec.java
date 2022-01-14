package com.minlessika.sdk.codecs;

import org.takes.facets.auth.Identity;
import org.takes.facets.auth.codecs.CcBase64;
import org.takes.facets.auth.codecs.CcCompact;
import org.takes.facets.auth.codecs.CcHex;
import org.takes.facets.auth.codecs.CcSafe;
import org.takes.facets.auth.codecs.CcSalted;
import org.takes.facets.auth.codecs.CcXor;
import org.takes.facets.auth.codecs.Codec;

import java.io.IOException;

public final class MCodec implements Codec {

	private final Codec origin;
	private static final String PASS_PHRASE = String.format("My faith is in Jesus-Christ !");
	
	public MCodec() {
		this(PASS_PHRASE);
	}
	
	public MCodec(final String passphrase) {
		origin = new CcBase64(
					new CcSafe(
		                	new CcHex(
		                        new CcXor(
		                            new CcSalted(new CcCompact()),
		                            passphrase
		                        )
		                    )
					)
				);
	}
	
	@Override
	public byte[] encode(Identity identity) throws IOException {
		return origin.encode(identity);
	}

	@Override
	public Identity decode(byte[] bytes) throws IOException {
		return origin.decode(bytes);
	}
	
}
