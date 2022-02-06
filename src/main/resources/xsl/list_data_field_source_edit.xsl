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
      <xsl:text>MySupervisor - Configurer une source de données</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12" ng-controller="AppCtrl as vm">
      <form class="card" action="/collect/field/list/source/save" method="post">
        <xsl:if test="item and item/id">
          <xsl:attribute name="action">
            <xsl:text>/collect/field/list/source/save?id=</xsl:text>
            <xsl:value-of select="item/id"/>
          </xsl:attribute>
        </xsl:if>
        <div class="card-header">
          <xsl:choose>
            <xsl:when test="item/id">
              <h3 class="card-title"><xsl:text>Modifier </xsl:text><i><xsl:value-of select="item/name"/></i> - <xsl:value-of select="model/name"/></h3>
            </xsl:when>
            <xsl:otherwise>
              <xsl:choose>
                <xsl:when test="table">
                  <h3 class="card-title">Nouvelle source de données - <xsl:value-of select="table/name"/></h3>
                </xsl:when>
                <xsl:otherwise>
                  <h3 class="card-title">Nouvelle source de données - <xsl:value-of select="model/name"/></h3>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:otherwise>
          </xsl:choose>
          <div class="card-options">
            <xsl:choose>
              <xsl:when test="table">
                <a role="button" class="btn btn-primary" href="/collect/field/list/edit?id={field/id}&amp;model={model/id}&amp;table={table/id}&amp;table_model={table/model_id}"><i class="fa fa-arrow-left"/> Retour
			                </a>
              </xsl:when>
              <xsl:otherwise>
                <a role="button" class="btn btn-primary" href="/collect/field/list/edit?id={field/id}&amp;model={model/id}"><i class="fa fa-arrow-left"/> Retour
			                </a>
              </xsl:otherwise>
            </xsl:choose>
          </div>
        </div>
        <div class="card-body">
          <input hidden="hidden" name="model_id" value="{model/id}"/>
          <input hidden="hidden" name="field_id" value="{field/id}"/>
          <xsl:if test="table">
            <input hidden="hidden" name="table_id" value="{table/id}"/>
            <input hidden="hidden" name="table_model_id" value="{table/model_id}"/>
          </xsl:if>
          <div class="row">
            <div class="col-sm-12 col-md-12">
              <div class="form-group">
                <label class="form-label">Modèle <span class="form-required">*</span></label>
                <select name="list_model_id" class="form-control" required="" ng-model="vm.modelId" ng-change="vm.modelChanged(vm.modelId)">
                  <option value="">-- None --</option>
                  <option ng-repeat="option in vm.models" value="{{{{option.id}}}}">{{option.name}}</option>
                </select>
              </div>
            </div>
            <div class="col-sm-6 col-md-3">
              <div class="form-group">
                <label class="form-label">Field to display <span class="form-required">*</span></label>
                <select name="field_to_display_id" class="form-control" required="" ng-model="vm.fieldToDisplayId">
                  <option value="">-- None --</option>
                  <option ng-repeat="option in vm.fieldsToDisplay" value="{{{{option.id}}}}">{{option.name}}</option>
                </select>
              </div>
            </div>
            <div class="col-sm-6 col-md-3">
              <div class="form-group">
                <label class="form-label">Order field <span class="form-required">*</span></label>
                <select name="order_field_id" class="form-control" required="" ng-model="vm.orderFieldId">
                  <option value="">-- None --</option>
                  <option ng-repeat="option in vm.selectedModel.fields" value="{{{{option.id}}}}">{{option.name}}</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="card-footer text-right">
          <div class="d-flex">
            <xsl:choose>
              <xsl:when test="table">
                <a href="/collect/field/list/edit?id={field/id}&amp;model={model/id}&amp;table={table/id}&amp;table_model={table/model_id}" class="btn btn-link">Annuler</a>
              </xsl:when>
              <xsl:otherwise>
                <a href="/collect/field/list/edit?id={field/id}&amp;model={model/id}" class="btn btn-link">Annuler</a>
              </xsl:otherwise>
            </xsl:choose>
            <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
          </div>
        </div>
      </form>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript">
    <script type="text/javascript"><![CDATA[		                    
				var app = angular.module("app", []);			
							
				app.controller("AppCtrl", ["$scope", function ($scope) {
					   var vm = this;
					   
					   vm.models = []]><xsl:for-each select="data_models/data_model"><xsl:if test="not(id = ../../model/id)">
		                       		{	
			                       		'id': "<xsl:value-of select="id"/>", 
			                       		'name': "<xsl:value-of select="name"/>" + " - " + "<xsl:value-of select="activity"/>",
			                       		'fields': [
			                       			<xsl:variable name="model_id" select="id"/>
			                       			<xsl:for-each select="../../datafields/datafield"><xsl:if test="model_id = $model_id">
			                       			{	
					                       		'id': "<xsl:value-of select="id"/>", 
					                       		'name': "<xsl:value-of select="name"/>",
					                       		'typeId': "<xsl:value-of select="type_id"/>"
					                       	},
					                       	</xsl:if></xsl:for-each>
			                       		]
			                       	},
		                       	</xsl:if></xsl:for-each><![CDATA[];
								    
					   vm.modelChanged = function(id){					   					   		
					   		vm.fieldsToDisplay = [];
					   		
					        angular.forEach(vm.models, function(model){
					        	if(model.id == id){
					        		vm.selectedModel = model;					        		
									refreshFieldsToDisplay(vm.typeId, vm.selectedModel);
					        	}
					        });					   		
					   }
					   
					   vm.typeChanged = function(id){
					   		
					   		vm.fieldsToDisplay = [];
					   
					   		refreshFieldsToDisplay(id, vm.selectedModel);				   		
					   }
					   
					   function refreshFieldsToDisplay(typeId, model){
					   		vm.fieldsToDisplay = [];
					   		
					   		if(!model)
					   			return;
					   			
					        angular.forEach(model.fields, function(field){
					        	if(field.typeId == typeId){
					        		vm.fieldsToDisplay.push(field); 
					        	}
					        });
					   }
					   
					   this.$onInit = function(){
							]]> 
							    vm.typeId = "<xsl:value-of select="field/type_id"/>";
							    <xsl:choose><xsl:when test="item/id">
							        	vm.modelId = "<xsl:value-of select="item/list_model_id"/>";
							        	vm.modelChanged(vm.modelId);							        								        										        	
							        	vm.typeChanged(vm.typeId);
							        	vm.fieldToDisplayId = "<xsl:value-of select="item/field_to_display_id"/>";
							        	vm.orderFieldId = "<xsl:value-of select="item/order_field_id"/>";							        			        
							        </xsl:when><xsl:otherwise>

							        </xsl:otherwise></xsl:choose>
		                    <![CDATA[
					   };
			    }]);	
				
				angular.bootstrap(document, ['app']);			
        ]]>
        </script>
  </xsl:template>
</xsl:stylesheet>
