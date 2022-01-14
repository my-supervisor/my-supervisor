package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		name="base_comment", 
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
