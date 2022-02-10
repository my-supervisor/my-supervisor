package com.supervisor.takes;

import com.supervisor.billing.PurchaseOrder;
import com.supervisor.domain.Membership;
import com.supervisor.domain.impl.DmMembership;
import com.supervisor.sdk.utils.OptUUID;
import com.supervisor.xe.XeMembership;
import com.supervisor.xe.XeOrderLine;
import com.supervisor.xe.XeOrderTax;
import com.supervisor.xe.XePurchaseOrder;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkForm;
import org.takes.Request;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TkPurchaseOrderEdit extends TkForm {

	public TkPurchaseOrderEdit(final Base base) {
		super(base);
	}

	@Override
	protected String xslFormPath() {
		return "/xsl/purchase_order_edit.xsl";
	}

	@Override
	protected Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException {
	
		final Membership module = new DmMembership(base, req);

		List<XeSource> content = new ArrayList<>();
		content.add(new XeMembership(module));
		content.add(itemToShow);
		
		return content;
	}

	@Override
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	@Override
	protected XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException {
		
		final Membership module = new DmMembership(base, req);
		
		PurchaseOrder item = module.purchaseOrders().get(id.get());
		return new XeChain(
				new XePurchaseOrder("item", item),
				new XeOrderLine(item.lines()),
				new XeOrderTax(item.taxes())
			);
	}

	@Override
	protected XeSource postItemDataToShow(OptUUID id, Request req, RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeDirectives(dir);
	}	
}
