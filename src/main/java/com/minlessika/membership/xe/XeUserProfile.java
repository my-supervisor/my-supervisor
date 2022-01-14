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

public final class XeUserProfile extends XeWrap {

	public XeUserProfile(final String root, final User item) throws IOException {
		this(transform(root, item));
	}
	
	public XeUserProfile(final User item) throws IOException {
		this("profile", item);
	}
	
	public XeUserProfile(final Users items) throws IOException {
		this(new Directives()
                		.add("profiles")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<User, Iterable<Directive>>(
	            			            item -> transform("profile", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeUserProfile(final Iterable<Directive> dir) {
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
                		.add("is_company", item.isCompany())
                		.add("country_id", item.address().country().id())
                		.add("preferred_language_id", item.preferredLanguage().id())
                        .add("preferred_currency_id", item.preferredCurrency().id())
                        .add("time_zone_id", item.timeZoneId())
                        .add("photo", item.photo())
                        .add("is_submitted_to_vat", item.billingAddress().submittedToVat())
                        .add("address_line1", item.address().addressLine1())
                        .add("address_line2", item.address().addressLine2())
                        .add("city", item.address().city())
                        .add("state_or_province", item.address().stateOrProvince())
                        .add("company", item.address().company())
                        .add("phone1", item.address().phone1())
                        .add("phone2", item.address().phone2())
                        .add("email", item.address().email())
                        .add("active", item.active())                      
                )
                .up();
	}

}
