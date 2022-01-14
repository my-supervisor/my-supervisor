package com.minlessika.supervisor.interaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataLink;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.FormularExpression;
import com.minlessika.supervisor.domain.FormularExpressionType;
import com.minlessika.supervisor.domain.FormularExtendedToModelExpression;
import com.minlessika.supervisor.domain.FormularExtendedToModelSource;
import com.minlessika.supervisor.domain.FormularExtendedToParentExpression;
import com.minlessika.supervisor.domain.FormularExtendedToParentSource;
import com.minlessika.supervisor.domain.ListDataField;
import com.minlessika.supervisor.domain.ListDataFieldSource;
import com.minlessika.supervisor.indicator.Indicator;

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
