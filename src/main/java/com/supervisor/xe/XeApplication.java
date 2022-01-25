package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.Application;
import com.supervisor.domain.Applications;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeApplication extends XeWrap {

	public XeApplication(final String root, final Application item) throws IOException {
		this(transform(root, item));
	}
	
	public XeApplication(final Application item) throws IOException {
		this("application", item);
	}
	
	public XeApplication(final Applications items) throws IOException {
		this(new Directives()
                		.add("applications")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Application, Iterable<Directive>>(
	            			            item -> transform("application", item),
	            			            items.items()
    	            		    )
                            )
    	                 )
    					 .up()
    	);
	}
	
	public XeApplication(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Application item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("userId", item.user().id())
	                    .add("user", item.user().name())
	                    .add("module", item.module())
	                    .add("profile", item.profile().name())
	                    .add("profile_id", item.profile().id())
	                    .add("fromNow", item.user().prettyTimeOf(item.creationDate()))                    
                )                
                .up();
	}

}
