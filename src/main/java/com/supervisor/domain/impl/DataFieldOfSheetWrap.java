package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.domain.UserScope;
import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;

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
	public UUID id() {
		return origin.id();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return origin.creationDate();
	}

	@Override
	public UUID creatorId() throws IOException {
		return origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return origin.lastModificationDate();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return origin.lastModifierId();
	}

	@Override
	public UUID ownerId() throws IOException {
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
