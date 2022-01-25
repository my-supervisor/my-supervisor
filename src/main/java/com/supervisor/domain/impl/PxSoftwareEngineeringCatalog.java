package com.supervisor.domain.impl;

import com.supervisor.billing.ProductCatalog;
import com.supervisor.billing.impl.PxProductCatalog;
import com.supervisor.domain.User;

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
