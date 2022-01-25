package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class XeModule extends XeWrap {

	public XeModule(final String root, final String item) throws IOException {
		this(transform(root, item));
	}
	
	public XeModule(final String item) throws IOException {
		this("module", item);
	}
	
	public XeModule() {
		this(Arrays.asList("com/membership", "supervisor", "coding", "accounting"));
	}
	
	public XeModule(final List<String> items) {
		this(
			new Directives()
            		.add("modules")
					.append(
	                    new Joined<>(
                    		new Mapped<String, Iterable<Directive>>(
            			            item -> transform("module", item),
            			            items
	            		    )
                        )
	                 ).up()
    	);
	}
	
	public XeModule(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final String item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("code", item)
	                    .add("name", item)                 
                )                
                .up();
	}

}
