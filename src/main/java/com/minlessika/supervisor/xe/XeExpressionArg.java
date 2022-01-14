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
import com.minlessika.supervisor.domain.ExpressionArg;

public final class XeExpressionArg extends XeWrap {

	public XeExpressionArg(final String root, final ExpressionArg item) throws IOException {
		this(transform(root, item));
	}
	
	public XeExpressionArg(final ExpressionArg item) throws IOException {
		this("value_arg", item);
	}
	
	public XeExpressionArg(final List<ExpressionArg> items) {
		this(
			new Directives()
        		.add("value_args")
				.append(
                    new Joined<>(
                		new Mapped<ExpressionArg, Iterable<Directive>>(
        			            item -> transform("expression_arg", item),
        			            items
            		    )
                    )
                 )
				 .up()
    	);
	}
	
	public XeExpressionArg(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ExpressionArg item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("expression", item.expression().name())
	                    .add("expression_id", item.expression().id())
	                    .add("expression_type_id", item.expression().type().name())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())         
                )                
                .up();
	}
}
