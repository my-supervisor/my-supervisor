package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.indicator.OrderingMode;

public final class XeOrderingMode extends XeWrap {

	public XeOrderingMode() {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return new Directives()
                		.add("ordering_modes")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<OrderingMode, Iterable<Directive>>(
	            			            item -> transform(item),
	            			            Arrays.asList(OrderingMode.values())
	            			            	  .stream()
										      .filter(m -> m != OrderingMode.NONE)
										      .collect(Collectors.toList())
    	            		    )
                            )
    	                 ).up();
            }
        });
	}
	
	private static Iterable<Directive> transform(final OrderingMode item) throws IOException {
		return new Directives()
                .add("ordering_mode")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())       
                )                
                .up();
	}

}
