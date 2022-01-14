package com.minlessika.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.ActivityParam;
import com.minlessika.supervisor.domain.ActivityParams;

public final class XeActivityParam extends XeWrap {

	public XeActivityParam(final String root, final ActivityParam item) throws IOException {
		this(transform(root, item));
	}
	
	public XeActivityParam(final ActivityParam item) throws IOException {
		this("activity_param", item);
	}
	
	public XeActivityParam(final ActivityParams items) throws IOException {
		this(new Directives()
                		.add("activity_params")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ActivityParam, Iterable<Directive>>(
	            			            item -> transform("activity_param", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeActivityParam(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ActivityParam item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("model", item.model().name())
	                    .add("model_id", item.model().id())
	                    .add("activity_id", item.activity().id())
	                    .add("name", item.name())
	                    .add("code", item.code())
	                    .add("value", item.value())
	                    .add("typeId", item.type().name())
	                    .add("type", item.type().toString())      
                )                
                .up();
	}

}
