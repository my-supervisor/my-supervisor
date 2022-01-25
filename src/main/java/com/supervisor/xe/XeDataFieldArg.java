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
import com.supervisor.domain.EditableDataFieldArg;

public final class XeDataFieldArg extends XeWrap {

	public XeDataFieldArg(final String root, final EditableDataFieldArg item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDataFieldArg(final EditableDataFieldArg item) throws IOException {
		this("data_field_arg", item);
	}
	
	public XeDataFieldArg(final List<EditableDataFieldArg> items) {
		this(
			new Directives()
        		.add("data_field_args")
				.append(
                    new Joined<>(
                		new Mapped<EditableDataFieldArg, Iterable<Directive>>(
        			            item -> transform("data_field_arg", item),
        			            items
            		    )
                    )
                 )
				 .up()
    	);
	}
	
	public XeDataFieldArg(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final EditableDataFieldArg item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("expression", item.expression().name())
	                    .add("expression_id", item.expression().id())
	                    .add("field", item.field().name())
	                    .add("field_id", item.field().id())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())           
                )                
                .up();
	}
}
