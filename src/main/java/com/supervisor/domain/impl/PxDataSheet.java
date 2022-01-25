package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataFieldOfSheet;
import com.supervisor.domain.DataFieldOfSheets;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.EditableDataFields;
import com.supervisor.domain.IndicatorDependencies;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRow;

public final class PxDataSheet extends DomainRecordable<DataSheet> implements DataSheet {
	
	public PxDataSheet(final Record<DataSheet> source) throws IOException {
		super(source);
	}

	@Override
	public DataSheetModel model() throws IOException {
		Record<DataSheetModel> rec = record.of(DataSheet::model);
		return new PxDataSheetModel(rec);
	}

	@Override
	public String reference() throws IOException {
		return record.valueOf(DataSheet::reference);
	}

	@Override
	public LocalDate date() throws IOException {
		return record.valueOf(DataSheet::date);
	}

	@Override
	public DataFieldOfSheets fields() throws IOException {
		return new PgDataFieldOfSheets(this);
	}

	@Override
	public void update(LocalDate date) throws IOException {
		
		fields().get("DATE").update(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		
		record.entryOf(DataSheet::date, date)
		      .update();
		
		if(model().isTable())
			return;
		
		for (DataFieldOfSheet field : fields().items()) {
			if(field.type() == DataFieldType.TABLE) {
				TableDataFieldOfSheet table = new PxTableDataFieldOfSheet(field);
				for (TableDataFieldOfSheetRow row : table.rows().items()) {
					row.update(date);
				}
			}
		}
	}

	@Override
	public void validate() throws IOException {
		// Vérifier que tous les champs obligatoires sont remplis
		EditableDataFields mandatoryFields = new PgEditableDataFields(model()).where(EditableDataField::isMandatory, true);
		for (DataField field : mandatoryFields.items()) {
			if(fields().where(DataFieldOfSheet::origin, field.id())
					   .isEmpty())
				throw new IllegalArgumentException(String.format("Le champ obligatoire %s n'a pas été saisi", field.name())); 
		}
	}

	@Override
	public User owner() throws IOException {
		return new DmUser(
					record.of(User.class, ownerId())
			   ); 
	}

	@Override
	public User creator() throws IOException {
		return new DmUser(
					record.of(User.class, creatorId())
			   ); 
	}

	@Override
	public Activity activity() throws IOException {
		return model().activity();
	}

	@Override
	public IndicatorDependencies indicatorsThatDependsOn() throws IOException {
		return new DataSheetIndicatorDependencies(this);
	}
}
