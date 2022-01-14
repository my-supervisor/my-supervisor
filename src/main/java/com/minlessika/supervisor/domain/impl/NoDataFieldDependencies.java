package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Ordering;
import com.minlessika.sdk.datasource.comparators.Matcher;
import com.minlessika.sdk.datasource.conditions.Filter;
import com.minlessika.sdk.metadata.MethodReferenceUtils.MethodRefWithoutArg;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldDependencies;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.ParamDataField;

public final class NoDataFieldDependencies implements DataFieldDependencies {

	@Override
	public List<EditableDataField> editables() throws IOException {
		return new ArrayList<>();
	}

	@Override
	public List<FormularDataField> formulars() throws IOException {
		return new ArrayList<>();
	}

	@Override
	public List<ParamDataField> params() throws IOException {
		return new ArrayList<>();
	}

	@Override
	public Base base() {
		return Base.EMPTY;
	}

	@Override
	public Filter<DataField> orFilter() {
		throw new UnsupportedOperationException("PgNoDependencies#orFilter");
	}

	@Override
	public Filter<DataField> andFilter() {
		throw new UnsupportedOperationException("PgNoDependencies#andFilter");
	}

	@Override
	public List<DataField> items() throws IOException {
		return new ArrayList<>();
	}

	@Override
	public DataField first() throws IOException {
		return DataField.EMPTY;
	}

	@Override
	public DataField last() throws IOException {
		return DataField.EMPTY;
	}

	@Override
	public DataField get(Long id) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#get");
	}

	@Override
	public Optional<DataField> getOrDefault(Long id) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#getOrDefault");
	}

	@Override
	public boolean contains(Long id) throws IOException {
		return false;
	}

	@Override
	public boolean contains(DataField item) throws IOException {
		return false;
	}

	@Override
	public long count() throws IOException {
		return 0;
	}

	@Override
	public boolean isEmpty() throws IOException {
		return true;
	}

	@Override
	public boolean any() throws IOException {
		return false;
	}

	@Override
	public void remove() throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#remove");
	}

	@Override
	public void remove(Long id) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#remove");
	}

	@Override
	public void remove(DataField item) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#remove");
	}

	@Override
	public DataFieldDependencies where(MethodRefWithoutArg<DataField> methodRef, Object value) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#where");
	}

	@Override
	public DataFieldDependencies where(MethodRefWithoutArg<DataField> methodRef, Matcher matcher) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#where");
	}

	@Override
	public <A1> DataFieldDependencies where(Filter<A1> filter) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#where");
	}

	@Override
	public DataFieldDependencies orderBy(MethodRefWithoutArg<DataField> methodRef, OrderDirection direction)
			throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#orderBy");
	}

	@Override
	public DataFieldDependencies orderBy(MethodRefWithoutArg<DataField> methodRef) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#orderBy");
	}

	@Override
	public DataFieldDependencies orderBy(MethodRefWithoutArg<DataField> methodRef1,
			MethodRefWithoutArg<DataField> methodRef2, OrderDirection direction) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#orderBy");
	}

	@Override
	public DataFieldDependencies orderBy(MethodRefWithoutArg<DataField> methodRef1,
			MethodRefWithoutArg<DataField> methodRef2) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#orderBy");
	}

	@Override
	public DataFieldDependencies orderBy(Ordering ordering) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#orderBy");
	}

	@Override
	public DataFieldDependencies limit(Long number) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#limit");
	}

	@Override
	public DataFieldDependencies start(Long position) throws IOException {
		throw new UnsupportedOperationException("PgNoDependencies#start");
	}
}
