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
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularExtendedToChildExpression;

public final class XeFormularExtendedToChildExpression extends XeWrap {

	public XeFormularExtendedToChildExpression(final String root, final FormularExtendedToChildExpression item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularExtendedToChildExpression(final FormularExtendedToChildExpression item) throws IOException {
		this("formular_extended_to_child_expression", item);
	}
	
	public XeFormularExtendedToChildExpression(final List<FormularExtendedToChildExpression> items) {
		this(new Directives()
                		.add("formular_extended_to_child_expressions")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<FormularExtendedToChildExpression, Iterable<Directive>>(
	            			            item -> transform("formular_extended_to_child_expression", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeFormularExtendedToChildExpression(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularExtendedToChildExpression item) throws IOException {
		
		final EditableDataField child = item.child();
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("text", item.toText())
	                    .add("name", item.name())
	                    .add("formular", item.formular().name())
	                    .add("formular_id", item.formular().id())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())
	                    .add("model_id", child.model().id())
	                    .add("child_id", child.id())
	                    .add("child", child.name())        
	                    .add("aggregate_id", item.aggregate().name())
	                    .add("aggregate", item.aggregate().toString())            
                )                
                .up();
	}
}
