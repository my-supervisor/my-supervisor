package com.supervisor.domain.bi.impl;

import java.io.IOException;

import com.supervisor.domain.bi.Printer;
import com.supervisor.indicator.impl.PxIndicatorDynamicNumberParam;
import com.supervisor.indicator.impl.PxIndicatorDynamicStringParam;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.DataField;
import com.supervisor.domain.DataLinkOperator;
import com.supervisor.indicator.IndicatorDynamicParam;

public final class MarkupIndicatorParamBodyPrinter implements Printer {

	private final DataField field;
	private final IndicatorDynamicParam param;
	private final DataLinkOperator operator;
	
	public MarkupIndicatorParamBodyPrinter(final IndicatorDynamicParam param, final DataLinkOperator operator, final DataField field) {
		this.param = param;
		this.field = field;		
		this.operator = operator;
	}
	
	@Override
	public String toText() throws IOException {
		
		String body = String.format("\"%s\"", field.code());
		
		switch (field.type()) {
			case NUMBER:
				body = String.format("ROUND(cast(%s as numeric), %s)::double precision", body, new PxIndicatorDynamicNumberParam(param).precision());
				break;
			case STRING:
				String markup =  new PxIndicatorDynamicStringParam(param).markup();
				if(StringUtils.isNotBlank(markup)) {
					body = StringUtils.replace(markup, "$$", body);
				}
				break;
			default:
				break;
		}
		
		if(operator == DataLinkOperator.NEGATIVE) {
			body = String.format("- %s", body);
		}
		
		return body;
	}

}
