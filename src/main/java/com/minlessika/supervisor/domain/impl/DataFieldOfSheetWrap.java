package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.supervisor.domain.DataFieldDependencies;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;

public class DataFieldOfSheetWrap implements DataFieldOfSheet {

	protected final DataFieldOfSheet origin;
	
	public DataFieldOfSheetWrap(final DataFieldOfSheet origin) throws IOException {
		this.origin = origin;
	}

	@Override
	public DataSheet sheet() throws IOException {
		return origin.sheet();
	}

	@Override
	public EditableDataField origin() throws IOException {
		return origin.origin();
	}

	@Override
	public int columnView() throws IOException {
		return origin.columnView();
	}

	@Override
	public String value() throws IOException {
		return origin.value();
	}

	@Override
	public void update(String value) throws IOException {
		origin.update(value);
	}

	@Override
	public int order() throws IOException {
		return origin.order();
	}

	@Override
	public boolean isMandatory() throws IOException {
		return origin.isMandatory();
	}

	@Override
	public UserScope userScope() throws IOException {
		return origin.userScope();
	}

	@Override
	public void order(int order) throws IOException {
		origin.order(order);
	}

	@Override
	public void makeMandatory(boolean mandatory) throws IOException {
		origin.makeMandatory(mandatory);
	}

	@Override
	public DataSheetModel model() throws IOException {
		return origin.model();
	}

	@Override
	public String code() throws IOException {
		return origin.code();
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public String description() throws IOException {
		return origin.description();
	}

	@Override
	public DataFieldType type() throws IOException {
		return origin.type();
	}

	@Override
	public DataFieldStyle style() throws IOException {
		return origin.style();
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description) throws IOException {
		origin.update(code, name, type, style, description);
	}

	@Override
	public Long id() {
		return origin.id();
	}

	@Override
	public UUID guid() throws IOException {
		return origin.guid();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return origin.creationDate();
	}

	@Override
	public Long creatorId() throws IOException {
		return origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return origin.lastModificationDate();
	}

	@Override
	public Long lastModifierId() throws IOException {
		return origin.lastModifierId();
	}

	@Override
	public Long ownerId() throws IOException {
		return origin.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return origin.tag();
	}

	@Override
	public Base base() {
		return origin.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return origin.listOf(clazz);
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return origin.listOf(clazz, viewScript);
	}

	@Override
	public void changeUserScope(UserScope scope) throws IOException {
		origin.changeUserScope(scope);
	}

	@Override
	public DataFieldDependencies dependencies() throws IOException {
		return origin.dependencies();
	}
}
