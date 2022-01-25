package com.supervisor.domain;

import java.io.IOException;
import java.util.List;

public interface ListDataFieldUpdater {
	List<DataSheet> update() throws IOException;
}
