package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.sdk.datasource.comparators.Comparator;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
	name="supervisor_when_case", 
	label="Etape d'une condition multiple"
)
public interface WhenCase extends com.supervisor.sdk.datasource.Recordable {

	@Field(
		label="Expression", 
		rel= Relation.MANY2ONE
	)
	FormularCaseExpression expression() throws IOException;
	
	@Field(
		label="Argument de gauche", 
		rel=Relation.MANY2ONE
	)
	ExpressionArg leftArg() throws IOException;
	
	@Field(label="Comparateur")
    Comparator comparator() throws IOException;
	
	@Field(
			label="Argument de droite", 
			rel=Relation.MANY2ONE,
			isMandatory=false
	)
	ExpressionArg rightArg() throws IOException;
	
	@Field(
			label="Résultat", 
			rel=Relation.MANY2ONE
	)
	ExpressionArg result() throws IOException;
	
	void updateComparator(Comparator comparator) throws IOException;
}
