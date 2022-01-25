package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface CommentsScored extends DomainSet<CommentScored, CommentsScored> {
	Comment post(String message, int score) throws IOException;
}