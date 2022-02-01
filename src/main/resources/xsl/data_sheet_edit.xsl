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
      <xsl:text>Supervisor - Minlessika - Gérer une feuille de données</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12" ng-controller="AppCtrl as vml">
      <form class="card" action="/collect/sheet/save?model={data_sheet_model/id}" method="post">
        <xsl:if test="item and item/id">
          <xsl:attribute name="action">
            <xsl:text>/collect/sheet/save?id=</xsl:text>
            <xsl:value-of select="item/id"/>
          </xsl:attribute>
        </xsl:if>
        <div class="card-header">
          <xsl:if test="item and item/id">
            <h3 class="card-title">
              <xsl:text>Modifier feuille </xsl:text>
              <i><xsl:value-of select="item/name"/>
								-
								<xsl:value-of select="item/reference"/>
							</i>
            </h3>
          </xsl:if>
          <xsl:if test="not(item)">
            <h3 class="card-title">
							Nouvelle feuille -
							<i><xsl:value-of select="data_sheet_model/name"/></i>
						</h3>
          </xsl:if>
          <div class="card-options">
            <a role="button" class="btn btn-primary" href="/collect/sheet/edit?model={data_sheet_model/id}"><i class="fa fa-plus"/>
							Nouveau
						</a>
          </div>
        </div>
        <div class="card-body">
          <div class="row">
            <xsl:apply-templates select="data_field_of_sheets/data_field_of_sheet"/>
          </div>
        </div>
        <div class="card-footer text-right">
          <div class="d-flex">
            <a href="/collect/sheet" class="btn btn-link">Annuler</a>
            <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
          </div>
        </div>
      </form>
    </div>
    <script type="text/ng-template" id="listValue.html">
      <form ng-submit="vm.save()">
        <div class="modal-header">
          <h4 ng-if="vm.listModels.length == 0 || vm.listModels.length &gt; 1" class="modal-title">Choose a value</h4>
          <h4 ng-if="vm.listModels.length == 1" class="modal-title">Choose a {{vm.listModels[0].name}}</h4>
          <input type="text" class="form-control pull-right col-md-6" ng-model="vm.filter" placeholder="Search..." ng-change="vm.search()" ng-model-options="{{ debounce: 500 }}"/>
        </div>
        <div class="modal-body">
          <div class="row">
            <div class="col-md-12">
              <h5 class="pull-left">
			              		{{vm.totalCount}} values - {{vm.pagesCount}} pages
			              	</h5>
              <button type="button" class="btn btn-sm btn-primary pull-right" ng-click="vm.search()"><i class="fa fa-refresh"/> Refresh
	                       	</button>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12">
              <div ng-show="vm.loadingData">
                <div class="col-xs-4"/>
                <div class="col-xs-4">
                  <i class="fa fa-refresh fa-5x fa-spin"/>
                  <label class="label label-primary">Loading data...</label>
                </div>
                <div class="col-xs-4"/>
              </div>
              <div class="table-responsive" ng-show="!vm.loadingData">
                <table class="table card-table table-vcenter">
                  <thead>
                    <tr>
                      <th class="w-1">N°</th>
                      <th ng-if="0">ID</th>
                      <th>Value</th>
                      <th/>
                    </tr>
                  </thead>
                  <tbody>
                    <tr ng-repeat="item in vm.items">
                      <td>
                        <span class="text-muted">{{ $index + 1 }}</span>
                      </td>
                      <td ng-if="0">
		                          	{{ item.id }}
		                          </td>
                      <td>
		                          	{{ item.value }}
		                          </td>
                      <td>
                        <a class="btn btn-sm btn-secondary mr-1" ng-click="vm.choose(item)">
			                         	Choose
			                        </a>
                        <a class="btn btn-sm btn-secondary" href="/collect/sheet/edit-by-ref?ref={{{{item.id}}}}" target="_blank">
                          <i class="fa fa-eye"/>
                        </a>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="card-footer d-flex justify-content-center">
                <ul uib-pagination="" first-text="First" last-text="Last" previous-text="Previous" next-text="Next" total-items="vm.totalCount" ng-model="vm.currentPage" items-per-page="vm.nbItemsPerPage" max-size="vm.pageSize" num-pages="vm.pagesCount" class="pagination-md" rotate="false" boundary-links="true" force-ellipses="true" ng-change="vm.pageChanged()"/>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <div class="dropdown" ng-if="vm.listModels.length &gt; 1">
            <a href="javascript:void(0)" role="button" class="dropdown-toggle btn btn-primary mr-1" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-plus"/> New
                   		</a>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
              <a ng-repeat="item in vm.listModels" class="dropdown-item" href="/collect/sheet/edit?model={{{{item.id}}}}" target="_blank">{{ item.name }}</a>
            </div>
          </div>
          <a ng-if="vm.listModels.length == 1" href="/collect/sheet/edit?model={{{{vm.listModels[0].id}}}}" role="button" class="btn btn-primary mr-1" target="_blank"><i class="fa fa-plus"/> New
	                </a>
          <button type="button" class="btn btn-danger" ng-click="vm.cancelEdit()">Cancel</button>
        </div>
      </form>
    </script>
    <xsl:for-each select="data_field_of_sheets/data_field_of_sheet">
      <xsl:if test="type_id = 'TABLE'">
        <script type="text/template" id="{code}RowTemplate">
          <xsl:variable name="table" select="."/>
          <xsl:for-each select="../../columns/data_field_of_sheet">
            <xsl:if test="table_code=$table/code">
              <td>
                <xsl:apply-templates select="."/>
              </td>
            </xsl:if>
          </xsl:for-each>
        </script>
      </xsl:if>
    </xsl:for-each>
  </xsl:template>
  <xsl:template match="page" mode="customScript">
    <script type="text/javascript"><![CDATA[	           	
            var app = angular.module("app", ["ui.bootstrap"]);
			app.config(["$controllerProvider", "$provide", function($controllerProvider, $provide){
				$provide.decorator('$uibModal', function ($delegate) {
							            var open = $delegate.open;
							
							            $delegate.open = function (options) { // avoid closing by backdrop click
							                options = angular.extend(options || {}, {
							                    backdrop: 'static',
							                    keyboard: false,
							                    size: 'lg'
							                });
							
							                return open(options);
							            };
							            return $delegate;
							        });
				
			}]).run(["$templateCache", function($templateCache){
				]]><xsl:for-each select="data_field_of_sheets/data_field_of_sheet"><xsl:if test="type_id = 'TABLE'"><![CDATA[$templateCache.put(']]><xsl:value-of select="code"/><![CDATA[RowTemplate.html', angular.element("#]]><xsl:value-of select="code"/><![CDATA[RowTemplate")[0].innerHTML);]]></xsl:if></xsl:for-each><![CDATA[
			}]).directive('cells', function ($templateCache, $compile, $uibModal, $rootScope) {
			    return {
			        restrict: 'EA',
			        scope: {
			            url: "=",
			            row: "=",
			        },				        
			        template: "",
			        link: function (scope, element, attrs) {
	   
			        	var html = $templateCache.get(scope.url);
			            var e = $compile(html)(scope);
			            for(var i = 0; i < scope.row.fields.length; i++){
			            	var field = scope.row.fields[i];
			            	if(field.styleId == 'LIST') {
			            	    var name = 'itemrow_' + scope.row.table_code + '_' + field.code;
			            	    scope.vm[name] = {id: field.key, value: field.value, isReadonly: field.isReadonly };
			            	    $rootScope.listValueChanged(scope.vm[name], scope.vm[name].id);	            	                    	    
		            	    } else {
			            	    if(field.typeId == 'BOOLEAN') {
			            	        if(field.value == 'true') {
			            	        	e.find("input[name*='" + field.inputName + "']").prop('checked', 'checked');
			            	        }				            	    	
			            	    } else {
			            	    	e.find("input[name*='" + field.inputName + "']").val(field.value);
			            	    }				            		
		            		}			            	
			            }
			            
			            element.replaceWith(e);
			        }
			    };
			});
			
			app.controller('listValueCtrl', ['data', '$uibModalInstance', '$http', 
				function (data, $uibModalInstance, $http) {
					var vm = this;							
					
					vm.date = data.date;
					vm.listModels = data.listModels;
					
					vm.totalCount = 0;
					
					vm.cancelEdit = cancelEdit;
					vm.choose = choose;												
							    	
					function choose(item){
						$uibModalInstance.close(item);
					};
					
					vm.search = function() {
												
			            var config = {
			                params: {
			                    page: vm.currentPage,
			                    itemsPerPage: vm.nbItemsPerPage,
			                    filter: vm.filter,
			                    field: data.fieldId,
			                    model: data.modelId
			                }
			            };
			
			            vm.loadingData = true;
			            return $http.get('/list-data-field/values/search', config).then(
					            function(response) {
					            	vm.loadingData = false;
					            	
					            	vm.totalCount = response.data.count;						            
						            vm.items = response.data.items;
					            },
					            function(error){
					            	vm.loadingData = false;
					            }
			            );
			        }
				        
					vm.pageChanged = function(){
	               		vm.search();
	                }
		               
					function cancelEdit(){
						$uibModalInstance.dismiss();
					}
					   
					this.$onInit = function(){
						vm.nbItemsPerPage = 5;
				   	    vm.pageSize = 5;
				   	    vm.currentPage = 1;
				   	    
				   	    vm.search();  					  				  							
					}
					
			}]);			
			
			]]><xsl:for-each select="data_field_of_sheets/data_field_of_sheet"><xsl:if test="type_id = 'TABLE'"><![CDATA[ 			    					    		
			    		app.controller("TableCtrl]]><xsl:value-of select="code"/><![CDATA[", ["$scope", "$rootScope", "$uibModal", function ($scope, $rootScope, $uibModal) {
							   var vm = this;					   							   				 								 
							   
							   vm.addRow = function(){
							   	    var row = {state: 'added', fields: []};   							   							   		
							   		vm.item.rows.push(row);
							   }
							   
							   vm.removeRow = function(index){
							   		var nbRow = 0;
							   		angular.forEach(vm.item.rows, function(row){
							   			if(row.state != "removed")
							   				nbRow++;
							   		});
							   		
							   		if(nbRow == 1)
							   			return;
							   		
							   		if(vm.item.rows[index].state == "added")
							   			vm.item.rows.splice(index, 1);
							   		else
							   			vm.item.rows[index].state = "removed";					   		   		
							   }
							   
							   vm.rowChanged = function(index){
							        console.log("row changed");
							   		if(vm.item.rows[index].state != "added") {
						   				vm.item.rows[index].state = "modified";	
						   			}
							   }								 
  
							   this.$onInit = function(){
			   					
									]]><xsl:choose><xsl:when test="id &gt; 0"><xsl:variable name="table_id" select="id"/>
				                   		vm.item = {
				                   				rows:[
				                   					<xsl:for-each select="../../rows/row"><xsl:if test="table_id=$table_id">
				                   					    	{
					                   							'id': <xsl:value-of select="id"/>,
					                   							'table_id': <xsl:value-of select="table_id"/>,
					                   							'table_code': "<xsl:value-of select="table_code"/>",
					                   							'fields': [
					                   								<xsl:for-each select="cell">
					                   									{
					                   										'id': <xsl:value-of select="id"/>,
					                   										'code': "<xsl:value-of select="code"/>",					                   										
					                   										'value': "<xsl:value-of select="value"/>",
					                   										'inputName': "row_<xsl:value-of select="../table_code"/>_<xsl:value-of select="code"/>",
					                   										'typeId': "<xsl:value-of select="type_id"/>",
					                   										'styleId': "<xsl:value-of select="style_id"/>",
					                   										<xsl:if test="style_id = 'LIST'">
					                   										'key': "<xsl:value-of select="key"/>",
					                   										'canBeUpdated': <xsl:value-of select="can_be_updated"/>,
					                   										</xsl:if>					                   										
					                   										'isReadonly': <xsl:value-of select="is_read_only = 'true' or (id &gt; 0 and must_edit_once = 'true')"/>
					                   									},
					                   								</xsl:for-each>
					                   							]
					                   						},
				                   					    </xsl:if></xsl:for-each>
				                   				]
				                   		};
				                   	</xsl:when><xsl:otherwise><![CDATA[
				                   		vm.item = { rows:[] };	
				                   		vm.addRow();		                   
				                   		]]></xsl:otherwise></xsl:choose><![CDATA[		                   		                    
							   };
					    }]);	
			    	]]></xsl:if></xsl:for-each><![CDATA[				
		    
		    app.controller("AppCtrl", ["$uibModal", "$rootScope", function($uibModal, $rootScope){
		    	var vml = this;
		    	
		    	$rootScope.defineValue = defineValue;
				$rootScope.listValueChanged = listValueChanged;
								
				var listModels = []]><xsl:for-each select="list_data_field_sources/list_data_field_source">                             	                     
		                       	{	
		                       		'id': <xsl:value-of select="list_model_id"/>, 
		                       		'name': "<xsl:value-of select="list_model"/>",
		                       		'fieldId': <xsl:value-of select="field_id"/>
		                       	},
		                   </xsl:for-each><![CDATA[];
		                    
		        var predefinedValues = []]><xsl:for-each select="data_field_of_sheets/data_field_of_sheet"><xsl:if test="style_id = 'LIST' and can_be_updated = 'false'"><xsl:for-each select="predefined_value">
		                       	{	
		                       		'id': '<xsl:value-of select="id"/>', 
		                       		'value': "<xsl:value-of select="value"/>"
		                       	},
		                       	</xsl:for-each></xsl:if></xsl:for-each><xsl:for-each select="rows/row/cell"><xsl:if test="style_id = 'LIST' and can_be_updated = 'false'"><xsl:for-each select="predefined_value">
		                       	{	
		                       		'id': '<xsl:value-of select="id"/>', 
		                       		'value': "<xsl:value-of select="value"/>"
		                       	},
		                       	</xsl:for-each></xsl:if></xsl:for-each><xsl:for-each select="columns/data_field_of_sheet"><xsl:if test="style_id = 'LIST' and can_be_updated = 'false'"><xsl:for-each select="predefined_value">
		                       	{	
		                       		'id': '<xsl:value-of select="id"/>', 
		                       		'value': "<xsl:value-of select="value"/>"
		                       	},
		                       	</xsl:for-each></xsl:if></xsl:for-each><xsl:for-each select="rows/row/cell"><xsl:if test="style_id = 'LIST' and can_be_updated = 'false'"><xsl:for-each select="predefined_value">
		                       	{	
		                       		'id': '<xsl:value-of select="id"/>', 
		                       		'value': "<xsl:value-of select="value"/>"
		                       	},
		                       	</xsl:for-each></xsl:if></xsl:for-each><![CDATA[];
		                    
		        var predefinedDistinctValues = [];
		        for(var i = 0; i < predefinedValues.length; i++) {
		            var value = predefinedValues[i];
		            var belong = false;
		        	for(var j = 0; j < predefinedDistinctValues.length; j++) {
		        		var valueDist = predefinedDistinctValues[j];
		        		if(value.id == valueDist.id) {
		        			belong = true;
		        			break;
		        		}
		        	}
		        	
		        	if(!belong) {
		        		predefinedDistinctValues.push(value);
		        	}
		        }
		        
				function listValueChanged(item, newId) {
					item.id = newId;
					for(var j = 0; j < predefinedDistinctValues.length; j++) {
		        		var value = predefinedDistinctValues[j];
		        		if(value.id == newId) {
		        			item.value = value.value;
		        			break;
		        		}
		        	}					
				}
				
			    function defineValue(original, fieldId, modelId){

					var models = [];
					
					angular.forEach(listModels, function(model){
						if(model.fieldId == fieldId) {
							models.push(model);
						}
					});
					
					$uibModal.open({
		                templateUrl: 'listValue.html',
		                controller: 'listValueCtrl as vm',
		                resolve: {
		                    data: {
		                    	fieldId: fieldId,
		                    	modelId: modelId,
		                    	listModels: models
		                    }
		                }
		            }).result.then(
		            	function (item) {
		            		original.id = item.id;
							original.value = item.value;
			            }, function () {
			
			            }
		            );
			    }
		    }]);
		    
		    angular.bootstrap(document, ['app']);			
        ]]></script>
  </xsl:template>
  <xsl:template match="data_field_of_sheet" mode="table">
    <xsl:apply-templates select="." mode="label"/>
    <xsl:variable name="table" select="."/>
    <div class="table-responsive form-control" ng-controller="TableCtrl{code} as vm">
      <input name="table" hidden="hidden" type="text" value="{$table/code}"/>
      <table class="table card-table table-vcenter text-nowrap" style="min-width: 750px">
        <thead>
          <tr>
            <th class="w-1">N°</th>
            <xsl:for-each select="../../columns/data_field_of_sheet">
              <xsl:if test="table_code=$table/code">
                <th>
                  <xsl:value-of select="name"/>
                  <xsl:if test="is_mandatory = 'true'">
                    <span class="form-required">*</span>
                  </xsl:if>
                </th>
              </xsl:if>
            </xsl:for-each>
            <th class="text-right">
              <a href="javascript:void(0)" ng-click="vm.addRow()" role="button" class="btn btn-sm btn-primary"><i class="fa fa-plus"/>
								Nouveau
							</a>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="row in vm.item.rows" ng-hide="row.state == 'removed'">
            <td>
              <span class="text-muted">{{$index + 1}}</span>
              <input name="row_{$table/code}_id" hidden="hidden" type="text" ng-model="row.id"/>
              <input name="row_{$table/code}_table" hidden="hidden" type="text" value="{$table/code}"/>
              <input name="row_{$table/code}_state" hidden="hidden" type="text" ng-model="row.state"/>
            </td>
            <td cells="" url="'{$table/code}RowTemplate.html'" row="row"/>
            <td>
              <a class="icon" ng-click="vm.removeRow($index)">
                <i class="fe fe-trash"/>
              </a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </xsl:template>
  <xsl:template match="data_field_of_sheet" mode="label">
    <xsl:if test="not(table)">
      <label class="form-label">
        <xsl:value-of select="name"/>
        <xsl:if test="is_mandatory = 'true' and not(id &gt; 0 and is_table = 'true' and code='DATE')">
          <span class="form-required">*</span>
        </xsl:if>
      </label>
    </xsl:if>
  </xsl:template>
  <xsl:template match="data_field_of_sheet" mode="number">
    <xsl:variable name="code">
      <xsl:choose>
        <xsl:when test="table">
          <xsl:text>row_</xsl:text>
          <xsl:value-of select="table_code"/>
          <xsl:text>_</xsl:text>
          <xsl:value-of select="code"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="code"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:apply-templates select="." mode="label"/>
    <xsl:choose>
      <xsl:when test="style_id = 'LIST'">
        <xsl:variable name="APOS_C">\\'</xsl:variable>
        <xsl:variable name="APOS">'</xsl:variable>
        <xsl:variable name="value" select="replace(value, $APOS, $APOS_C)"/>
        <xsl:variable name="list_model" select="replace(list_model, $APOS, $APOS_C)"/>
        <xsl:variable name="is_read_only" select="is_read_only = 'true' or (id &gt; 0 and must_edit_once = 'true')"/>
        <xsl:variable name="is_mandatory" select="is_mandatory"/>
        <div hidden="hidden" ng-init="vm.item{$code} = {{ 'id': '{key}', 'value': '{$value}', 'isReadonly': {$is_read_only}, 'isMandatory': {$is_mandatory} }}">Ok</div>
        <input name="key-{$code}" type="text" hidden="hidden" ng-model="vm.item{$code}.id"/>
        <input name="{$code}" type="text" hidden="hidden" ng-model="vm.item{$code}.value"/>
        <xsl:choose>
          <xsl:when test="can_be_updated = 'true'">
            <input type="text" class="form-control" disabled="disabled" ng-model="vm.item{$code}.value" ng-hide="!vm.item{$code}.isReadonly" ng-required="vm.item{$code}.isMandatory"/>
            <div class="input-group" ng-hide="vm.item{$code}.isReadonly">
              <input name="view-{$code}" type="text" class="form-control" disabled="disabled" ng-model="vm.item{$code}.value" ng-required="vm.item{$code}.isMandatory"/>
              <div class="input-group-append">
                <a ng-show="vm.item{$code}.id" class="btn btn-outline-primary" href="/collect/sheet/edit-by-ref?ref={{{{vm.item{$code}.id}}}}" target="_blank">
                  <i class="fa fa-eye"/>
                </a>
                <button class="btn btn-outline-primary" type="button" ng-click="$parent.defineValue(vm.item{$code}, {origin_id}, {model_id})">...</button>
              </div>
            </div>
          </xsl:when>
          <xsl:otherwise>
            <select class="form-control" ng-required="vm.item{$code}.isMandatory" ng-model="vm.item{$code}.id" ng-disabled="vm.item{$code}.isReadonly" ng-change="$parent.listValueChanged(vm.item{$code}, vm.item{$code}.id)">
              <xsl:for-each select="predefined_value">
                <option>
                  <xsl:attribute name="value">
                    <xsl:value-of select="id"/>
                  </xsl:attribute>
                  <xsl:value-of select="value"/>
                </option>
              </xsl:for-each>
            </select>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <input name="{$code}" type="number" class="form-control" step="any">
          <xsl:if test="is_read_only = 'true' or (id &gt; 0 and must_edit_once = 'true')">
            <xsl:attribute name="disabled">
              <xsl:text>disabled</xsl:text>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="is_mandatory = 'true'">
            <xsl:attribute name="required">
              <xsl:text>required</xsl:text>
            </xsl:attribute>
          </xsl:if>
          <xsl:attribute name="value">
            <xsl:value-of select="value"/>
          </xsl:attribute>
        </input>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:apply-templates select="." mode="readonly"/>
  </xsl:template>
  <xsl:template match="data_field_of_sheet" mode="string">
    <xsl:variable name="code">
      <xsl:choose>
        <xsl:when test="table">
          <xsl:text>row_</xsl:text>
          <xsl:value-of select="table_code"/>
          <xsl:text>_</xsl:text>
          <xsl:value-of select="code"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="code"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:apply-templates select="." mode="label"/>
    <xsl:choose>
      <xsl:when test="style_id = 'LIST'">
        <xsl:variable name="APOS_C">\\'</xsl:variable>
        <xsl:variable name="APOS">'</xsl:variable>
        <xsl:variable name="value" select="replace(value, $APOS, $APOS_C)"/>
        <xsl:variable name="list_model" select="replace(list_model, $APOS, $APOS_C)"/>
        <xsl:variable name="is_read_only" select="is_read_only = 'true' or (id &gt; 0 and must_edit_once = 'true')"/>
        <xsl:variable name="is_mandatory" select="is_mandatory"/>
        <div hidden="hidden" ng-init="vm.item{$code} = {{ 'id': '{key}', 'value': '{$value}', 'isReadonly': {$is_read_only}, 'isMandatory': {$is_mandatory} }}">Ok</div>
        <input name="key-{$code}" type="text" hidden="hidden" ng-model="vm.item{$code}.id"/>
        <input name="{$code}" type="text" hidden="hidden" ng-model="vm.item{$code}.value"/>
        <xsl:choose>
          <xsl:when test="can_be_updated = 'true'">
            <input type="text" class="form-control" disabled="disabled" ng-model="vm.item{$code}.value" ng-hide="!vm.item{$code}.isReadonly" ng-required="vm.item{$code}.isMandatory"/>
            <div class="input-group" ng-hide="vm.item{$code}.isReadonly">
              <input name="view-{$code}" type="text" class="form-control" disabled="disabled" ng-model="vm.item{$code}.value" ng-required="vm.item{$code}.isMandatory"/>
              <div class="input-group-append">
                <a ng-show="vm.item{$code}.id" class="btn btn-outline-primary" href="/collect/sheet/edit-by-ref?ref={{{{vm.item{$code}.id}}}}" target="_blank">
                  <i class="fa fa-eye"/>
                </a>
                <button class="btn btn-outline-primary" type="button" ng-click="$parent.defineValue(vm.item{$code}, {origin_id}, {model_id})">...</button>
              </div>
            </div>
          </xsl:when>
          <xsl:otherwise>
            <select class="form-control" ng-required="vm.item{$code}.isMandatory" ng-model="vm.item{$code}.id" ng-disabled="vm.item{$code}.isReadonly" ng-change="$parent.listValueChanged(vm.item{$code}, vm.item{$code}.id)">
              <xsl:for-each select="predefined_value">
                <option>
                  <xsl:attribute name="value">
                    <xsl:value-of select="id"/>
                  </xsl:attribute>
                  <xsl:value-of select="value"/>
                </option>
              </xsl:for-each>
            </select>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <input name="{$code}" type="text" class="form-control">
          <xsl:if test="is_read_only = 'true' or (id &gt; 0 and must_edit_once = 'true')">
            <xsl:attribute name="disabled">
              <xsl:text>disabled</xsl:text>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="is_mandatory = 'true'">
            <xsl:attribute name="required">
              <xsl:text>required</xsl:text>
            </xsl:attribute>
          </xsl:if>
          <xsl:attribute name="value">
            <xsl:value-of select="value"/>
          </xsl:attribute>
        </input>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:apply-templates select="." mode="readonly"/>
  </xsl:template>
  <xsl:template match="data_field_of_sheet" mode="date">
    <xsl:variable name="code">
      <xsl:choose>
        <xsl:when test="table">
          <xsl:text>row_</xsl:text>
          <xsl:value-of select="table_code"/>
          <xsl:text>_</xsl:text>
          <xsl:value-of select="code"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="code"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:apply-templates select="." mode="label"/>
    <xsl:choose>
      <xsl:when test="style_id = 'LIST'">
        <xsl:variable name="APOS_C">\\'</xsl:variable>
        <xsl:variable name="APOS">'</xsl:variable>
        <xsl:variable name="value" select="replace(value, $APOS, $APOS_C)"/>
        <xsl:variable name="list_model" select="replace(list_model, $APOS, $APOS_C)"/>
        <xsl:variable name="is_read_only" select="is_read_only = 'true' or (id &gt; 0 and must_edit_once = 'true')"/>
        <xsl:variable name="is_mandatory" select="is_mandatory"/>
        <div hidden="hidden" ng-init="vm.item{$code} = {{ 'id': '{key}', 'value': '{$value}', 'isReadonly': {$is_read_only}, 'isMandatory': {$is_mandatory} }}">Ok</div>
        <input name="key-{$code}" type="text" hidden="hidden" ng-model="vm.item{$code}.id"/>
        <input name="{$code}" type="text" hidden="hidden" ng-model="vm.item{$code}.value"/>
        <xsl:choose>
          <xsl:when test="can_be_updated = 'true'">
            <input type="text" class="form-control" disabled="disabled" ng-model="vm.item{$code}.value" ng-hide="!vm.item{$code}.isReadonly" ng-required="vm.item{$code}.isMandatory"/>
            <div class="input-group" ng-hide="vm.item{$code}.isReadonly">
              <input name="view-{$code}" type="text" class="form-control" disabled="disabled" ng-model="vm.item{$code}.value" ng-required="vm.item{$code}.isMandatory"/>
              <div class="input-group-append">
                <a ng-show="vm.item{$code}.id" class="btn btn-outline-primary" href="/collect/sheet/edit-by-ref?ref={{{{vm.item{$code}.id}}}}" target="_blank">
                  <i class="fa fa-eye"/>
                </a>
                <button class="btn btn-outline-primary" type="button" ng-click="$parent.defineValue(vm.item{$code}, {origin_id}, {model_id})">...</button>
              </div>
            </div>
          </xsl:when>
          <xsl:otherwise>
            <select class="form-control" ng-required="vm.item{$code}.isMandatory" ng-model="vm.item{$code}.id" ng-disabled="vm.item{$code}.isReadonly" ng-change="$parent.listValueChanged(vm.item{$code}, vm.item{$code}.id)">
              <xsl:for-each select="predefined_value">
                <option>
                  <xsl:attribute name="value">
                    <xsl:value-of select="id"/>
                  </xsl:attribute>
                  <xsl:value-of select="value"/>
                </option>
              </xsl:for-each>
            </select>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <xsl:otherwise>
        <input name="{$code}" type="date" class="form-control">
          <xsl:if test="is_read_only = 'true' or (id &gt; 0 and must_edit_once = 'true') or (id &gt; 0 and is_table = 'true')">
            <xsl:attribute name="disabled">
              <xsl:text>disabled</xsl:text>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="is_mandatory = 'true' and not(id &gt; 0 and is_table = 'true' and code='DATE')">
            <xsl:attribute name="required">
              <xsl:text>required</xsl:text>
            </xsl:attribute>
          </xsl:if>
          <xsl:attribute name="value">
            <xsl:value-of select="value"/>
          </xsl:attribute>
        </input>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:apply-templates select="." mode="readonly"/>
  </xsl:template>
  <xsl:template match="data_field_of_sheet" mode="boolean">
    <xsl:variable name="code">
      <xsl:choose>
        <xsl:when test="table">
          <xsl:text>row_</xsl:text>
          <xsl:value-of select="table_code"/>
          <xsl:text>_</xsl:text>
          <xsl:value-of select="code"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="code"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <label class="custom-switch">
      <input name="{$code}" type="checkbox" class="custom-switch-input">
        <xsl:if test="value='true'">
          <xsl:attribute name="checked">checked</xsl:attribute>
        </xsl:if>
      </input>
      <span class="custom-switch-indicator"/>
      <span class="custom-switch-description">
        <xsl:value-of select="name"/>
      </span>
    </label>
  </xsl:template>
  <xsl:template match="data_field_of_sheet" mode="readonly">
    <xsl:variable name="code">
      <xsl:choose>
        <xsl:when test="table">
          <xsl:text>row_</xsl:text>
          <xsl:value-of select="table_code"/>
          <xsl:text>_</xsl:text>
          <xsl:value-of select="code"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="code"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:if test="not(style_id = 'LIST')">
      <xsl:if test="is_read_only = 'true' or (id and must_edit_once = 'true')">
        <input name="{$code}" type="text" class="form-control">
          <xsl:attribute name="hidden">
            <xsl:text>hidden</xsl:text>
          </xsl:attribute>
          <xsl:attribute name="value">
            <xsl:value-of select="value"/>
          </xsl:attribute>
        </input>
      </xsl:if>
    </xsl:if>
  </xsl:template>
  <xsl:template match="data_field_of_sheet">
    <xsl:variable name="colNumber" select="12 div column_view"/>
    <div class="col-sm-12 col-md-{$colNumber}">
      <xsl:if test="table">
        <xsl:attribute name="class">col-sm-12</xsl:attribute>
      </xsl:if>
      <div>
        <xsl:if test="not(table)">
          <xsl:attribute name="class">form-group</xsl:attribute>
        </xsl:if>
        <xsl:choose>
          <xsl:when test="type_id = 'TABLE'">
            <xsl:apply-templates select="." mode="table"/>
          </xsl:when>
          <xsl:when test="type_id = 'NUMBER'">
            <xsl:apply-templates select="." mode="number"/>
          </xsl:when>
          <xsl:when test="type_id = 'STRING'">
            <xsl:apply-templates select="." mode="string"/>
          </xsl:when>
          <xsl:when test="type_id = 'DATE'">
            <xsl:apply-templates select="." mode="date"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="." mode="boolean"/>
          </xsl:otherwise>
        </xsl:choose>
      </div>
    </div>
  </xsl:template>
</xsl:stylesheet>
