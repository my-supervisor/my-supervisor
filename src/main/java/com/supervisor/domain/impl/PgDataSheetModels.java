package com.supervisor.domain.impl;

import java.io.IOException;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.DomainRecordables;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Table;
import com.supervisor.sdk.datasource.TableImpl;
import com.supervisor.domain.Activity;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModelType;
import com.supervisor.domain.DataSheetModel;
import com.supervisor.domain.DataSheetModels;
import com.supervisor.domain.SharedResource;
import com.supervisor.domain.Supervisor;

public final class PgDataSheetModels extends DomainRecordables<DataSheetModel, DataSheetModels> implements DataSheetModels {

	private final User user;
	private final Activity activity;
	
	public PgDataSheetModels(final User user) throws IOException {
		this(user, Activity.EMPTY);
	}
	
	public PgDataSheetModels(final Activity activity) throws IOException {
		this(new UserOf(activity), activity);
	}
	
	public PgDataSheetModels(final User user, final Activity activity) throws IOException {
		this(viewSource(user), user, activity);
	}
	
	private PgDataSheetModels(final RecordSet<DataSheetModel> source, final User user, final Activity activity) throws IOException {
		super(PxDataSheetModel.class, source);
		
		this.activity = activity;
		this.user = user;
		
		if(activity != Activity.EMPTY) {
			this.source = source.where(DataSheetModel::activity, activity.id());
		}
	}
	
	@Override
	protected DataSheetModels domainSetOf(final RecordSet<DataSheetModel> source) throws IOException {
		return new PgDataSheetModels(source, user, activity);
	}
	
	private static RecordSet<DataSheetModel> viewSource(final User user) throws IOException {
		
		Table tableModel = new TableImpl(DataSheetModel.class);
		Table tableCoreModel = new TableImpl(DataModel.class);
		Long ownerId = user.id();
		
		String viewScript = String.format(
								"(\r\n" + 
								"	select src.*, core.interacting, core.code, core.type, core.active, core.activity_id, core.name, core.description, core.is_template \r\n" + 
								"   from %s src \r\n"+ 
								"   left join %s core on core.id = src.id \r\n" + 
								"	where core.owner_id = %s \r\n" + 
								"	UNION ALL \r\n" + 
								"	select model.*, core.interacting, core.code, core.type, core.active, core.activity_id, core.name, core.description, core.is_template \r\n" + 
								"	from %s model \r\n" + 
								"   left join %s core on core.id = model.id \r\n" +
								"	left join %s res on res.resource_id = model.id \r\n" + 
								"	where res.type = 'DATA_SHEET_MODEL' and res.subscriber_id = %s\r\n" + 
								") as %s",
								tableModel.name(),
								tableCoreModel.name(),
								ownerId,
								tableModel.name(),
								tableCoreModel.name(),
								new TableImpl(SharedResource.class).name(),
								ownerId,
								tableModel.name()
						   );
		
		return user.base()
				   .select(DataSheetModel.class, viewScript);
	}
	
	@Override
	public DataSheetModel add(String code, String name, String description) throws IOException {
		
		if(activity == Activity.EMPTY)
			throw new IllegalArgumentException("Vous devez renseigner l'activité !");
		
		new UserOf(this).profileOf(Supervisor.NAME).validateAccessibility("NEW_DATA_SHEET_MODEL", String.format("%s", count() + 1));
		
		final DataModel model = new PgDataModels(activity).add(code, name, DataModelType.DATA_SHEET_MODEL, description);
		
		Record<DataSheetModel> record = source.entryOf(DataSheetModel::id, model.id())
											  .entryOf(DataSheetModel::canMergeAtSameDate, false)
											  .addForUser(model.ownerId());
		
		DataSheetModel item = domainOf(record);
		
		// Créer les champs systèmes par défaut
		item.initialize();
		
		return item;
	}
	
	@Override
	public void remove(DataSheetModel item) throws IOException {
		item.sharing().remove();
		if(activity == Activity.EMPTY) {
			new PgDataModels(user).remove(item);
		} else {
			new PgDataModels(activity).remove(item);
		}		
	}

	@Override
	public boolean contains(String code) throws IOException {
		
		if(activity == Activity.EMPTY)
			throw new IllegalArgumentException("You must specify an activity before searching data sheet models by code !");
		
		return where(DataSheetModel::code, code).any();
	}

}
