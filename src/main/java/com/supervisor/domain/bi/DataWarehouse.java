package com.supervisor.domain.bi;

import java.io.IOException;

public interface DataWarehouse {
	BiResult query(BiRequest request) throws IOException;
}
