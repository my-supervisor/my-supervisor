<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Etendre au parent - Configurer source </xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12" ng-controller="AppCtrl as vm">
           <form class="card" action="/collect/aggregated-model/formular/extended-to-parent-expression/source/save" method="post">
		       <div class="card-header">
		       		<xsl:if test="item and item/id">
			            <h3 class="card-title">Modifier source de l'extension au parent <xsl:value-of select="parent/name"/> - <xsl:value-of select="item/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
			        </xsl:if>
			        <xsl:if test="not(item) or not(item/id)">
			            <h3 class="card-title">Nouvelle source de l'extension au parent <xsl:value-of select="parent/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
			        </xsl:if>
		       </div>
               <div class="card-body">  
                 <xsl:if test="item/id">
                 	<input name="id" type="text" hidden="hidden" value="{item/id}"/>
                 </xsl:if>               	 
               	 <input name="formular_id" hidden="hidden" type="text" value="{formular_id}" />
               	 <input name="model_id" hidden="hidden" type="text" value="{model_id}" />
               	 <input name="expr_id" hidden="hidden" type="text" value="{expr_id}" />        
               	 <input name="reference_id" hidden="hidden" type="text" value="{reference_id}" />  	          	
                 <div class="row">
                   <div class="col-sm-12 col-md-6">
                     <div class="form-group">
                       <label class="form-label">Parent model <span class="form-required">*</span></label>
                       <select name="parent_model_id" class="form-control" 
                       		ng-model="vm.modelId"
                       		ng-change="vm.modelChanged(vm.modelId)">
                       		<xsl:if test="item/id"> 
								<xsl:attribute name="disabled">disabled</xsl:attribute> 
							</xsl:if>
							<xsl:if test="not(item/id)"> 
								<xsl:attribute name="required">required</xsl:attribute> 
							</xsl:if>
                       		<option value="">-- None --</option>
                       		<option ng-repeat="option in vm.models" value="{{{{option.id}}}}">{{option.name}}</option>
                       </select>
                     </div>
                   </div>
                   <div class="col-sm-12 col-md-6">
                     <div class="form-group">
                       <label class="form-label">Parent <span class="form-required">*</span></label>
                       <select name="parent_id" class="form-control" 
                       		required="" 
                       		ng-model="vm.parentId">
                       		<option value="">-- None --</option>
                       		<option ng-repeat="option in vm.fields" value="{{{{option.id}}}}">{{option.name}}</option>
                       </select>
                     </div>
                   </div>                  
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">                    
                    <a role="button" class="btn btn-primary" href="/collect/aggregated-model/formular/extended-to-parent-expression/edit?id={expr_id}&amp;model={model_id}&amp;formular={formular_id}">
	                  	<i class="fa fa-arrow-left"></i> Retour
	                </a>
	                <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>                                    
                  </div>
               	</div>               	
             </form>
        </div>      
	</xsl:template> 
	<xsl:template match="page" mode="customScript">
		<script type="text/javascript">
        <![CDATA[		                    
				var app = angular.module("app", []);			
							
				app.controller("AppCtrl", ["$scope", function ($scope) {
					   var vm = this;
					   
					   vm.models = []]> 
						   <xsl:for-each select="data_sheet_models/data_sheet_model">                             	                     
		                       	{	
		                       		'id': "<xsl:value-of select="id"/>", 
		                       		'name': "<xsl:value-of select="name"/>" + " - " + "<xsl:value-of select="activity"/>",
		                       		'fields': [
		                       			<xsl:variable name="model_id" select="id"/>
		                       			<xsl:for-each select="../../parent_datafields/datafield">
		                       			<xsl:if test="model_id = $model_id">
		                       			{	
				                       		'id': "<xsl:value-of select="id"/>", 
				                       		'name': "<xsl:value-of select="name"/>"
				                       	},
				                       	</xsl:if>			                       	
				                       	</xsl:for-each>
		                       		]
		                       	},
		                   </xsl:for-each>
		                    <![CDATA[];								  
					   
					   vm.modelChanged = function(id){		
					   		vm.fields = [];						   				   					   		
				   			angular.forEach(vm.models, function(model){
					        	if(model.id == id){
					        		vm.fields = model.fields;					        		
					        	}
					        });	
					   }
					   
					   this.$onInit = function(){
							]]> 
							    <xsl:choose>  
							        <xsl:when test="item/id">
							        	vm.modelId = "<xsl:value-of select="item/data_sheet_model_id"/>";
							        	vm.modelChanged(vm.modelId);							        								        										        	
							        	vm.parentId = "<xsl:value-of select="item/field_id"/>";							        			        
							        </xsl:when>
							        <xsl:otherwise>

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