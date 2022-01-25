package com.supervisor.xe;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.SharedResource;

public final class XeSharedResource extends XeWrap {

	public XeSharedResource(final String root, final SharedResource item) throws IOException {
		this(transform(root, item));
	}
	
	public XeSharedResource(final SharedResource item) throws IOException {
		this("shared_resource", item);
	}
	
	public XeSharedResource(final List<SharedResource> items) throws IOException {
		this(new Directives()
                		.add("shared_resources")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<SharedResource, Iterable<Directive>>(
	            			            item -> transform("resource", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeSharedResource(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final SharedResource item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("owner_id", item.ownerId())
	                    .add("resource_id", item.resourceId())
	                    .add("subscriber", item.subscriber().name())
	                    .add("subscriber_id", item.subscriber().id())
	                    .add("creation_date", item.creationDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())      
                )                
                .up();
	}

}
