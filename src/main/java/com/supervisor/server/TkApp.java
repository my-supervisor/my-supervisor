package com.supervisor.server;

import com.supervisor.sdk.codecs.MCodec;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.PreviousLocation;
import com.supervisor.sdk.utils.logging.Logger;
import com.supervisor.sdk.utils.logging.MLogger;
import com.minlessika.tk.TkCachedFiles;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.auth.PsByFlag;
import org.takes.facets.auth.PsChain;
import org.takes.facets.auth.PsCookie;
import org.takes.facets.auth.PsLogout;
import org.takes.facets.auth.TkAuth;
import org.takes.facets.fallback.Fallback;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.FbStatus;
import org.takes.facets.fallback.RqFallback;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.flash.TkFlash;
import org.takes.facets.fork.FkFixed;
import org.takes.facets.fork.FkParams;
import org.takes.facets.fork.Fork;
import org.takes.facets.fork.TkFork;
import org.takes.facets.forward.RsForward;
import org.takes.facets.forward.TkForward;
import org.takes.misc.Opt;
import org.takes.rs.RsHtml;
import org.takes.rs.RsText;
import org.takes.rs.RsVelocity;
import org.takes.rs.RsWithStatus;
import org.takes.tk.TkGzip;
import org.takes.tk.TkRedirect;
import org.takes.tk.TkWithHeaders;
import org.takes.tk.TkWrap;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.regex.Pattern;

public final class TkApp extends TkWrap {
	
	private static final Logger logger = new MLogger(TkApp.class);
	
    public static final String PASS_PHRASE = "My faith is in Jesus-Christ !";
    		
	public TkApp(final Fork fork, final Base base) {
		super(app(fork, base));
	}
	
	/**
     * Ctor.
     * @param base Base
     * @return App
     * @throws IOException If fails
     */
    private static Take app(final Fork fork, final Base base) {
    	return new TkWithHeaders(
			new TkFlash(
				new TkCachedFiles(
					auth(
						safe(
							new TkForward(
								new TkGzip(
									new TkFork(fork)
								)
							)
						),
						base
					),
					3600, // 1 heure
					"js", "css", "svg", "png", "jpg", "jpeg",
					"gif", "eot", "ttf", "woff", "woff2", "ico"
				)
			),
			"Vary: Cookie"
		);
    }

    /**
     * Auth.
     * @param take Takes
     * @return Authenticated takes
     */
    private static Take auth(final Take take, final Base base) {
        return new TkAuth(        	
        	new TkFork(
                new FkParams(
                    PsByFlag.class.getSimpleName(),
                    Pattern.compile(".+"),
                    new TkRedirect(String.format("%s/login", base.appInfo().minlessikaDomain()))
                ),
                new FkFixed(take)
            ),        	
            new PsChain(
        		new PsByFlag(
                    new PsByFlag.Pair(
                        PsLogout.class.getSimpleName(),
                        new PsLogout()
                    )
                ),
        		new PsCookie(
                    new MCodec(PASS_PHRASE)
                )	
            )
        );
    }
    
    /**
     * With fallback.
     * @param take Takes
     * @return Safe takes
     */
    private static Take safe(final Take take){
    	
        return new TkFallback(
            take,
            new FbChain(
                new FbStatus(
                    HttpURLConnection.HTTP_NOT_FOUND,
                    (Fallback) req -> { 
                    	logger.fatal(req.throwable());
                    	return new Opt.Single<>(
	                        new RsWithStatus(
	                    		new RsHtml(
	                    			new RsVelocity(
                                        TkApp.class.getResource("/404.html.vm")
                                    )
	            			    ),
	                            HttpURLConnection.HTTP_NOT_FOUND
	                        )
	                    );
                    } 
                ),
                new FbStatus(
                    HttpURLConnection.HTTP_BAD_REQUEST,
                    (Fallback) req -> {         
                    	final String fullMessage = req.throwable().getLocalizedMessage();
                    	final String message = fullMessage.split("\\[400\\]")[1].trim();
                    	if(message.startsWith("IllEg:")) {                    		
                    		String url = new PreviousLocation(req, "/").toString();
                        	return new Opt.Single<Response>(
                        				new RsForward(
                        					new RsFlash(
                        						message.replaceFirst("IllEg:", ""),
                    							Level.WARNING
                        					), 
                        					308, 
                        					url
                            			)                   				
                        		   );
                    	} else {
                    		return new Opt.Single<>(
                					new RsWithStatus(
	                					new RsHtml(
	                						new RsText(req.throwable().getLocalizedMessage())
	                				    ),
	                				    HttpURLConnection.HTTP_BAD_REQUEST
	                			    )
                			   );
                    	}                    		             
                    }
                ),
                new FbStatus(
                    HttpURLConnection.HTTP_UNAUTHORIZED,
                    (Fallback) req -> { 
                    	logger.error(req.throwable());
                    	return new Opt.Single<>(
                            new RsWithStatus(
                                new RsFlash("Access denied", Level.WARNING),
                                HttpURLConnection.HTTP_UNAUTHORIZED,
                                "/login?PsByFlag=PsLogout"
                            )
                        );
                    }
                ),
                req -> new Opt.Single<>(fatal(req))
            )
        );
    }

    /**
     * Make fatal error page.
     * @param req Request
     * @return Response
     * @throws Exception 
     */
    private static Response fatal(final RqFallback req) { 
    	logger.fatal(req.throwable());
    	return new RsWithStatus(
					new RsHtml(
	        			TkApp.class.getResource("/500.html.vm")
				    ),
				    HttpURLConnection.HTTP_INTERNAL_ERROR
			   );
    }
}
