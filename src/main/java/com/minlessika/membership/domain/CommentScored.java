package com.minlessika.membership.domain;

import com.minlessika.sdk.metadata.Field;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
		comodel=Comment.class
)
public interface CommentScored extends Comment {
	
	@Field(label="score") /* de 1 à 5 étoiles */
	int score() throws IOException;
}
