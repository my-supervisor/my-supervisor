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
      <xsl:text>MySupervisor - Configurer une expression étendue à l'enfant</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12" ng-controller="AppCtrl as vm">
      <form class="card" action="/collect/aggregated-model/formular/extended-to-child-expression/save" method="post">
        <div class="card-header">
          <xsl:if test="item and item/id">
            <h3 class="card-title">Modifier expression étendue à l'enfant <xsl:value-of select="parent/name"/> - <xsl:value-of select="item/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
          </xsl:if>
          <xsl:if test="not(item) or not(item/id)">
            <h3 class="card-title">Nouvelle expression étendue à l'enfant <xsl:value-of select="parent/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
          </xsl:if>
        </div>
        <div class="card-body">
          <xsl:if test="item/id">
            <input name="id" type="text" hidden="hidden" value="{item/id}"/>
          </xsl:if>
          <input name="formular_id" hidden="hidden" type="text" value="{formular_id}"/>
          <input name="model_id" hidden="hidden" type="text" value="{model_id}"/>
          <div class="row">
            <div class="col-sm-6 col-md-4">
              <div class="form-group">
                <label class="form-label">Modèle enfant <span class="form-required">*</span></label>
                <select name="child_model_id" class="form-control" required="" ng-model="vm.modelId" ng-change="vm.modelChanged(vm.modelId)">
                  <option value="">-- None --</option>
                  <option ng-repeat="option in vm.models" value="{{{{option.id}}}}">{{option.name}}</option>
                </select>
              </div>
            </div>
            <div class="col-sm-6 col-md-4">
              <div class="form-group">
                <label class="form-label">Champ enfant <span class="form-required">*</span></label>
                <select name="child_id" class="form-control" required="" ng-model="vm.childId">
                  <option value="">-- None --</option>
                  <option ng-repeat="option in vm.selectedModel.fields" value="{{{{option.id}}}}">{{option.name}}</option>
                </select>
              </div>
            </div>
            <div class="col-sm-6 col-md-4">
              <div class="form-group">
                <label class="form-label">Agrégat <span class="form-required">*</span></label>
                <select name="aggregate_id" class="form-control" required="">
                  <xsl:variable name="expression" select="item"/>
                  <xsl:for-each select="aggregate_funcs/aggregate_func">
                    <option>
                      <xsl:if test="$expression and id = $expression/aggregate_id">
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
          </div>
        </div>
        <div class="card-footer text-right">
          <div class="d-flex">
            <a role="button" class="btn btn-primary" href="/collect/aggregated-model/formular/edit?id={formular_id}&amp;model={model_id}"><i class="fa fa-arrow-left"/> Retour
	                </a>
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
					   
					   vm.models = []]><xsl:for-each select="data_sheet_models/data_sheet_model">                             	                     
		                       	{	
		                       		'id': "<xsl:value-of select="id"/>", 
		                       		'name': "<xsl:value-of select="name"/>",
		                       		'fields': [
		                       			<xsl:variable name="model_id" select="id"/>
		                       			<xsl:for-each select="../../datafields/datafield"><xsl:if test="model_id = $model_id">
		                       			{	
				                       		'id': "<xsl:value-of select="id"/>", 
				                       		'name': "<xsl:value-of select="name"/>"
				                       	},
				                       	</xsl:if></xsl:for-each>
		                       		]
		                       	},
		                   </xsl:for-each><![CDATA[];
								    
					   vm.modelChanged = function(id){					   					   		
					        angular.forEach(vm.models, function(model){
					        	if(model.id == id){
					        		vm.selectedModel = model;					        		
					        	}
					        });					   		
					   }
					   
					   this.$onInit = function(){
							]]><xsl:choose><xsl:when test="item/id">
							        	vm.modelId = "<xsl:value-of select="item/model_id"/>";
							        	vm.modelChanged(vm.modelId);							        								        										        	
							        	vm.childId = "<xsl:value-of select="item/child_id"/>";						        			        
							        </xsl:when><xsl:otherwise>

							        </xsl:otherwise></xsl:choose><![CDATA[
					   };
			    }]);	
				
				angular.bootstrap(document, ['app']);			
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
