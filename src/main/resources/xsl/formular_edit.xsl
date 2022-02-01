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
      <xsl:text>Supervisor - Minlessika - Configurer une formule</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <form class="card" action="/collect/aggregated-model/formular/save" method="post" ng-controller="AppController as vm">
        <div class="card-header">
          <xsl:if test="item and item/id">
            <h3 class="card-title">
              <xsl:text>Modifier formule </xsl:text>
              <i>
                <xsl:value-of select="item/name"/>
              </i>
            </h3>
          </xsl:if>
          <xsl:if test="not(item) or (item and not(item/id))">
            <h3 class="card-title">Nouvelle formule</h3>
          </xsl:if>
          <div class="card-options">
            <xsl:if test="item and item/id">
              <a ng-if="vm.item.condition.state == 'removed'" href="javascript:void(0)" role="button" class="btn btn-primary" ng-click="vm.addFormularCondition()"><i class="fa fa-plus"/> Ajouter condition
			                </a>
              <a ng-if="vm.item.condition.state == 'added'" href="javascript:void(0)" role="button" class="btn btn-primary" ng-click="vm.removeFormularCondition()"><i class="fa fa-minus"/> Supprimer condition
			                </a>
            </xsl:if>
          </div>
        </div>
        <div class="card-body">
          <xsl:if test="item/id">
            <input name="id" type="text" hidden="hidden" value="{item/id}"/>
          </xsl:if>
          <input name="model_id" hidden="hidden" type="text" ng-model="vm.item.rule_id"/>
          <div class="row">
            <div class="col-sm-6 col-md-4">
              <div class="form-group">
                <label class="form-label">Libellé <span class="form-required">*</span></label>
                <input name="name" type="text" class="form-control" placeholder="Saisir un libellé" required="" value="{item/name}"/>
              </div>
            </div>
            <xsl:if test="item/id">
              <div class="col-sm-6 col-md-4">
                <div class="form-group">
                  <label class="form-label">Code</label>
                  <input type="text" class="form-control" value="{item/code}" disabled=""/>
                </div>
              </div>
            </xsl:if>
            <div class="col-sm-6 col-md-4">
              <div class="form-group">
                <label class="form-label">Type <span class="form-required">*</span></label>
                <select name="type_id" class="form-control" required="">
                  <xsl:variable name="formular" select="item"/>
                  <xsl:for-each select="datafieldtypes/datafieldtype">
                    <option>
                      <xsl:if test="$formular and id = $formular/type_id">
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
            <xsl:if test="item/id">
              <div class="col-md-12" ng-if="vm.item.condition.state == 'added'">
                <div class="form-group">
                  <label class="form-label">Condition</label>
                  <div class="table-responsive form-control">
                    <table class="table card-table table-vcenter text-nowrap">
                      <tbody>
                        <tr>
                          <td colspan="2">
                            <span class="text-muted">Condition</span>
                            <input hidden="" type="text" name="formular_condition_code" ng-model="vm.item.condition.code"/>
                            <input hidden="" type="text" name="formular_condition_state" ng-model="vm.item.condition.state"/>
                            <input hidden="" type="text" name="formular_condition_comparator_id" ng-model="vm.item.condition.comparatorId"/>
                            <input hidden="" type="text" name="formular_condition_value" ng-model="vm.item.condition.value"/>
                            <input hidden="" type="text" name="formular_condition_default_value" ng-model="vm.item.condition.defaultValue"/>
                          </td>
                          <td colspan="2">
                            <select class="form-control" required="" ng-model="vm.item.condition.code" ng-options="v.code as v.name for v in vm.params" ng-change="vm.formularChanged()">                                                 
						                     </select>
                          </td>
                          <td>
                            <select class="form-control" required="" ng-model="vm.item.condition.comparatorId" ng-options="v.id as v.name for v in vm.comparators" ng-change="vm.formularChanged()">                                                 
						                     </select>
                          </td>
                          <td>
                            <input type="text" ng-model="vm.item.condition.value" class="form-control" placeholder="Saisir une valeur" required="" ng-change="vm.formularConditionChanged()"/>
                          </td>
                        </tr>
                        <tr>
                          <td colspan="2">
                            <span class="text-muted">Valeur par défaut</span>
                          </td>
                          <td colspan="2">
                            <input type="number" step="any" ng-model="vm.item.condition.defaultValue" class="form-control" placeholder="Saisir une valeur par défaut" required="" ng-change="vm.formularConditionChanged()"/>
                          </td>
                          <td colspan="3">
				                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <div class="col-md-12 mb-0">
                <div class="form-group">
                  <label class="form-label">
		                       		Expressions 
		                       		(<a href="javascript:void(0)" class="dropdown-toggle" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-plus"/> Nouvelle expression
		                       		 </a>
		                       		 <div class="dropdown-menu" aria-labelledby="dropdownMenuButton"><a class="dropdown-item" href="/collect/aggregated-model/formular/simple-expression/edit?model={{{{vm.item.rule_id}}}}&amp;formular={item/id}">Simple</a><a class="dropdown-item" href="/collect/aggregated-model/formular/case-expression/edit?model={{{{vm.item.rule_id}}}}&amp;formular={item/id}">Condition multiple</a><a class="dropdown-item" href="/collect/aggregated-model/formular/extended-to-parent-expression/edit?model={{{{vm.item.rule_id}}}}&amp;formular={item/id}">Etendre au parent</a><a class="dropdown-item" href="/collect/aggregated-model/formular/extended-to-child-expression/edit?model={{{{vm.item.rule_id}}}}&amp;formular={item/id}">Etendre à l'enfant</a><a class="dropdown-item" href="/collect/aggregated-model/formular/extended-to-model-expression/new?model={{{{vm.item.rule_id}}}}&amp;formular={item/id}" onclick="return confirm('Voulez-vous étendre le modèle ?');">Etendre au modèle</a></div>
		                       		)
		                       </label>
                  <div class="table-responsive form-control">
                    <table class="table card-table table-vcenter text-nowrap">
                      <thead>
                        <tr>
                          <th ng-show="vm.ordering"/>
                          <th class="w-1">N°</th>
                          <th>Libellé</th>
                          <th>Résumé</th>
                          <th>Type</th>
                          <th colspan="2" class="text-right">
                            <a href="javascript:void(0)" role="button" class="btn btn-sm btn-primary mr-1" ng-click="vm.order()" ng-if="!vm.ordering"><i class="fa fa-plus"/> Ordonner
							                </a>
                            <a href="javascript:void(0)" role="button" class="btn btn-sm btn-primary mr-1" ng-click="vm.saveOrder()" ng-if="vm.ordering" ng-disabled="vm.inOrganizeMode"><i class="fa fa-save"/> Enregistrer ordre
							                </a>
                          </th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr ng-repeat="item in vm.item.expressions">
                          <td ng-show="vm.ordering">
                            <button type="button" class="btn btn-primary btn-sm" style="margin:5px" ng-click="vm.downgrade($index)" ng-disabled="$index == vm.item.expressions.length - 1">
                              <i class="fa fa-chevron-down"/>
                            </button>
                            <button type="button" class="btn btn-primary btn-sm" ng-click="vm.upgrade($index)" ng-disabled="$index == 0">
                              <i class="fa fa-chevron-up"/>
                            </button>
                          </td>
                          <td>
                            <span class="text-muted">{{$index + 1}}</span>
                          </td>
                          <td>		                            
				                            {{item.name}}
				                          </td>
                          <td>		                            
				                            {{item.text}}
				                          </td>
                          <td>				                          	
				                          	{{item.type}}
				                          </td>
                          <td>
                            <a class="icon" href="/collect/aggregated-model/formular/simple-expression/edit?id={{{{item.id}}}}&amp;model={{{{vm.item.rule_id}}}}&amp;formular={item/id}" ng-if="item.typeId == 'SIMPLE'">
                              <i class="fe fe-edit"/>
                            </a>
                            <a class="icon" href="/collect/aggregated-model/formular/case-expression/edit?id={{{{item.id}}}}&amp;model={{{{vm.item.rule_id}}}}&amp;formular={item/id}" ng-if="item.typeId == 'CASE'">
                              <i class="fe fe-edit"/>
                            </a>
                            <a class="icon" href="/collect/aggregated-model/formular/extended-to-parent-expression/edit?id={{{{item.id}}}}&amp;model={{{{vm.item.rule_id}}}}&amp;formular={item/id}" ng-if="item.typeId == 'EXTENDED_TO_PARENT'">
                              <i class="fe fe-edit"/>
                            </a>
                            <a class="icon" href="/collect/aggregated-model/formular/extended-to-child-expression/edit?id={{{{item.id}}}}&amp;model={{{{vm.item.rule_id}}}}&amp;formular={item/id}" ng-if="item.typeId == 'EXTENDED_TO_CHILD'">
                              <i class="fe fe-edit"/>
                            </a>
                            <a class="icon" href="/collect/aggregated-model/formular/extended-to-model-expression/edit?id={{{{item.id}}}}&amp;model={{{{vm.item.rule_id}}}}&amp;formular={item/id}" ng-if="item.typeId == 'EXTENDED_TO_MODEL'">
                              <i class="fe fe-edit"/>
                            </a>
                          </td>
                          <td>
                            <a class="icon" href="/collect/aggregated-model/formular/expression/delete?id={{{{item.id}}}}&amp;model={{{{vm.item.rule_id}}}}&amp;formular={item/id}" onclick="return confirm('Voulez-vous supprimer cette expression ?');">
                              <i class="fe fe-trash"/>
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
            <a role="button" class="btn btn-primary" href="/collect/aggregated-model/edit?id={{{{vm.item.rule_id}}}}"><i class="fa fa-arrow-left"/> Retour
	                </a>
            <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
          </div>
        </div>
      </form>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript">
    <script type="text/javascript"><![CDATA[	
            var app = angular.module("app", ["ui.bootstrap", "ngGuid"])
				                 .config(["$provide", function($provide){
							        
				                 }]);			
	
			app.controller("AppController", ["$scope", "$http", function ($scope, $http) {
				   var vm = this;
				   	
				   vm.comparators = []]><xsl:for-each select="comparators/comparator">                             	                     
	                       	{	'id': "<xsl:value-of select="id"/>", 
	                       		'name': "<xsl:value-of select="name"/>"
	                       	},
	                   </xsl:for-each><![CDATA[];
	                    
	                vm.dataFields = []]><xsl:for-each select="datafields"><xsl:for-each select="datafield">                             	                     
			                       	{	'id': <xsl:value-of select="id"/>, 
			                       	    'code': "<xsl:value-of select="code"/>",
			                       		'name': "<xsl:value-of select="name"/>",
			                       		'viewName': "<xsl:value-of select="viewName"/>",
			                       		'type': "<xsl:value-of select="type"/>",
			                       		'typeId': "<xsl:value-of select="type_id"/>",
			                       		'modelId': <xsl:value-of select="model_id"/>
			                       	},
			                    </xsl:for-each></xsl:for-each><![CDATA[];		              		                			                   
				  
				  ]]><xsl:if test="item/id"><![CDATA[
	              	   vm.downgrade = function(expressionIndex){
					   		vm.item.expressions.swap(expressionIndex, expressionIndex + 1);
					   }
					   
					   vm.upgrade = function(expressionIndex){
					   		vm.item.expressions.swap(expressionIndex - 1, expressionIndex);
					   }
					   
					   vm.addFormularCondition = function(){
				   			vm.item.condition.state = "added";
					   }	
					   
					   vm.removeFormularCondition = function(){
					   	    vm.item.condition.state = "removed";
					   }
					   
					   vm.order = function(){
					   		vm.ordering = true;
					   }				
					   
					   vm.saveOrder = function(){
					   							
					   		vm.inOrganizeMode = true;
					   		   		
					   		// enregistrer
						    var orders = [];
						    for(var i = 0; i < vm.item.expressions.length; i++) {
						    	
						    	var data = {
						    		id: vm.item.expressions[i].id,
						    		no: i + 1
						    	}
						    	
						    	orders.push(data);
						    }
						    
						    $http.post('/collect/aggregated-model/formular/expression/organize?formular=' + ]]><xsl:value-of select="item/id"/><![CDATA[ + '&model=' + ]]><xsl:value-of select="item/rule_id"/><![CDATA[, orders)
						         .then(
							         function(success){
							         	vm.ordering = false;
							         	vm.inOrganizeMode = false;
										
										toastr.success('Expressions organisées avec succès !');
							         },
							         function(error){
							         	vm.inOrganizeMode = false;
							         	toastr.error('Expressions non organisées suite à une erreur produite !');
							         }
							     );		
					   }
	              	]]></xsl:if><![CDATA[
				   
				   this.$onInit = function(){
			                   	   
						]]><xsl:if test="not(item) or not(item/id)">
		                   		vm.item = { rule_id: <xsl:value-of select="aggregated_model/id"/>, expressions: [], condition: {} };
		                   </xsl:if><xsl:if test="item and item/id">
		                   
		                        vm.item = {	'id': <xsl:value-of select="item/id"/>, 
		                       		'rule_id': "<xsl:value-of select="item/rule_id"/>",
		                       		'code': "<xsl:value-of select="item/code"/>",
		                       		'name': "<xsl:value-of select="item/name"/>",
		                       		'type': "<xsl:value-of select="item/type"/>",
		                       		'expressions': [
		                       			<xsl:for-each select="item/expressions/expression">                             	                     
					                       	{	'id': <xsl:value-of select="id"/>, 
					                       		'name': "<xsl:value-of select="name"/>",
					                       		'text': "<xsl:value-of select="text"/>",
					                       		'formularId': <xsl:value-of select="formular_id"/>,
					                       		'type': "<xsl:value-of select="type"/>",
					                       		'typeId': "<xsl:value-of select="type_id"/>"
					                       	},
					                    </xsl:for-each>
					                ],
				                    'condition': {	
				                    		'id': <xsl:value-of select="item/condition/id"/>, 
				                       		'paramId': <xsl:value-of select="item/condition/param_id"/>,
				                       		'code': "<xsl:value-of select="item/condition/param_code"/>",
				                       		'comparatorId': "<xsl:value-of select="item/condition/comparator_id"/>",
				                       		'value': "<xsl:value-of select="item/condition/value"/>",
				                       		'state': "<xsl:value-of select="item/condition/state"/>",
				                       		'defaultValue': <xsl:value-of select="item/condition/default_value"/>
				                     }
		                       	};
		                       	
		                       	vm.params = [
	                   				<xsl:for-each select="rule_params/rule_param">                             	                     
				                       	{	'id': <xsl:value-of select="id"/>, 
				                       		'name': "<xsl:value-of select="name"/>",
				                       		'code': "<xsl:value-of select="code"/>",
				                       		<xsl:choose><xsl:when test="typeId = 'NUMBER'">
				                       				'value': <xsl:value-of select="value"/>,
				                       			</xsl:when><xsl:when test="typeId = 'DATE'">
				                       				'value': new Date("<xsl:value-of select="value"/>"),
				                       			</xsl:when><xsl:otherwise>
				                       				'value': "<xsl:value-of select="value"/>",
				                       			</xsl:otherwise></xsl:choose>						                       		
				                       		'typeId': '<xsl:value-of select="typeId"/>'
				                       	},
				                   	</xsl:for-each>
	                   			];
	                   			
	                   			vm.dataFieldTypes = [
	                   				<xsl:for-each select="datafieldtypes/datafieldtype">                             	                     
				                       	{	'id': "<xsl:value-of select="id"/>", 
				                       		'name': "<xsl:value-of select="name"/>"
				                       	},
				                   	</xsl:for-each>
	                   			];			                   					                   				                   					                  			                   				                   					                 	                   							                   		
		                   		                  		
		                   </xsl:if><![CDATA[		                   		                    
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);				
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
