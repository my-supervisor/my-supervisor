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
import com.supervisor.domain.FormularExpression;

public final class XeFormularExpression extends XeWrap {

	public XeFormularExpression(final String root, final FormularExpression item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularExpression(final FormularExpression item) throws IOException {
		this("formular_expression", item);
	}
	
	public XeFormularExpression(final List<FormularExpression> items) throws IOException {
		this(new Directives()
                		.add("formular_expressions")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<FormularExpression, Iterable<Directive>>(
	            			            item -> transform("formular_expression", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeFormularExpression(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularExpression item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("text", item.toText())
	                    .add("name", item.name())
	                    .add("full_name", String.format("%s = %s", item.name(), item.toText()))
	                    .add("formular", item.formular().name())
	                    .add("formular_id", item.formular().id())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())       
                )                
                .up();
	}
	
}
