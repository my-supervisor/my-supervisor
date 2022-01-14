package com.minlessika.supervisor.copying.templating;

import java.io.IOException;
import java.util.Arrays;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.sdk.time.Periodicity;
import com.minlessika.supervisor.copying.AbstractActivityWriter;
import com.minlessika.supervisor.copying.ModelFilterWriter;
import com.minlessika.supervisor.copying.ParamDataFieldWriter;
import com.minlessika.supervisor.data.sharing.AggregatedModelUniqueSharing;
import com.minlessika.supervisor.data.sharing.DataSheetSharing;
import com.minlessika.supervisor.data.sharing.DataSheetSharingImpl;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityParam;
import com.minlessika.supervisor.domain.ActivityParams;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.DataModels;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.EditableDataField;
import com.minlessika.supervisor.domain.FormularDataField;
import com.minlessika.supervisor.domain.ModelFilter;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.TableDataField;
import com.minlessika.supervisor.domain.TemplateLicense;
import com.minlessika.supervisor.domain.TemplateState;
import com.minlessika.supervisor.domain.impl.PxActivityTemplate;
import com.minlessika.supervisor.indicator.Indicator;

public final class ActivityTemplating extends AbstractActivityWriter {

	public ActivityTemplating(Activity source, Activity target) throws IOException {
		super(source, target);
	}

	public ActivityTemplating(final User user, final Activity source) {
		super(user, source);
	}
	
	@Override
	protected DataModel copyDataModel(Activity copy, DataModel source) throws IOException {
		return new DataModelTemplating(source.code(), copy, source, dataModelMappings).copy();
	}

	@Override
	protected DataModel copyDataModel(DataModel source, DataModel target) throws IOException {
		return new DataModelTemplating(source, target, dataModelMappings).copy();
	}
	
	@Override
	protected Indicator copyIndicator(Activity copy, Indicator source) throws IOException {
		return new IndicatorTemplating(copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyIndicator(Indicator source, Indicator target) throws IOException {
		new IndicatorTemplating(source, target, dataModelMappings).copy();
	}

	@Override
	protected DataSheet copyDataSheet(DataSheetModel copy, DataSheet source) throws IOException {
		
		final DataSheet newSheet;
		
		final DataSheetSharing concreteDataSheets = new DataSheetSharingImpl(source);
		if(concreteDataSheets.any()) {
			newSheet = new DataSheetTemplating(source, concreteDataSheets.counterpart(), dataModelMappings).copy();
		} else {
			newSheet = new DataSheetTemplating(copy, source, dataModelMappings).copy();
		}
		
		return newSheet;
	}
	
	@Override
	protected void copyDataSheetsTo(final Activity copy) throws IOException {
		
		// remove absent data sheets in template
		for (DataSheetModel form : copy.forms().items()) {
			for (DataSheet sheet : form.sheets().items()) {
				if(new DataSheetSharingImpl(sheet, source).isEmpty()) {
					form.sheets().remove(sheet);
				}
			}
		}
		
		super.copyDataSheetsTo(copy); 
	}
	
	@Override
	protected void copyActivityParamsTo(final Activity copy) throws IOException {
		
		// Update parameters values
		for (ActivityParam aParam : source.params().items()) {
			
			AggregatedModel newAModel = (AggregatedModel)dataModelMappings.get(aParam.model().id());
			
			ActivityParams newParams = copy.params()
					                       .where(ActivityParam::code, aParam.code())
					                       .where(ActivityParam::model, newAModel.id()); 			
			
			if(newParams.any()) {
				ActivityParam newParam = newParams.first();
				newParam.update(aParam.value());			
			}
		}
		
		// Add new parameters
		super.copyActivityParamsTo(copy);
	}

	@Override
	public Activity copy() throws IOException {
		
		if(target == Activity.EMPTY && source.templateSrc() != ActivityTemplate.EMPTY) // tentative de création d'un modèle à partir d'une activité déjà liée à un autre modèle
			throw new IllegalArgumentException("Cette activité est déjà liée à un modèle !");
			
		final Long designerId = source.listOf(ActivityTemplate.class)
				                      .get(source.id())
				                      .valueOf(ActivityTemplate::designer);

		if(designerId != null && !designerId.equals(user.id()))
			throw new IllegalArgumentException("Vous ne pouvez pas créer un modèle de cette activité car vous n'en êtes pas le concepteur !");
		
		final Activity copy = super.copy();
		
		// mettre à jour l'activité
		copy.update(source.name(), source.description());
		Periodicity periodicitySrc = source.periodicity();
		copy.periodicity(periodicitySrc.number(), periodicitySrc.unit(), periodicitySrc.reference(), periodicitySrc.closeInterval());
				
		// recopier le concepteur et la licence dans la source
		
		if(target == Activity.EMPTY) {
			copy.listOf(ActivityTemplate.class)
		        .get(copy.id())
		        .entryOf(ActivityTemplate::isTemplate, true)
		        .entryOf(ActivityTemplate::state, TemplateState.CREATED)
		        .entryOf(ActivityTemplate::designer, user.id())
		        .entryOf(ActivityTemplate::license, TemplateLicense.AGPL_3)
		        .entryOf(ActivityTemplate::tags, Arrays.asList("Modèle"))
		        .update();
			
			source.listOf(ActivityTemplate.class)
				  .get(source.id())			
				  .entryOf(ActivityTemplate::license, TemplateLicense.AGPL_3)
			      .entryOf(ActivityTemplate::designer, new OwnerOf(copy).id())
			      .entryOf(ActivityTemplate::templateSrc, copy.id())
			      .update();
		}
		
		final ActivityTemplate template = new PxActivityTemplate(copy);
		final String version;
		if(template.releases().isEmpty())
			version = template.version();
		else
			version = template.releases().first().version();
		
		copy.listOf(ActivityTemplate.class)
	        .get(copy.id())
	        .entryOf(ActivityTemplate::version, version)
	        .update();
		
		source.listOf(Activity.class)
			  .get(source.id())						  
		      .entryOf(Activity::version, version)
		      .update();		
		
		return copy;
	}

	@Override
	protected ModelFilter copyModelFilter(AggregatedModel copy, ModelFilter source) throws IOException {
		return new ModelFilterWriter(copy, source).copy();
	}

	@Override
	protected void copyTableDataField(DataSheetModel targetModel, TableDataField source) throws IOException {
		new EditableDataFieldTemplating(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyEditableDataField(DataSheetModel targetModel, EditableDataField source) throws IOException {
		new EditableDataFieldTemplating(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyFormularDataField(AggregatedModel targetModel, FormularDataField source) throws IOException {
		new FormularDataFieldTemplating(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyParamDataField(AggregatedModel targetModel, ParamDataField source) throws IOException {
		new ParamDataFieldWriter(targetModel, source).copy();
	}

	@Override
	protected void copyInteractingDataModels(Activity copy) throws IOException {
		// as receiver, create all interacting model for template actors
		for (AggregatedModel iModel : source.aggregatedModels().where(AggregatedModel::interacting, true).items()) {
			
			final Activity actor = iModel.model().activity();
			final ActivityTemplate lSourceModelActivityTemplate = actor.templateSrc();
			if(lSourceModelActivityTemplate == ActivityTemplate.EMPTY)
				continue;
			
			if(!lSourceModelActivityTemplate.version().equals(actor.version()))
				throw new IllegalArgumentException(String.format("Pour continuer cette release, le modèle %s doit être mis à jour !", lSourceModelActivityTemplate.name()));
			
			final DataModels sourceDataModelTemplates = lSourceModelActivityTemplate.dataModels().where(DataModel::code, iModel.model().code());
			final DataModel targetDataModel = sourceDataModelTemplates.first();
			new AggregatedModelTemplating(iModel.code(), copy, targetDataModel, iModel, dataModelMappings).copy();
			copyDataModelContent(Arrays.asList(iModel), copy, lSourceModelActivityTemplate);
		}
	}

	@Override
	protected DataModel targetModelOf(DataModel sourceModel, Activity copy, Activity interactingActivity) throws IOException {
		
		if(sourceModel.interacting()) {
			final AggregatedModel aModel = (AggregatedModel)sourceModel;
			return new AggregatedModelUniqueSharing(aModel).template();
		} else {
			return dataModelMappings.get(sourceModel.id());
		}
	}
}
