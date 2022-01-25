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
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.FormularExtendedToParentSources;
import com.supervisor.domain.impl.PxAggregatedModel;
import com.supervisor.domain.impl.PxDataSheetModel;

public final class XeFormularExtendedToParentSource extends XeWrap {

	public XeFormularExtendedToParentSource(final String root, final FormularExtendedToParentSource item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularExtendedToParentSource(final FormularExtendedToParentSource item) throws IOException {
		this("formular_extended_to_parent_source", item);
	}
	
	public XeFormularExtendedToParentSource(final FormularExtendedToParentSources items) throws IOException {
		this(items.items());
	}
	
	public XeFormularExtendedToParentSource(String root, final List<FormularExtendedToParentSource> items) {
		this(
			new Directives()
        		.add(root)
				.append(
                    new Joined<>(
                		new Mapped<FormularExtendedToParentSource, Iterable<Directive>>(
        			            item -> transform("formular_extended_to_parent_source", item),
        			            items
            		    )
                    )
                 )
				.up()
    	);
	}
	
	public XeFormularExtendedToParentSource(final List<FormularExtendedToParentSource> items) {
		this("formular_extended_to_parent_sources", items);
	}
	
	public XeFormularExtendedToParentSource(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularExtendedToParentSource item) throws IOException {
		
		final DataModel model = item.listSource().model();
		final DataSheetModel dataSheetModel;
		if(model.type() == DataModelType.DATA_SHEET_MODEL) {
			dataSheetModel = new PxDataSheetModel(model);
		} else {
			dataSheetModel = new PxAggregatedModel(model).coreModel();
		}
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("field_id", item.field().id())
	                    .add("field", item.field().name())
	                    .add("type_id", item.field().type().name())
	                    .add("type", item.field().type().toString())
	                    .add("expr_id", item.expr().id())
	                    .add("expr", item.expr().name())
	                    .add("model_id", model.id())
	                    .add("model", model.name())
	                    .add("data_sheet_model_id", dataSheetModel.id())
	                    .add("data_sheet_model", dataSheetModel.name())
	                    .add("formular_model_id", item.expr().formular().model().id())
	                    .add("formular_model", item.expr().formular().model().name())
	                    .add("active", item.active())
	                    .add("modelViewName", String.format("%s - %s", model.name(), model.activity().name()))           
                )                
                .up();
	}
}
