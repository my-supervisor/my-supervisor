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
import com.minlessika.supervisor.domain.DataFieldStyle;

public final class XeDataFieldStyle extends XeWrap {

	public XeDataFieldStyle() {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return new Directives()
                		.add("datafieldstyles")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<DataFieldStyle, Iterable<Directive>>(
	            			            item -> transform(item),
	            			            Arrays.asList(DataFieldStyle.values())
	            			            	  .stream()
										      .filter(m -> m != DataFieldStyle.NONE && m != DataFieldStyle.STRUCTURE)
										      .collect(Collectors.toList())
    	            		    )
                            )
    	                 )
    					.up();
            }
        });
	}
	
	private static Iterable<Directive> transform(final DataFieldStyle item) throws IOException {
		return new Directives()
                .add("datafieldstyle")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())       
                )                
                .up();
	}

}
