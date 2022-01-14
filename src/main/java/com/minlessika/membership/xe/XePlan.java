package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.Plan;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XePlan extends XeWrap {

	public XePlan(final String root, final Plan item) throws IOException {
		this(transform(root, item));
	}
	
	public XePlan(final Plan item) throws IOException {
		this("plan", item);
	}
	
	public XePlan(final List<Plan> items) {
		this(new Directives()
                		.add("plans")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Plan, Iterable<Directive>>(
	            			            item -> transform("plan", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XePlan(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Plan item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("order", item.order())  
	                    .add("name", item.name())
	                    .add("reference", item.reference())
	                    .add("profile", item.profile().name())
	                    .add("profile_id", item.profile().id())
	                    .add("preferred", item.preferred())
	                    .add("enabled", item.enabled())
	                    .add("price", item.price())  
	                    .add("viewPrice", item.toString())                
                )                
				.up();
	}
}
