package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.sdk.time.PeriodicityUnit;

public final class XePeriodicity extends XeWrap {

	public XePeriodicity() throws IOException {
		this(Arrays.asList(PeriodicityUnit.values())
                .stream()
                .filter(c -> c != PeriodicityUnit.NONE)
                .collect(Collectors.toList()));
	}
	
	public XePeriodicity(final String root, final PeriodicityUnit item) throws IOException {
		this(transform(root, item));
	}
	
	public XePeriodicity(final PeriodicityUnit item) throws IOException {
		this("periodicity", item);
	}
	
	public XePeriodicity(final List<PeriodicityUnit> items) throws IOException {
		this(new Directives()
                		.add("periodicities")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<PeriodicityUnit, Iterable<Directive>>(
	            			            item -> transform("periodicity", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XePeriodicity(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final PeriodicityUnit item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())    
                )                
                .up();
	}

}
