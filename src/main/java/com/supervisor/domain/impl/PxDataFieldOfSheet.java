package com.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.comparators.Matchers;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.UserScope;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldDependencies;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldStyle;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;

public final class PxDataFieldOfSheet extends DomainRecordable<DataFieldOfSheet> implements DataFieldOfSheet {

	private final EditableDataField origin;
	
	public PxDataFieldOfSheet(final Record<DataFieldOfSheet> record) throws IOException {
		super(record);
		
		final UUID originId = record.valueOf(DataFieldOfSheet::origin);
		this.origin = (EditableDataField)TypedDataField.convert(record.of(DataField.class, originId)); 
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
	public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
			throws IOException {
		origin.update(code, name, type, style, description);
	}

	@Override
	public DataSheet sheet() throws IOException {
		final UUID id = record.valueOf(DataFieldOfSheet::sheet);
		return new PxDataSheet(
			record.of(DataSheet.class, id)
		);
	}

	@Override
	public EditableDataField origin() throws IOException {
		return origin;		
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
		return record.valueOf(DataFieldOfSheet::value);
	}

	@Override
	public void update(String value) throws IOException {
		
		record.mustCheckThisCondition(
			!isMandatory() || (isMandatory() && !StringUtils.isBlank(value)),
			String.format("La valeur du champ %s est obligatoire !", name())
		);
		
		record.mustCheckThisCondition(
			StringUtils.isBlank(value) || (StringUtils.isNotBlank(value)),
			String.format("La valeur du champ %s doit être de type %s !", name(), type().toString())
		);		
		
		// vérifier l'unicité de la valeur pour un champ unique
		if(
				code().equals("REF") &&
				base().select(DataFieldOfSheet.class, PgDataFieldOfSheets.viewScript())
					  .where(DataFieldOfSheet::model, model().id())
					  .where(DataFieldOfSheet::code, code())
					  .where(DataFieldOfSheet::value, value)
					  .where(DataFieldOfSheet::sheet, Matchers.notEqualsTo(sheet().id()))
					  .any()
		) {
			throw new IllegalArgumentException(String.format("Champ %s : la valeur %s existe déjà !", name(), value));
		}
		
		if(StringUtils.isBlank(value)) {
			record.entryOf(DataFieldOfSheet::value, null)
			      .update();
		} else {
			record.entryOf(DataFieldOfSheet::value, value)
			      .update();
		}
	}

	@Override
	public UserScope userScope() throws IOException {
		return origin.userScope();
	}

	@Override
	public int columnView() throws IOException {
		
		int column = 0;
		
		List<DataField> fields = model().fields().items();
		int dizaine = order() - order() % 10;
		for (DataField fieldO : fields) {
			int order = ((EditableDataField)fieldO).order();
			int dizaineO = order - order % 10;
			if(dizaineO == dizaine)
				column++;
		}
				
		return column;
	}

	@Override
	public DataSheetModel model() throws IOException {
		return origin.model();
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
	public void changeUserScope(UserScope scope) throws IOException {
		origin.changeUserScope(scope);
	}

	@Override
	public DataFieldDependencies dependencies() throws IOException {
		return origin.dependencies();
	}
}
