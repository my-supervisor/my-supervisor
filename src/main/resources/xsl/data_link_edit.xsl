<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer une liaison de données</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/activity/indic/data-link/save?indic={indicator_setting/id}&amp;source={source}" method="post" ng-controller="AppController as vm">
               <xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/activity/indic/data-link/save?&amp;source=</xsl:text><xsl:value-of select="source"/>&amp;indic=<xsl:value-of select="indicator_setting/id"/>&amp;id=<xsl:value-of select="item/id"/>
		            </xsl:attribute>
		        </xsl:if>
		       <div class="card-header">
		       		<xsl:if test="item and item/id">
			            <h3 class="card-title"><xsl:text>Modifier liaison </xsl:text><i></i></h3>
			        </xsl:if>
			        <xsl:if test="not(item)">
			            <h3 class="card-title">Nouvelle liaison</h3>
			        </xsl:if>
			        <div class="card-options">
	                  <label class="custom-switch">
	                     <input name="status_id" type="checkbox" class="custom-switch-input">
	                     	<xsl:if test="not(item) or (item and item/active='true')"> 
								<xsl:attribute name="checked">checked</xsl:attribute> 
							</xsl:if>
	                     </input>
	                     <span class="custom-switch-indicator"></span>
	                     <span class="custom-switch-description">Activé</span>
	                   </label>
	                </div>
		       </div>
               <div class="card-body">               	
                 <div class="row">
                   <div class="col-sm-12 col-md-6">
                      <div class="form-group">
                        <label class="form-label">Modèle à utiliser <span class="form-required">*</span></label>
                        <select class="form-control" required="" 
                        	ng-model="vm.item.modelId"
                        	ng-change="vm.modelChanged(vm.item.modelId)"
                        	ng-options="v.id as v.viewName for v in vm.models"> 
                        		<xsl:if test="item and item/id">
                        			<xsl:attribute name="disabled">disabled</xsl:attribute>
                        		</xsl:if>                                               
	                     </select>
	                     <input hidden="" type="text" name="model_id" ng-model="vm.item.modelId" />
                      </div>
                   </div>
                   <div class="col-sm-12 col-md-6">
                      <div class="form-group">
                        <label class="form-label">Intitulé <span class="form-required">*</span></label>
                        <input name="name" type="text" class="form-control" placeholder="Saisir un nom" required="" ng-model="vm.item.name"/>
                      </div>
                   </div>  
                   <div class="col-sm-12 col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Domaine de définition des données <span class="form-required">*</span></label>
	                       <select name="data_domain_definition_id" class="form-control" required="">                         
	                         <xsl:variable name="item" select="item" />
	                         <xsl:for-each select="data_domain_definitions/data_domain_definition">  
	                         	<option>
	                         		<xsl:if test="id = $item/data_domain_definition_id"> 
										<xsl:attribute name="selected">selected</xsl:attribute> 
									</xsl:if>
	                         		<xsl:attribute name="value">
	                         			<xsl:value-of select="id"/>
	                         		</xsl:attribute>
	                         		<xsl:value-of select="name"/>                         		                       	
	                         	</option>                           	                     	                         	
	                         </xsl:for-each>                         
	                       </select>
	                     </div>
	                </div>                 
                    <div class="col-md-12">
                     <div class="form-group">
                       <label class="form-label">Table de correspondance</label>
                       <div class="table-responsive form-control">
		                    <table class="table card-table table-vcenter text-nowrap">
		                      <thead>
		                        <tr>
		                          <th class="w-1">N°</th>
		                          <th>Libellé</th>
		                          <th>Type</th>
		                          <th>Opérateur</th>
		                          <th>Champ à utiliser</th>
		                        </tr>
		                      </thead>
		                      <tbody>		                        
		                        <tr ng-repeat="item in vm.mappings">
		                          <td>
		                          	<span class="text-muted">{{$index + 1}}</span>
		                          	<input hidden="" type="text" name="item_field_to_use_id" ng-model="item.fieldToUseId" />
		                          	<input hidden="" type="text" name="item_param_id" ng-model="item.paramId" />
		                          	<input hidden="" type="text" name="operator_id" ng-model="item.operatorId" />
		                          </td>
		                          <td>
		                          	{{item.param}}
		                          </td>
		                          <td>
		                            {{item.type}}
		                          </td>
		                          <td>		                           
		                            <select class="form-control" 
			                            required=""
			                            ng-model="item.operatorId"
			                            ng-options="v.id as v.name for v in vm.operators">                                                 
				                     </select>				                     
		                          </td>
		                          <td>		                           
		                            <select class="form-control" 
			                            required=""
			                            ng-model="item.fieldToUseId"
			                            ng-options="v.id as v.name for v in vm.dataFieldsOfModel | filter:vm.skipFields(item)">                                                 
				                     </select>				                     
		                          </td>	                          
		                        </tr>
		                      </tbody>
		                    </table>
		                  </div>
                      </div>
                    </div>
                    <xsl:if test="item and item/id">
                    <div class="col-md-12">
	                     <div class="form-group">
	                       <label class="form-label">Paramètres</label>
	                       <div class="table-responsive form-control">
			                    <table class="table card-table table-vcenter text-nowrap">
			                      <thead>
			                        <tr>
			                          <th class="w-1">N°</th>
			                          <th>Paramètre</th>
			                          <th>Type</th>
			                          <th>Valeur</th>
			                          <th class="text-right">
			                            <a href="javascript:void(0)" ng-click="vm.addLinkParam()" role="button" class="btn btn-sm btn-primary">
						                  	<i class="fa fa-plus"></i> Nouveau paramètre
						                </a>				                          	
			                          </th>
			                        </tr>
			                      </thead>
			                      <tbody>	
			                        <tr ng-repeat="param in vm.linkParams" ng-hide="param.state == 'removed'">
			                          <td>
			                          	<span class="text-muted">{{$index + 1}}</span>
			                          	<input hidden="" type="text" name="param_index" ng-model="$index" />
			                          	<input hidden="" type="text" name="param_id" ng-model="param.id" />
			                          	<input hidden="" type="text" name="param_state" ng-model="param.state" />
			                          </td>
			                          <td>		                           
			                            <select class="form-control"
				                            ng-model="param.baseParam"
				                            ng-options="v as v.name for v in vm.params"
				                            ng-change="vm.paramChanged($index, param.baseParam)"
				                            required=""
				                            ng-disabled="param.state != 'added'">                                                 
					                     </select>				                     
			                          </td>			                          
			                          <td>
			                          	{{param.type}}
			                          </td>	
			                          <td>
			                          	<input name="param_value" ng-model="param.value" ng-change="vm.linkParamChanged($index)" step="any" type="{{{{vm.adaptTypeOfField(param.typeId)}}}}" class="form-control" placeholder="Saisir une valeur" required=""></input>
			                          </td>	
			                          <td>
					                       <a class="icon" ng-click="vm.removeLinkParam($index)">
					                         <i class="fe fe-trash"></i>
					                       </a>
					                  </td>	                          
			                        </tr>                        				                        
			                      </tbody>
			                    </table>
			                  </div>
	                      </div>
	                   </div>
	                   </xsl:if>
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/{indicator_setting/type_short_name}-setting/edit?id={indicator_setting/id}&amp;shortname={indicator_setting/type_short_name}&amp;source={source}" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>               	
             </form>
        </div>                     
	</xsl:template>  		
	<xsl:template match="page" mode="customScript">
		<script type="text/javascript">
        <![CDATA[	
            var app = angular.module("app", [])
				                 .config(["$provide", function($provide){
							        
				                 }]);			
	
			app.controller("AppController", ["$scope", function ($scope) {
				   var vm = this;
	                    
	               vm.skipFields = function(map) {
	               		return function(field) {
					        return field.typeId == map.typeId;
					    }
				   };
				   
				   vm.paramChanged = function(index, baseParam){
				   		var param = vm.linkParams[index];
				        
				        if(baseParam) {		
				            param.id = baseParam.id;		        				   			
				   			param.typeId = baseParam.typeId;
				   			param.type = baseParam.type;
				   			param.value = baseParam.value;
				        } else {
				        	param.id = undefined;
				        	param.typeId = undefined;
				   			param.type = undefined;
				   			param.value = undefined;
				        }
				   		
				   		vm.linkParamChanged(index);
				   }
				   
				   vm.models = []]> 
					   <xsl:for-each select="data_models/data_model">                             	                     
	                       	{	'id': <xsl:value-of select="id"/>, 
	                       		'name': "<xsl:value-of select="name"/>",
	                       		'viewName': "<xsl:value-of select="viewName"/>"
	                       	},
	                   </xsl:for-each>
	                    <![CDATA[];
	                    
	                vm.dataFieldsOfModel = [];
	                vm.paramsOfModel = [];
	                
	                vm.dataFields = []]> 
		                   <xsl:for-each select="datafields">
		                   		<xsl:for-each select="datafield">                             	                     
			                       	{	'id': <xsl:value-of select="id"/>, 
			                       		'name': "<xsl:value-of select="name"/>",
			                       		'type': "<xsl:value-of select="type"/>",
			                       		'typeId': "<xsl:value-of select="type_id"/>",
			                       		'modelId': <xsl:value-of select="model_id"/>
			                       	},
			                    </xsl:for-each>
		                   </xsl:for-each>						   
	                    <![CDATA[];		              		                
		                   
		           vm.modelChanged = function(modelId){
				   		
				   		vm.dataFieldsOfModel = [];
				   		
				   		if(modelId){
					   		for(var i=0; i<vm.dataFields.length; i++){
					   		    var field = vm.dataFields[i];
					   			if(field.modelId == modelId){
					   				vm.dataFieldsOfModel.push(field);
				   				}
					   		}
					   		
					   		for(var i=0; i<vm.params.length; i++){
					   		    var field = vm.params[i];
					   			if(field.modelId == modelId){
					   				vm.paramsOfModel.push(field);
				   				}
					   		}
					   		
					   		if(!vm.item.name){
					   			angular.forEach(vm.models, function(model){
					   				if(model.id == modelId){
					   					vm.item.name = model.name;
					   				}							   			
						   		});
					   		}						   		
				   		}else{
				   			vm.item.name = undefined;
				   		}
				   };						
				   
				   vm.adaptTypeOfField = function(typeId){
				   		var adaptType;

				   		switch(typeId){
				   			case 'NUMBER':
				   				adaptType = 'number';
				   				break;
				   			case 'DATE':
				   			    adaptType = 'date';
				   			    break;
				   			default:
				   				adaptType = 'text';
				   				break;
				   		}
				   							   		
				   		return adaptType;
				   }	
				   
				   vm.addLinkParam = function(){
				   	    var param = {state: 'added'};   							   							   		
				   		vm.linkParams.push(param);
				   }
				   
				   vm.removeLinkParam = function(index){
				   		
				   		if(vm.linkParams[index].state == "added")
				   			vm.linkParams.splice(index, 1);
				   		else
				   			vm.linkParams[index].state = "removed";					   		   		
				   }
				   
				   vm.linkParamChanged = function(index){
				   		if(vm.linkParams[index].state != "added")
			   			{
			   				vm.linkParams[index].state = "modified";	
			   			}
				   }		   
				   
				   this.$onInit = function(){
				   				
				   		vm.operators = []]> 
		                       <xsl:for-each select="data_link_operators/data_link_operator">                             	                     
			                       	{	'id': "<xsl:value-of select="id"/>", 
			                       		'name': "<xsl:value-of select="name"/>"
			                       	},
			                   	</xsl:for-each>							   
			                   <![CDATA[];
			                   
				   		vm.mappings = []]> 
		                       <xsl:for-each select="mapped_data_fields/mapped_data_field">                             	                     
			                       	{	'id': <xsl:value-of select="id"/>, 
			                       		'paramId': <xsl:value-of select="param_id"/>,
			                       		'param': "<xsl:value-of select="param"/>",
			                       		'fieldToUse': "<xsl:value-of select="field_to_use"/>",
			                       		'fieldToUseId': <xsl:value-of select="field_to_use_id"/>,
			                       		'operator': "<xsl:value-of select="operator"/>",
			                       		'operatorId': "<xsl:value-of select="operator_id"/>",
			                       		'type': "<xsl:value-of select="type"/>",
			                       		'typeId': "<xsl:value-of select="type_id"/>"
			                       	},
			                   	</xsl:for-each>							   
			                   <![CDATA[];
			            
			            vm.linkParams = []]> 
		                       <xsl:for-each select="data_link_params/data_link_param">                             	                     
			                       	{	'id': <xsl:value-of select="id"/>, 
			                       		'name': "<xsl:value-of select="name"/>",
			                       		'type': "<xsl:value-of select="type"/>",
			                       		'typeId': "<xsl:value-of select="typeId"/>",
			                       		<xsl:choose>
			                       			<xsl:when test="typeId = 'NUMBER'">
			                       				'value': <xsl:value-of select="value"/>,
			                       			</xsl:when>
			                       			<xsl:when test="typeId = 'DATE'">
			                       				'value': new Date("<xsl:value-of select="value"/>"),
			                       			</xsl:when>
			                       			<xsl:otherwise>
			                       				'value': "<xsl:value-of select="value"/>",
			                       			</xsl:otherwise>
			                       		</xsl:choose>
			                       	},
			                   	</xsl:for-each>							   
			                   <![CDATA[];
			                   
			            vm.params = []]> 
		                       <xsl:for-each select="rule_params/rule_param">                             	                     
			                       	{	'id': <xsl:value-of select="id"/>, 
			                       		'modelId': <xsl:value-of select="rule_id"/>,
			                       		'name': "<xsl:value-of select="name"/>",
			                       		'typeId': "<xsl:value-of select="typeId"/>",
			                       		'type': "<xsl:value-of select="type"/>",
			                       		<xsl:choose>
			                       			<xsl:when test="typeId = 'NUMBER'">
			                       				'value': <xsl:value-of select="value"/>,
			                       			</xsl:when>
			                       			<xsl:when test="typeId = 'DATE'">
			                       				'value': new Date("<xsl:value-of select="value"/>"),
			                       			</xsl:when>
			                       			<xsl:otherwise>
			                       				'value': "<xsl:value-of select="value"/>",
			                       			</xsl:otherwise>
			                       		</xsl:choose>
			                       	},
			                   	</xsl:for-each>							   
			                   <![CDATA[];
			            ]]>    
			            <xsl:choose>
			            	<xsl:when test="item and item/id">
			            		vm.item = {
		                   			'id': <xsl:value-of select="item/id"/>,
		                   			'modelId': <xsl:value-of select="item/model_id"/>,
		                   			'name': "<xsl:value-of select="item/name"/>"
		                   		}		                   	  
	
				                vm.modelChanged(vm.item.modelId);
				                if(vm.linkParams.length > 0){
				                	angular.forEach(vm.linkParams, function(param){
				                		angular.forEach(vm.params, function(baseParam){
				                			if(param.id == baseParam.id){
				                				param.baseParam = baseParam;
				                			}
				                		});			                		
				                	});
				                }
			            	</xsl:when>
			            	<xsl:otherwise>
								vm.item = {};
			            	</xsl:otherwise>
			            </xsl:choose>
		                <![CDATA[			                   		               
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);				
        ]]>
        </script>
	</xsl:template>
</xsl:stylesheet>