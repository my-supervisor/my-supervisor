package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.billing.PurchaseOrder;
import com.minlessika.membership.billing.PurchaseOrders;
import com.minlessika.membership.domain.Currency;
import com.minlessika.membership.domain.impl.UserOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XePurchaseOrder extends XeWrap {

	public XePurchaseOrder(final String root, final PurchaseOrder item) throws IOException {
		this(transform(root, item));
	}
	
	public XePurchaseOrder(final PurchaseOrder item) throws IOException {
		this("purchase_order", item);
	}
	
	public XePurchaseOrder(final PurchaseOrders items) throws IOException {
		this(new Directives()
                		.add("purchase_orders")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<PurchaseOrder, Iterable<Directive>>(
	            			            item -> transform("purchase_order", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XePurchaseOrder(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final PurchaseOrder item) throws IOException {
		
		final Currency currency = item.currency();
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())                
	                    .add("reference", item.reference())
	                    .add("description", item.description())
	                    .add("ttc_amount", currency.toString(item.totalAmount()))
	                    .add("paid_amount", currency.toString(item.paidAmount()))
	                    .add("vat_amount", currency.toString(item.vatAmount()))
	                    .add("statusId", item.state().name())
	                    .add("status", item.state().toString())
	                    .add("fromNow", new UserOf(item).prettyTimeOf(item.creationDate()))                  
                )                
                .up();
	}

}
