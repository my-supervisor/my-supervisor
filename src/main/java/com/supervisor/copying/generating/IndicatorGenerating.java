package com.supervisor.copying.generating;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.supervisor.domain.impl.UserOf;
import com.supervisor.copying.AbstractIndicatorWriter;
import com.supervisor.sharing.AggregatedModelUniqueSharing;
import com.supervisor.sharing.DataLinkSharing;
import com.supervisor.sharing.DataLinkSharingImpl;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.impl.PgActivities;
import com.supervisor.indicator.Indicator;

public final class IndicatorGenerating extends AbstractIndicatorWriter {

	public IndicatorGenerating(Activity targetActivity, Indicator source, Map<UUID, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}

	public IndicatorGenerating(final Indicator source, final Indicator target, final Map<UUID, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}

	@Override
	protected DataLink copyDataLink(final Indicator copy, final DataModel targetModel, final DataLink source) throws IOException {
		return new DataLinkGenerating(copy, targetModel, source).copy();
	}

	@Override
	protected void copyDataLink(DataLink source, DataLink target) throws IOException {
		new DataLinkGenerating(source, target).copy();
	}

	@Override
	protected void copyDataLinksTo(Indicator copy) throws IOException {
		
		// remove unused target links
		for (DataLink newLink : copy.links().items()) {
			if(new DataLinkSharingImpl(newLink).isEmpty()) {
				copy.links().remove(newLink);
			}
		}
		
		for (DataLink link : source.links().items()) {
			
			final DataModel linkDataModel = link.model();
			
			final DataLinkSharing dataLinkSharing = new DataLinkSharingImpl(link, copy.activity());
			if(dataLinkSharing.isEmpty()) {
				if(link.interacting()) {
					final Activity templateActor;
					if(linkDataModel.interacting())
						templateActor = ((AggregatedModel)linkDataModel).coreActivity();
					else
						templateActor = linkDataModel.activity();
					
					final Activities actors = new PgActivities(new UserOf(copy)).ownActivities().where(Activity::templateSrc, templateActor.id());
					if(actors.isEmpty())
						continue;
					
					for (Activity actor : actors.items()) {												
						if(!templateActor.version().equals(actor.version()))
							throw new IllegalArgumentException(String.format("Pour continuer cette mise à jour, vous devez mettre à jour l'activité %s !", actor.name()));
						
						final DataModel targetDataModel;
						if(linkDataModel.interacting()) {
							targetDataModel = new AggregatedModelUniqueSharing((AggregatedModel)linkDataModel, actor, targetActivity).counterpart();						    
					    } else {
					    	targetDataModel = actor.dataModels().where(DataModel::code, linkDataModel.code()).first();
					    }

						copyDataLink(copy, targetDataModel, link);
					}
				} else {
					final DataModel targetDataModel = dataModelMappings.get(linkDataModel.id());
					copyDataLink(copy, targetDataModel, link);
				}				
			} else {
				final DataLink newLink = dataLinkSharing.counterpart();					
				copyDataLink(link, newLink);
			}							
		}
	}
	
	@Override
	public Indicator copy() throws IOException {
		
		final Indicator copy = super.copy();
		
		source.listOf(Indicator.class)
		      .get(copy.id())
		      .entryOf(Indicator::singleLabel, source.singleLabel())
		      .entryOf(Indicator::pluralLabel, source.pluralLabel())
		      .update();
					
		return copy;
	}
}
