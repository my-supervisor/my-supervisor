package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.sdk.time.PeriodicityUnit;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.Indicators;

public final class XeIndicator extends XeWrap {

	public XeIndicator(final String root, final Indicator item) throws IOException {
		this(transform(root, item));
	}
	
	public XeIndicator(final Indicator item) throws IOException {
		this("indicator", item);
	}
	
	public XeIndicator(final List<Indicator> items) {
		this(new Directives()
                		.add("indicators")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Indicator, Iterable<Directive>>(
	            			            item -> transform("indicator", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeIndicator(final Indicators items) throws IOException {
		this(items.items());
	}
	
	public XeIndicator(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Indicator item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())
	                    .add("type", item.type().name())
	                    .add("type_id", item.type().id())
	                    .add("short_name", item.type().shortName())
	                    .add("description", item.description())
	                    .add("periodicity", periodicityOf(item))
	                    .add("periodicity_reference", String.format("%s", item.periodicity().reference().format(DateTimeFormatter.ISO_LOCAL_DATE)))
	                    .add("is_template", item.isTemplate())
	                    .add("activity_id", item.activity().id())
	                    .add("activity", item.activity().name())
	                    .add("sizeX", item.sizeX())
	                    .add("sizeY", item.sizeY())
	                    .add("row", item.row())
	                    .add("col", item.col())       
                )                
				.up();
	}
	
	private static String periodicityOf(Indicator item) throws IOException {
		
		if(item.periodicity().unit() == PeriodicityUnit.FOREVER) {
			return "Tous les temps";
		}
		
		return String.format("Chaque %s %s", item.periodicity().number(), item.periodicity().unit().toString());
	}
}
