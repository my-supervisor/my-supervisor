package com.minlessika.membership.domain.impl;

import com.minlessika.membership.domain.AccessParam;
import com.minlessika.membership.domain.ProfileAccess;
import com.minlessika.membership.domain.ProfileAccessParam;
import com.minlessika.membership.domain.ProfileAccessParams;
import com.minlessika.sdk.datasource.DomainRecordables;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Table;
import com.minlessika.sdk.datasource.TableImpl;

import java.io.IOException;

public final class PgProfileAccessParams extends DomainRecordables<ProfileAccessParam, ProfileAccessParams> implements ProfileAccessParams {
	
	private final ProfileAccess access;
	
	public PgProfileAccessParams(final ProfileAccess access) throws IOException {
		super(
			PxProfileAccessParam.class,
			viewSource(access)
		);
		
		this.access = access;
	}
	
	private PgProfileAccessParams(final RecordSet<ProfileAccessParam> source, final ProfileAccess access) throws IOException {
		super(
			PxProfileAccessParam.class,
			source
		);
		
		this.access = access;
	}
	
	private static RecordSet<ProfileAccessParam> viewSource(final ProfileAccess access) throws IOException{
		Table table = new TableImpl(ProfileAccessParam.class);
		
		String viewScript = String.format("(\r\n" + 
										"	select dest.*, src.name, src.code, src.value_type, src.default_value from %s as dest\r\n" + 
										"	left join %s as src on src.id = dest.param_inherited_id\r\n" + 
										") as %s",
							table.name(),
							new TableImpl(AccessParam.class).name(),
							table.name()
				);
		
		return access.base()
				     .select(ProfileAccessParam.class, viewScript)
				     .where(ProfileAccessParam::access, access.id());
	}
	
	@Override
	protected ProfileAccessParams domainSetOf(final RecordSet<ProfileAccessParam> source) throws IOException {
		return new PgProfileAccessParams(source, access);
	}

	@Override
	public ProfileAccessParam put(AccessParam param, String value) throws IOException {
		
		source.isRequired(ProfileAccessParam::value, value);
		
		// vérifier que le paramètre appartient au droit d'accès hérité
		if(param.access().id() != access.accessInherited().id())
			throw new IllegalArgumentException(String.format("Le paramètre %s n'appartient pas au droit d'accès %s", param.name(), access.name()));
		
		ProfileAccessParam pParam;
		
		if(where(ProfileAccessParam::paramInherited, param.id()).isEmpty())
			pParam = domainOf(source.entryOf(ProfileAccessParam::access, access.id())
					                .entryOf(ProfileAccessParam::paramInherited, param.id())
					                .entryOf(ProfileAccessParam::value, value)
					                .add()
					          );
		else {
				pParam = where(ProfileAccessParam::paramInherited, param.id()).first();
				pParam.update(value);
		}
		
		return pParam;
	}	
}
