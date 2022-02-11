package com.supervisor.indicator;

import java.io.IOException;

import com.supervisor.sdk.metadata.Field;

@com.supervisor.sdk.metadata.Recordable(
		name="indicator_type",
		label="Type d'un indicateur"
)
public interface IndicatorType extends com.supervisor.sdk.datasource.Recordable {
	
	final String NUMBER_ORIENTED = "number-oriented";
	final String CHART_CAMEMBERT = "chart-camembert";
	final String GAUGE = "gauge";
	final String GOAL_NUMBER = "goal-number";
	final String DYNAMIC_TABLE_2_COL = "dynamic-table-2-col";
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Nom court")
	String shortName() throws IOException;
	
	@Field(label="X par défaut")
	int defaultSizeX() throws IOException;
	
	@Field(label="Y par défaut")
	int defaultSizeY() throws IOException;
	
	@Field(label="Description")
	String description() throws IOException;
	
	IndicatorTypeParams params() throws IOException;
	IndicatorTypeStaticParams staticParams() throws IOException;
	IndicatorTypeDynamicParams dynamicParams() throws IOException;
	
	void update(String name, String shortName, int defaultSizeX, int defaultSizeY, String description) throws IOException;
}
