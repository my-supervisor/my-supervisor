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
import com.supervisor.indicator.ChartCamembertType;

public final class XeChartCamembertType extends XeWrap {

	public XeChartCamembertType() {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return new Directives()
                		.add("chart_camembert_types")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ChartCamembertType, Iterable<Directive>>(
	            			            item -> transform(item),
	            			            Arrays.asList(ChartCamembertType.values())
	            			            	  .stream()
										      .filter(m -> m != ChartCamembertType.NONE)
										      .collect(Collectors.toList())
    	            		    )
                            )
    	                 );
            }
        });
	}
	
	private static Iterable<Directive> transform(final ChartCamembertType item) throws IOException {
		return new Directives()				
                .add("chart_camembert_type")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())     
                )                
                .up();
	}

}
