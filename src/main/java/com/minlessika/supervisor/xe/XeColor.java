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
import com.minlessika.sdk.colors.Color;

public final class XeColor extends XeWrap {

	public XeColor() {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return new Directives()
                		.add("colors")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Color, Iterable<Directive>>(
	            			            item -> transform(item),
	            			            Arrays.asList(Color.values())
	            			            	  .stream()
										      .filter(m -> m != Color.NONE)
										      .collect(Collectors.toList())
    	            		    )
                            )
    	                 )
    					.up();
            }
        });
	}
	
	private static Iterable<Directive> transform(final Color item) throws IOException {
		return new Directives()
                .add("color")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())
	                    .add("code", item.code())       
                )                
                .up();
	}

}
