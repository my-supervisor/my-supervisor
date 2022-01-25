package com.supervisor.sdk.utils;

import com.supervisor.sdk.datasource.Recordable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class ListOfUniqueRecord<T extends Recordable> implements List<T> {

	private final List<T> origin;
	
	public ListOfUniqueRecord() {
		this(new ArrayList<>());
	}
	
	public ListOfUniqueRecord(final List<T> origin) {
		this.origin = origin;
	}
	
	@Override
	public int size() {
		return origin.size();
	}

	@Override
	public boolean isEmpty() {
		return origin.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		Recordable r = (Recordable)o;
		return origin.stream()
				     .anyMatch(c -> c.id().equals(r.id())); 
	}

	@Override
	public Iterator<T> iterator() {
		return origin.iterator();
	}

	@Override
	public Object[] toArray() {
		return origin.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return origin.toArray(a);
	}

	@Override
	public boolean add(T e) {
		if(!contains(e)) 
		{
			return origin.add(e);
		}
		
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return origin.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean containsAll = true;
		
		for (Object o : c) {
			containsAll = containsAll && contains(o);
		}
		
		return containsAll;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean addAll = true;
		
		for (T t : c) {
			boolean added = add(t);
			addAll = addAll && added;
		}
		
		return addAll;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean addAll = true; 
		
		for (T t : c) {
			addAll = addAll && !contains(t);
			add(index, t);
		}
		
		return addAll;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return origin.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return origin.retainAll(c);
	}

	@Override
	public void clear() {
		origin.clear();
	}

	@Override
	public T get(int index) {
		return origin.get(index);
	}

	@Override
	public T set(int index, T element) {
		if(!contains(element)) {
			return origin.set(index, element);
		}else
			return element;
	}

	@Override
	public void add(int index, T element) {
		if(!contains(element)) 
		{
			origin.add(index, element);
		}
	}

	@Override
	public T remove(int index) {
		return origin.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return origin.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return origin.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return origin.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return origin.listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return origin.subList(fromIndex, toIndex);
	}

}
