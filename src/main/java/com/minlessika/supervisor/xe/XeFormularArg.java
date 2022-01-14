package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.FormularArg;

public final class XeFormularArg extends XeWrap {

	public XeFormularArg(final String root, final FormularArg item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularArg(final FormularArg item) throws IOException {
		this("formular_arg", item);
	}
	
	public XeFormularArg(final List<FormularArg> items) {
		this(new Directives()
                		.add("formular_args")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<FormularArg, Iterable<Directive>>(
	            			            item -> transform("formular_arg", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeFormularArg(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularArg item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("expression", item.expression().name())
	                    .add("expression_id", item.expression().id())
	                    .add("formular", item.field().name())
	                    .add("formular_id", item.field().id())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())           
                )                
                .up();
	}
}
