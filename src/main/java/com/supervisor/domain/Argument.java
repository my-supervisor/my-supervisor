package com.supervisor.domain;

import java.io.IOException;

public interface Argument {
	boolean isValid() throws IOException;
	String value() throws IOException;
}
