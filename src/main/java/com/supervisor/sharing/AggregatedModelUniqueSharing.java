package com.supervisor.sharing;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.impl.PxAggregatedModel;
import com.supervisor.sdk.utils.OptUUID;

public final class AggregatedModelUniqueSharing extends DataSharingBase<AggregatedModel, AggregatedModelShared> implements AggregatedModelSharing {

	private final Activity concreteActor;
	
	public AggregatedModelUniqueSharing(final AggregatedModel concrete) {
		super(AggregatedModel.class, AggregatedModelShared.class, concrete);
		
		this.concreteActor = null;
	}
	
	public AggregatedModelUniqueSharing(final AggregatedModel template, final Activity concreteActor, final Activity concreteReceiver) {
		super(AggregatedModel.class, AggregatedModelShared.class, template, concreteReceiver);
		
		this.concreteActor = concreteActor;
	}

	@Override
	public AggregatedModel concrete() throws IOException {
		return new PxAggregatedModel(concreteRecord());
	}

	@Override
	public AggregatedModel template() throws IOException {
		return new PxAggregatedModel(templateRecord());
	}
	
	@Override
	protected Record<AggregatedModel> concreteRecord() throws IOException {
				
		OptUUID concreteId = new OptUUID("0");
		if(action == WriterAction.TEMPLATING) {			
			concreteId = new OptUUID(source.id());
		} else {
			for (
					Record<AggregatedModelShared> rec : 
					source.listOf(AggregatedModelShared.class)
					   	  .where(AggregatedModelShared::template, source.id())
					   	  .where(AggregatedModelShared::activity, targetActivity.id())
					   	  .items()
			) {
				final UUID id = rec.valueOf(AggregatedModelShared::id);
				final AggregatedModel concrete = new PxAggregatedModel(source.listOf(AggregatedModel.class).get(id));
				if(concrete.model().activity().id().equals(concreteActor.id())) {
					concreteId = new OptUUID(id);
					break;
				}
			}	
			
			if(!concreteId.isPresent())
				throw new IllegalArgumentException(String.format("Concrete AggregatedModel not found (Generating activity %s )!", targetActivity.name()));
		}
		
		return source.listOf(AggregatedModel.class).get(concreteId.get());
	}
	
	@Override
	public boolean any() throws IOException {
		
		if(action == WriterAction.TEMPLATING) {
			return source.listOf(AggregatedModelShared.class)
				         .contains(source.id());
		} else {
			for (
					Record<AggregatedModelShared> rec : 
					source.listOf(AggregatedModelShared.class)
					   	  .where(AggregatedModelShared::template, source.id())
					   	  .where(AggregatedModelShared::activity, targetActivity.id())
					   	  .items()
			) {
				final UUID id = rec.valueOf(AggregatedModelShared::id);
				final AggregatedModel concrete = new PxAggregatedModel(source.listOf(AggregatedModel.class).get(id));
				if(concrete.model().activity().id().equals(concreteActor.id())) {
					return true;
				}
			}	
			
			return false;
		}
	}
}
