package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import com.supervisor.billing.OrderLine;
import com.supervisor.billing.OrderLines;
import com.supervisor.domain.Currency;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeOrderLine extends XeWrap {

	public XeOrderLine(final String root, final OrderLine item) throws IOException {
		this(transform(root, item));
	}
	
	public XeOrderLine(final OrderLine item) throws IOException {
		this("order_line", item);
	}
	
	public XeOrderLine(final OrderLines items) throws IOException {
		this(new Directives()
                		.add("order_lines")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<OrderLine, Iterable<Directive>>(
	            			            item -> transform("order_line", item),
	            			            items.items()
    	            		    )
                            )
    	                 ));
	}
	
	public XeOrderLine(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final OrderLine item) throws IOException {
		
		Currency currency = item.order().currency();
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())                
	                    .add("name", item.name())
	                    .add("description", item.description())
	                    .add("quantity", String.format("%s mois", item.quantity()))
	                    .add("unit_price", currency.toString(item.unitPrice()))
	                    .add("ht_amount", currency.toString(item.htAmount()))                  
                )                
                .up();
	}

}
