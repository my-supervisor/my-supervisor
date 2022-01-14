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
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.EditableDataFields;

public final class XeEditableDataField extends XeWrap {

	public XeEditableDataField(final String root, final EditableDataField field, final DataModel model) throws IOException {
		this(transform(root, field, model));
	}
	
	public XeEditableDataField(final String root, final EditableDataField field) throws IOException {
		this(root, field, field.model());
	}
	
	public XeEditableDataField(final EditableDataField field) throws IOException {
		this("datafield", field);
	}
	
	public XeEditableDataField(final String root, final List<EditableDataField> fields, final DataModel model) {
		this(
			new Directives()
        		.add(root)
				.append(
                    new Joined<>(
                		new Mapped<EditableDataField, Iterable<Directive>>(
        			            item -> transform("datafield", item, model),
        			            fields
            		    )
                    )
                 )
				 .up()
    	);
	}
	
	public XeEditableDataField(final EditableDataFields fields) throws IOException {
		this("datafields", fields.items(), fields.items().isEmpty() ? DataModel.EMPTY : fields.items().get(0).model());
	}
	
	public XeEditableDataField(final List<EditableDataField> fields) throws IOException {
		this("datafields", fields, fields.isEmpty() ? DataModel.EMPTY : fields.get(0).model());
	}
	
	public XeEditableDataField(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final EditableDataField field, final DataModel model) throws IOException {
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
	                    .add("order", field.order())
	                    .add("is_mandatory", field.isMandatory())     
                )                
                .up();
	}
}
