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
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;
import com.minlessika.supervisor.domain.FormularExtendedToModelSources;

public final class XeFormularExtendedToModelSource extends XeWrap {

	public XeFormularExtendedToModelSource(final String root, final FormularExtendedToModelSource item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularExtendedToModelSource(final FormularExtendedToModelSource item) throws IOException {
		this("formular_extended_to_model_source", item);
	}
	
	public XeFormularExtendedToModelSource(final FormularExtendedToModelSources items) throws IOException {
		this(items.items());
	}
	
	public XeFormularExtendedToModelSource(String root, final List<FormularExtendedToModelSource> items) {
		this(
			new Directives()
        		.add(root)
				.append(
                    new Joined<>(
                		new Mapped<FormularExtendedToModelSource, Iterable<Directive>>(
        			            item -> transform("formular_extended_to_model_source", item),
        			            items
            		    )
                    )
                 )
				.up()
    	);
	}
	
	public XeFormularExtendedToModelSource(final List<FormularExtendedToModelSource> items) {
		this("formular_extended_to_model_sources", items);
	}
	
	public XeFormularExtendedToModelSource(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularExtendedToModelSource item) throws IOException {
		
		final AggregatedModel model = item.expr().formular().model();
		final DataSheetModel modelToExtend = item.model();
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("model_field_id", item.modelField().id())
	                    .add("model_field", item.modelField().name())
	                    .add("type_id", item.modelField().type().name())
	                    .add("type", item.modelField().type().toString())
	                    .add("reference_id", item.reference().id())
	                    .add("reference", item.reference().name())
	                    .add("comparator_id", item.comparator().name())
	                    .add("comparator", item.comparator().toString())
	                    .add("expr_id", item.expr().id())
	                    .add("expr", item.expr().name())
	                    .add("model_to_extend_id", modelToExtend.id())
	                    .add("model_to_extend", modelToExtend.name())
	                    .add("field_to_extend_id", item.fieldToExtend().id())
	                    .add("field_to_extend", item.fieldToExtend().name())
	                    .add("model_id", model.id())
	                    .add("model", model.name())
	                    .add("formular_id", item.expr().formular().id())
	                    .add("formular", item.expr().formular().name())
	                    .add("active", item.active())
	                    .add("modelViewName", String.format("%s - %s", modelToExtend.name(), modelToExtend.activity().name()))           
                )                
                .up();
	}
}
