package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface Comments extends DomainSet<Comment, Comments> {
	Comment post(String message) throws IOException;
}