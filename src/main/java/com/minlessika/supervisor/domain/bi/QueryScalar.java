package com.minlessika.supervisor.domain.bi;

import com.minlessika.sdk.time.Periodicity;
import java.io.IOException;
import java.time.LocalDate;


public interface QueryScalar {
	Object result() throws IOException;
	QueryScalar with(LocalDate today, Periodicity periodicity) throws IOException;
}
