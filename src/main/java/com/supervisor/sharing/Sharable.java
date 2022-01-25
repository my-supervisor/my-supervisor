package com.supervisor.sharing;

import java.io.IOException;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.domain.Activity;

public interface Sharable extends Recordable {
	Activity activity() throws IOException;
}
