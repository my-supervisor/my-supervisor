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
import com.supervisor.domain.DataModels;
import com.supervisor.indicator.impl.TypedDataModel;

public final class PgDataModels extends DomainRecordables<DataModel, DataModels> implements DataModels {

	private final Activity activity;
	private final User user;
	
	public PgDataModels(final User user) throws IOException {
		this(viewSource(Activity.EMPTY, user), Activity.EMPTY, user);
	}
	
	public PgDataModels(final Activity activity) throws IOException {
		this(viewSource(activity, User.EMPTY), activity, User.EMPTY);
	}
	
	private PgDataModels(final RecordSet<DataModel> source, final Activity activity, final User user) throws IOException {
		super(PxDataModel.class, source);
		
		this.activity = activity;
		this.user = user;
	}
	
	@Override
	protected DataModel domainOf(final Record<DataModel> record) throws IOException {
		return TypedDataModel.convert(record);
	}
	
	@Override
	protected DataModels domainSetOf(final RecordSet<DataModel> source) throws IOException {
		return new PgDataModels(source, activity, user);
	}
	
	private static RecordSet<DataModel> viewSource(final Activity activity, final User user) throws IOException {
		
		if(activity == Activity.EMPTY && user == User.EMPTY)
			throw new IllegalArgumentException("Data models list : You must specify an activity or an user !");
		
		Table tableModel = new TableImpl(DataModel.class);
		
		String viewScript;
		if(activity != Activity.EMPTY) {
			viewScript = String.format(
								"(\r\n" + 
								"	select src.* \r\n" + 
								"   from %s src \r\n"+
								"   where src.activity_id='%s'::uuid \r\n"+
								") as %s",
								tableModel.name(),
								activity.id(),
								tableModel.name()							
						 );
		} else {
			viewScript = String.format(
								"(\r\n" + 
								"	select src.* \r\n" + 
								"   from %s src \r\n"+
								"   where src.owner_id='%s'::uuid \r\n"+
								") as %s",
								tableModel.name(),
								user.id(),
								tableModel.name()								
						 );
		}			
		
		return activity.base()
				       .select(DataModel.class, viewScript);
	}

	@Override
	public DataModel add(String code, String name, DataModelType type, String description) throws IOException {
		
		if(activity == Activity.EMPTY)
			throw new IllegalArgumentException("Ajout d'un modèle à une activité : vous devez sélectionner une activité !");
		
		source.isRequired(DataModel::code, code); 
		source.isRequired(DataModel::name, name);

		source.mustBeUnique(DataModel::code, code.toUpperCase(), DataModel::activity, activity.id());
		
		Record<DataModel> record = source.entryOf(DataModel::name, name)
										 .entryOf(DataModel::code, code.toUpperCase())
										 .entryOf(DataModel::activity, activity.id())
										 .entryOf(DataModel::active, true)
										 .entryOf(DataModel::isTemplate, false)
										 .entryOf(DataModel::type, type)
										 .entryOf(DataModel::interacting, false)
										 .entryOf(DataModel::description, description)
										 .add();
		
		return new PxDataModel(record);
	}
	
	@Override
	public boolean contains(String code) throws IOException {
		
		if(activity == Activity.EMPTY)
			throw new IllegalArgumentException("You must specify an activity before searching data models by code !");
		
		return where(DataModel::code, code).any();
	}
}
