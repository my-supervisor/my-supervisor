package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.Resource;

public final class XeResource extends XeWrap {

	public XeResource(final String root, final Resource item) throws IOException {
		this(transform(root, item));
	}
	
	public XeResource(final Resource item) throws IOException {
		this("resource", item);
	}
	
	public XeResource(final List<Resource> items) throws IOException {
		this(new Directives()
                		.add("resources")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Resource, Iterable<Directive>>(
	            			            item -> transform("resource", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeResource(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Resource item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())           
                )                              
                .up();
	}

}
