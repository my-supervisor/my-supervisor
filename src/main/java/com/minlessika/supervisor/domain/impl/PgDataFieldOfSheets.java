package com.minlessika.supervisor.domain.impl;

import java.io.IOException;

import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;
import com.minlessika.supervisor.domain.DataField;
import com.minlessika.supervisor.domain.DataFieldOfSheet;
import com.minlessika.supervisor.domain.DataFieldOfSheets;
import com.minlessika.supervisor.domain.DataFieldStyle;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldOfSheet;
import com.minlessika.supervisor.domain.SimpleDataField;
import com.minlessika.supervisor.domain.SimpleDataFieldOfSheet;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.TableDataFieldOfSheet;

public final class PgDataFieldOfSheets extends DomainRecordables<DataFieldOfSheet, DataFieldOfSheets> implements DataFieldOfSheets {

	private final DataSheet sheet;

	public PgDataFieldOfSheets(final DataSheet sheet) throws IOException {
		super(PxDataFieldOfSheet.class, viewSource(sheet));
		
		this.sheet = sheet;
	}
	
	private PgDataFieldOfSheets(final RecordSet<DataFieldOfSheet> source, final DataSheet sheet) throws IOException {
		super(PxDataFieldOfSheet.class, source);
		
		this.sheet = sheet;
		this.source = source.orderBy(DataFieldOfSheet::order);
	}
	
	public static String viewScript() {
		Table table = new TableImpl(DataFieldOfSheet.class);
		return String.format(
							"(\r\n" + 
							"	select entity.*, df.code, df.model_id, edf.no, df.type, df.style, df.name, edf.user_scope \r\n"
							+ "from %s as entity\r\n" + 
							"	left join %s as df on df.id = entity.origin_field_id\r\n" + 
							"	left join %s as edf on edf.id = df.id \r\n" +
							") as %s",
							table.name(),
							new TableImpl(DataField.class).name(),
							new TableImpl(EditableDataField.class).name(),
							table.name()
				);
	}
	
	private static RecordSet<DataFieldOfSheet> viewSource(final DataSheet sheet) throws IOException {
		return sheet.base().select(DataFieldOfSheet.class, viewScript())
				     .where(DataFieldOfSheet::sheet, sheet.id())
	                 .orderBy(DataFieldOfSheet::order);
	}
	
	@Override
	protected DataFieldOfSheet domainOf(final Record<DataFieldOfSheet> record) throws IOException {
		
		DataFieldOfSheet item;
		
	    final DataFieldOfSheet origin = new PxDataFieldOfSheet(record);
		DataFieldStyle style = origin.style();
		
		switch (style) {
			case STRUCTURE:
				item = new PxTableDataFieldOfSheet(origin);
				break;
			case LIST:
				item = new PxListDataFieldOfSheet(origin); 
				break;
			case SIMPLE:
				item = new PxSimpleDataFieldOfSheet(origin); 
				break;
			default:
				throw new IllegalArgumentException("DomainOf DataFieldOfSheet: unsupported style !");
		}
		
		return item;		
	}
	
	@Override
	protected DataFieldOfSheets domainSetOf(final RecordSet<DataFieldOfSheet> source) throws IOException {
		return new PgDataFieldOfSheets(source, sheet);
	}
	
	private DataFieldOfSheet add(final EditableDataField field, final String value) throws IOException {
		
		source.mustCheckThisCondition(
				sheet.model()
				     .fields()
				     .where(DataField::id, field.id())
				     .any(), 
				String.format("Le champ %s n'existe pas dans le modèle !", field.name())
		);
		
		// vérifier l'unicité de la valeur pour un champ unique
		if(
			field.code().equals("REF") && 
			base().select(DataFieldOfSheet.class, viewScript())
				  .where(DataFieldOfSheet::model, sheet.model().id())
				  .where(DataFieldOfSheet::code, field.code())
				  .where(DataFieldOfSheet::value, value)
				  .any()
		) {
			throw new IllegalArgumentException(String.format("Champ %s : la valeur %s existe déjà !", field.name(), value));
		}
		
		final DataFieldOfSheet item;
		if(where(DataFieldOfSheet::code, field.code()).any()) {
			item = where(DataFieldOfSheet::code, field.code()).first();
		} else {
			Record<DataFieldOfSheet> record = source.entryOf(DataFieldOfSheet::sheet, sheet.id())
												    .entryOf(DataFieldOfSheet::origin, field.id())
												    .addForUser(sheet.ownerId());
			
			item = domainOf(record);
		}	
		
		return item;	      
	}

	@Override
	public SimpleDataFieldOfSheet add(SimpleDataField field, String value) throws IOException {
		final SimpleDataFieldOfSheet item = (SimpleDataFieldOfSheet)add((EditableDataField)field, value);
		item.update(value);
		return item;
	}

	@Override
	public ListDataFieldOfSheet add(ListDataField field, String reference, String value) throws IOException {
		final ListDataFieldOfSheet item = (ListDataFieldOfSheet)add((EditableDataField)field, value);
		item.update(reference, value);
		return item;
	}

	@Override
	public TableDataFieldOfSheet add(TableDataField field) throws IOException {
		final TableDataFieldOfSheet item = (TableDataFieldOfSheet)add((EditableDataField)field, sheet.reference());
		item.update(sheet.reference());
		return item;
	}

	@Override
	public DataFieldOfSheet put(String code, String reference, String value) throws IOException {
		
		final DataFieldOfSheet item;
		
		final DataSheetModel model = sheet.model();
		if(where(DataFieldOfSheet::code, code).isEmpty()) {
			EditableDataField field = model.fields().editables().get(code);
			switch (field.style()) {
				case SIMPLE:
					final SimpleDataField simple = (SimpleDataField)field;
					item = add(simple, value);
					break;
				case LIST:
					final ListDataField list = (ListDataField)field;
				    item = add(list, reference, value);
					break;
				case STRUCTURE:
					TableDataField table = (TableDataField)field;
					item = add(table);
					break;
				default:
					throw new IllegalArgumentException(String.format("Création de champ d'une feuille : champ de type %s non pris en charge !", field.style().toString()));
			}				
		} else {
			DataFieldOfSheet field = where(DataFieldOfSheet::code, code).first();			
			switch (field.style()) {
				case SIMPLE:
					final SimpleDataFieldOfSheet simple = (SimpleDataFieldOfSheet)field;
					simple.update(value);
					item = simple;
					break;
				case LIST:
					final ListDataFieldOfSheet list = (ListDataFieldOfSheet)field;
					list.update(reference, value);
					item = list;
					break;
				case STRUCTURE:
					item = field;
					break;
				default:
					throw new IllegalArgumentException(String.format("Modification de champ d'une feuille : champ de style %s non pris en charge !", field.style().toString()));
			}		
		}
		
		return item;
	}

	@Override
	public DataFieldOfSheet get(String code) throws IOException {
		return where(DataFieldOfSheet::code, code).first();
	}

	@Override
	public ListDataFieldOfSheet add(ListDataField field, DataSheet sheetToRefer, String value) throws IOException {
		final ListDataFieldOfSheet item = (ListDataFieldOfSheet)add((EditableDataField)field, value);
		item.update(sheetToRefer, value);
		return item;
	}
}
