package com.supervisor.sdk.datasource;

import java.io.IOException;
import java.util.List;

public interface Statement {
	List<ResultStatement> execute() throws IOException;
}
