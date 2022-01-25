package com.supervisor.takes;

import java.io.IOException;
import java.time.LocalDate;

public interface DashboardDate {	
	LocalDate toLocalDate() throws IOException;
}
