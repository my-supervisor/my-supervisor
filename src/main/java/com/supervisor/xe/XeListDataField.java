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
import com.supervisor.domain.ListDataField;

public final class XeListDataField extends XeWrap {

	public XeListDataField(final String root, final ListDataField field) throws IOException {
		this(transform(root, field));
	}
	
	public XeListDataField(final ListDataField field) throws IOException {
		this("list_data_field", field);
	}
	
	public XeListDataField(final List<ListDataField> fields) {
		this(
			new Directives()
        		.add("list_data_fields")
				.append(
                    new Joined<>(
                		new Mapped<ListDataField, Iterable<Directive>>(
    			            item -> transform("list_data_field", item),
    			            fields
            		    )
                    )
                 )
				.up()
    	);
	}
	
	public XeListDataField(final Iterable<Directive> dir) {
		super(
			new XeSource() {
	            @Override
	            public Iterable<Directive> toXembly() throws IOException {
	                return dir;
	            }
	        }
		);
	}
	
	private static Iterable<Directive> transform(final String root, final ListDataField field) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", field.id())
	                    .add("code", field.code())
	                    .add("name", field.name())
	                    .add("viewName", field.toString())
	                    .add("order_direction", field.orderDirection().toString())
	                    .add("order_direction_id", field.orderDirection().name())
	                    .add("type", field.type().toString())
	                    .add("type_id", field.type().name())
	                    .add("style", field.style().toString())
	                    .add("style_id", field.style().name())
	                    .add("description", field.description())
	                    .add("owner_id", field.ownerId())
	                    .add("order", field.order())
	                    .add("is_mandatory", field.isMandatory())
	                    .add("is_read_only", field.isReadOnly())
	                    .add("model_id", field.model().id())
	                    .add("must_edit_once", field.mustEditOnce())
	                    .add("can_be_updated", field.canBeUpdated())
	                    .add("limit", field.limit())          
                )                
                .up();
	}
}
