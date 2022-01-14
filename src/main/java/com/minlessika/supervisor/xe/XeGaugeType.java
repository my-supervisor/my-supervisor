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
import com.minlessika.supervisor.indicator.GaugeType;

public final class XeGaugeType extends XeWrap {

	public XeGaugeType() {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return new Directives()
                		.add("gauge_types")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<GaugeType, Iterable<Directive>>(
	            			            item -> transform(item),
	            			            Arrays.asList(GaugeType.values())
	            			            	  .stream()
										      .filter(m -> m != GaugeType.NONE)
										      .collect(Collectors.toList())
    	            		    )
                            )
    	                 ).up();
            }
        });
	}
	
	private static Iterable<Directive> transform(final GaugeType item) throws IOException {
		return new Directives()
                .add("gauge_type")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())         
                )                
                .up();
	}

}
