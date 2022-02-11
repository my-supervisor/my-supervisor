package com.supervisor.domain;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
	name="formular_expression",
	label="Expression d'une formule"
)
public interface FormularExpression extends com.supervisor.sdk.datasource.Recordable {
	
	String name() throws IOException;
	
	String toText() throws IOException;
	
	@Field(
		label="Formule", 
		rel= Relation.MANY2ONE
	)
	FormularDataField formular() throws IOException;
	
	@Field(name="no", label="Order")
	int no() throws IOException;
	
	@Field(label="Type")
	FormularExpressionType type() throws IOException;
	
	ExpressionArgs arguments() throws IOException;
	
	List<DataField> dataFieldArgs() throws IOException;
	
	void order(int no) throws IOException;	
}
