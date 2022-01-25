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
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.ListDataFieldSources;

public final class XeListDataFieldSource extends XeWrap {

	public XeListDataFieldSource(final String root, final ListDataFieldSource item) throws IOException {
		this(transform(root, item));
	}
	
	public XeListDataFieldSource(final ListDataFieldSource item) throws IOException {
		this("list_data_field_source", item);
	}
	
	public XeListDataFieldSource(final ListDataFieldSources items) throws IOException {
		this(items.items());
	}
	
	public XeListDataFieldSource(String root, final List<ListDataFieldSource> items) {
		this(new Directives()
                		.add(root)
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ListDataFieldSource, Iterable<Directive>>(
	            			            item -> transform("list_data_field_source", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeListDataFieldSource(final List<ListDataFieldSource> items) {
		this("list_data_field_sources", items);
	}
	
	public XeListDataFieldSource(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ListDataFieldSource item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("field_id", item.field().id())
	                    .add("field", item.field().name())
	                    .add("type_id", item.field().type().name())
	                    .add("type", item.field().type().toString())
	                    .add("model_id", item.field().model().id())
	                    .add("model", item.field().model().name())
	                    .add("list_model_id", item.model().id())
	                    .add("list_model", item.model().name())
	                    .add("field_to_display_id", item.fieldToDisplay().id())
	                    .add("field_to_display", item.fieldToDisplay().name())
	                    .add("order_field_id", item.orderField().id())
	                    .add("order_field", item.orderField().name())
	                    .add("active", item.active())           
                )                
                .up();
	}
}
