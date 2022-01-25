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
import com.supervisor.domain.MappedDataField;
import com.supervisor.domain.MappedDataFields;

public final class XeMappedField extends XeWrap {

	public XeMappedField(final String root, final MappedDataField field) throws IOException {
		this(transform(root, field));
	}
	
	public XeMappedField(final MappedDataField field) throws IOException {
		this("mapped_data_field", field);
	}
	
	public XeMappedField(final MappedDataFields fields) throws IOException {
		this(fields.items());
	}
	
	public XeMappedField(final List<MappedDataField> fields) {
		this(new Directives()
                		.add("mapped_data_fields")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<MappedDataField, Iterable<Directive>>(
	            			            item -> transform("mapped_data_field", item),
	            			            fields
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeMappedField(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final MappedDataField item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("param_id", item.param().id())
	                    .add("param", item.param().name())
	                    .add("field_to_use", item.fieldToUse().name())
	                    .add("field_to_use_id", item.fieldToUse().id())
	                    .add("type", item.param().type().toString())
	                    .add("type_id", item.param().type().name())
	                    .add("operator", item.operator().toString())
	                    .add("operator_id", item.operator().name())          
                )                
                .up();
	}
}
