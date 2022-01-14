package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.minlessika.supervisor.domain.ParamDataField;

public final class FilterParamArg {
	
	private final String arg;
	private final List<ParamDataField> params;
	
	public FilterParamArg(final String arg, final List<ParamDataField> params) {
		this.arg = arg;
		this.params = params;
	}
	
	public ParamDataField param() throws IOException {
		ParamDataField param = null;
		for (ParamDataField p : params) {
			if(String.format("@%s", p.code()).equals(value())) {
				param = p;
				break;
			}
		}
		
		if(param == null)
			throw new IllegalArgumentException(String.format("Constante %s inexistante dans la formule !", value()));
		
		return param;
	}

	public boolean isValid() throws IOException {
		boolean isValid = false;
		
		Pattern pattern = Pattern.compile("^@(.*?)");
		Matcher matcher = pattern.matcher(value());
		if(matcher.find())
		{
			try {
				param();
				isValid = true;
			} catch (Exception e) {
			}
		}
		
		return isValid;
	}

	public String value() {
		return StringUtils.trim(arg);
	}

}
