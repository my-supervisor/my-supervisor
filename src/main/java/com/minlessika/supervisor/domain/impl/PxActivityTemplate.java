package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.DmUser;
import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.sdk.datasource.Record;
import com.minlessika.supervisor.domain.ActivityTemplateReleases;
import com.minlessika.supervisor.copying.generating.ActivityGenerating;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityParam;
import com.minlessika.supervisor.domain.ActivityTemplate;
import com.minlessika.supervisor.domain.ActivityTemplateParamRequest;
import com.minlessika.supervisor.domain.ActivityTemplateRequest;
import com.minlessika.supervisor.domain.AggregatedModel;
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.ParamDataFields;
import com.minlessika.supervisor.domain.TemplateLicense;
import com.minlessika.supervisor.domain.TemplateState;

public final class PxActivityTemplate extends ActivityWrap implements ActivityTemplate {

	private final Record<ActivityTemplate> record;
	
	public PxActivityTemplate(final Record<ActivityTemplate> record) throws IOException {
		this(
				new PxActivity(
						record.of(
								Activity.class, 
								record.id()
						)
				)
		); 
	}
	
	public PxActivityTemplate(final Activity origin) throws IOException {
		super(origin);
		
		if(!origin.isTemplate())
			throw new IllegalArgumentException("Cette activité n'est pas un modèle !");
		
		this.record = origin.base()
				            .select(
			            		ActivityTemplate.class, 
			            		origin.id()
				            );
	}

	@Override
	public List<String> tags() throws IOException {
		return record.valueOf(ActivityTemplate::tags);
	}

	@Override
	public TemplateState state() throws IOException {
		return record.valueOf(ActivityTemplate::state);
	}

	@Override
	public TemplateLicense license() throws IOException {
		return record.valueOf(ActivityTemplate::license);
	}

	@Override
	public User designer() throws IOException {
		Record<User> rec = record.of(ActivityTemplate::designer);
		return new DmUser(rec);
	}

	@Override
	public void tags(List<String> tags) throws IOException {
		record.entryOf(ActivityTemplate::tags, tags)
	  	  	  .update();
	}

	@Override
	public Activity generate(ActivityTemplateRequest request) throws IOException {
		
		final User user = new UserOf(this);
		Activity newActivity = new ActivityGenerating(user, this).copy();
		newActivity.update(request.name(), description());
		
		// renseigner les paramètres personnalisés de l'activité		
		for (ActivityParam aParam : params().items()) {
			
			AggregatedModel tplModel = aParam.model();
			AggregatedModel newAModel = newActivity.aggregatedModels()
					                               .where(AggregatedModel::code, tplModel.code())
												   .where(AggregatedModel::ownerId, user.id())
						                           .where(AggregatedModel::isTemplate, false)
						                           .first();	
						
			ParamDataFields newParams = newAModel.params().where(ParamDataField::code, aParam.code());
		
			String value = aParam.value();
			for (ActivityTemplateParamRequest paramRq : request.params()) {
				if(tplModel.id().equals(paramRq.modelId()) && aParam.code().equals(paramRq.code())) {
					value = paramRq.value();
					break;
				}
			}
			
			newActivity.params().put(newParams.first(), value);
		}
		
		return newActivity;
	}

	@Override
	public ActivityTemplateReleases releases() throws IOException {
		return new PxActivityTemplateReleases(this);
	}

	@Override
	public void changeDesigner(User newDesigner) throws IOException {
		
		final Long ownerId = newDesigner.id();
		
		if(ownerId.equals(designer().id()))
			throw new IllegalArgumentException("Vous devez sélectionner un utilisateur différent du concepteur actuel !");
		
		// le template
		base().update(
				String.format(
						"update supervisor_activity \r\n" + 
						"set creator_id = %s,\r\n" +
						"owner_id = %s,\r\n" +
						"last_modifier_id = %s,\r\n" + 
						"designer_id = %s\r\n" + 
						"where id = %s",
						ownerId,
						ownerId,
						ownerId,
						ownerId,
						id()
				),
				Arrays.asList()
		);
		
		// publication
		base().update(
				String.format(
						"update supervisor_activity_template_published \r\n" + 
						"set creator_id = %s,\r\n" + 
						"owner_id = %s,\r\n" +
						"last_modifier_id = %s\r\n" + 
						"where id = %s",
						ownerId,
						ownerId,
						ownerId,
						id()
				),
				Arrays.asList()
		);
		
		// les activités filles
		base().update(
				String.format(
						"update supervisor_activity \r\n" + 
						"set designer_id = %s\r\n" + 
						"where template_src_id = %s",
						ownerId,
						id()
				),
				Arrays.asList()
		);
		
		// les indicateurs
		base().update(
				String.format(
						"update supervisor_indicator_general_setting\r\n" + 
						"set creator_id = %s,\r\n" + 
						"owner_id = %s,\r\n" +
						"last_modifier_id = %s\r\n" + 
						"where activity_id = %s",
						ownerId,
						ownerId,
						ownerId,
						id()
				),
				Arrays.asList()
		);
		
		// les modèles
		base().update(
				String.format(
						"update supervisor_data_sheet_model\r\n" + 
						"set creator_id = %s,\r\n" + 
						"owner_id = %s,\r\n" +
						"last_modifier_id = %s\r\n" + 
						"where activity_id = %s",
						ownerId,
						ownerId,
						ownerId,
						id()
				),
				Arrays.asList()
		);
		
		// les modèles agrégés
		base().update(
				String.format(
						"update supervisor_aggregated_model as adm\r\n" + 
						"set creator_id = %s,\r\n" + 
						"owner_id = %s,\r\n" +
						"last_modifier_id = %s\r\n"
						+ "from supervisor_data_sheet_model as dm\r\n" + 
						"where dm.activity_id = %s and adm.model_id = dm.id",
						ownerId,
						ownerId,
						ownerId,
						id()
				),
				Arrays.asList()
		);
		
		// les releases
		base().update(
				String.format(
						"update supervisor_activity_template_release\r\n" + 
						"set creator_id = %s,\r\n" + 
						"owner_id = %s,\r\n" +
						"last_modifier_id = %s\r\n" + 
						"where template_id = %s",
						ownerId,
						ownerId,
						ownerId,
						id()
				),
				Arrays.asList()
		);
	}
}
