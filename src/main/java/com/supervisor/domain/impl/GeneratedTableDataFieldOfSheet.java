package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.UserScope;
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRows;

public final class GeneratedTableDataFieldOfSheet implements TableDataFieldOfSheet {

	private final TableDataField origin;
	private String value = StringUtils.EMPTY;
	
	public GeneratedTableDataFieldOfSheet(final TableDataField origin) {
		this.origin = origin;
	}
	
	@Override
	public String code() throws IOException {
		return this.origin.code();
	}

	@Override
	public String name() throws IOException {
		return this.origin.name();
	}

	@Override
	public String description() throws IOException {
		return this.origin.description();
	}

	@Override
	public DataFieldType type() throws IOException {
		return this.origin.type();
	}

	@Override
	public DataFieldStyle style() throws IOException {
		return this.origin.style();
	}

	@Override
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
			throws IOException {
		throw  new UnsupportedOperationException();

	}

	@Override
	public UUID id() {
		return null;
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return this.origin.creationDate();
	}

	@Override
	public UUID creatorId() throws IOException {
		return this.origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return this.origin.lastModificationDate();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return this.origin.lastModifierId();
	}

	@Override
	public UUID ownerId() throws IOException {
		return this.origin.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return this.origin.tag();
	}

	@Override
	public DataSheet sheet() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public EditableDataField origin() throws IOException {
		return this.origin;
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
	public String value() throws IOException {
		return value;
	}

	@Override
	public void update(String value) throws IOException {
		this.value = value;
	}

	@Override
	public UserScope userScope() throws IOException {
		return origin.userScope();
	}

	@Override
	public int columnView() throws IOException {
		
		int column = 0;
			
		List<EditableDataField> fields;
		if(origin instanceof DataFieldOfSheet) {
			List<DataFieldOfSheet> fieldsOfSheet = ((DataFieldOfSheet)origin).sheet().fields().items();
			fields = new ArrayList<>();
			for (DataFieldOfSheet fs : fieldsOfSheet) {
				fields.add(fs);
			}
		} else {
			fields = origin.model().fields().items().stream().map(c -> (EditableDataField)c).collect(Collectors.toList());
		}
		
		int dizaine = order() - order() % 10;
		for (EditableDataField fieldO : fields) {
			int order = fieldO.order();
			int dizaineO = order - order % 10;
			if(dizaineO == dizaine)
				column++;
		}
				
		return column;
	}

	@Override
	public void makeMandatory(boolean mandatory) throws IOException {
		origin.makeMandatory(mandatory); 
	}

	@Override
	public void order(int order) throws IOException {
		origin.order(order);
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
	public DataSheetModel model() throws IOException {
		return origin.model();
	}

	@Override
	public DataSheetModel structure() throws IOException {
		return origin.structure();
	}

	@Override
	public List<EditableDataField> columns() throws IOException {
		return origin.columns();
	}

	@Override
	public void update(String code, String name, String description) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public TableDataFieldOfSheetRows rows() throws IOException {
		return null;
	}
	
	@Override
	public void changeUserScope(UserScope scope) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DataFieldDependencies dependencies() throws IOException {
		return origin.dependencies();
	}
}
