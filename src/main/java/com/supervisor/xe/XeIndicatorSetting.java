package com.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.Indicators;

public final class XeIndicatorSetting extends XeWrap {

	public XeIndicatorSetting(final String root, final Indicator item) throws IOException {
		this(transform(root, item));
	}
	
	public XeIndicatorSetting(final Indicator item) throws IOException {
		this("indicator_setting", item);
	}
	
	public XeIndicatorSetting(final Indicators items) throws IOException {
		this(new Directives()
                		.add("indicator_settings")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Indicator, Iterable<Directive>>(
	            			            item -> transform("indicator_setting", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeIndicatorSetting(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Indicator item) throws IOException {
		
		Iterable<Directive> dir =  new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("type_short_name", item.type().shortName())
	                    .add("description", item.description())          
                )                
				.up();
		
		return dir;
	}

}
