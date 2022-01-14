package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.billing.OrderTax;
import com.minlessika.membership.billing.OrderTaxes;
import com.minlessika.membership.domain.Currency;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeOrderTax extends XeWrap {

	public XeOrderTax(final String root, final OrderTax item) throws IOException {
		this(transform(root, item));
	}
	
	public XeOrderTax(final OrderTax item) throws IOException {
		this("order_tax", item);
	}
	
	public XeOrderTax(final OrderTaxes items) throws IOException {
		this(new Directives()
                		.add("order_taxes")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<OrderTax, Iterable<Directive>>(
	            			            item -> transform("order_tax", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeOrderTax(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final OrderTax item) throws IOException {
		
		Currency currency = item.order().currency();
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                    .add("id", item.id())                
	                    .add("name", item.name())
	                    .add("amount", currency.toString(item.amount()))                   
                )
                .up();
	}

}
