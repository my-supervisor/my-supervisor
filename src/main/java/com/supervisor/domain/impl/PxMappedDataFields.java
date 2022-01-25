package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.OrderDirection;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.domain.DataField;
import com.supervisor.domain.MappedDataField;
import com.supervisor.domain.MappedDataFields;
import com.supervisor.indicator.IndicatorDynamicParam;

public final class PxMappedDataFields extends DomainRecordables<MappedDataField, MappedDataFields> implements MappedDataFields {

	private final DataLink link;
	
	public PxMappedDataFields(final RecordSet<MappedDataField> source, final DataLink link) throws IOException {
		super(PxMappedDataField.class, source);
		
		this.link = link;
		this.source = source.where(MappedDataField::link, link.id())
				            .orderBy(MappedDataField::id, OrderDirection.ASC);
	}
	
	@Override
	protected MappedDataFields domainSetOf(final RecordSet<MappedDataField> source) throws IOException {
		return new PxMappedDataFields(source, link);
	}

	@Override
	public MappedDataField put(IndicatorDynamicParam param, DataLinkOperator operator, DataField field) throws IOException {
		
		final MappedDataField mappedField;
		
		MappedDataFields fields = this.where(MappedDataField::param, param.id())
				                      .where(MappedDataField::link, link.id());
		
		if(fields.any()) {
			mappedField = fields.first();
			mappedField.update(operator, field); 
		}else {
			
			source.mustCheckThisCondition(
				param.type() == field.type(), 
				String.format("Paramètre %s : Le champ %s n'est pas du même type que le paramètre !", param.name(), field.name())
			);
			
			source.mustBeUnique(MappedDataField::param, param.id(), MappedDataField::link, link.id());
			
			Record<MappedDataField> record = source.entryOf(MappedDataField::param, param.id())
											       .entryOf(MappedDataField::link, link.id())
											       .entryOf(MappedDataField::fieldToUse, field.id())
											       .entryOf(MappedDataField::operator, operator)
											       .add();

			mappedField = domainOf(record);
		}
		
		return mappedField;
	}
}
