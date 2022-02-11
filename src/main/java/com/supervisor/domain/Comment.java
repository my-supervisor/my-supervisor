package com.supervisor.domain;

import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

import java.io.IOException;

@com.supervisor.sdk.metadata.Recordable(
		name="comment",
		label="Commentaires"
)
public interface Comment extends Recordable {
	
	@Field(
		label="Autheur",
		rel=Relation.MANY2ONE
	)
	User author() throws IOException;
	
	@Field(label="Message")
	String message() throws IOException;
}
