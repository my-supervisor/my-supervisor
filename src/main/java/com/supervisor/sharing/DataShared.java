package com.supervisor.sharing;

import java.io.IOException;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;
import com.supervisor.domain.Activity;

public interface DataShared <T extends Recordable> extends Recordable {
	
	@Field(
		label="Activity",
		rel= Relation.MANY2ONE
	)
	Activity activity() throws IOException;
	
	T template() throws IOException;
}
