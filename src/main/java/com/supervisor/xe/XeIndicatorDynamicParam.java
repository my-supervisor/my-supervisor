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
import com.supervisor.indicator.IndicatorDynamicNumberParam;
import com.supervisor.indicator.IndicatorDynamicParam;
import com.supervisor.indicator.IndicatorDynamicParams;
import com.supervisor.indicator.IndicatorDynamicStringParam;
import com.supervisor.indicator.impl.PxIndicatorDynamicNumberParam;
import com.supervisor.indicator.impl.PxIndicatorDynamicStringParam;

public final class XeIndicatorDynamicParam extends XeWrap {

	public XeIndicatorDynamicParam(final String root, final IndicatorDynamicParam field) throws IOException {
		this(transform(root, field));
	}
	
	public XeIndicatorDynamicParam(final IndicatorDynamicParam field) throws IOException {
		this("indicator_dynamic_param", field);
	}
	
	public XeIndicatorDynamicParam(final IndicatorDynamicParams fields) throws IOException {
		this(fields.items());
	}
	
	public XeIndicatorDynamicParam(final List<IndicatorDynamicParam> fields) {
		this(new Directives()
                		.add("indicator_dynamic_params")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<IndicatorDynamicParam, Iterable<Directive>>(
	            			            item -> transform("indicator_dynamic_param", item),
	            			            fields
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeIndicatorDynamicParam(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final IndicatorDynamicParam item) throws IOException {
		Directives dir = new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("indicator", item.indicator().name())
	                    .add("indicator_id", item.indicator().id())
	                    .add("origin", item.origin().name())
	                    .add("origin_id", item.origin().id())
	                    .add("aggregate_func", item.aggregateFunc().toString())
	                    .add("aggregate_func_id", item.aggregateFunc().name())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())        
                );

			switch (item.type()) {
				case NUMBER:
					IndicatorDynamicNumberParam nItem = new PxIndicatorDynamicNumberParam(item);
					dir.add("precision").set(nItem.precision()).up()
	                   .add("apply_thousands_separator").set(nItem.applyThousandsSeparator()).up();
					break;
				case STRING:
					IndicatorDynamicStringParam sItem = new PxIndicatorDynamicStringParam(item);
					dir.add("markup").set(sItem.markup()).up();
					break;
				default:
					break;
		    }
			
            dir.up();
            
            return dir;
	}
}
