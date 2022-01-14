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
import com.minlessika.supervisor.domain.FormularSimpleExpression;

public final class XeFormularSimpleExpression extends XeWrap {

	public XeFormularSimpleExpression(final String root, final FormularSimpleExpression item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularSimpleExpression(final FormularSimpleExpression item) throws IOException {
		this("formular_simple_expression", item);
	}
	
	public XeFormularSimpleExpression(final List<FormularSimpleExpression> items) throws IOException {
		this(new Directives()
                		.add("formular_simple_expressions")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<FormularSimpleExpression, Iterable<Directive>>(
	            			            item -> transform("formular_simple_expression", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeFormularSimpleExpression(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularSimpleExpression item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("text", item.toText())
	                    .add("name", item.name())
	                    .add("formular", item.formular().name())
	                    .add("formular_id", item.formular().id())
	                    .add("func_id", item.func().name())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())	                              
                )       
                .add("arguments")
                .append(
                    new Joined<>(
                		new Mapped<ExpressionArg, Iterable<Directive>>(
        			            cd -> transform("argument", cd),
        			            item.arguments().items()
            		    )
                    )
                 )
                .up()
                .up();
	}
	
	private static Iterable<Directive> transform(final String root, final ExpressionArg item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())                
	                    .add("expression_id", item.expression().id())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())           
                )                
                .up();
	}

}
