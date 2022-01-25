package com.supervisor.copying;

import java.io.IOException;
import java.util.Map;

import com.supervisor.sdk.utils.BasicCodeGenerator;
import com.supervisor.sdk.utils.CodeGenerator;
import org.apache.commons.lang.StringUtils;

import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.Writer;

public abstract class AbstractAggregatedModelWriter implements Writer<AggregatedModel> {

	private String code;
	protected final Activity targetActivity;
	protected final AggregatedModel source;
	protected final AggregatedModel target;
	protected final DataModel targetModel;
	protected final Map<Long, DataModel> dataModelMappings;
	
	public AbstractAggregatedModelWriter(final String code, final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final Map<Long, DataModel> dataModelMappings) throws IOException {
		this(code, targetActivity, targetModel, source, AggregatedModel.EMPTY, dataModelMappings);
	}
	
	public AbstractAggregatedModelWriter(final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final Map<Long, DataModel> dataModelMappings) throws IOException {
		this(StringUtils.EMPTY, targetActivity, targetModel, source, AggregatedModel.EMPTY, dataModelMappings);
	}
	
	public AbstractAggregatedModelWriter(final AggregatedModel source, final AggregatedModel target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		this(StringUtils.EMPTY, target.activity(), target.model(), source, target, dataModelMappings);
	}
	
	private AbstractAggregatedModelWriter(final String code, final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final AggregatedModel target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		this.code = code;
		this.targetActivity = targetActivity;
		this.source = source;
		this.target = findTarget(targetActivity, targetModel, source, target);
		this.targetModel = targetModel;
		this.dataModelMappings = dataModelMappings;
	}
	
	protected abstract AggregatedModel findTarget(final Activity targetActivity, final DataModel targetModel, final AggregatedModel source, final AggregatedModel target) throws IOException;

	private AggregatedModel copyBaseOf(AggregatedModel source) throws IOException {	

		final AggregatedModel copy;
		
		if(target == AggregatedModel.EMPTY) {
			if(StringUtils.isBlank(code)) {
				final CodeGenerator generator = new BasicCodeGenerator(
													targetActivity.dataModels(), 
													source.name()
												 );
				code = generator.generate();
			}
			
			copy = targetActivity.aggregatedModels().add(code, targetModel);						
		} else {
			copy = target;			
		}
		
		copy.update(source.name(), copy.fields().get("DATE")); 
		
		return copy;
	}

	@Override
	public AggregatedModel copy() throws IOException {		
		return copyBaseOf(source);
	}
	
}
