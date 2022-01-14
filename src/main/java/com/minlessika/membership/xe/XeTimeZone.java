package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

public final class XeTimeZone extends XeWrap {

	public XeTimeZone(final String root, final TimeZone item) throws IOException {
		this(transform(root, item));
	}
	
	public XeTimeZone(final TimeZone item) throws IOException {
		this("country", item);
	}
	
	public XeTimeZone(final List<TimeZone> items) {
		this(new Directives()
                		.add("time_zones")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<TimeZone, Iterable<Directive>>(
	            			            item -> transform("time_zone", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeTimeZone(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final TimeZone item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.getID())        
	                    .add("name", item.getDisplayName())           
                )                
                .up();
	}

}
