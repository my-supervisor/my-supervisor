package com.supervisor.interaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervisor.domain.impl.OwnerOf;
import com.supervisor.domain.Activity;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExpressionType;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.indicator.Indicator;

public final class InteractablesImpl implements Interactables {

	private final Interaction interaction;
	
	public InteractablesImpl(final Interaction interaction) {
		this.interaction = interaction;
	}
	
	@Override
	public List<Interactable> items() throws IOException {
		
		final Activity actor = interaction.actor();
		final Activity receiver = interaction.receiver();
		
		final List<Interactable> items = new ArrayList<>();
		
		for (DataSheetModel model : receiver.formsOf(new OwnerOf(receiver)).items()) {
			for (ListDataField list : model.fields().lists().items()) {
				for (ListDataFieldSource src : list.sources().items()) {
					if(src.interacting() && src.model().activity().id().equals(actor.id())) {
						items.add(src);
					}
				}				
			}
		}
		
		for (AggregatedModel model : receiver.aggregatedModels().items()) {
			
			if(model.interacting() && model.model().activity().id().equals(actor.id())) {
				items.add(model);
			}
			
			for (FormularDataField formular : model.formulars().items()) {
				for (FormularExpression expr : formular.expressions().items()) {
					if(expr.type() == FormularExpressionType.EXTENDED_TO_PARENT) {
						for (FormularExtendedToParentSource src : ((FormularExtendedToParentExpression)expr).sources().items()) {
							if(src.interacting() && src.model().activity().id().equals(actor.id())) {
								items.add(src);
							}
						}	
					} else if(expr.type() == FormularExpressionType.EXTENDED_TO_MODEL) {
						for (FormularExtendedToModelSource src : ((FormularExtendedToModelExpression)expr).sources().items()) {
							if(src.interacting() && src.model().activity().id().equals(actor.id())) {
								items.add(src);
							}
						}	
					}
				}			
			}
		}
		
		for (Indicator indicator : receiver.indicators().items()) {
			for (DataLink link : indicator.links().items()) {
				if(link.interacting()) {
					final DataModel linkModel = link.model();
					final boolean interactByModel = linkModel.interacting() && ((AggregatedModel)linkModel).coreActivity().id().equals(actor.id());
					final boolean interactByLink = !linkModel.interacting() && link.activity().id().equals(actor.id());
					if(interactByModel || interactByLink) {
						items.add(link);
					}									
				}
			}
		}
		
		return items;		
	}

	@Override
	public boolean isEmpty() throws IOException {
		return items().isEmpty();
	}

	@Override
	public boolean areAllActives() throws IOException {
		
		for (Interactable rule : items()) {
			if(!rule.active()) {
				return false;
			}
		}
		
		return true;
	}

}
