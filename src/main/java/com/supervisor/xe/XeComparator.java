package com.supervisor.xe;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.supervisor.sdk.datasource.comparators.Comparator;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;

public final class XeComparator extends XeWrap {

	public XeComparator() throws IOException {
		this(Arrays.asList(Comparator.values())
                .stream()
                .filter(c -> c != Comparator.NONE && c != Comparator.BETWEEN && c != Comparator.NOT_BETWEEN)
                .collect(Collectors.toList()));
	}
	
	public XeComparator(final String root, final Comparator item) throws IOException {
		this(transform(root, item));
	}
	
	public XeComparator(final Comparator item) throws IOException {
		this("comparator", item);
	}
	
	public XeComparator(final List<Comparator> items) throws IOException {
		this(
			new Directives()
        		.add("comparators")
				.append(
                    new Joined<>(
                		new Mapped<Comparator, Iterable<Directive>>(
        			            item -> transform("comparator", item),
        			            items
            		    )
                    )
                 ).up()
    	);
	}
	
	public XeComparator(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Comparator item) throws IOException {
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
