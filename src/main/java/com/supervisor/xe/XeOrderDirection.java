package com.supervisor.xe;

import java.io.IOException;
import java.util.Arrays;

import com.supervisor.sdk.datasource.OrderDirection;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;

public final class XeOrderDirection extends XeWrap {

	public XeOrderDirection() {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return new Directives()
                		.add("order_directions")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<OrderDirection, Iterable<Directive>>(
	            			            item -> transform(item),
	            			            Arrays.asList(OrderDirection.values())
    	            		    )
                            )
    	                 ).up();
            }
        });
	}
	
	private static Iterable<Directive> transform(final OrderDirection item) throws IOException {
		return new Directives()
                .add("order_direction")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())       
                )                
                .up();
	}

}
