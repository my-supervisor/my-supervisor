package com.minlessika.supervisor.copying;

import java.io.IOException;
import java.util.Map;

import com.minlessika.sdk.time.Periodicity;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.Writer;
import com.minlessika.supervisor.indicator.BasePeriodicity;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorDynamicNumberParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicParam;
import com.minlessika.supervisor.indicator.IndicatorDynamicStringParam;
import com.minlessika.supervisor.indicator.impl.PxIndicatorDynamicNumberParam;
import com.minlessika.supervisor.indicator.impl.PxIndicatorDynamicStringParam;

public abstract class AbstractIndicatorWriter implements Writer<Indicator> {

	protected final Activity targetActivity;
	protected final Indicator source;
	protected final Indicator target;
	protected final Map<Long, DataModel> dataModelMappings;
	
	public AbstractIndicatorWriter(final Activity targetActivity, final Indicator source, final Map<Long, DataModel> dataModelMappings) {
		this(targetActivity, source, Indicator.EMPTY, dataModelMappings);
	}
	
	public AbstractIndicatorWriter(final Indicator source, final Indicator target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		this(target.activity(), source, target, dataModelMappings);
	}
	
	private AbstractIndicatorWriter(final Activity targetActivity, final Indicator source, final Indicator target, final Map<Long, DataModel> dataModelMappings) {
		this.targetActivity = targetActivity;
		this.source = source;
		this.target = target;
		this.dataModelMappings = dataModelMappings;
	}

	protected Indicator copyBaseOf(final Indicator source) throws IOException {
		
		final Indicator copy;
		
		if(target == Indicator.EMPTY)
			copy = source.copyTo(targetActivity);
		else {
			copy = target;
			copy.copy(source);
		}
		
		Periodicity periodicity = source.periodicity();
		if(periodicity != BasePeriodicity.EMPTY) {
			copy.periodicity(periodicity.number(), periodicity.unit(), periodicity.reference(), periodicity.closeInterval());
		}
		
		return copy;
	}

	private void copyIndicatorParamsTo(final Indicator copy) throws IOException {
		
		// enregistrer les paramètres dynamiques
		for (IndicatorDynamicParam param : source.dynamicParams().items()) {
			IndicatorDynamicParam newParam = copy.dynamicParams().put(param.origin(), param.aggregateFunc());
			switch (param.type()) {
				case NUMBER:
					IndicatorDynamicNumberParam nParam = new PxIndicatorDynamicNumberParam(param);
					new PxIndicatorDynamicNumberParam(newParam).update(nParam.precision(), nParam.applyThousandsSeparator()); 
					break;
				case STRING:
					IndicatorDynamicStringParam sParam = new PxIndicatorDynamicStringParam(param);
					new PxIndicatorDynamicStringParam(newParam).update(sParam.markup()); 
					break;
				default:
					break;
			}
		}
	}

	protected abstract DataLink copyDataLink(final Indicator copy, final DataModel targetModel, final DataLink source) throws IOException;
	
	protected abstract void copyDataLink(final DataLink source, final DataLink target) throws IOException;
	
	protected abstract void copyDataLinksTo(final Indicator copy) throws IOException;

	@Override
	public Indicator copy() throws IOException {
		final Indicator copy = copyBaseOf(source);
		copyIndicatorParamsTo(copy);
		copyDataLinksTo(copy);
		return copy;
	}
	
}
