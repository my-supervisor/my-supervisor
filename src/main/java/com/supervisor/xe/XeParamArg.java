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
import com.supervisor.domain.ParamArg;

public final class XeParamArg extends XeWrap {

	public XeParamArg(final String root, final ParamArg item) throws IOException {
		this(transform(root, item));
	}
	
	public XeParamArg(final ParamArg item) throws IOException {
		this("param_arg", item);
	}
	
	public XeParamArg(final List<ParamArg> items) {
		this(new Directives()
                		.add("param_args")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ParamArg, Iterable<Directive>>(
	            			            item -> transform("param_arg", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeParamArg(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ParamArg item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("expression", item.expression().name())
	                    .add("expression_id", item.expression().id())
	                    .add("param", item.field().name())
	                    .add("param_id", item.field().id())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())         
                )                
                .up();
	}
}
