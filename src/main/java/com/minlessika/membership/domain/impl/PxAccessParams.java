package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.Access;
import com.minlessika.membership.domain.AccessParam;
import com.minlessika.membership.domain.AccessParamQuantifier;
import com.minlessika.membership.domain.AccessParamValueType;
import com.minlessika.membership.domain.AccessParams;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.sdk.datasource.RecordSet;

import java.io.IOException;

public final class PxAccessParams extends DomainRecordables<AccessParam, AccessParams> implements AccessParams {
	
	private final Access access;
	
	public PxAccessParams(final RecordSet<AccessParam> source, final Access access) throws IOException {
		super(PxAccessParam.class, source);
		
		this.access = access;
		this.source = this.source.where(AccessParam::access, access.id());
	}
	
	@Override
	protected PxAccessParams domainSetOf(final RecordSet<AccessParam> source) throws IOException {
		return new PxAccessParams(source, access);
	}

	@Override
	public AccessParam add(String code, String name, AccessParamValueType valueType, String defaultValue, AccessParamQuantifier quantifier, String message) throws IOException {
		
		source.isRequired(AccessParam::name, name);	
		source.isRequired(AccessParam::code, code);
		source.isRequired(AccessParam::defaultValue, defaultValue);
		source.isRequired(AccessParam::message, message);
		
		source.mustCheckThisCondition(
				valueType != AccessParamValueType.NONE, 
				"Vous devez spécifier le type de la valeur !"
		);
		
		source.mustCheckThisCondition(
				quantifier != AccessParamQuantifier.NONE, 
				"Vous devez spécifier le quantificateur du paramètre !"
		);
	
		source.mustBeUnique(AccessParam::code, code.toUpperCase(), AccessParam::access, access.id());
		
		Record<AccessParam> record = source.entryOf(AccessParam::name, name)
				  					       .entryOf(AccessParam::code, code.toUpperCase())
				  					       .entryOf(AccessParam::defaultValue, defaultValue)
				  					       .entryOf(AccessParam::message, message) 
				  					       .entryOf(AccessParam::access, access.id())
				  					       .add();
		
		return domainOf(record);
	}
}
