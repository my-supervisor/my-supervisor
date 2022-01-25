package com.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.indicator.IndicatorType;
import com.supervisor.indicator.IndicatorTypes;

public final class XeIndicatorType extends XeWrap {
	
	public XeIndicatorType(final String root, final IndicatorType item) throws IOException {
		this(transform(root, item));
	}
	
	public XeIndicatorType(final IndicatorType item) throws IOException {
		this("indicator_type", item);
	}
	
	public XeIndicatorType(final IndicatorTypes items) throws IOException {
		this(new Directives()
                		.add("indicator_types")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<IndicatorType, Iterable<Directive>>(
	            			            item -> transform("indicator_type", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeIndicatorType(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final IndicatorType item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("short_name", item.shortName())
	                    .add("description", item.description())        
                )                
                .up();
	}

}
