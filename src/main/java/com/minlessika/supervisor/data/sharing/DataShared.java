package com.minlessika.supervisor.data.sharing;

import java.io.IOException;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.domain.Activity;

public interface DataShared <T extends Recordable> extends Recordable {
	
	@Field(
		label="Activity",
		rel=Relation.MANY2ONE
	)
	Activity activity() throws IOException;
	
	T template() throws IOException;
}
