package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.Currency;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XeCurrency extends XeWrap {

	public XeCurrency(final String root, final Currency item) throws IOException {
		this(transform(root, item));
	}
	
	public XeCurrency(final Currency item) throws IOException {
		this("currency", item);
	}
	
	public XeCurrency(final List<Currency> items) {
		this(new Directives()
                		.add("currencies")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Currency, Iterable<Directive>>(
	            			            item -> transform("currency", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeCurrency(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Currency item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
                    	.add("id", item.id())
                        .add("name", item.name())
                        .add("code", item.code())
                        .add("symbol", item.symbol())
                        .add("precision", item.precision())
                        .add("after", item.after())                    
                )                  
				.up();
	}
}
