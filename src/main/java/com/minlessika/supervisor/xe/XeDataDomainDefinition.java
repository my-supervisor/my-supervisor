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
import com.minlessika.supervisor.domain.DataDomainDefinition;

public final class XeDataDomainDefinition extends XeWrap {

	public XeDataDomainDefinition() throws IOException {
		this(Arrays.asList(DataDomainDefinition.values())
                .stream()
                .collect(Collectors.toList()));
	}
	
	public XeDataDomainDefinition(final String root, final DataDomainDefinition item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDataDomainDefinition(final DataDomainDefinition item) throws IOException {
		this("data_domain_definition", item);
	}
	
	public XeDataDomainDefinition(final List<DataDomainDefinition> items) throws IOException {
		this(
			new Directives()
	    		.add("data_domain_definitions")
				.append(
	                new Joined<>(
	            		new Mapped<DataDomainDefinition, Iterable<Directive>>(
	    			            item -> transform("data_domain_definition", item),
	    			            items
	        		    )
	                )
	             )
				.up()
    	);
	}
	
	public XeDataDomainDefinition(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final DataDomainDefinition item) throws IOException {
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
