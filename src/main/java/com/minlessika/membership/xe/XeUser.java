package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.Users;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XeUser extends XeWrap {

	public XeUser(final String root, final User item) throws IOException {
		this(transform(root, item));
	}
	
	public XeUser(final User item) throws IOException {
		this("user", item);
	}
	
	public XeUser(final List<User> items) {
		this(new Directives()
                		.add("users")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<User, Iterable<Directive>>(
	            			            item -> transform("user", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeUser(final Users items) throws IOException {
		this(items.items());
	}
	
	public XeUser(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final User item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())                
	                    .add("name", item.name())
	                    .add("email", item.address().email())
	                    .add("active", item.active())      
	                    .add("photo", item.photo())
	                    .add("is_company", item.isCompany())                  
                )                               
                .up();
	}

}
