package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.util.List;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_formular_expression", 
	label="Expression d'une formule"
)
public interface FormularExpression extends Recordable {
	
	String name() throws IOException;
	
	String toText() throws IOException;
	
	@Field(
		label="Formule", 
		rel=Relation.MANY2ONE
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
