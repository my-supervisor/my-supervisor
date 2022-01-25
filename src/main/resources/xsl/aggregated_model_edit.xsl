<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer un modèle agrégé</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/collect/aggregated-model/save" method="post" ng-controller="AppController as vm">
               <xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/collect/aggregated-model/save?id=</xsl:text><xsl:value-of select="item/id"/>
		            </xsl:attribute>
		        </xsl:if>
		       <div class="card-header">
		       		<xsl:if test="item and item/id">
			            <h3 class="card-title"><xsl:text>Modifier modèle agrégé </xsl:text><i><xsl:value-of select="item/name"/></i></h3>
			        </xsl:if>
			        <xsl:if test="not(item)">
			            <h3 class="card-title">Nouveau modèle agrégé</h3>			            
			        </xsl:if>
		       </div>
               <div class="card-body">          	                 
                 <div class="row">
                   <div class="col-md-6">
                      <div class="form-group">
                        <label class="form-label">Libellé <span class="form-required">*</span></label>
                        <input name="name" type="text" class="form-control" placeholder="Saisir un nom" required="">
                        	<xsl:if test="item and item/id">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/name"/>
					            </xsl:attribute>
					        </xsl:if>
                        </input>
                      </div>
                   </div>
                   <xsl:if test="not(item/id)">
                   		<div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Activité <span class="form-required">*</span></label>
	                        <select class="form-control" required=""
	                        	ng-model="vm.item.activityId"
	                        	ng-options="v.id as v.name for v in vm.activities"> 
	                        		<xsl:if test="item and item/id">
	                        			<xsl:attribute name="disabled">disabled</xsl:attribute>
	                        		</xsl:if>                                               
		                     </select>
		                     <input hidden="" type="text" name="activity_id" ng-model="vm.item.activityId" />
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Modèle à agréger <span class="form-required">*</span></label>
	                        <select class="form-control" required="" 
	                        	ng-model="vm.item.modelId"
	                        	ng-change="vm.modelChanged(vm.item.modelId)"
	                        	ng-options="v.id as v.name for v in vm.models"> 
	                        		<xsl:if test="item and item/id">
	                        			<xsl:attribute name="disabled">disabled</xsl:attribute>
	                        		</xsl:if>                                               
		                     </select>
		                     <input hidden="" type="text" name="model_id" ng-model="vm.item.modelId" />
	                      </div>
	                   </div>
                   </xsl:if>                   
                   <xsl:if test="item and item/id">
                   <div class="col-md-6">
                      <div class="form-group">
                        <label class="form-label">Activité </label>
                        <input type="text" class="form-control" disabled="" value="{item/activity}" />
                        <input hidden="" type="text" name="activity_id" ng-model="vm.item.activityId" />
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="form-label">Modèle à agréger</label>
                        <input type="text" class="form-control" disabled="" value="{item/model}" />
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <label class="form-label">Activité du modèle</label>
                        <input type="text" class="form-control" disabled="" value="{item/model_activity}" />
                      </div>
                    </div>
                    <div class="col-sm-6 col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Date de référence <span class="form-required">*</span></label>
	                       <select name="date_reference_id" class="form-control" required="">                         
	                         <xsl:variable name="item" select="item" />
	                         <xsl:for-each select="date_datafields/datafield">  
	                         	<option>
	                         		<xsl:if test="id = $item/date_reference_id"> 
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
                       <label class="form-label">Champs de base</label>
                       <div class="table-responsive form-control">
		                    <table class="table card-table table-vcenter text-nowrap">
		                      <thead>
		                        <tr>
		                          <th class="w-1">N°</th>
		                          <th>Code</th>
		                          <th>Libellé</th>
		                          <th>Type</th>
		                        </tr>
		                      </thead>
		                      <tbody>		                        
		                        <tr ng-repeat="item in vm.fields">
		                          <td>
		                          	<span class="text-muted">{{$index + 1}}</span>
		                          </td>
		                          <td>
		                          	{{item.code}}
		                          </td>
		                          <td>
		                          	{{item.name}}
		                          </td>
		                          <td>
		                            {{item.type}}
		                          </td>	                          
		                        </tr>
		                      </tbody>
		                    </table>
		                  </div>
                      </div>
                    </div>
                    <div class="col-md-12">
                    	<div class="form-group">
	                    	<label class="form-label">Paramètres (<a href="javascript:void(0)" ng-click="vm.addParam()"><i class="fa fa-plus"></i> Nouveau paramètre</a>)</label>
	                        <div class="table-responsive form-control" ng-show="vm.item.params.length > 0">
			                    <table class="table card-table table-vcenter text-nowrap">
			                      <thead>
			                        <tr>
			                          <th class="w-1">N°</th>
			                          <th>Code</th>
			                          <th>Libellé</th>			                          
			                          <th>Type</th>
			                          <th>Valeur</th>
			                          <th></th>			                          
			                        </tr>
			                      </thead>
			                      <tbody>
			                        <tr ng-repeat="param in vm.item.params" ng-if="param.modelId != vm.item.id">			                          
			                          <td>
			                          	<span class="text-muted">{{$index + 1}}</span>
			                          </td>
			                          <td>
			                            {{param.code}}
			                          </td>	
			                          <td>
			                            {{param.name}}
			                          </td>		                          			                          
			                          <td>	
			                          	{{param.type}}	                           			                     
			                          </td>	
			                          <td>
			                          	{{param.value}}
			                          </td>	
			                          <td></td>	                          
			                        </tr>		                        
			                        <tr ng-repeat="param in vm.item.params" ng-hide="param.state == 'removed'" ng-if="param.modelId == vm.item.id">			                          
			                          <td>
			                          	<span class="text-muted">{{$index + 1}}</span>
			                          	<input hidden="" type="text" name="param_index" ng-model="$index" />
			                          	<input hidden="" type="text" name="param_id" ng-model="param.id" />
			                          	<input hidden="" type="text" name="param_name" ng-model="param.name" />
			                          	<input hidden="" type="text" name="param_state" ng-model="param.state" />
			                          	<input hidden="" type="text" name="param_type" ng-model="param.typeId" />
			                          </td>
			                          <td>
			                            <span ng-if="param.id">{{param.code}}</span>
			                          </td>	
			                          <td>
			                            <input ng-model="param.name" ng-change="vm.paramChanged($index)" type="text" class="form-control" placeholder="Saisir un nom" required=""></input>
			                          </td>		                          			                          
			                          <td>		                           
			                            <select class="form-control"
				                            ng-model="param.typeId"
				                            ng-options="v.id as v.name for v in vm.dataFieldTypes"
				                            ng-change="vm.paramChanged($index)"
				                            required=""
				                            ng-disabled="param.state != 'added'">                                                 
					                     </select>				                     
			                          </td>	
			                          <td>
			                          	<input name="param_value" ng-model="param.value" ng-change="vm.paramChanged($index)" step="any" type="{{{{vm.adaptTypeOfField(param.typeId)}}}}" class="form-control" placeholder="Saisir une valeur" required=""></input>
			                          </td>	
			                          <td>
					                       <a class="icon" ng-click="vm.removeParam($index)">
					                         <i class="fe fe-trash"></i>
					                       </a>
					                  </td>	                          
			                        </tr>
			                      </tbody>
			                    </table>
			                 </div>
			             </div>
                    </div>
                    <div class="col-md-12">   
                    	<div class="form-group">                  
	                    	<label class="form-label">Formules (<a href="/collect/aggregated-model/formular/edit?model={{{{vm.item.id}}}}"><i class="fa fa-plus"></i> Nouvelle formule</a>)</label>
	                        <div class="table-responsive form-control" ng-show="vm.item.formulars.length > 0">
			                    <table class="table card-table table-vcenter text-nowrap">
			                      <thead>
			                        <tr>
			                          <th class="w-1">N°</th>
			                          <th>Code</th>
			                          <th>Libellé</th>			                          
			                          <th>Type</th>
			                          <th></th>
			                          <th></th>
			                        </tr>
			                      </thead>
			                      <tbody>		                        		                       
			                        <tr ng-repeat="formular in vm.item.formulars">
			                          <td>
			                          	<span class="text-muted">{{$index + 1}}</span>
			                          </td>
			                          <td>
			                            {{formular.code}}
			                          </td>		
			                          <td>		                            
			                            {{formular.name}}
			                          </td>			                                                    
			                          <td>
			                          	{{formular.type}}
			                          </td>	
			                          <td>
					                       <a class="icon" href="/collect/aggregated-model/formular/edit?id={{{{formular.id}}}}&amp;model={{{{vm.item.id}}}}" ng-if="formular.modelId == vm.item.id">
					                         <i class="fe fe-edit"></i>
					                       </a>
					                  </td>	 
					                  <td>
					                       <a class="icon" href="/collect/aggregated-model/formular/delete?id={{{{formular.id}}}}&amp;model={{{{vm.item.id}}}}" onclick="return confirm('Voulez-vous supprimer cette formule ?');" ng-if="formular.modelId == vm.item.id">
					                         <i class="fe fe-trash"></i>
					                       </a>
					                  </td>                         
			                        </tr>
			                      </tbody>
			                    </table>
			                </div>
			             </div>
                    </div>
                    <div class="col-md-12">
                    	<div class="form-group">
		                     <label class="form-label">Filtres (<a href="javascript:void(0)" ng-click="vm.addFilter()"><i class="fa fa-plus"></i> Nouveau filtre</a>)</label>
		                     <div class="form-group" ng-repeat="filter in vm.item.filters" ng-if="filter.modelId != vm.item.id">
		                       <label class="form-label">Filtre {{$index + 1}}</label>
		                       <div class="table-responsive form-control">
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">N°</th>
				                          <th>Champ à comparer</th>
				                          <th>Comparateur</th>
				                          <th>Valeur</th>
				                          <th></th>
				                        </tr>
				                      </thead>
				                      <tbody>		                        
				                        <tr ng-repeat="item in filter.conditions">
				                          <td>
				                          	<span class="text-muted">{{$index + 1}}</span>
				                          </td>
				                          <td>	
				                            {{item.field}}
				                          </td>
				                          <td>
				                            {{item.comparator}}
				                          </td>		                          
				                          <td>
				                          	{{item.value}}
				                          </td>	
				                          <td></td>	                          
				                        </tr>
				                      </tbody>
				                    </table>
				                  </div>
		                     </div>
		                     <div class="form-group" ng-repeat="filter in vm.item.filters" ng-hide="filter.state == 'removed'" ng-if="filter.modelId == vm.item.id">
		                       <input hidden="" type="text" name="filter_index" ng-model="$index" />
		                       <input hidden="" type="text" name="filter_state" ng-model="filter.state" />
		                       <input hidden="" type="text" name="filter_id" ng-model="filter.id" />
		                       <label class="form-label">Filtre {{$index + 1}} (<a href="javascript:void(0)" ng-click="vm.removeFilter($index)"><i class="fa fa-minus"></i> Supprimer filtre</a>, <a href="javascript:void(0)" ng-click="vm.addCondition(filter)"><i class="fa fa-plus"></i> Nouvelle condition</a>)</label>
		                       <div class="table-responsive form-control">
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">N°</th>
				                          <th>Champ à comparer</th>
				                          <th>Comparateur</th>
				                          <th>Valeur</th>
				                          <th></th>
				                        </tr>
				                      </thead>
				                      <tbody>		                        
				                        <tr ng-repeat="item in filter.conditions" ng-hide="item.state == 'removed'">
				                          <td>
				                          	<span class="text-muted">{{$index + 1}}</span>
				                          	<input hidden="" type="text" name="cond_filter_index" ng-model="$parent.$index" />
				                          	<input hidden="" type="text" name="cond_id" ng-model="item.id" />
				                          	<input hidden="" type="text" name="cond_field_code" ng-model="item.fieldCode" />
				                          	<input hidden="" type="text" name="cond_comparator_id" ng-model="item.comparatorId" />
				                          	<input hidden="" type="text" name="cond_value" ng-model="item.value" />
				                          	<input hidden="" type="text" name="cond_state" ng-model="item.state" />
				                          </td>
				                          <td>		                            
				                            <select class="form-control" 
					                            required=""
					                            ng-model="item.fieldCode"
					                            ng-change="vm.conditionChanged($parent.$index, $index)"
					                            ng-options="v.code as v.name for v in vm.filterFields">                                                 
						                     </select>
				                          </td>
				                          <td>
				                            <select class="form-control" 
					                            required=""
					                            ng-model="item.comparatorId"
					                            ng-change="vm.conditionChanged($parent.$index, $index)"
					                            ng-options="v.id as v.name for v in vm.comparators">                                                 
						                     </select>
				                          </td>		                          
				                          <td>
				                          	<input ng-model="item.value" ng-change="vm.conditionChanged($parent.$index, $index)" type="text" class="form-control" placeholder="Saisir une valeur" required=""></input>
				                          </td>	
				                          <td>
						                       <a class="icon" ng-click="vm.removeCondition($parent.$index, $index)">
						                         <i class="fe fe-trash"></i>
						                       </a>
						                  </td>	                          
				                        </tr>
				                      </tbody>
				                    </table>
				                  </div>
		                      </div>
		                 </div>
                    </div>
                    </xsl:if>
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/collect/aggregated-model" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>               	
             </form>
        </div>                     
	</xsl:template> 
	<xsl:template match="page" mode="customScript">
		<script type="text/javascript">
        <![CDATA[	
            var app = angular.module("app", ["ui.bootstrap", "ngGuid"])
				                 .config(["$provide", function($provide){
							        
				                 }]);			
	
				app.controller("AppController", ["$scope", function ($scope) {
					   var vm = this;
					   		
					   vm.comparators = []]> 
						   <xsl:for-each select="comparators/comparator">                             	                     
		                       	{	'id': "<xsl:value-of select="id"/>", 
		                       		'name': "<xsl:value-of select="name"/>"
		                       	},
		                   </xsl:for-each>
		                    <![CDATA[];

					   vm.models = []]> 
						   <xsl:for-each select="data_models/data_model">                             	                     
		                       	{	'id': <xsl:value-of select="id"/>, 
		                       		'name': "<xsl:value-of select="viewName"/>",
		                       		'activityId': <xsl:value-of select="activity_id"/>
		                       	},
		                   </xsl:for-each>
		                    <![CDATA[];		                
		                
		                vm.dataFieldTypes = []]> 
						   <xsl:for-each select="datafieldtypes/datafieldtype">                             	                     
		                       	{	'id': "<xsl:value-of select="id"/>", 
		                       		'name': "<xsl:value-of select="name"/>"
		                       	},
		                   </xsl:for-each>
		                    <![CDATA[];
		                    
		                vm.dataFieldsOfModel = [];
		                
		                vm.dataFields = []]> 
			                   <xsl:for-each select="datafields">
			                   		<xsl:for-each select="datafield">                             	                     
				                       	{	'id': <xsl:value-of select="id"/>, 
				                       	    'code': "<xsl:value-of select="code"/>",
				                       		'name': "<xsl:value-of select="name"/>",
				                       		'viewName': "<xsl:value-of select="viewName"/>",
				                       		'type': "<xsl:value-of select="type"/>",
				                       		'typeId': "<xsl:value-of select="type_id"/>",
				                       		'modelId': <xsl:value-of select="model_id"/>
				                       	},
				                    </xsl:for-each>
			                   </xsl:for-each>						   
		                    <![CDATA[];		              		                
			                   
			           vm.filterFields = []]> 
			                   <xsl:for-each select="datafields">
			                   		<xsl:for-each select="datafield">                             	                     
				                       	{	'code': "<xsl:value-of select="code"/>",
				                       		'name': "<xsl:value-of select="name"/>",
				                       		'type': "<xsl:value-of select="type"/>",
				                       		'typeId': "<xsl:value-of select="type_id"/>"
				                       	},
				                    </xsl:for-each>
			                   </xsl:for-each>	
			                   <xsl:for-each select="rule_formulars">
			                   		<xsl:for-each select="rule_formular">                             	                     
				                       	{	'code': "<xsl:value-of select="code"/>",
				                       		'name': "<xsl:value-of select="name"/>",
				                       		'type': "<xsl:value-of select="type"/>",
				                       		'typeId': "<xsl:value-of select="type_id"/>"
				                       	},
				                    </xsl:for-each>
			                   </xsl:for-each>					   
		                    <![CDATA[];	
		                    
			           vm.modelChanged = function(modelId){
					   		
					   		vm.dataFieldsOfModel = [];
					   		
					   		if(modelId){
						   		for(var i=0; i<vm.dataFields.length; i++){
						   			if(vm.dataFields[i].modelId == modelId){
						   				vm.dataFieldsOfModel.push(vm.dataFields[i]);
					   				}
						   		}
						   		
						   		for(var i=0; i<vm.models.length; i++){
						   			if(vm.models[i].id == modelId){
						   				vm.modelSelected = vm.models[i];
						   				break;
					   				}
						   		}
					   		} else {
					   			vm.modelSelected = undefined;
					   		}
					   };
					   
					   vm.addFilter = function(){
					   		var filter = { conditions:[], state: 'added', modelId: vm.item.id};
					   		vm.item.filters.push(filter);
					   		vm.addCondition(filter);
					   }
					   
					   vm.removeFilter = function(index){	
					   		var filter = vm.item.filters[index];
					   		if(filter.state == "added")
					   			vm.item.filters.splice(index, 1);
					   		else
					   			filter.state = "removed";
					   }
					   
					   vm.addCondition = function(filter){
					   		filter.conditions.push({state: 'added'});
					   		if(filter.state != 'added')
					   			filter.state = "modified";
					   }
					   
					   vm.removeCondition = function(filterIndex, conditionIndex){
					   		
					   		var filter = vm.item.filters[filterIndex];
					   		if(filter.conditions[conditionIndex].state == "added")
					   			filter.conditions.splice(conditionIndex, 1);
					   		else
					   			filter.conditions[conditionIndex].state = "removed";
					   		
					   		if(filter.state != 'added')
					   			filter.state = "modified";
					   				
					   		if(filter.conditions.length == 0 || (filter.conditions.length == 1 && filter.conditions[0].state == "removed"))
					   			vm.removeFilter(filterIndex);			   		
					   }
					   
					   vm.conditionChanged = function(filterIndex, conditionIndex){
					   		var filter = vm.item.filters[filterIndex];
					   		if(filter.conditions[conditionIndex].state != "added")
				   			{
				   				filter.conditions[conditionIndex].state = "modified";	
				   				filter.state = "modified";	
				   			}
				   			
				   			if(filter.state != 'added')
					   			filter.state = "modified";
					   }					  
					   
					   vm.addParam = function(){
					   	    var param = {state: 'added', typeId: 'NUMBER', modelId: vm.item.id};   							   							   		
					   		vm.item.params.push(param);
					   }
					   
					   vm.removeParam = function(index){
					   		
					   		if(vm.item.params[index].state == "added")
					   			vm.item.params.splice(index, 1);
					   		else
					   			vm.item.params[index].state = "removed";					   		   		
					   }
					   
					   vm.paramChanged = function(index){
					   		if(vm.item.params[index].state != "added")
				   			{
				   				vm.item.params[index].state = "modified";	
				   			}
					   }
					   
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
					   
					   this.$onInit = function(){
					   				
					   		vm.fields = []]> 
			                       <xsl:for-each select="datafields/datafield">                             	                     
				                       	{	'id': <xsl:value-of select="id"/>, 
				                       		'code': "<xsl:value-of select="code"/>",
				                       		'name': "<xsl:value-of select="name"/>",
				                       		'type': "<xsl:value-of select="type"/>",
				                       		'typeId': "<xsl:value-of select="type_id"/>",
				                       	},
				                   	</xsl:for-each>							   
				                   <![CDATA[];
				                   	   
							]]> 
							
			                   <xsl:if test="not(item) or not(item/id)">
			                   		<![CDATA[
			                   		vm.activities = []]> 
												   <xsl:for-each select="user_activities/activity">                             	                     
								                       	{	'id': <xsl:value-of select="id"/>, 
								                       		'name': "<xsl:value-of select="name"/>"
								                       	},
								                   </xsl:for-each>
								                    <![CDATA[];
		                    
			                   		vm.item = { filters: [], formulars: [], params:[] };
			                   		
			                   		if(vm.models.length > 0){
			                   			vm.item.modelId = vm.models[0].id;
			                   			vm.modelChanged(vm.models[0].id);
			                   		}
			                   		
			                   		if(vm.activities.length > 0){
			                   			vm.item.activityId = vm.activities[0].id;
			                   		}
			                   		]]>
			                   </xsl:if>		
			                   
			                   <xsl:if test="item and item/id">
			                   		vm.item = {
			                   			'id': <xsl:value-of select="item/id"/>,
			                   			'modelId': <xsl:value-of select="item/model_id"/>,
			                   			'activity': "<xsl:value-of select="item/activity"/>",
			                   			'activityId': <xsl:value-of select="item/activity_id"/>,
			                   			'modelActivity': "<xsl:value-of select="item/model_activity"/>",
			                   			'modelActivityId': <xsl:value-of select="item/model_activity_id"/>,
			                   			'filters': [
			                   				<xsl:for-each select="rule_filters/rule_filter">                             	                     
						                       	{	
						                       		'id': <xsl:value-of select="id"/>, 
						                       		'rule_id': "<xsl:value-of select="rule_id"/>",
						                       		'modelId': <xsl:value-of select="model_id"/>,
						                       		'model': "<xsl:value-of select="model"/>",
						                       		'conditions': [
						                       			<xsl:for-each select="conditions/condition">                             	                     
									                       	{	
									                       		'id': <xsl:value-of select="id"/>, 
									                       		'filterId': <xsl:value-of select="filter_id"/>,
									                       		'field': "<xsl:value-of select="field"/>",
									                       		'fieldCode': "<xsl:value-of select="field_code"/>",
									                       		'comparatorId': "<xsl:value-of select="comparator_id"/>",									                       		
									                       		'comparator': "<xsl:value-of select="comparator"/>",
									                       		'value': "<xsl:value-of select="value"/>"
									                       	},
									                    </xsl:for-each>
						                       		]
						                       	},
						                   	</xsl:for-each>
			                   			],
			                   			'formulars': [
			                   				<xsl:for-each select="rule_formulars/rule_formular">                             	                     
						                       	{	'id': <xsl:value-of select="id"/>, 
						                       		'rule_id': "<xsl:value-of select="rule_id"/>",
						                       		'code': "<xsl:value-of select="code"/>",
						                       		'name': "<xsl:value-of select="name"/>",
						                       		'type': "<xsl:value-of select="type"/>",
						                       		'typeId': "<xsl:value-of select="type_id"/>",
						                       		'modelId': <xsl:value-of select="model_id"/>,
						                       		'model': "<xsl:value-of select="model"/>"
						                       	},
						                   	</xsl:for-each>
			                   			],
			                   			'params': [
			                   				<xsl:for-each select="rule_params/rule_param">                             	                     
						                       	{	'id': <xsl:value-of select="id"/>, 
						                       		'name': "<xsl:value-of select="name"/>",
						                       		'code': "<xsl:value-of select="code"/>",
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
						                       		'typeId': '<xsl:value-of select="typeId"/>',
						                       		'type': "<xsl:value-of select="type"/>",
						                       		'modelId': <xsl:value-of select="model_id"/>,
						                       		'model': "<xsl:value-of select="model"/>"
						                       	},
						                   	</xsl:for-each>
			                   			]		        
			                   		};			                   					                   				                   					                  
			                   		
			                   		vm.modelChanged(vm.item.modelId);			                   					                   			                   							                   	
			                   		                  		
			                   </xsl:if>				   
		                    <![CDATA[		                   		                    
					   };
			    }]);	
				
				angular.bootstrap(document, ['app']);				
        ]]>
        </script>
	</xsl:template> 		
</xsl:stylesheet>