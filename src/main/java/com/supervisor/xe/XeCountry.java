package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.Countries;
import com.supervisor.domain.Country;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeCountry extends XeWrap {

	public XeCountry(final String root, final Country item) throws IOException {
		this(transform(root, item));
	}
	
	public XeCountry(final Country item) throws IOException {
		this("country", item);
	}
	
	public XeCountry(final Countries items) throws IOException {
		this(new Directives()
                		.add("countries")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Country, Iterable<Directive>>(
	            			            item -> transform("country", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeCountry(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Country item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())                
	                    .add("name", item.name())
	                    .add("phone_code", item.phoneCode())                    
                )                   
                .up();
	}

}
