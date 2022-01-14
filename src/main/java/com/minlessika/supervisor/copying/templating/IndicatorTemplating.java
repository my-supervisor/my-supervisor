package com.minlessika.supervisor.copying.templating;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.minlessika.supervisor.copying.AbstractIndicatorWriter;
import com.minlessika.supervisor.data.sharing.AggregatedModelUniqueSharing;
import com.minlessika.supervisor.data.sharing.DataLinkSharing;
import com.minlessika.supervisor.data.sharing.DataLinkSharingImpl;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.TemplateState;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.indicator.IndicatorTemplate;

public final class IndicatorTemplating extends AbstractIndicatorWriter {

	public IndicatorTemplating(Activity targetActivity, Indicator source, Map<Long, DataModel> dataModelMappings) {
		super(targetActivity, source, dataModelMappings);
	}

	public IndicatorTemplating(final Indicator source, final Indicator target, final Map<Long, DataModel> dataModelMappings) throws IOException {
		super(source, target, dataModelMappings);
	}
	
	@Override
	protected DataLink copyDataLink(final Indicator copy, final DataModel targetModel, final DataLink source) throws IOException {
		return new DataLinkTemplating(copy, targetModel, source).copy();
	}

	@Override
	protected void copyDataLink(DataLink source, DataLink target) throws IOException {
		new DataLinkTemplating(source, target).copy();
	}

	@Override
	protected void copyDataLinksTo(Indicator copy) throws IOException {
		
		final Activity linkActivity = source.activity();
		
		// remove unused target links
		for (DataLink newLink : copy.links().items()) {
			if(new DataLinkSharingImpl(newLink, linkActivity).isEmpty()) {
				copy.links().remove(newLink);
			}
		}
		
		for (DataLink link : source.links().items()) {
			
			final DataModel linkDataModel = link.model();
			
			final DataLinkSharing dataLinkSharing = new DataLinkSharingImpl(link);
			if(dataLinkSharing.isEmpty()) {
				final DataModel targetDataModel;
				if(link.interacting()) {
					final Activity actor;
					if(linkDataModel.interacting())
						actor = ((AggregatedModel)linkDataModel).coreActivity();
					else
						actor = linkDataModel.activity();
					
					final ActivityTemplate linkModelActivityTemplate = actor.templateSrc();
					if(linkModelActivityTemplate == ActivityTemplate.EMPTY)
						continue;
										
					if(!linkModelActivityTemplate.version().equals(actor.version()))
						throw new IllegalArgumentException(String.format("Pour continuer cette release, le modèle %s doit être mis à jour !", linkModelActivityTemplate.name()));
					
					if(linkDataModel.interacting()) {
						targetDataModel = new AggregatedModelUniqueSharing((AggregatedModel)linkDataModel).counterpart();						    
				    } else {
				    	targetDataModel = linkModelActivityTemplate.dataModels().where(DataModel::code, linkDataModel.code()).first();
				    }

				} else {
					targetDataModel = dataModelMappings.get(linkDataModel.id());
				}
					
				copyDataLink(copy, targetDataModel, link);
			} else {
				final DataLink newLink = dataLinkSharing.counterpart();					
				copyDataLink(link, newLink);
			}							
		}
	}

	@Override
	public Indicator copy() throws IOException {
		
		final Indicator copy = super.copy();
		
		// signifier que c'est un modèle
		copy.listOf(Indicator.class)
		    .get(copy.id())
		    .entryOf(Indicator::isTemplate, true)
		    .update();
		
		// Rajouter les autres champs utiles à un template
		copy.listOf(IndicatorTemplate.class)
	        .get(copy.id())
			.entryOf(IndicatorTemplate::name, source.singleLabel())
	        .entryOf(IndicatorTemplate::description, source.description())
	        .entryOf(IndicatorTemplate::state, TemplateState.CREATED)
	        .entryOf(IndicatorTemplate::ownerId, source.ownerId())
	        .entryOf(IndicatorTemplate::tags, Arrays.asList("Modèle"))
	        .update();
					
		return copy;
	}
}
