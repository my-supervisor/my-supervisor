package com.supervisor.xe;

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
import com.supervisor.domain.DataLinkOperator;

public final class XeDataLinkOperator extends XeWrap {

	public XeDataLinkOperator() throws IOException {
		this(Arrays.asList(DataLinkOperator.values())
                .stream()
                .collect(Collectors.toList()));
	}
	
	public XeDataLinkOperator(final String root, final DataLinkOperator item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDataLinkOperator(final DataLinkOperator item) throws IOException {
		this("data_link_operator", item);
	}
	
	public XeDataLinkOperator(final List<DataLinkOperator> items) throws IOException {
		this(
			new Directives()
        		.add("data_link_operators")
				.append(
                    new Joined<>(
                		new Mapped<DataLinkOperator, Iterable<Directive>>(
        			            item -> transform("data_link_operator", item),
        			            items
            		    )
                    )
                 )
				 .up()
    	);
	}
	
	public XeDataLinkOperator(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final DataLinkOperator item) throws IOException {
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
