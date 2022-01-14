package com.minlessika.sdk.takes;

import com.minlessika.sdk.datasource.Base;
import org.takes.Request;
import org.takes.Response;
import org.takes.Scalar;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.facets.auth.XeLogoutLink;
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
import org.takes.rs.xe.XeWhen;

import java.io.IOException;
import java.util.Collections;

/**
 * Index resource, front page of the website.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public class RsPage extends RsWrap {

    /**
     * Ctor.
     * @param xsl XSL
     * @param req Request
     * @throws IOException If fails
     */
    public RsPage(final String xsl, final Request req, final Base base) throws IOException {
        super(RsPage.make(xsl, req, base, Collections::emptyList));
    }

    /**
     * Ctor.
     * @param xsl XSL
     * @param req Request
     * @param src Source
     * @throws IOException If fails
     */
    public RsPage(
    		final String xsl, 
    		final Request req, 
    		final Base base,
    		final Scalar<Iterable<XeSource>> src
    ) throws IOException {
        super(RsPage.make(xsl, req, base, src));
    }
    
    /**
     * Make it.
     * @param xsl XSL
     * @param req Request
     * @param src Source
     * @return Response
     * @throws IOException If fails
     */
    private static Response make(
    		final String xsl, 
    		final Request req, 
    		final Base base,
    		final Scalar<Iterable<XeSource>> src
    ) throws IOException {
 	
        final Response raw = new RsXembly(
            new XeStylesheet(xsl),
            new XeAppend(
                "page",
                new XeMillis(false),
                new XeChain(src),
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
                new XeFlash(req),
                new XeWhen(
                    !new RqAuth(req).identity().equals(Identity.ANONYMOUS),
                    new XeChain(	
                        new XeMIdentity(req, base),
                        new XeLogoutLink(req)
                    )
                ),
                new XeLang(req, base),
                new XeMailChip(base.appInfo()),
                new XeModuleLinks(base.appInfo()),
                new XeAppend("current_module", base.appInfo().currentModule())
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
