package com.supervisor.domain.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.TableDataFieldOfSheet;
import com.supervisor.domain.TableDataFieldOfSheetRow;
import com.supervisor.domain.TableDataFieldOfSheetRows;

public final class PxTableDataFieldOfSheetRows extends DomainRecordables<TableDataFieldOfSheetRow, TableDataFieldOfSheetRows> implements TableDataFieldOfSheetRows {
	
	private final TableDataFieldOfSheet table;
	
	public PxTableDataFieldOfSheetRows(final TableDataFieldOfSheet table) throws IOException {
		this(table.listOf(TableDataFieldOfSheetRow.class), table);
	}
	
	private PxTableDataFieldOfSheetRows(final RecordSet<TableDataFieldOfSheetRow> source, final TableDataFieldOfSheet table) throws IOException {
		super(PxTableDataFieldOfSheetRow.class, source);
		
		this.table = table;
		this.source = source.where(TableDataFieldOfSheetRow::table, table.id())
							.orderBy(TableDataFieldOfSheetRow::id);
	}
	
	@Override
	protected PxTableDataFieldOfSheetRows domainSetOf(final RecordSet<TableDataFieldOfSheetRow> source) throws IOException {
		return new PxTableDataFieldOfSheetRows(source, table);
	}

	@Override
	public TableDataFieldOfSheetRow add(String reference, LocalDate date) throws IOException {

		final DataSheetModel model = table.structure();
		
		source.mustCheckThisCondition(
				model.active(), 
				String.format("Le modèle %s n'est pas actif !", model.name())
		);
		
		source.mustBeUnique(TableDataFieldOfSheetRow::reference, reference, TableDataFieldOfSheetRow::ownerId, model.ownerId());
		
		Record<TableDataFieldOfSheetRow> record = source.entryOf(TableDataFieldOfSheetRow::table, table.id())
														.entryOf(TableDataFieldOfSheetRow::model, model.id())
													    .entryOf(TableDataFieldOfSheetRow::reference, reference)
													    .entryOf(TableDataFieldOfSheetRow::date, date)
													    .addForUser(model.ownerId());
		
		// add field system
		final TableDataFieldOfSheetRow item = domainOf(record); 
		
		item.fields().add(model.fields().simples().get("REF"), reference);
		
		item.fields().add(model.fields().simples().get("DATE"), date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		
		item.fields().add(model.fields().lists().get("PARENT"), table.sheet(), table.sheet().reference());
		
		return item;
	}

	@Override
	public TableDataFieldOfSheetRow copy(TableDataFieldOfSheetRow source) throws IOException {
		throw new UnsupportedOperationException("copy#source");
	}

	@Override
	public TableDataFieldOfSheetRow add() throws IOException {
		
		final LocalDate date = table.sheet().date();
		final String reference = new DataSheetRandomSequence(table.structure(), date).generate();
		
		return add(reference, date);
	}
}
