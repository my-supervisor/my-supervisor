package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.DataFields;

public final class XeTableDataField extends XeWrap {

	public XeTableDataField(final String root, final TableDataField field) throws IOException {
		this(transform(root, field));
	}
	
	public XeTableDataField(final TableDataField field) throws IOException {
		this("datafield", field);
	}
	
	public XeTableDataField(final String root, final List<TableDataField> fields) {
		this(new Directives()
                		.add(root)
    					.append(
    	                    new Joined<>(
	                    		new Mapped<TableDataField, Iterable<Directive>>(
	            			            item -> transform("datafield", item),
	            			            fields
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeTableDataField(final DataFields fields) throws IOException {
		this("datafields", fields.tables().items());
	}
	
	public XeTableDataField(final List<TableDataField> fields) {
		this("datafields", fields);
	}
	
	public XeTableDataField(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final TableDataField field) throws IOException {
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
	                    .add("model_id", field.model().id())          
                )                
                .up();
	}
}
