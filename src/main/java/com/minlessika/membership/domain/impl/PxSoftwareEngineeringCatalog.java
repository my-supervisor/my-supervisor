package com.minlessika.membership.domain.impl;

import com.minlessika.membership.billing.ProductCatalog;
import com.minlessika.membership.billing.impl.PxProductCatalog;
import com.minlessika.membership.domain.User;

import java.io.IOException;

public final class PxSoftwareEngineeringCatalog extends ProductCatalogWrap {

	public PxSoftwareEngineeringCatalog(final User user) throws IOException {
		super(
				new PxProductCatalog(
						user.listOf(ProductCatalog.class)
						    .where(ProductCatalog::tag, "SOFT_ENG_CATALOG")
						    .first()
			    )
		);
	}

}
