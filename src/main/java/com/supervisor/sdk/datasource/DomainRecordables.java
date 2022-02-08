package com.supervisor.sdk.datasource;

import com.supervisor.sdk.datasource.comparators.Matcher;
import com.supervisor.sdk.datasource.conditions.Filter;
import com.supervisor.sdk.utils.logging.Logger;
import com.supervisor.sdk.utils.logging.MLogger;
import com.supervisor.sdk.metadata.MethodReferenceUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DomainRecordables<A1 extends Recordable, D extends DomainSet<A1, D>> implements DomainSet<A1, D> {

	private static final Logger logger = new MLogger(DomainRecordables.class);
	
	protected RecordSet<A1> source;
	private final Class<? extends A1> clazzImpl;
	
	public DomainRecordables(final Class<? extends A1> clazzImpl, final RecordSet<A1> source) {
		this.clazzImpl = clazzImpl;
		this.source = source;
	}
	
	protected A1 domainOf(final Record<A1> record) throws IOException {
		return domainOf(record.id());				
	}
	
	protected A1 domainOf(final UUID id) throws IOException {
		Constructor<? extends A1> constructor;
		try {			
			constructor = clazzImpl.getConstructor(Record.class);
			return constructor.newInstance(source.get(id)); 
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error(e);
			throw new IOException(e);
		}					
	}
	
	@Override
	public List<A1> items() throws IOException {
		List<A1> list = new ArrayList<>();
		for (Record<A1> record : source.items()) {
			list.add(domainOf(record));
		}		
		
		return list;
	}

	@Override
	public A1 first() throws IOException {
		return domainOf(source.first());
	}

	@Override
	public A1 last() throws IOException {
		return domainOf(source.last());
	}

	@Override
	public long count() throws IOException {
		return source.count();
	}

	@Override
	public boolean isEmpty() throws IOException {
		return source.isEmpty();
	}

	@Override
	public final void remove() throws IOException {
		for (A1 item : items()) {
			remove(item);
		}
	}

	@Override
	public boolean any() throws IOException {
		return source.any();
	}

	@Override
	public D where(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Object value) throws IOException {
		return domainSetOf(source.where(methodRef, value));
	}
	
	@SuppressWarnings("unchecked")
	protected D domainSetOf(final RecordSet<A1> source) throws IOException {

		Constructor<? extends D> constructor;
		try {			
			constructor = (Constructor<? extends D>) this.getClass().getConstructor(RecordSet.class);
			return constructor.newInstance(source); 
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error(e); 
			throw new IOException(e);
		}
	}

	@Override
	public D where(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, Matcher matcher) throws IOException {
		return domainSetOf(source.where(methodRef, matcher));
	}

	@Override
	public Filter<A1> orFilter() {
		return source.orFilter();
	}
	
	@Override
	public Filter<A1> andFilter() {
		return source.andFilter();
	}

	@SuppressWarnings("hiding")
	@Override
	public <A1> D where(Filter<A1> filter) throws IOException {
		return domainSetOf(source.where(filter.condition()));
	}

	@Override
	public D orderBy(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef, OrderDirection direction) throws IOException {
		return domainSetOf(source.orderBy(methodRef, direction));
	}

	@Override
	public D orderBy(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef) throws IOException {
		return domainSetOf(source.orderBy(methodRef));
	}

	@Override
	public D orderBy(Ordering ordering) throws IOException {
		return domainSetOf(source.orderBy(ordering));
	}

	@Override
	public D orderBy(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef1, MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef2,
                     OrderDirection direction) throws IOException {
		return domainSetOf(source.orderBy(methodRef1, methodRef2, direction));
	}

	@Override
	public D orderBy(MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef1, MethodReferenceUtils.MethodRefWithoutArg<A1> methodRef2)
			throws IOException {
		return domainSetOf(source.orderBy(methodRef1, methodRef2));
	}

	@Override
	public D limit(Long number) throws IOException {
		return domainSetOf(source.limit(number));
	}

	@Override
	public D start(Long position) throws IOException {
		return domainSetOf(source.start(position));
	}

	@Override
	public A1 get(final UUID id) throws IOException {
		return domainOf(source.get(id));
	}

	@Override
	public Optional<A1> getOrDefault(final UUID id) throws IOException {
		
		A1 item = null;
		
		try {
			item = get(id);
		} catch (Exception e) {

		}
		
		return Optional.of(item);
	}

	@Override
	public boolean contains(final UUID id) throws IOException {
		return source.contains(id);
	}

	@Override
	public boolean contains(final A1 item) throws IOException {
		return source.contains(item.id()); 
	}

	@Override
	public final void remove(final UUID id) throws IOException {
		if(contains(id)) {
			final A1 item = get(id);
			remove(item);
		}
	}

	@Override
	public void remove(A1 item) throws IOException {
		source.where(A1::id, item.id()).remove();
	}

	@Override
	public Base base() {
		return source.base();
	}
}
