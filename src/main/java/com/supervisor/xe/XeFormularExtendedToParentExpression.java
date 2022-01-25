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
import com.supervisor.domain.FormularExtendedToParentExpression;

public final class XeFormularExtendedToParentExpression extends XeWrap {

	public XeFormularExtendedToParentExpression(final String root, final FormularExtendedToParentExpression item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularExtendedToParentExpression(final FormularExtendedToParentExpression item) throws IOException {
		this("formular_extended_to_parent_expression", item);
	}
	
	public XeFormularExtendedToParentExpression(final List<FormularExtendedToParentExpression> items) {
		this(new Directives()
                		.add("formular_extended_to_parent_expressions")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<FormularExtendedToParentExpression, Iterable<Directive>>(
	            			            item -> transform("formular_extended_to_parent_expression", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeFormularExtendedToParentExpression(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularExtendedToParentExpression item) throws IOException {
		
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
	                    .add("reference_id", item.reference().id())
	                    .add("reference", item.reference().name())             
                )                
                .up();
	}
}
