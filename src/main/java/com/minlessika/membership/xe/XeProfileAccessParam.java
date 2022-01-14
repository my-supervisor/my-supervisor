package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.ProfileAccessParam;
import com.minlessika.membership.domain.ProfileAccessParams;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeProfileAccessParam extends XeWrap {

	public XeProfileAccessParam(final String root, final ProfileAccessParam item) throws IOException {
		this(transform(root, item));
	}
	
	public XeProfileAccessParam(final ProfileAccessParam item) throws IOException {
		this("profile_access_param", item);
	}
	
	public XeProfileAccessParam(final ProfileAccessParams items) throws IOException {
		this(new Directives()
                		.add("profile_access_params")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ProfileAccessParam, Iterable<Directive>>(
	            			            item -> transform("profile_access_param", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeProfileAccessParam(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ProfileAccessParam item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())
	                    .add("quantifier", item.quantifier().toString()) 
	                    .add("value_type", item.valueType().toString()) 
	                    .add("value", item.value())
	                    .add("message", item.message())                    
                )                
                .up();
	}

}
