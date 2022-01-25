package com.supervisor.billing.impl;

import com.supervisor.billing.Order;
import com.supervisor.billing.Orders;
import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.RecordSet;

import java.io.IOException;

public class PxOrders extends DomainRecordables<Order, Orders> implements Orders {
	
	private final User user;
	
	public PxOrders(final User user) throws IOException {
		this(user.listOf(Order.class), user);
	}
	
	public PxOrders(final RecordSet<Order> source, final User user) throws IOException {
		super(PxOrder.class, source);
		
		this.user = user;
		this.source = source.where(Order::customer, user.id())
				            .orderBy(Order::id, OrderDirection.DESC);
	}
	
	@Override
	protected Orders domainSetOf(final RecordSet<Order> source) throws IOException {
		return new PxOrders(source, user);
	}
}
