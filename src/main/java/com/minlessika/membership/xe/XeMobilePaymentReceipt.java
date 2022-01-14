package com.minlessika.membership.xe;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.billing.PaymentReceipt;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.List;

public final class XeMobilePaymentReceipt extends XeWrap {

	public XeMobilePaymentReceipt(final String root, final PaymentReceipt item) throws IOException {
		this(transform(root, item));
	}
	
	public XeMobilePaymentReceipt(final PaymentReceipt item) throws IOException {
		this("mobile_payment_receipt", item);
	}
	
	public XeMobilePaymentReceipt(final List<PaymentReceipt> items) {
		this(new Directives()
                		.add("mobile_payment_receipts")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<PaymentReceipt, Iterable<Directive>>(
	            			            item -> transform("mobile_payment_receipt", item),
	            			            items
    	            		    )
                            )
    	                 )
    					 .up());
	}
	
	public XeMobilePaymentReceipt(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final PaymentReceipt item) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id()) 
	                    .add("reference", item.reference())
	                    .add("number", item.metadata().get("FullNumber").replace("225", ""))
	                    .add("transaction_id1", item.metadata().get("TransactionId1"))
	                    .add("transaction_id2", item.metadata().get("TransactionId2"))                  
                )                
				.up();
	}
}
