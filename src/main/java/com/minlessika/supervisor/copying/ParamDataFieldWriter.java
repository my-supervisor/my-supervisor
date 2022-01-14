package com.minlessika.supervisor.copying;

import java.io.IOException;

import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.Writer;

public final class ParamDataFieldWriter implements Writer<ParamDataField> {

	private final ParamDataField source;
	private final AggregatedModel targetModel;
	
	public ParamDataFieldWriter(final AggregatedModel targetModel, final ParamDataField source) {
		this.source = source;
		this.targetModel = targetModel;
	}
	
	@Override
	public ParamDataField copy() throws IOException {
		
		final ParamDataField copy;
		
		if(
				targetModel.params()
		                   .where(ParamDataField::code, source.code())
		                   .any()
		) {
			copy = targetModel.params().get(source.code());
			copy.update(source.code(), source.name(), source.type());
			copy.update(source.value());
		}else {
			copy = targetModel.params().add(source.code(), source.name(), source.value(), source.type());		
		}
		
		return copy;
	}

}
