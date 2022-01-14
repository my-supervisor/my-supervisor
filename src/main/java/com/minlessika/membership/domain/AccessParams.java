package com.minlessika.membership.domain;

import com.minlessika.sdk.datasource.DomainSet;

import java.io.IOException;

public interface AccessParams extends DomainSet<AccessParam, AccessParams> {
	AccessParam add(String code, String name, AccessParamValueType valueType, String defaultValue, AccessParamQuantifier quantifier, String message) throws IOException;
}
