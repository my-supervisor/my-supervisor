package com.supervisor.sharing;

import java.io.IOException;
import java.util.UUID;

import com.supervisor.sdk.datasource.Record;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.impl.PxListDataFieldSource;
import com.supervisor.sdk.utils.OptUUID;

public final class ListDataFieldSourceUniqueSharing extends DataSharingBase<ListDataFieldSource, ListDataFieldSourceShared> implements ListDataFieldSourceSharing {

	private final Activity concreteListSourceModelActivity;
	
	public ListDataFieldSourceUniqueSharing(final ListDataFieldSource concrete) {
		super(ListDataFieldSource.class, ListDataFieldSourceShared.class, concrete);
		
		this.concreteListSourceModelActivity = null;
	}
	
	public ListDataFieldSourceUniqueSharing(final ListDataFieldSource template, final Activity concreteListSourceModelActivity, final Activity concreteActivity) {
		super(ListDataFieldSource.class, ListDataFieldSourceShared.class, template, concreteActivity);
		
		this.concreteListSourceModelActivity = concreteListSourceModelActivity;
	}

	@Override
	public ListDataFieldSource concrete() throws IOException {
		return new PxListDataFieldSource(concreteRecord());
	}

	@Override
	public ListDataFieldSource template() throws IOException {
		return new PxListDataFieldSource(templateRecord());
	}
	
	@Override
	protected Record<ListDataFieldSource> concreteRecord() throws IOException {
		
		
		OptUUID concreteId = new OptUUID("0");
		if(action == WriterAction.TEMPLATING) {			
			concreteId = new OptUUID(source.id());
		} else {
			for (
					Record<ListDataFieldSourceShared> rec : 
					source.listOf(ListDataFieldSourceShared.class)
					   	  .where(ListDataFieldSourceShared::template, source.id())
					   	  .where(ListDataFieldSourceShared::activity, targetActivity.id())
					   	  .items()
			) {
				final UUID id = rec.valueOf(ListDataFieldSourceShared::id);
				final ListDataFieldSource concrete = new PxListDataFieldSource(source.listOf(ListDataFieldSource.class).get(id));
				if(concrete.model().activity().id().equals(concreteListSourceModelActivity.id())) {
					concreteId = new OptUUID(id);
					break;
				}
			}	
			
			if(!concreteId.isPresent())
				throw new IllegalArgumentException(String.format("Concrete ListDataFieldSource not found (Generating activity %s )!", targetActivity.name()));
		}
		
		return source.listOf(ListDataFieldSource.class).get(concreteId.get());
	}
	
	@Override
	public boolean any() throws IOException {
		if(action == WriterAction.TEMPLATING) {
			return source.listOf(ListDataFieldSourceShared.class)
				         .contains(source.id());
		} else {
			for (
					Record<ListDataFieldSourceShared> rec : 
					source.listOf(ListDataFieldSourceShared.class)
					   	  .where(ListDataFieldSourceShared::template, source.id())
					   	  .where(ListDataFieldSourceShared::activity, targetActivity.id())
					   	  .items()
			) {
				final UUID id = rec.valueOf(ListDataFieldSourceShared::id);
				final ListDataFieldSource concrete = new PxListDataFieldSource(source.listOf(ListDataFieldSource.class).get(id));
				if(concrete.model().activity().id().equals(concreteListSourceModelActivity.id())) {
					return true;
				}
			}	
			
			return false;
		}
	}
}
