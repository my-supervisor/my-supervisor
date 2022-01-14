package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.ProfileAccess;
import com.minlessika.membership.domain.ProfileAccesses;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeProfileAccess extends XeWrap {

	public XeProfileAccess(final String root, final ProfileAccess item) throws IOException {
		this(transform(root, item));
	}
	
	public XeProfileAccess(final ProfileAccess item) throws IOException {
		this("profile_access", item);
	}
	
	public XeProfileAccess(final ProfileAccesses items) throws IOException {
		this(new Directives()
                		.add("profile_accesses")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ProfileAccess, Iterable<Directive>>(
	            			            item -> transform("profile_access", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeProfileAccess(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ProfileAccess item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())    
	                    .add("nb_params", item.parameterValues().count())
	                    .add("profile_id", item.profile().id()) 
	                    .add("profile", item.profile().name())                
                )                
                .up();
	}

}
