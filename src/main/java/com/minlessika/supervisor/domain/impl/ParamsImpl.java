package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.minlessika.supervisor.domain.ActivityParam;
import com.minlessika.supervisor.domain.DataLinkParam;
import com.minlessika.supervisor.domain.Params;
import com.minlessika.supervisor.domain.ParamDataField;

public final class ParamsImpl implements Params {

	private final List<ActivityParam> activityParams;
	private final List<DataLinkParam> linkParams;
	private final List<ParamDataField> ruleParams;
	
	public ParamsImpl(final List<ParamDataField> ruleParams) {
		this(new ArrayList<>(), new ArrayList<>(), ruleParams);
	}
	
	public ParamsImpl(final List<ActivityParam> activityParams, final List<DataLinkParam> linkParams, final List<ParamDataField> ruleParams) {
		this.activityParams = activityParams;
		this.linkParams = linkParams;
		this.ruleParams = ruleParams;
	}
	
	@Override
	public boolean contains(long id) throws IOException {
		return linkParams.stream().anyMatch(c -> c.id() == id) || activityParams.stream().anyMatch(c -> c.id() == id) || ruleParams.stream().anyMatch(c -> c.id() == id);
	}

	@Override
	public ParamDataField get(long id) throws IOException {
		
		final ParamDataField param;
		if(linkParams.stream().anyMatch(c -> c.id() == id)) {
			param = linkParams.stream().filter(c -> c.id() == id).findFirst().get();
		} else if(activityParams.stream().anyMatch(c -> c.id() == id)) {
			param = activityParams.stream().filter(c -> c.id() == id).findFirst().get();						
		} else if(ruleParams.stream().anyMatch(c -> c.id() == id)) {
			param = ruleParams.stream().filter(c -> c.id() == id).findFirst().get();						
		} else {
			throw new IllegalArgumentException(String.format("Parameter with id=%s not found", id));
		}
		
		return param;
	}
}
