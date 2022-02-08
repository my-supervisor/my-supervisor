package com.supervisor.domain;

import java.io.IOException;
import java.util.UUID;

public interface Params {
	boolean contains(UUID id) throws IOException;
	ParamDataField get(UUID id) throws IOException;
}
