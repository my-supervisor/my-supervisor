package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.AccessParam;
import com.minlessika.membership.domain.AccessParams;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeAccessParam extends XeWrap {

	public XeAccessParam(final String root, final AccessParam item) throws IOException {
		this(transform(root, item));
	}
	
	public XeAccessParam(final AccessParam item) throws IOException {
		this("access_param", item);
	}
	
	public XeAccessParam(final AccessParams items) throws IOException {
		this(new Directives()
                		.add("access_params")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<AccessParam, Iterable<Directive>>(
	            			            item -> transform("access_param", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeAccessParam(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final AccessParam item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())
	                    .add("quantifier", item.quantifier().toString()) 
	                    .add("value_type", item.valueType().toString()) 
	                    .add("default_value", item.defaultValue())
	                    .add("message", item.message())                 
                )
                .up();
	}

}
