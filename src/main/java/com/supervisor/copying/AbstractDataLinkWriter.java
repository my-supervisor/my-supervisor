package com.supervisor.copying;

import java.io.IOException;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinkParam;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.MappedDataField;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Writer;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParam;

public abstract class AbstractDataLinkWriter implements Writer<DataLink> {

	protected final Indicator targetIndicator;
	protected final DataModel targetModel;
	protected final DataLink source;
	protected final DataLink target;
	
	public AbstractDataLinkWriter(final DataLink source, final DataLink target) throws IOException {
		this(target.indicator(), target.model(), source, target);
	}
	
	public AbstractDataLinkWriter(final Indicator targetIndicator, final DataModel targetModel, final DataLink source) {
		this(targetIndicator, targetModel, source, DataLink.EMPTY);
	}
	
	private AbstractDataLinkWriter(final Indicator targetIndicator, final DataModel targetModel, final DataLink source, final DataLink target) {
		this.targetIndicator = targetIndicator;
		this.targetModel = targetModel;
		this.source = source;
		this.target = target;
	}

	private void copyMappedDataFieldsTo(DataLink copy) throws IOException {
		for (MappedDataField mappedField : source.mappings().items()) {
			DataField fieldToUse = mappedField.fieldToUse();
			DataField newField = targetModel.fields().get(fieldToUse.code());
			
			IndicatorDynamicParam newParam =  targetIndicator.dynamicParams().get(mappedField.param().code());
			copy.mappings().put(newParam, mappedField.operator(), newField);
		}
	}
	
	private void copyDataLinkParamsTo(DataLink copy) throws IOException {
		for (DataLinkParam param : source.params().items()) {
			ParamDataField newParam = targetModel.fields()
					                             .params()
												 .where(ParamDataField::code, param.code())
												 .first();
			
			copy.params().put(newParam, param.value());
		}
	}
	
	private DataLink copyBaseOf(DataLink source) throws IOException {
		
		final DataLink copy;
		if(target == DataLink.EMPTY) {
			copy = targetIndicator.links().add(source.name(), targetModel);
		} else {
			copy = target;
			copy.update(source.name());
			copy.update(source.dataDomainDefinition());
			copy.mappings().remove();
			copy.params().remove();
		}
		
		return copy;
	}
	
	@Override
	public DataLink copy() throws IOException {
		final DataLink copy = copyBaseOf(this.source);
		copy.update(this.source.dataDomainDefinition());
		copyMappedDataFieldsTo(copy);
		copyDataLinkParamsTo(copy);
		return copy;
	}
	
}
