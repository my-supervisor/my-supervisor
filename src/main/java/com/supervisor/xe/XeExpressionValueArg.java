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
import com.supervisor.domain.ExpressionValueArg;

public final class XeExpressionValueArg extends XeWrap {

	public XeExpressionValueArg(final String root, final ExpressionValueArg item) throws IOException {
		this(transform(root, item));
	}
	
	public XeExpressionValueArg(final ExpressionValueArg item) throws IOException {
		this("expression_value_arg", item);
	}
	
	public XeExpressionValueArg(final List<ExpressionValueArg> items) {
		this(new Directives()
                		.add("expression_value_args")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ExpressionValueArg, Iterable<Directive>>(
	            			            item -> transform("expression_value_arg", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeExpressionValueArg(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ExpressionValueArg item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("expression", item.expression().name())
	                    .add("expression_id", item.expression().id())
	                    .add("target_expr", String.format("%s = %s", item.targetExpr().name(), item.targetExpr().toText()))
	                    .add("target_expr_id", item.targetExpr().id())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())          
                )                
                .up();
	}
}
