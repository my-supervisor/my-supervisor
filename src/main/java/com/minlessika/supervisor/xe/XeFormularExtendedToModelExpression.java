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
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;

public final class XeFormularExtendedToModelExpression extends XeWrap {

	public XeFormularExtendedToModelExpression(final String root, final FormularExtendedToModelExpression item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularExtendedToModelExpression(final FormularExtendedToModelExpression item) throws IOException {
		this("formular_extended_to_model_expression", item);
	}
	
	public XeFormularExtendedToModelExpression(final List<FormularExtendedToModelExpression> items) {
		this(new Directives()
                		.add("formular_extended_to_model_expressions")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<FormularExtendedToModelExpression, Iterable<Directive>>(
	            			            item -> transform("formular_extended_to_model_expression", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeFormularExtendedToModelExpression(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularExtendedToModelExpression item) throws IOException {
		
		final ExpressionArg defaultValue = item.defaultValue();
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("text", item.toText())
	                    .add("name", item.name())
	                    .add("formular", item.formular().name())
	                    .add("formular_id", item.formular().id())
	                    .add("aggregate", item.aggregate().toString())
	                    .add("aggregate_id", item.aggregate().name())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())         
                )                
                .add("default_value")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", defaultValue.id())
		                .add("name", defaultValue.name())                
		                .add("expression_id", defaultValue.expression().id())
		                .add("type", defaultValue.type().toString())
		                .add("type_id", defaultValue.type().name())           
                )
                .up()
                .up();
	}
}
