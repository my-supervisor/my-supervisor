package com.supervisor.domain;

import com.supervisor.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Comments extends DomainSet<Comment, Comments> {
	Comment post(String message) throws IOException;
}