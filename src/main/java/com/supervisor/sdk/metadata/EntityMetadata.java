package com.supervisor.sdk.metadata;

import com.supervisor.sdk.datasource.Base;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EntityMetadata {
	
	String name();
	String label();
	
	FieldMetadata idField();
	FieldMetadata guidField();
	FieldMetadata creationDateField();
	FieldMetadata lastModificationDateField();
	FieldMetadata lastModifierIdField();
	FieldMetadata creatorIdField();
	FieldMetadata ownerIdField();
	FieldMetadata tagField();
	
	String script();
	
	List<Map<String, Object>> settingData(Base base) throws IOException;
	List<Map<String, Object>> demoData(Base base) throws IOException;
}
