package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import com.supervisor.billing.PaymentMethod;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XePaymentMethod extends XeWrap {

	public XePaymentMethod(final String root, final PaymentMethod item) throws IOException {
		this(transform(root, item));
	}
	
	public XePaymentMethod(final PaymentMethod item) throws IOException {
		this("payment_method", item);
	}
	
	public XePaymentMethod(final List<PaymentMethod> items) {
		this(new Directives()
                		.add("payment_methods")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<PaymentMethod, Iterable<Directive>>(
	            			            item -> transform("payment_method", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XePaymentMethod(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final PaymentMethod item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id()) 
	                    .add("name", item.name())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("username", item.username())
	                    .add("password", item.password())
	                    .add("online", item.online())
	                    .add("enabled", item.enabled())
	                    .add("logo", item.logo()) 
	                    .add("tag", item.tag())                   
                )                
				.up();
	}
}
