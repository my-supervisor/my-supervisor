package com.supervisor.indicator.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataLinks;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.MappedDataField;
import com.supervisor.indicator.BasePeriodicity;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParams;
import com.supervisor.indicator.IndicatorStaticParams;
import com.supervisor.indicator.IndicatorType;

public class IndicatorWrap implements Indicator {

	protected final Indicator origin;
	
	public IndicatorWrap(final Indicator origin) {
		this.origin = origin;
	}
	
	@Override
	public UUID id() {
		return origin.id();
	}

	@Override
	public LocalDateTime creationDate() throws IOException {
		return origin.creationDate();
	}

	@Override
	public UUID creatorId() throws IOException {
		return origin.creatorId();
	}

	@Override
	public LocalDateTime lastModificationDate() throws IOException {
		return origin.lastModificationDate();
	}

	@Override
	public UUID lastModifierId() throws IOException {
		return origin.lastModifierId();
	}

	@Override
	public UUID ownerId() throws IOException {
		return origin.ownerId();
	}

	@Override
	public String tag() throws IOException {
		return origin.tag();
	}

	@Override
	public IndicatorType type() throws IOException {
		return origin.type();
	}

	@Override
	public String singleLabel() throws IOException {
		return origin.singleLabel();
	}

	@Override
	public String pluralLabel() throws IOException {
		return origin.pluralLabel();
	}

	@Override
	public String description() throws IOException {
		return origin.description();
	}

	@Override
	public String code() throws IOException {
		return origin.code();
	}

	@Override
	public BasePeriodicity periodicity() throws IOException {
		return origin.periodicity();
	}

	@Override
	public String name() throws IOException {
		return origin.name();
	}

	@Override
	public IndicatorStaticParams staticParams() throws IOException {
		return origin.staticParams();
	}
	
	@Override
	public IndicatorDynamicParams dynamicParams() throws IOException {
		return origin.dynamicParams();
	}

	@Override
	public DataLinks links() throws IOException {
		return origin.links();
	}

	@Override
	public List<MappedDataField> generateMappedFields() throws IOException {
		return origin.generateMappedFields();
	}

	@Override
	public void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException {
		origin.periodicity(number, unit, reference, closeInterval);
	}

	@Override
	public void calculate(Activity activity) throws IOException {
		origin.calculate(activity);
	}

	@Override
	public void calculate(LocalDate date, Activity activity) throws IOException {
		origin.calculate(date, activity); 
	}

	@Override
	public Base base() {
		return origin.base();
	}

	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
		return origin.listOf(clazz);
	}

	@Override
	public boolean isTemplate() throws IOException {
		return origin.isTemplate();
	}

	@Override
	public List<DataModel> dataModels() throws IOException {
		return origin.dataModels();
	}

	@Override
	public void update(String code, String singleLabel, String pluralLabel, String description) throws IOException {
		origin.update(code, singleLabel, pluralLabel, description); 
	}
	
	@Override
	public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
		return origin.listOf(clazz, viewScript);
	}

	@Override
	public void removePeriodicity() throws IOException {
		origin.removePeriodicity();
	}
	
	@Override
	public BasePeriodicity periodicity(Activity activity) throws IOException {
		return origin.periodicity(activity);
	}

	@Override
	public Indicator copyTo(Activity activity) throws IOException {
		return origin.copyTo(activity);
	}

	@Override
	public void copy(Indicator source) throws IOException {
		origin.copy(source);
	}

	@Override
	public Activity activity() throws IOException {
		return origin.activity();
	}

	@Override
	public int sizeX() throws IOException {
		return origin.sizeX();
	}

	@Override
	public int sizeY() throws IOException {
		return origin.sizeY();
	}

	@Override
	public int row() throws IOException {
		return origin.row();
	}

	@Override
	public int col() throws IOException {
		return origin.col();
	}

	@Override
	public void locate(int sizeX, int sizeY, int row, int col) throws IOException {
		origin.locate(sizeX, sizeY, row, col);
	}
}
