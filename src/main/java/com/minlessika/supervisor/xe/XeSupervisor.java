package com.minlessika.supervisor.xe;

import java.io.IOException;

import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.membership.billing.PaymentRequest;
import com.minlessika.membership.billing.PaymentRequestStatus;
import com.minlessika.membership.xe.XeApplication;
import com.minlessika.membership.xe.XePaymentRequest;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;

public final class XeSupervisor extends XeWrap {

	public XeSupervisor(final Supervisor module) throws IOException {
		this(
			new Directives()
				.append(
						new XePaymentRequest(
	                    		module.user()
	                    			  .paymentRequests()
	                		          .where(PaymentRequest::status, PaymentRequestStatus.PENDING)
	                		          .orderBy(PaymentRequest::id, OrderDirection.DESC)
	                    ).toXembly()
                 )
				.append(
     				new XeActivity(module.activities().where(Activity::isTemplate, false)).toXembly()
         		)
				.append(
                     new XeApplication(
                     		module.user().applications()
                     ).toXembly() 
                 )
		);
	}
	
	public XeSupervisor(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
}
