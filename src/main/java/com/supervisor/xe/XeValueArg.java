package com.supervisor.xe;

import java.io.IOException;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.ValueArg;

public final class XeValueArg extends XeWrap {

	public XeValueArg(final String root, final ValueArg item) throws IOException {
		this(transform(root, item));
	}
	
	public XeValueArg(final ValueArg item) throws IOException {
		this("value_arg", item);
	}
	
	public XeValueArg(final List<ValueArg> items) {
		this(new Directives()
                		.add("value_args")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ValueArg, Iterable<Directive>>(
	            			            item -> transform("value_arg", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeValueArg(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ValueArg item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("expression", item.expression().name())
	                    .add("expression_id", item.expression().id())
	                    .add("value", item.value())
	                    .add("value_type", item.valueType().toString())
	                    .add("value_type_id", item.valueType().name())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())          
                )                
                .up();
	}
}
