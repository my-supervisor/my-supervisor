package com.supervisor.copying.cloning;

import java.io.IOException;
import com.supervisor.domain.User;
import com.supervisor.copying.AbstractActivityWriter;
import com.supervisor.copying.ModelFilterWriter;
import com.supervisor.copying.ParamDataFieldWriter;
import com.supervisor.domain.Activity;
import com.supervisor.domain.ActivityTemplate;
import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataSheet;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.ModelFilter;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.TableDataField;
import com.supervisor.indicator.Indicator;

public final class ActivityCloning extends AbstractActivityWriter {

	public ActivityCloning(Activity source, Activity target) throws IOException {
		super(source, target);
	}

	public ActivityCloning(final User user, final Activity source) {
		super(user, source);
	}

	@Override
	protected DataModel copyDataModel(Activity copy, DataModel source) throws IOException {
		return new DataModelCloning(source.code(), copy, source, dataModelMappings).copy();
	}
	
	@Override
	protected DataModel copyDataModel(DataModel source, DataModel target) throws IOException {
		return new DataModelCloning(source, target, dataModelMappings).copy();
	}

	@Override
	protected Indicator copyIndicator(Activity copy, Indicator source) throws IOException {
		return new IndicatorCloning(copy, source, dataModelMappings).copy();
	}

	@Override
	protected void copyIndicator(Indicator source, Indicator target) throws IOException {
		new IndicatorCloning(source, target, dataModelMappings).copy();
	}

	@Override
	protected DataSheet copyDataSheet(DataSheetModel copy, DataSheet source) throws IOException {
		return new DataSheetCloning(copy, source, dataModelMappings).copy();
	}
	
	@Override
	protected void copyDataSheetsTo(final Activity copy) throws IOException {
		
		// remove all sheets
		for (DataSheetModel form : copy.primaryForms()) {
			form.sheets().remove();			
		}
		
		super.copyDataSheetsTo(copy); 
	}
	
	@Override
	protected void copyActivityParamsTo(final Activity copy) throws IOException {
		
		// Remove all
		copy.params().remove();
		
		// Add new parameters
		super.copyActivityParamsTo(copy);
	}
	
	@Override
	protected ModelFilter copyModelFilter(AggregatedModel copy, ModelFilter source) throws IOException {
		return new ModelFilterWriter(copy, source).copy();
	}

	@Override
	protected void copyTableDataField(DataSheetModel targetModel, TableDataField source) throws IOException {
		new EditableDataFieldCloning(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyEditableDataField(DataSheetModel targetModel, EditableDataField source) throws IOException {
		new EditableDataFieldCloning(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyFormularDataField(AggregatedModel targetModel, FormularDataField source) throws IOException {
		new FormularDataFieldCloning(targetModel, source, dataModelMappings).copy();
	}

	@Override
	protected void copyParamDataField(AggregatedModel targetModel, ParamDataField source) throws IOException {
		new ParamDataFieldWriter(targetModel, source).copy();
	}
	
	@Override
	public Activity copy() throws IOException {
		
		final Long designerId = source.listOf(ActivityTemplate.class)
				                      .get(source.id())
				                      .valueOf(ActivityTemplate::designer);

		if(designerId != null && !designerId.equals(user.id()))
			throw new IllegalArgumentException("Vous ne pouvez pas créer un modèle de cette activité car vous n'en êtes pas le concepteur !");
		
		return super.copy();
	}

	@Override
	protected void copyInteractingDataModels(Activity copy) throws IOException {
		// don't copy interacting models
	}

	@Override
	protected DataModel targetModelOf(DataModel sourceModel, Activity copy, Activity interactingActivity)throws IOException {
		return dataModelMappings.get(sourceModel.id());
	}
}
