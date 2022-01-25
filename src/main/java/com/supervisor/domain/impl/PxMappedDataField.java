package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.DataField;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.MappedDataField;
import com.supervisor.indicator.IndicatorDynamicParam;
import com.supervisor.indicator.impl.PxIndicatorDynamicParam;

public final class PxMappedDataField extends DomainRecordable<MappedDataField> implements MappedDataField {

	public PxMappedDataField(final Record<MappedDataField> record) throws IOException {
		super(record);
	}

	@Override
	public DataField fieldToUse() throws IOException {
		return TypedDataField.convert(
					record.of(MappedDataField::fieldToUse)
			   ); 
	}

	@Override
	public DataLink link() throws IOException {
		Record<DataLink> rec = record.of(MappedDataField::link);
		return new PxDataLink(rec);
	}

	@Override
	public void update(DataLinkOperator operator, DataField field) throws IOException {

		record.mustCheckThisCondition(
				link().model().fields().contains(field), 
				String.format("Mappage de champs : le champ %s n'existe pas dans le modèle !", field.name())
		);
		
		record.mustCheckThisCondition(
				field.type() == param().type(), 
				String.format("Paramètre %s : Le champ à mapper n'est pas du même type que le paramètre !", field.name())
		);
		
		record.entryOf(MappedDataField::fieldToUse, field.id())
			  .entryOf(MappedDataField::operator, operator)
		      .update();
	}

	@Override
	public IndicatorDynamicParam param() throws IOException {
		Record<IndicatorDynamicParam> rec = record.of(MappedDataField::param);
		return new PxIndicatorDynamicParam(rec);
	}

	@Override
	public DataLinkOperator operator() throws IOException {
		return record.valueOf(MappedDataField::operator);
	}
}
