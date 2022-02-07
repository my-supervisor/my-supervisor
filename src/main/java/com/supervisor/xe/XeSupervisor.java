package com.supervisor.xe;

import java.io.IOException;

import com.supervisor.sdk.datasource.OrderDirection;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.supervisor.billing.PaymentRequest;
import com.supervisor.billing.PaymentRequestStatus;
import com.supervisor.domain.Activity;
import com.supervisor.domain.Supervisor;

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
