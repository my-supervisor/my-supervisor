package com.supervisor.xe;

import java.io.IOException;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

public final class XeSubscriber extends XeWrap {

	public XeSubscriber(final String root, final String email) throws IOException {
		this(transform(root, email));
	}
	
	public XeSubscriber(final String email) throws IOException {
		this("subscriber", email);
	}
	
	public XeSubscriber(final List<String> emails) throws IOException {
		this(new Directives()
                		.add("subscribers")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<String, Iterable<Directive>>(
	            			            item -> transform("subscriber", item),
	            			            emails
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeSubscriber(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final String email) throws IOException {
		return new Directives()
                .add(root)
                .add("email").set(email).up()
                .up();
	}

}
