package com.supervisor.indicator.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.supervisor.domain.User;
import com.supervisor.domain.impl.DmUser;
import com.supervisor.sdk.datasource.DomainRecordable;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.time.PeriodicityUnit;
import com.supervisor.sdk.utils.ListOfUniqueRecord;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataLinks;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.MappedDataField;
import com.supervisor.domain.impl.PxActivity;
import com.supervisor.domain.impl.GeneratedMappedDataField;
import com.supervisor.domain.impl.PxDataLinks;
import com.supervisor.indicator.BasePeriodicity;
import com.supervisor.indicator.Indicator;
import com.supervisor.indicator.IndicatorDynamicParam;
import com.supervisor.indicator.IndicatorDynamicParams;
import com.supervisor.indicator.IndicatorStaticParams;
import com.supervisor.indicator.IndicatorType;

public final class IndicatorBase extends DomainRecordable<Indicator> implements Indicator {

	public IndicatorBase(final Record<Indicator> record) throws IOException {
		super(record);
	}

	@Override
	public IndicatorStaticParams staticParams() throws IOException {
		return new PgIndicatorStaticParams(this);
	}
	
	@Override
	public IndicatorDynamicParams dynamicParams() throws IOException {
		return new PgIndicatorDynamicParams(this);
	}
	
	@Override
	public void update
	(
			String code, 
			String singleLabel, 
			String pluralLabel, 
			String description
	) throws IOException {
		
		record.isRequired(Indicator::code, code);
		record.isRequired(Indicator::singleLabel, singleLabel);
		record.isRequired(Indicator::pluralLabel, pluralLabel);
		
		record.mustBeUnique(Indicator::code, code, Indicator::ownerId);
		
		record.entryOf(Indicator::code, code)
			  .entryOf(Indicator::singleLabel, singleLabel)
			  .entryOf(Indicator::pluralLabel, pluralLabel)
			  .entryOf(Indicator::description, description)
			  .update();
	}

	@Override
	public DataLinks links() throws IOException {
		return new PxDataLinks(
				record.listOf(DataLink.class), 
				this
		);
	}

	@Override
	public BasePeriodicity periodicity() throws IOException {
		
		Long periodicityId = record.valueOf(Indicator::periodicity);
		if(periodicityId == null)
			return BasePeriodicity.EMPTY;
		
		Record<BasePeriodicity> rec = record.of(Indicator::periodicity);
		return new PxBasePeriodicity(rec);
	}

	@Override
	public IndicatorType type() throws IOException {
		Record<IndicatorType> rec = record.of(Indicator::type);
		return new PxIndicatorType(rec);
	}

	@Override
	public String singleLabel() throws IOException {
		return record.valueOf(Indicator::singleLabel);
	}

	@Override
	public String pluralLabel() throws IOException {
		return record.valueOf(Indicator::pluralLabel);
	}

	@Override
	public String description() throws IOException {
		return record.valueOf(Indicator::description);
	}

	@Override
	public String code() throws IOException {
		return record.valueOf(Indicator::code);
	}

	@Override
	public String name() throws IOException {
		return String.format(
				"%s-%s", 
				type().name(), 
				code()
		);
	}

	@Override
	public List<MappedDataField> generateMappedFields() throws IOException {
		List<MappedDataField> fields = new ArrayList<>();
		
		for (IndicatorDynamicParam param : dynamicParams().items()) {
			fields.add(new GeneratedMappedDataField(param));
		}
		
		return fields;
	}

	@Override
	public void calculate(Activity activity) throws IOException {
		throw new UnsupportedOperationException("indicator-base#calculate"); 
	}

	@Override
	public void calculate(LocalDate date, Activity activity) throws IOException {
		throw new UnsupportedOperationException("indicator-base#calculate$date"); 
	}
	
	public User owner() throws IOException {
		return new DmUser(
				record.of(User.class, ownerId())
		);
	}

	@Override
	public void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException {
		
		record.mustCheckThisCondition(
				number > 0, 
				"Le nombre de la période doit être un entier positif !"
		);
		
		record.mustCheckThisCondition(
				!closeInterval || (unit == PeriodicityUnit.MONTHLY || unit == PeriodicityUnit.YEARLY), 
				"La ferméture de l'intervalle s'effectue avec les unités de temps Mois et Année uniquement !"
		);
		
		record.mustCheckThisCondition(
				unit != PeriodicityUnit.NONE, 
				"Vous devez spécifier la périodicité !"
		);
		
		if(periodicity() == BasePeriodicity.EMPTY) {
			Record<BasePeriodicity> rec = record.listOf(BasePeriodicity.class)
										      .entryOf(BasePeriodicity::number, number)
										      .entryOf(BasePeriodicity::unit, unit)
										      .entryOf(BasePeriodicity::reference, reference)
										      .entryOf(BasePeriodicity::closeInterval, closeInterval)
										      .add();
			
			record.entryOf(Indicator::periodicity, rec.id())
			      .update();
		}else {
			record.of(BasePeriodicity.class, periodicity().id())
			      .entryOf(BasePeriodicity::number, number)
			      .entryOf(BasePeriodicity::unit, unit)
			      .entryOf(BasePeriodicity::reference, reference)
			      .entryOf(BasePeriodicity::closeInterval, closeInterval)
			      .update();
		}
	}

	@Override
	public boolean isTemplate() throws IOException {
		return record.valueOf(Indicator::isTemplate);
	}

	@Override
	public List<DataModel> dataModels() throws IOException {
		
		List<DataModel> models = new ListOfUniqueRecord<>();
		
		for (DataLink link : links().items()) {
			models.add(link.model());			
		}
		
		return models;
	}

	@Override
	public void removePeriodicity() throws IOException {
		
		BasePeriodicity periodicity = periodicity();
		if(periodicity == BasePeriodicity.EMPTY)
			return;
		
		record.entryOf(Indicator::periodicity, null)
	      	  .update();
		
		record.listOf(BasePeriodicity.class).remove(periodicity.id());
	}

	@Override
	public BasePeriodicity periodicity(Activity activity) throws IOException {
		BasePeriodicity periodicity = periodicity();
		if(periodicity == BasePeriodicity.EMPTY)
			periodicity = activity.periodicity();
		
		return periodicity;
	}

	@Override
	public Indicator copyTo(Activity activity) throws IOException {
		throw new UnsupportedOperationException("#copy");
	}

	@Override
	public void copy(Indicator source) throws IOException {
		throw new UnsupportedOperationException("#copy:source");
	}

	@Override
	public Activity activity() throws IOException {
		Record<Activity> rec = record.of(Indicator::activity);
		return new PxActivity(rec);
	}

	@Override
	public int sizeX() throws IOException {
		return record.valueOf(Indicator::sizeX);
	}

	@Override
	public int sizeY() throws IOException {
		return record.valueOf(Indicator::sizeY);
	}

	@Override
	public int row() throws IOException {
		return record.valueOf(Indicator::row);
	}

	@Override
	public int col() throws IOException {
		return record.valueOf(Indicator::col);
	}

	@Override
	public void locate(int sizeX, int sizeY, int row, int col) throws IOException {		
		record.entryOf(Indicator::sizeX, sizeX)
		      .entryOf(Indicator::sizeY, sizeY)
		      .entryOf(Indicator::row, row)
		      .entryOf(Indicator::col, col)
		      .update();
	}
}
