package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.PlanFeature;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XePlanFeature extends XeWrap {

	public XePlanFeature(final String root, final PlanFeature item) throws IOException {
		this(transform(root, item));
	}
	
	public XePlanFeature(final PlanFeature item) throws IOException {
		this("plan_feature", item);
	}
	
	public XePlanFeature(final List<PlanFeature> items) {
		this(new Directives()
                		.add("plan_features")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<PlanFeature, Iterable<Directive>>(
	            			            item -> transform("plan_feature", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XePlanFeature(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final PlanFeature item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("order", item.order())  
	                    .add("name", item.name())
	                    .add("basic", item.basic())
	                    .add("enabled", item.enabled())
	                    .add("plan_id", item.plan().id())
	                    .add("plan", item.plan().name())
	                    .add("description", item.description())                   
                )                           
				.up();
	}
}
