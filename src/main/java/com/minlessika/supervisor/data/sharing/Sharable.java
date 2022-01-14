package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.supervisor.domain.Activity;

public interface Sharable extends Recordable {
	Activity activity() throws IOException;
}
