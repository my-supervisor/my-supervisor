package com.supervisor.domain.impl;

import com.supervisor.billing.ProductCatalog;
import com.supervisor.billing.impl.PxProductCatalog;
import com.supervisor.domain.User;

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
