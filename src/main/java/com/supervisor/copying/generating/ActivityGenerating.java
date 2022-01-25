package com.supervisor.copying.generating;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.supervisor.domain.User;
import com.supervisor.domain.impl.UserOf;
import com.supervisor.sdk.time.Periodicity;
import com.supervisor.copying.AbstractActivityWriter;
import com.supervisor.copying.ModelFilterWriter;
import com.supervisor.copying.ParamDataFieldWriter;
import com.supervisor.sharing.AggregatedModelUniqueSharing;
import com.supervisor.sharing.DataSheetSharing;
import com.supervisor.sharing.DataSheetSharingImpl;
import com.supervisor.domain.Activities;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataLink;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModels;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularExtendedToModelExpression;
import com.supervisor.domain.FormularExtendedToModelSource;
import com.supervisor.domain.FormularExtendedToParentExpression;
import com.supervisor.domain.FormularExtendedToParentSource;
import com.supervisor.domain.ListDataField;
import com.supervisor.domain.ListDataFieldSource;
import com.supervisor.domain.ModelFilter;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.TableDataField;
import com.supervisor.domain.impl.PgActivities;
import com.supervisor.domain.impl.PxActivityTemplate;
import com.supervisor.indicator.Indicator;
import com.supervisor.interaction.Interactable;

public final class ActivityGenerating extends AbstractActivityWriter {

	public ActivityGenerating(Activity source, Activity target) throws IOException {
		super(source, target);
	}

	public ActivityGenerating(final User user, final Activity source) {
		super(user, source);
	}

	@Override
	protected DataModel copyDataModel(Activity copy, DataModel source) throws IOException {
		return new DataModelGenerating(source.code(), copy, source, dataModelMappings).copy();
	}
	
	@Override
	protected DataModel copyDataModel(DataModel source, DataModel target) throws IOException {
		return new DataModelGenerating(source, target, dataModelMappings).copy();
	}

	@Override
	protected Indicator copyIndicator(Activity copy, Indicator source) throws IOException {
		return new IndicatorGenerating(copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyIndicator(Indicator source, Indicator target) throws IOException {
		new IndicatorGenerating(source, target, dataModelMappings).copy();
	}
	
	@Override
	protected ModelFilter copyModelFilter(AggregatedModel copy, ModelFilter source) throws IOException {
		return new ModelFilterWriter(copy, source).copy();
	}

	@Override
	protected void copyTableDataField(DataSheetModel targetModel, TableDataField source) throws IOException {
		new EditableDataFieldGenerating(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyEditableDataField(DataSheetModel targetModel, EditableDataField source) throws IOException {
		new EditableDataFieldGenerating(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyFormularDataField(AggregatedModel targetModel, FormularDataField source) throws IOException {
		new FormularDataFieldGenerating(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyParamDataField(AggregatedModel targetModel, ParamDataField source) throws IOException {
		new ParamDataFieldWriter(targetModel, source).copy();
	}

	@Override
	protected DataSheet copyDataSheet(DataSheetModel copy, DataSheet source) throws IOException {
		
		final DataSheet newSheet;
		
		final DataSheetSharing concreteDataSheets = new DataSheetSharingImpl(source, copy.activity());
		if(concreteDataSheets.any()) {
			newSheet = new DataSheetGenerating(source, concreteDataSheets.counterpart(), dataModelMappings).copy();
		} else {
			newSheet = new DataSheetGenerating(copy, source, dataModelMappings).copy();
		}
		
		return newSheet;
	}
	
	@Override
	public Activity copy() throws IOException {
		
		final Activity copy = super.copy();	
		
		copy.listOf(Activity.class)
	        .get(copy.id())
	        .entryOf(Activity::templateSrc, source.id())
	        .entryOf(Activity::version, source.version())
	        .update();
	
		Periodicity periodicitySrc = source.periodicity();
		Periodicity periodicityTrg = copy.periodicity();
		copy.periodicity(periodicitySrc.number(), periodicitySrc.unit(), periodicityTrg.reference(), periodicitySrc.closeInterval());
		
		// recopier le concepteur et la licence
		ActivityTemplate template = new PxActivityTemplate(source);
		copy.listOf(ActivityTemplate.class)
			.get(copy.id())			
		    .entryOf(ActivityTemplate::license, template.license())
	        .entryOf(ActivityTemplate::designer, template.designer().id())
	        .update();		
				
		if(target == Activity.EMPTY) {
			// nouvelle activité - recopier les intéractions éventuelles dans les récepteurs existants
			final Activities userActivities = new PgActivities(new UserOf(copy));
			for (Activity receiverTemplate : source.receivers()) {
				final Activities receivers = userActivities.where(Activity::templateSrc, receiverTemplate.id());
				final List<Interactable> interactableTemplates = receiverTemplate.interactions().getWith(source).interactables().items();
				for (Activity receiver : receivers.items()) {
					
					if(!receiver.isUpToDate())
						throw new IllegalArgumentException(String.format("Vous devez mettre à jour l'activité %s pour assurer son intéractivité avec l'activité %s !", receiver.name(), copy.name()));
					
					for (Interactable interactableTemplate : interactableTemplates) {											
						
						if(interactableTemplate instanceof AggregatedModel) {
							final AggregatedModel lSourceTemplate = (AggregatedModel)interactableTemplate;
							final DataModel targetModel = copy.dataModels().where(DataModel::code, lSourceTemplate.model().code()).first();
							new AggregatedModelGenerating(receiver, targetModel, lSourceTemplate, dataModelMappings).copy();
							copyDataModelContent(Arrays.asList(lSourceTemplate), receiver, copy);
						}
						
						if(interactableTemplate instanceof ListDataFieldSource) {
							final ListDataFieldSource lSourceTemplate = (ListDataFieldSource)interactableTemplate;
							final ListDataField listField = lSourceTemplate.field();
							final DataModel listModel = listField.model();
							final ListDataField targetListField = receiver.dataModels().where(DataModel::code, listModel.code()).first().fields().lists().get(listField.code());
							final DataModel targetModel = copy.dataModels().where(DataModel::code, lSourceTemplate.model().code()).first();
							new ListDataFieldSourceGenerating(targetListField, targetModel, lSourceTemplate).copy();
						}
						
						if(interactableTemplate instanceof FormularExtendedToParentSource) {
							final FormularExtendedToParentSource srcTemplate = (FormularExtendedToParentSource)interactableTemplate;
							final FormularDataField sourceFormular = srcTemplate.expr().formular();
							final AggregatedModel receiverModel = receiver.aggregatedModels().where(AggregatedModel::code, sourceFormular.model().code()).first();
							final FormularDataField targetFormular = receiverModel.formulars().get(sourceFormular.code());
							final FormularExtendedToParentExpression targetExpr = (FormularExtendedToParentExpression)targetFormular.expressions().where(FormularExpression::no, srcTemplate.expr().no()).first();
							new FormularExtendedToParentSourceGenerating(targetExpr, copy, srcTemplate).copy();
						}
						
						if(interactableTemplate instanceof FormularExtendedToModelSource) {
							final FormularExtendedToModelSource srcTemplate = (FormularExtendedToModelSource)interactableTemplate;
							final FormularDataField sourceFormular = srcTemplate.expr().formular();
							final AggregatedModel receiverModel = receiver.aggregatedModels().where(AggregatedModel::code, sourceFormular.model().code()).first();
							final FormularDataField targetFormular = receiverModel.formulars().get(sourceFormular.code());
							final FormularExtendedToModelExpression targetExpr = (FormularExtendedToModelExpression)targetFormular.expressions().where(FormularExpression::no, srcTemplate.expr().no()).first();
							final DataSheetModel dataModel = srcTemplate.model();
							final DataSheetModel targetModel = copy.forms().where(DataSheetModel::code, dataModel.code()).first();
							new FormularExtendedToModelSourceGenerating(targetExpr, targetModel, srcTemplate).copy();
						}
						
						if(interactableTemplate instanceof DataLink) {
							final DataLink linkTemplate = (DataLink)interactableTemplate;
							final Indicator targetIndicator = receiver.indicators().where(Indicator::code, linkTemplate.indicator().code()).first();
						    final DataModel linkModelTemplate = linkTemplate.model();
						    final DataModel targetModel;
						    if(linkModelTemplate.interacting()) {
						    	targetModel = new AggregatedModelUniqueSharing((AggregatedModel)linkModelTemplate, copy, receiver).concrete();						    
						    } else {
						    	targetModel = copy.dataModels().where(DataModel::code, linkModelTemplate.code()).first();
						    }
						    
							new DataLinkGenerating(targetIndicator, targetModel, linkTemplate).copy();
						}
					}
					
					receiver.interactions().getWith(copy).activate(false);
				}
			}
			
			// deactivate all interactions with present actors
			for (Activity activity : userActivities.items()) {
				if(activity.equals(copy))
					continue;
				copy.interactions().getWith(activity).activate(false);
			}
		}			
					
		return copy;
	}

	@Override
	protected void copyInteractingDataModels(Activity copy) throws IOException {
		// as receiver, create all interacting model for existing actors
		for (AggregatedModel iModel : source.aggregatedModels().where(AggregatedModel::interacting, true).items()) {
			final Activity templateActor = iModel.model().activity();
			final Activities actors = new PgActivities(new UserOf(copy)).ownActivities().where(Activity::templateSrc, templateActor.id());
			if(actors.isEmpty())
				continue;
			
			for (Activity actor : actors.items()) {												
				if(!templateActor.version().equals(actor.version()))
					throw new IllegalArgumentException(String.format("Pour continuer cette mise à jour, vous devez mettre à jour l'activité %s !", actor.name()));
				
				final DataModels actorDataModels = actor.dataModels().where(DataModel::code, iModel.model().code());
				final DataModel targetDataModel = actorDataModels.first();
				new AggregatedModelGenerating(copy, targetDataModel, iModel, dataModelMappings).copy();
				copyDataModelContent(Arrays.asList(iModel), copy, actor);
			}
		}
	}
	
	@Override
	protected DataModel targetModelOf(DataModel sourceModel, Activity receiver, final Activity actor) throws IOException {
		
		if(sourceModel.interacting()) {
			final AggregatedModel aModel = (AggregatedModel)sourceModel;
			return new AggregatedModelUniqueSharing(aModel, actor, receiver).concrete();
		} else {
			return dataModelMappings.get(sourceModel.id());
		}
	}
}
