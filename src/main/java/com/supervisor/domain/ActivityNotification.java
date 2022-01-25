package com.supervisor.domain;

import java.io.IOException;

public interface ActivityNotification {
	void publish(DataSheet sheet) throws IOException;
	void publish(DataSheetModel model) throws IOException;
}
