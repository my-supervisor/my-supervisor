package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.UserScope;
import com.supervisor.domain.SimpleDataField;
import com.supervisor.domain.SimpleDataFieldOfSheet;
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldOfSheets;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheets;
import com.supervisor.domain.EditableDataField;

public final class GeneratedSimpleDataFieldOfSheet implements SimpleDataFieldOfSheet {

	private final SimpleDataField origin;
	private String value = StringUtils.EMPTY;
	
	public GeneratedSimpleDataFieldOfSheet(final SimpleDataField origin) throws IOException {
		this.origin = origin;
		
		final SimpleDataField field = origin;
		this.value = field.defaultValue();
		if(field.takeLastValue()) {
			DataSheets latestSheets = new PgDataSheets(new UserOf(origin))
					  .where(DataSheet::model, origin.model().id()) 
					  .orderBy(DataSheet::date, DataSheet::id);
			
			if(latestSheets.any()) {
				DataFieldOfSheets lastData = latestSheets.last()
														 .fields()
														 .where(DataFieldOfSheet::code, origin.code());
				
				if(lastData.any()) {
					this.value = lastData.first().value();
				}
			}			
		}
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
	public Long id() {
		return 0L;
	}

	@Override
	public UUID guid() throws IOException {
		return this.origin.guid();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return this.origin.creationDate();
	}

	@Override
	public Long creatorId() throws IOException {
		return this.origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return this.origin.lastModificationDate();
	}

	@Override
	public Long lastModifierId() throws IOException {
		return this.origin.lastModifierId();
	}

	@Override
	public Long ownerId() throws IOException {
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
			fields = origin.model().fields().editables().items();
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
	public String defaultValue() throws IOException {
		return origin.defaultValue();
	}

	@Override
	public void defaultValue(String value) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean takeLastValue() throws IOException {
		return origin.takeLastValue();
	}

	@Override
	public void takeLastValue(boolean takeLastValue) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean mustEditOnce() throws IOException {
		return origin.mustEditOnce();
	}

	@Override
	public void makeMustEditOnce(boolean mustEditOnce) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly() throws IOException {
		return origin.isReadOnly();
	}

	@Override
	public void makeReadOnly(boolean readOnly) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void changeUserScope(UserScope scope) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(String code, String name, DataFieldType type, String description) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DataFieldDependencies dependencies() throws IOException {
		return origin.dependencies();
	}
}
