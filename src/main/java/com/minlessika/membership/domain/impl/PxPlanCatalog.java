package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.billing.impl.PxProductCatalog;
import com.minlessika.membership.domain.User;

import java.io.IOException;

public final class PxPlanCatalog extends ProductCatalogWrap {

	public PxPlanCatalog(final User user) throws IOException {
		super(
				new PxProductCatalog(
						user.listOf(ProductCatalog.class)
						    .where(ProductCatalog::tag, "PLAN_CATALOG")
						    .first()
			    )
		);
	}

}
