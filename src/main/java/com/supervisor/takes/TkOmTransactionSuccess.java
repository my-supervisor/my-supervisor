package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;

import java.util.logging.Level;

public final class TkOmTransactionSuccess extends TkBaseWrap {

	public TkOmTransactionSuccess(final Base base) {
		super(
				base,
				req -> new RsForward(
							new RsFlash(
								"Paiement réussi !", 
								Level.FINE
							), 
							"/home"
					   )
		);
	}
}
