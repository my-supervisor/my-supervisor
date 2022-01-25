package com.supervisor.domain;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;

import java.io.IOException;

@Recordable(
		comodel=Comment.class
)
public interface CommentScored extends Comment {
	
	@Field(label="score") /* de 1 à 5 étoiles */
	int score() throws IOException;
}
