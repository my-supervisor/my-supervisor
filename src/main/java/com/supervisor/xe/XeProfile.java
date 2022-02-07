package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.AllProfiles;
import com.supervisor.domain.Profile;
import com.supervisor.domain.Profiles;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XeProfile extends XeWrap {

	public XeProfile(final String root, final Profile item) throws IOException {
		this(transform(root, item));
	}
	
	public XeProfile(final Profile item) throws IOException {
		this("profile", item);
	}
	
	public XeProfile(final Profiles items) throws IOException {
		this(items.items());
	}
	
	public XeProfile(final AllProfiles items) throws IOException {
		this(items.items());
	}
	
	public XeProfile(final List<Profile> items) {
		this(
			new Directives()
            		.add("profiles")
					.append(
	                    new Joined<>(
                    		new Mapped<Profile, Iterable<Directive>>(
            			            item -> transform("profile", item),
            			            items
	            		    )
                        )
	                 ).up()
    	);
	}
	
	public XeProfile(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Profile item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("code", item.code())
	                    .add("parent", item.parent().name())
	                    .add("parent_id", item.parent().id())
	                    .add("is_admin", item.isAdmin())
	                    .add("is_simple_user", item.isSimpleUser())
	                    .add("is_anonymous", item.isAnonymous())    
                )                
                .up();
	}

}
