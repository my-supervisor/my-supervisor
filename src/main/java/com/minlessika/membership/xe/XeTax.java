package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.billing.Tax;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XeTax extends XeWrap {

	public XeTax(final String root, final Tax item) throws IOException {
		this(transform(root, item));
	}
	
	public XeTax(final Tax item) throws IOException {
		this("tax", item);
	}
	
	public XeTax(final List<Tax> items) {
		this(new Directives()
                		.add("taxes")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Tax, Iterable<Directive>>(
	            			            item -> transform("tax", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeTax(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Tax item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("short_name", item.shortName())
	                    .add("value", item.value())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("value_type", item.valueType().toString())
	                    .add("value_type_id", item.valueType().name())                  
                )                
				.up();
	}
}
