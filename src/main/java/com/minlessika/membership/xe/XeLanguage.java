package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.Language;
import com.minlessika.membership.domain.Languages;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeLanguage extends XeWrap {

	public XeLanguage(final String root, final Language item) throws IOException {
		this(transform(root, item));
	}
	
	public XeLanguage(final Language item) throws IOException {
		this("language", item);
	}
	
	public XeLanguage(final Languages items) throws IOException {
		this(new Directives()
                		.add("languages")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Language, Iterable<Directive>>(
	            			            item -> transform("language", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeLanguage(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Language item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
                	.add("id", item.id())                
                    .add("name", item.name())                   
                )                
                .up();
	}

}
