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
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFields;
import com.minlessika.supervisor.domain.DataModel;

public final class XeDataField extends XeWrap {

	public XeDataField(final String root, final DataField field) throws IOException {
		this(transform(root, field, field.model()));
	}
	
	public XeDataField(final DataField field) throws IOException {
		this("datafield", field);
	}
	
	public XeDataField(final String root, final List<DataField> fields, final DataModel model) {
		this(
			new Directives()
        		.add(root)
				.append(
                    new Joined<>(
                		new Mapped<DataField, Iterable<Directive>>(
        			            item -> transform("datafield", item, model),
        			            fields
            		    )
                    )
                 )
				.up()
    	);
	}
	
	public XeDataField(final DataFields fields, final DataModel model) throws IOException {
		this("datafields", fields.items(), model);
	}
	
	public XeDataField(final List<DataField> fields, final DataModel model) {
		this("datafields", fields, model);
	}
	
	public XeDataField(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final DataField field, final DataModel model) throws IOException {
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
	                    .add("model_id", model.id())          
                )                
                .up();
	}
}
