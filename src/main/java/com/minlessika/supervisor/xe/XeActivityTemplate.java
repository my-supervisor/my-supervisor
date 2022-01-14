package com.minlessika.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.ActivityTemplates;

public final class XeActivityTemplate extends XeWrap {

	public XeActivityTemplate(final String root, final ActivityTemplate item) throws IOException {
		this(transform(root, item));
	}
	
	public XeActivityTemplate(final ActivityTemplate item) throws IOException {
		this("activity_template", item);
	}
	
	public XeActivityTemplate(final ActivityTemplates items) throws IOException {
		this(
			new Directives()
        		.add("activity_templates")
				.append(
                    new Joined<>(
                		new Mapped<ActivityTemplate, Iterable<Directive>>(
        			            item -> transform("activity_template", item),
        			            items.items()
            		    )
                    )
                 )
				 .up()
    	);
	}
	
	public XeActivityTemplate(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ActivityTemplate item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("state", item.state().toString())
	                    .add("state_id", item.state().name())
	                    .add("description", item.description())
	                    .add("designer_id", item.designer().id())
	                    .add("designer", item.designer().name())
	                    .add("is_owner", new UserOf(item).own(item))
	                    .add("is_template", item.isTemplate())
	                    .add("version", item.version())
	                    .add("periodicity_unit", item.periodicity().unit().toString())
	                    .add("periodicity_unit_id", item.periodicity().unit().name())
	                    .add("periodicity_reference", item.periodicity().reference())
	                    .add("periodicity_number", item.periodicity().number())
	                    .add("periodicity_close_interval", item.periodicity().closeInterval())      
                )                
                .up();
	}

}
