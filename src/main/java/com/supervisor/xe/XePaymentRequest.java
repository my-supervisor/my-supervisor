package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import com.supervisor.billing.PaymentRequest;
import com.supervisor.billing.PaymentRequests;
import com.supervisor.billing.UserPaymentRequests;
import com.supervisor.domain.Currency;
import com.supervisor.domain.impl.UserOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XePaymentRequest extends XeWrap {

	public XePaymentRequest(final String root, final PaymentRequest item) throws IOException {
		this(transform(root, item));
	}
	
	public XePaymentRequest(final PaymentRequest item) throws IOException {
		this("payment_request", item);
	}
	
	public XePaymentRequest(final PaymentRequests requests) throws IOException {
		this(requests.items());
	}
	
	public XePaymentRequest(final UserPaymentRequests requests) throws IOException {
		this(requests.items());
	}
	
	public XePaymentRequest(final List<PaymentRequest> items) throws IOException {
		this(new Directives()
                		.add("payment_requests")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<PaymentRequest, Iterable<Directive>>(
	            			            item -> transform("payment_request", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XePaymentRequest(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final PaymentRequest item) throws IOException {
		
		final Currency currency = item.order().currency();
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())                
	                    .add("reference", item.reference())
	                    .add("object", item.object())
	                    .add("notes", item.notes())
	                    .add("order", item.order().reference())
	                    .add("order_id", item.order().id())
	                    .add("amount", currency.toString(item.amount()))
	                    .add("statusId", item.status().name())
	                    .add("status", item.status().toString())
	                    .add("fromNow", new UserOf(item).prettyTimeOf(item.creationDate()))                   
                )                
                .up();
	}

}
