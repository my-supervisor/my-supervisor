package com.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.ActivityTemplateRelease;
import com.supervisor.domain.ActivityTemplateReleases;

public final class XeActivityTemplateRelease extends XeWrap {

	public XeActivityTemplateRelease(final String root, final ActivityTemplateRelease item) throws IOException {
		this(transform(root, item));
	}
	
	public XeActivityTemplateRelease(final ActivityTemplateRelease item) throws IOException {
		this("activity_template_release", item);
	}
	
	public XeActivityTemplateRelease(final ActivityTemplateReleases items) throws IOException {
		this(
			new Directives()
        		.add("activity_template_releases")
				.append(
                    new Joined<>(
                		new Mapped<ActivityTemplateRelease, Iterable<Directive>>(
        			            item -> transform("activity_template_release", item),
        			            items.items()
            		    )
                    )
                 ).up()
    	);
	}
	
	public XeActivityTemplateRelease(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ActivityTemplateRelease item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("version", item.version())
	                    .add("notes", item.notes())
	                    .add("template_id", item.template().id())
	                    .add("template", item.template().name())
	                    .add("creation_date", item.creationDate().toLocalDate().toString())       
                )                
                .up();
	}

}
