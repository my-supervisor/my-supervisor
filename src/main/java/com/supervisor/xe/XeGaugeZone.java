package com.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.indicator.GaugeZone;
import com.supervisor.indicator.GaugeZones;

public final class XeGaugeZone extends XeWrap {

	public XeGaugeZone(final String root, final GaugeZone item) throws IOException {
		this(transform(root, item));
	}
	
	public XeGaugeZone(final GaugeZone item) throws IOException {
		this("gauge_zone", item);
	}
	
	public XeGaugeZone(final GaugeZones items) throws IOException {
		this(new Directives()
                		.add("gauge_zones")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<GaugeZone, Iterable<Directive>>(
	            			            item -> transform("gauge_zone", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeGaugeZone(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final GaugeZone item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("color", item.color().toString())
	                    .add("color_id", item.color().name())
	                    .add("min", item.min())
	                    .add("max", item.max())
	                    .add("gauge_id", item.gauge().id())         
                )                
                .up();
	}
}
