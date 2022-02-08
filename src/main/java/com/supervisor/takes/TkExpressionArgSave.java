package com.supervisor.takes;

import java.util.UUID;
import java.util.logging.Level;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.takes.TkBaseWrap;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.supervisor.domain.AggregatedModel;
import com.supervisor.domain.DataFieldType;
import com.supervisor.domain.EditableDataField;
import com.supervisor.domain.ExpressionArg;
import com.supervisor.domain.ExpressionArgType;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.ParamDataField;
import com.supervisor.domain.Supervisor;
import com.supervisor.domain.impl.PxSupervisor;

public final class TkExpressionArgSave extends TkBaseWrap {

	public TkExpressionArgSave(final Base base) {
		super(
				base,
				req -> {
					final Supervisor module = new PxSupervisor(base, req);
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));				
					
					final UUID modelId = UUID.fromString(form.single("model_id"));
					AggregatedModel model = module.aggregatedModels().get(modelId); 
					
					final UUID formularId = UUID.fromString(form.single("formular_id"));
					FormularDataField formular = model.formulars().get(formularId); 
					
					final UUID expressionId = UUID.fromString(form.single("expression_id"));
					FormularExpression expression = formular.expressions().get(expressionId); 
					
					ExpressionArgType type = ExpressionArgType.valueOf(form.single("type_id"));
					
					final UUID id = UUID.fromString(form.single("id"));
					final ExpressionArg item = expression.arguments().get(id);		
					
					switch (type) {
						case VALUE :
							final String value = form.single("value");
							final DataFieldType valueType = DataFieldType.valueOf(form.single("value_type_id"));
							item.update(value, valueType); 
							break;
						case DATA_FIELD :
							final UUID fieldId = UUID.fromString(form.single("field_id"));
							EditableDataField field = model.model().fields().editables().get(fieldId);
							item.update(field); 
							break;
						case PARAMETER :
							final UUID paramId = UUID.fromString(form.single("param_id"));
							ParamDataField param = model.params().get(paramId);
							item.update(param); 
							break;
						case FORMULAR :
							final UUID targetFormularId = UUID.fromString(form.single("target_formular_id"));
							FormularDataField targetFormular = model.formulars().get(targetFormularId);
							item.update(targetFormular); 
							break;
						case EXPRESSION :
							final UUID targetExprId = UUID.fromString(form.single("target_expr_id"));
							FormularExpression targetExpr = formular.expressions().get(targetExprId);
							item.update(targetExpr); 
							break;
						default:
							throw new IllegalArgumentException("Ce type d'argument n'est pas pris en charge !");
					}
					
					final String url;
					switch (expression.type()) {
						case SIMPLE:
							url = String.format("/collect/aggregated-model/formular/simple-expression/edit?id=%s&model=%s&formular=%s", expression.id(), model.id(), formular.id());
							break;
						case CASE:
							url = String.format("/collect/aggregated-model/formular/case-expression/edit?id=%s&model=%s&formular=%s", expression.id(), model.id(), formular.id());
							break;
						case EXTENDED_TO_MODEL:
							url = String.format("/collect/aggregated-model/formular/extended-to-model-expression/edit?id=%s&model=%s&formular=%s", expression.id(), model.id(), formular.id());
							break;	
						default:
							throw new IllegalArgumentException(String.format("Save expression argument : expression type %s not supported !", expression.type().toString())); 
					}
					
					return new RsForward(
						new RsFlash(
			                "L'argument valeur a été modifiée avec succès !",
			                Level.FINE
			            ),
						url
				    );
				}
		);
	}	
}
