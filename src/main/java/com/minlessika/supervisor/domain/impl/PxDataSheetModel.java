package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.comparators.Matchers;
import com.minlessika.supervisor.domain.SimpleDataField;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataFieldType;
import com.minlessika.supervisor.domain.DataFields;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModelType;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.DataSheetOfModels;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.EditableDataFields;
import com.minlessika.supervisor.domain.ExtendedDataSheetModels;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.ResourceType;
import com.minlessika.supervisor.domain.SharedResource;
import com.minlessika.supervisor.domain.Sharing;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.TableDataField;

public final class PxDataSheetModel extends DataModelWrap implements DataSheetModel {

	private final Record<DataSheetModel> record;

	public PxDataSheetModel(final Record<DataSheetModel> record) throws IOException {
		this(modelOf(record));
	}
	
	public PxDataSheetModel(final DataModel origin) throws IOException {
		super(origin);
		
		if(type() != DataModelType.DATA_SHEET_MODEL)
			throw new IllegalArgumentException("Data sheet model waited !");
		
		this.record = origin.listOf(DataSheetModel.class).get(origin.id());
	}
	
	private static DataModel modelOf(Record<DataSheetModel> record) throws IOException {
		return new PxDataModel(record.listOf(DataModel.class).get(record.id()));
	}
	
	@Override
	public List<DataFieldOfSheet> generateFields() throws IOException {
		
		final List<DataFieldOfSheet> fields = new ArrayList<>();
		
		final LocalDate date = LocalDate.now();
		
		for (DataField field : fields().editables().items()) {
			
			final DataFieldOfSheet gField;
			switch (field.style()) {
				case LIST:
					gField = new GeneratedListDataFieldOfSheet((ListDataField)field);
					break;
				case SIMPLE:
					gField = new GeneratedSimpleDataFieldOfSheet((SimpleDataField)field);
					break;
				case STRUCTURE:
					gField = new GeneratedTableDataFieldOfSheet((TableDataField)field);
					break;
				default:
					throw new IllegalArgumentException(String.format("Generated data field of sheet : %s is not supported !", field.style()));
			}
			
			if(field.code().equals("DATE"))
				gField.update(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
			
			if(field.code().equals("REF")) {
				final String reference = new DataSheetRandomSequence(this, date).generate();
				gField.update(reference);
			}				
			
			fields.add(gField);
		}
		
		return fields;
	}

	@Override
	public DataSheetOfModels sheets() throws IOException {
		return new PxDataSheetOfModels(this);
	}

	@Override
	public Sharing sharing() throws IOException {
		return new PxSharing(record.listOf(SharedResource.class))
					.where(SharedResource::type, ResourceType.DATA_SHEET_MODEL)
				    .where(SharedResource::resourceId, id());
	}

	@Override
	public boolean canMergeAtSameDate() throws IOException {
		return record.valueOf(DataSheetModel::canMergeAtSameDate);
	}

	@Override
	public void importDataFrom(DataSheetModel source) throws IOException {
		
		// vérifier la compatibilité des modèles
		DataFields srcFields = source.fields();
		
		for (DataField field : fields().items()) {
			if(
					srcFields.where(DataField::code, field.code())
					         .where(DataField::type, field.type())
					         .isEmpty()
			  ) throw new IllegalArgumentException("Les deux (2) modèles ne sont pas compatibles !");			
		}
		
		DataSheetOfModels srcSheets = source.sheets()
				                            .orderBy(DataSheet::date, OrderDirection.ASC);
		
		DataSheetOfModels trgSheets = sheets();
		for (DataSheet srcSheet : srcSheets.items()) {
			trgSheets.copy(srcSheet);
		}
	}

	@Override
	public ExtendedDataSheetModels parents() throws IOException {
		return new DataSheetModelParents(this);
	}

	@Override
	public ExtendedDataSheetModels children() throws IOException {
		return new DataSheetModelChildren(this);
	}

	@Override
	public void merging(boolean canMergeAtSameDate) throws IOException {
		record.entryOf(DataSheetModel::canMergeAtSameDate, canMergeAtSameDate)
		      .update();
	}

	@Override
	public void initialize() throws IOException {
		
		if(fields().isEmpty()) {
			
			final SimpleDataField ref = fields().simples().add("REF", "Référence", DataFieldType.STRING, true, StringUtils.EMPTY, "Référence d'une feuille de donnée");
			ref.changeUserScope(UserScope.SYSTEM);
			ref.makeMustEditOnce(true);
			
			final SimpleDataField date = fields().simples().add("DATE", "Date du document", DataFieldType.DATE, true, StringUtils.EMPTY, "Date de production du document");
			date.changeUserScope(UserScope.SYSTEM);
		}
	}

	@Override
	public EditableDataFields editableFields() throws IOException {
		return new PgEditableDataFields(this);
	}

	@Override
	public EditableDataFields scalarEditableFields() throws IOException {
		return editableFields().where(EditableDataField::type, Matchers.notEqualsTo(DataFieldType.TABLE));
	}

	@Override
	public boolean isTable() throws IOException {
		return fields().where(DataField::code, "PARENT").any();
	}
}
