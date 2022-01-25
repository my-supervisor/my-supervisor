package com.supervisor.sdk.datasource;

import com.supervisor.sdk.datasource.comparators.Matcher;
import com.supervisor.sdk.datasource.conditions.Filter;
import com.supervisor.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DomainSet<T, D> {
	
	Base base();
	
	Filter<T> orFilter();
	Filter<T> andFilter();
	
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
	
	D where(MethodRefWithoutArg<T> methodRef, Object value) throws IOException;
	D where(MethodRefWithoutArg<T> methodRef, Matcher matcher) throws IOException;
	<A1> D where(Filter<A1> filter) throws IOException;
	
	D orderBy(MethodRefWithoutArg<T> methodRef, OrderDirection direction) throws IOException;
	D orderBy(MethodRefWithoutArg<T> methodRef) throws IOException;
	D orderBy(MethodRefWithoutArg<T> methodRef1, MethodRefWithoutArg<T> methodRef2, OrderDirection direction) throws IOException;
	D orderBy(MethodRefWithoutArg<T> methodRef1, MethodRefWithoutArg<T> methodRef2) throws IOException;
	D orderBy(Ordering ordering) throws IOException;
	
	D limit(Long number) throws IOException;
	D start(Long position) throws IOException;	
}
