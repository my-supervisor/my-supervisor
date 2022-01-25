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
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.AggregatedModels;

public final class XeAggregatedModel extends XeWrap {

	public XeAggregatedModel(final String root, final AggregatedModel item) throws IOException {
		this(transform(root, item));
	}
	
	public XeAggregatedModel(final AggregatedModel item) throws IOException {
		this("aggregated_model", item);
	}
	
	public XeAggregatedModel(final AggregatedModels items) throws IOException {
		this(items.items());
	}
	
	public XeAggregatedModel(final List<AggregatedModel> items) {
		this(
			new Directives()
        		.add("aggregated_models")
				.append(
                    new Joined<>(
                		new Mapped<AggregatedModel, Iterable<Directive>>(
        			            item -> transform("aggregated_model", item),
        			            items
            		    )
                    )
                 ).up()
    	);
	}
	
	public XeAggregatedModel(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final AggregatedModel item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())
	                    .add("model", String.format("%s - %s", item.model().code(), item.model().name())) 
	                    .add("model_id", item.model().id())
	                    .add("activity_id", item.activity().id())
	                    .add("activity", item.activity().name())
	                    .add("model_activity_id", item.model().activity().id())
	                    .add("model_activity", item.model().activity().name())
	                    .add("date_reference_id", item.dateReference().id())
	                    .add("date_reference", item.dateReference().name())          
                )                
                .up();
	}

}
