package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface CommentsScored extends DomainSet<CommentScored, CommentsScored> {
	Comment post(String message, int score) throws IOException;
}