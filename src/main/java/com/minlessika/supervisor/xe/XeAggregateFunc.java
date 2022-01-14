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
import com.minlessika.supervisor.domain.bi.AggregateFunc;

public final class XeAggregateFunc extends XeWrap {

	public XeAggregateFunc() throws IOException {
		this(Arrays.asList(AggregateFunc.values())
                .stream()
                .filter(c -> c != AggregateFunc.NONE)
                .collect(Collectors.toList()));
	}
	
	public XeAggregateFunc(final String root, final AggregateFunc item) throws IOException {
		this(transform(root, item));
	}
	
	public XeAggregateFunc(final AggregateFunc item) throws IOException {
		this("aggregate_func", item);
	}
	
	public XeAggregateFunc(final List<AggregateFunc> items) throws IOException {
		this(
			new Directives()
        		.add("aggregate_funcs")
				.append(
                    new Joined<>(
                		new Mapped<AggregateFunc, Iterable<Directive>>(
        			            item -> transform("aggregate_func", item),
        			            items
            		    )
                    )
                 ).up()
    	);
	}
	
	public XeAggregateFunc(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final AggregateFunc item) throws IOException {
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
