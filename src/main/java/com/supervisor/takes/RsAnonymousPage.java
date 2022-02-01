package com.supervisor.takes;

import com.supervisor.sdk.datasource.Base;
import org.takes.Request;
import org.takes.Response;
import org.takes.Scalar;
import org.takes.facets.flash.XeFlash;
import org.takes.facets.fork.FkTypes;
import org.takes.facets.fork.RsFork;
import org.takes.rs.RsWithType;
import org.takes.rs.RsWrap;
import org.takes.rs.RsXslt;
import org.takes.rs.xe.RsXembly;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDate;
import org.takes.rs.xe.XeLinkHome;
import org.takes.rs.xe.XeLinkSelf;
import org.takes.rs.xe.XeLocalhost;
import org.takes.rs.xe.XeMemory;
import org.takes.rs.xe.XeMillis;
import org.takes.rs.xe.XeSla;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeStylesheet;

import java.io.IOException;
import java.util.Collections;

/**
 * Front page of anonymous pages.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class RsAnonymousPage extends RsWrap {

    /**
     * Ctor.
     * @param xsl XSL
     * @param req Request
     * @param src XeSource
     * @throws IOException If fails
     */
    public RsAnonymousPage(final String xsl, final Request req, final XeSource src) {
        super(RsAnonymousPage.make(xsl, req, src));
    }
    
    /**
     * Make it.
     * @param xsl XSL
     * @param req Request
     * @param src XeSource
     * @return Response
     * @throws IOException If fails
     */
    private static Response make(final String xsl, final Request req, final XeSource src) {
        final Response raw = new RsXembly(
            new XeStylesheet(xsl),
            new XeAppend(
                "page",
                new XeMillis(false),
                src,
                new XeMemory(),
                new XeLinkHome(req),
                new XeLinkSelf(req),
                new XeMillis(true),
                new XeDate(),                
                new XeAppend(
                    "epoch",
                    Long.toString(System.currentTimeMillis())
                ),
                new XeSla(),
                new XeLocalhost(),
                new XeFlash(req)
            )
        );
        return new RsFork(
            req,
            new FkTypes(
                "*/*",
                new RsXslt(new RsWithType(raw, "text/html"))
            )
        );
    }

}
