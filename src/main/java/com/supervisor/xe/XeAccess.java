package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.Access;
import com.supervisor.domain.Accesses;
import com.supervisor.domain.AllAccesses;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XeAccess extends XeWrap {

	public XeAccess(final String root, final Access item) throws IOException {
		this(transform(root, item));
	}
	
	public XeAccess(final Access item) throws IOException {
		this("access", item);
	}
	
	public XeAccess(final List<Access> items) {
		this(
			new Directives()
            		.add("accesses")
					.append(
	                    new Joined<>(
                    		new Mapped<Access, Iterable<Directive>>(
            			            item -> transform("access", item),
            			            items
	            		    )
                        )
	                 )
					 .up()
    	);
	}
	
	public XeAccess(final AllAccesses items) throws IOException {
		this(items.items());
	}
	
	public XeAccess(final Accesses items) throws IOException {
		this(items.items());
	}
	
	public XeAccess(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Access item) throws IOException {
		return new Directives()
                .add(root)                
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())
	                    .add("nb_params", item.parameters().count())    
	                    .add("module", item.module())                   
                )
                .up();
	}

}
