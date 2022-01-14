package com.minlessika.supervisor.domain;

import java.io.IOException;

public interface Params {
	boolean contains(long id) throws IOException;
	ParamDataField get(long id) throws IOException;
}
