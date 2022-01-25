package com.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinks;

public final class XeDataLink extends XeWrap {

	public XeDataLink(final String root, final DataLink item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDataLink(final DataLink item) throws IOException {
		this("data_link", item);
	}
	
	public XeDataLink(final DataLinks items) throws IOException {
		this(
			new Directives()
        		.add("data_links")
				.append(
                    new Joined<>(
                		new Mapped<DataLink, Iterable<Directive>>(
        			            item -> transform("data_link", item),
        			            items.items()
            		    )
                    )
                 )
				 .up()
    	);
	}
	
	public XeDataLink(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final DataLink item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("indic", item.indicator().name())
	                    .add("indic_id", item.indicator().id())
	                    .add("model", item.model().name())
	                    .add("model_id", item.model().id())
	                    .add("active", item.active())
	                    .add("data_domain_definition", item.dataDomainDefinition().toString())
	                    .add("data_domain_definition_id", item.dataDomainDefinition().name())          
                )                
                .up();
	}

}
