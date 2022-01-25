package com.supervisor.sdk.datasource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EntitySet<T> {
	List<T> items() throws IOException;
	
	T first() throws IOException;
	T last() throws IOException;
	
	T get(Long id) throws IOException;
	Optional<T> getOrDefault(Long id) throws IOException;
	
	boolean contains(Long id) throws IOException; 
	boolean contains(T item) throws IOException;
	
	long count() throws IOException;
	boolean isEmpty() throws IOException;
	boolean any() throws IOException;
	
	void remove() throws IOException;
	void remove(Long id) throws IOException;
	void remove(T item) throws IOException;
}
