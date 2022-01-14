package com.minlessika.supervisor.xe;

import java.io.IOException;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.SimpleDataField;

public final class XeSimpleDataField extends XeWrap {

	public XeSimpleDataField(final String root, final SimpleDataField field) throws IOException {
		this(transform(root, field));
	}
	
	public XeSimpleDataField(final SimpleDataField field) throws IOException {
		this("simple_data_field", field);
	}
	
	public XeSimpleDataField(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final SimpleDataField field) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", field.id())
	                    .add("code", field.code())
	                    .add("name", field.name())
	                    .add("viewName", field.toString())
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
	                    .add("default_value", field.defaultValue())
	                    .add("take_last_value", field.takeLastValue())
	                    .add("must_edit_once", field.mustEditOnce())          
                )                
                .up();
	}
}
