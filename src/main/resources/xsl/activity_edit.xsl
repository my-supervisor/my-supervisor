<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2018-2022 Minlessika

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to read
the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>Supervisor - Minlessika - Créer une activité</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-lg-12">
      <form action="/activity/save" method="post" ng-controller="AppController as vm">
        <xsl:if test="item and item/id">
          <xsl:attribute name="action">
            <xsl:text>/activity/save?id=</xsl:text>
            <xsl:value-of select="item/id"/>
          </xsl:attribute>
        </xsl:if>
        <div class="card">
          <div class="card-header">
            <xsl:if test="item and item/id">
              <h3 class="card-title">
                <xsl:text>Propriétés de l'activité </xsl:text>
                <i>
                  <xsl:value-of select="item/name"/>
                </i>
              </h3>
            </xsl:if>
            <xsl:if test="not(item) or not(item/id)">
              <h3 class="card-title">Nouvelle activité</h3>
            </xsl:if>
            <div class="card-options">
              <xsl:if test="item/id and item/is_template = 'true'">
                <a role="button" class="btn btn-primary" href="/activity/template/change-designer/edit?activity={item/id}"><i class="fa fa-plus"/> Changer le concepteur
				                </a>
              </xsl:if>
            </div>
          </div>
          <div class="card-body">
            <div class="row">
              <div class="col-md-6">
                <div class="form-group">
                  <label class="form-label">Intitulé <span class="form-required">*</span></label>
                  <input name="name" type="text" class="form-control" placeholder="Saisir un nom" required="">
                    <xsl:if test="item">
                      <xsl:attribute name="value">
                        <xsl:value-of select="item/name"/>
                      </xsl:attribute>
                    </xsl:if>
                  </input>
                </div>
              </div>
              <div class="col-md-6">
                <div class="form-group">
                  <label class="form-label">Version</label>
                  <input type="text" class="form-control" disabled="" value="{item/version}"/>
                </div>
              </div>
              <div class="col-sm-6 col-md-3">
                <div class="form-group">
                  <label class="form-label">Nombre de périodicité <span class="form-required">*</span></label>
                  <input name="periodicity_number" type="number" class="form-control" placeholder="Saisir un nombre" required="" value="1">
                    <xsl:if test="item">
                      <xsl:attribute name="value">
                        <xsl:value-of select="item/periodicity_number"/>
                      </xsl:attribute>
                    </xsl:if>
                  </input>
                </div>
              </div>
              <div class="col-sm-6 col-md-3">
                <div class="form-group">
                  <label class="form-label">Unité de périodicité<span class="form-required">*</span></label>
                  <select name="periodicity_unit_id" class="form-control" required="">
                    <xsl:variable name="item" select="item"/>
                    <xsl:for-each select="periodicities/periodicity">
                      <option>
                        <xsl:if test="$item and id = $item/periodicity_unit_id">
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
              <div class="col-sm-6 col-md-3">
                <div class="form-group">
                  <label class="form-label">Référence de périodicité <span class="form-required">*</span></label>
                  <input name="periodicity_reference" type="date" class="form-control" placeholder="Saisir une date" required="" value="2018-01-01">
                    <xsl:if test="item">
                      <xsl:attribute name="value">
                        <xsl:value-of select="item/periodicity_reference"/>
                      </xsl:attribute>
                    </xsl:if>
                  </input>
                </div>
              </div>
              <div class="col-sm-6 col-md-3">
                <div class="form-group">
                  <label class="custom-control custom-checkbox">
                    <input name="periodicity_close_interval" type="checkbox" class="custom-control-input">
                      <xsl:if test="item/periodicity_close_interval='true'">
                        <xsl:attribute name="checked">checked</xsl:attribute>
                      </xsl:if>
                    </input>
                    <span class="custom-control-label">Fermer l'intervalle ?</span>
                  </label>
                </div>
              </div>
              <div class="col-md-12">
                <div class="form-group">
                  <label class="form-label">Description</label>
                  <textarea name="description" rows="5" class="form-control" placeholder="Décrire le champ...">
                    <xsl:if test="item">
                      <xsl:value-of select="item/description"/>
                    </xsl:if>
                  </textarea>
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
                              <xsl:if test="item/is_template = 'false'">
                                <a href="javascript:void(0)" ng-click="vm.addActivityParam()" role="button" class="btn btn-sm btn-primary"><i class="fa fa-plus"/> Nouveau paramètre
								                </a>
                              </xsl:if>
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr ng-repeat="param in vm.activityParams" ng-hide="param.state == 'removed'">
                            <td>
                              <span class="text-muted">{{$index + 1}}</span>
                              <input hidden="" type="text" name="param_index" ng-model="$index"/>
                              <input hidden="" type="text" name="param_id" ng-model="param.id"/>
                              <input hidden="" type="text" name="param_model_id" ng-model="param.baseParam.modelId"/>
                              <input hidden="" type="text" name="param_base_param_id" ng-model="param.baseParam.id"/>
                              <input hidden="" type="text" name="param_state" ng-model="param.state"/>
                            </td>
                            <td>
                              <select class="form-control" ng-model="param.baseParam" ng-options="v as v.name for v in vm.params" ng-change="vm.paramChanged($index, param.baseParam)" required="" ng-disabled="param.state != 'added'">                                                 
						                     </select>
                            </td>
                            <td>
				                          	{{param.type}}
				                          </td>
                            <td>
                              <input name="param_value" ng-model="param.value" ng-change="vm.activityParamChanged($index)" step="any" type="{{{{vm.adaptTypeOfField(param.typeId)}}}}" class="form-control" placeholder="Saisir une valeur" required=""/>
                            </td>
                            <td>
                              <xsl:if test="item/is_template = 'false'">
                                <a class="icon" ng-click="vm.removeActivityParam($index)">
                                  <i class="fe fe-trash"/>
                                </a>
                              </xsl:if>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </xsl:if>
              <xsl:if test="item/id and item/is_template = 'true'">
                <div class="col-md-12 mb-0">
                  <div class="form-group">
                    <label class="form-label">Historique des releases</label>
                    <div class="table-responsive form-control">
                      <table class="table card-table table-vcenter text-nowrap">
                        <thead>
                          <tr>
                            <th class="w-1">N°</th>
                            <th>Date</th>
                            <th>Version</th>
                            <th/>
                          </tr>
                        </thead>
                        <tbody>
                          <xsl:for-each select="activity_template_releases/activity_template_release">
                            <tr>
                              <td>
                                <span class="text-muted">
                                  <xsl:value-of select="position()"/>
                                </span>
                              </td>
                              <td>
                                <xsl:value-of select="creation_date"/>
                              </td>
                              <td>
                                <xsl:value-of select="version"/>
                              </td>
                              <td>
                                <a class="icon" href="/activity/template/release/edit?id={id}&amp;template={../../item/id}" data-toggle="tooltip" data-placement="top" title="Modifier">
                                  <i class="fe fe-edit"/>
                                </a>
                              </td>
                            </tr>
                          </xsl:for-each>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </xsl:if>
            </div>
          </div>
          <div class="card-footer text-right">
            <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
          </div>
        </div>
      </form>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript">
    <script type="text/javascript"><![CDATA[	
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
				   		var param = vm.activityParams[index];
				   		if(!baseParam){
				   			param.typeId = undefined;
				   			param.type = undefined;
				   			param.value = undefined;
				   		}else{					   		
				   			param.typeId = baseParam.typeId;
				   			param.type = baseParam.type;
				   			param.value = baseParam.value;
				   		}
				   		
				   		vm.activityParamChanged(index);
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
				   
				   vm.addActivityParam = function(){
				   	    var param = {state: 'added'};   							   							   		
				   		vm.activityParams.push(param);
				   }
				   
				   vm.removeActivityParam = function(index){
				   		
				   		if(vm.activityParams[index].state == "added")
				   			vm.activityParams.splice(index, 1);
				   		else
				   			vm.activityParams[index].state = "removed";					   		   		
				   }
				   
				   vm.activityParamChanged = function(index){
				   		if(vm.activityParams[index].state != "added")
			   			{
			   				vm.activityParams[index].state = "modified";	
			   			}
				   }		   
				   
				   this.$onInit = function(){					   			
			            
			            vm.activityParams = []]><xsl:for-each select="activity_params/activity_param">                             	                     
			                       	{	'id': <xsl:value-of select="id"/>, 
			                       		'name': "<xsl:value-of select="name"/>",
			                       		'type': "<xsl:value-of select="type"/>",
			                       		'typeId': "<xsl:value-of select="typeId"/>",
			                       		<xsl:choose><xsl:when test="typeId = 'NUMBER'">
			                       				'value': <xsl:value-of select="value"/>,
			                       			</xsl:when><xsl:when test="typeId = 'DATE'">
			                       				'value': new Date("<xsl:value-of select="value"/>"),
			                       			</xsl:when><xsl:otherwise>
			                       				'value': "<xsl:value-of select="value"/>",
			                       			</xsl:otherwise></xsl:choose>
			                       	},
			                   	</xsl:for-each><![CDATA[];
			                   
			            vm.params = []]><xsl:for-each select="rule_params/rule_param">                             	                     
			                       	{	'id': <xsl:value-of select="id"/>, 
			                       		'modelId': <xsl:value-of select="rule_id"/>,
			                       		'name': "<xsl:value-of select="name"/>",
			                       		'typeId': "<xsl:value-of select="typeId"/>",
			                       		'type': "<xsl:value-of select="type"/>",
			                       		<xsl:choose><xsl:when test="typeId = 'NUMBER'">
			                       				'value': <xsl:value-of select="value"/>,
			                       			</xsl:when><xsl:when test="typeId = 'DATE'">
			                       				'value': new Date("<xsl:value-of select="value"/>"),
			                       			</xsl:when><xsl:otherwise>
			                       				'value': "<xsl:value-of select="value"/>",
			                       			</xsl:otherwise></xsl:choose>
			                       	},
			                   	</xsl:for-each><![CDATA[];
			            ]]><xsl:if test="item and item/id">
			                if(vm.activityParams.length &gt; 0){
			                	angular.forEach(vm.activityParams, function(param){
			                		angular.forEach(vm.params, function(baseParam){
			                			if(param.id == baseParam.id){
			                				param.baseParam = baseParam;
			                			}
			                		});			                		
			                	});
			                }
		            	</xsl:if><![CDATA[			                   		               
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);			
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
