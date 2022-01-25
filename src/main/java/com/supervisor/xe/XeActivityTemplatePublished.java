package com.supervisor.xe;

import java.io.IOException;

import com.supervisor.domain.impl.UserOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.ActivityTemplatePublished;
import com.supervisor.domain.ActivityTemplatesPublished;

public final class XeActivityTemplatePublished extends XeWrap {

	public XeActivityTemplatePublished(final String root, final ActivityTemplatePublished item) throws IOException {
		this(transform(root, item));
	}
	
	public XeActivityTemplatePublished(final ActivityTemplatePublished item) throws IOException {
		this("activity_template_published", item);
	}
	
	public XeActivityTemplatePublished(final ActivityTemplatesPublished items) throws IOException {
		this(
			new Directives()
        		.add("activity_template_publisheds")
				.append(
                    new Joined<>(
                		new Mapped<ActivityTemplatePublished, Iterable<Directive>>(
        			            item -> transform("activity_template_published", item),
        			            items.items()
            		    )
                    )
                 )    					
				.up()
		);
	}
	
	public XeActivityTemplatePublished(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ActivityTemplatePublished item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
			        	.add("id", item.id())
			            .add("name", item.name())
			            .add("profile", item.profile().name())
			            .add("profile_id", item.profile().id())
			            .add("state", item.state().toString())
			            .add("state_id", item.state().name())
			            .add("icon", item.icon())
			            .add("license_id", item.license().name())
			            .add("license", item.license().toString())
			            .add("description", item.description())
			            .add("designer_id", item.designer().id())
			            .add("designer", item.designer().name())
			            .add("liked", item.likedBy(new UserOf(item)))
			            .add("version", item.version())          
                )                
                .up();
	}

}
