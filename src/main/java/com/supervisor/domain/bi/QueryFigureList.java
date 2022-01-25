package com.supervisor.domain.bi;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import com.supervisor.sdk.time.Periodicity;

public interface QueryFigureList {
	Map<String, Double> figures() throws IOException;
	QueryFigureList with(LocalDate today, Periodicity periodicity) throws IOException;
}
