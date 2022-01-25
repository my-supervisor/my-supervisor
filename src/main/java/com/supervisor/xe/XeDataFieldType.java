package com.supervisor.xe;

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
import com.supervisor.domain.DataFieldType;

public final class XeDataFieldType extends XeWrap {

	public XeDataFieldType() {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return new Directives()
                		.add("datafieldtypes")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<DataFieldType, Iterable<Directive>>(
	            			            item -> transform(item),
	            			            Arrays.asList(DataFieldType.values())
	            			            	  .stream()
										      .filter(m -> m != DataFieldType.NONE && m != DataFieldType.TABLE)
										      .collect(Collectors.toList())
    	            		    )
                            )
    	                 )
    					.up();
            }
        });
	}
	
	private static Iterable<Directive> transform(final DataFieldType item) throws IOException {
		return new Directives()
                .add("datafieldtype")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString()) 
                )                
                .up();
	}

}
