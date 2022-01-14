package com.minlessika.membership.billing;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;

import java.io.IOException;

@com.minlessika.sdk.metadata.Recordable(
	name="billing_tax", 
	label="Taxe"
)
public interface Tax extends Recordable {
	
	@Field(label="Type")
	TaxType type() throws IOException;
	
	@Field(label="Libellé")
	String name() throws IOException;	
	
	@Field(label="Nom court")
	String shortName() throws IOException;
	
	@Field(label="Valeur")
	double value() throws IOException;	
	
	@Field(label="Type de valeur")
	TaxValueType valueType() throws IOException;
	
	double decimalValue() throws IOException;
	
	String valueToString() throws IOException;
	
	String toString();
	
	void update(String name, String shortName, TaxType type, double value, TaxValueType valueType) throws IOException;
	double evaluateAmount(double amountHt) throws IOException;	
}
