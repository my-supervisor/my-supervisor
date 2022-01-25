package com.supervisor.domain;

import com.supervisor.sdk.metadata.Recordable;

@Recordable(
	comodel=DataFieldOfSheet.class
)
public interface SimpleDataFieldOfSheet extends DataFieldOfSheet, SimpleDataField {

}
