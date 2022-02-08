package com.supervisor.sdk.datasource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntitySet<T> {
	List<T> items() throws IOException;
	
	T first() throws IOException;
	T last() throws IOException;
	
	T get(UUID id) throws IOException;
	Optional<T> getOrDefault(UUID id) throws IOException;
	
	boolean contains(UUID id) throws IOException;
	boolean contains(T item) throws IOException;
	
	long count() throws IOException;
	boolean isEmpty() throws IOException;
	boolean any() throws IOException;
	
	void remove() throws IOException;
	void remove(UUID id) throws IOException;
	void remove(T item) throws IOException;
}
