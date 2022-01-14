package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDate;

public interface DateMacroArg extends Argument {
	LocalDate toDate() throws IOException;
}
