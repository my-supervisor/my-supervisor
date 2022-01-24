<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Etendre au model - Configurer source </xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12" ng-controller="AppCtrl as vm">
           <form class="card" action="/collect/aggregated-model/formular/extended-to-model-expression/source/save" method="post">
		       <div class="card-header">
		       		<xsl:if test="item/id">
			            <h3 class="card-title">Modifier source de l'extension au modèle <xsl:value-of select="item/model"/> - <xsl:value-of select="item/name"/> - formule <i><xsl:value-of select="formular"/></i></h3>
			        </xsl:if>
			        <xsl:if test="not(item/id)">
			            <h3 class="card-title">Nouvelle source de l'extension au modèle <xsl:value-of select="item/model"/> - formule <i><xsl:value-of select="formular"/></i></h3>
			        </xsl:if>
		       </div>
               <div class="card-body">  
                 <xsl:if test="item/id">
                 	<input name="id" type="text" hidden="hidden" value="{item/id}"/>
                 </xsl:if>               	 
               	 <input name="formular_id" hidden="hidden" type="text" value="{formular_id}" />
               	 <input name="model_id" hidden="hidden" type="text" value="{model_id}" />
               	 <input name="expr_id" hidden="hidden" type="text" value="{expr_id}" />  	          	
                 <div class="row">
                   <div class="col-sm-12 col-md-12">
                     <div class="form-group">
                       <label class="form-label">Modèle à étendre<span class="form-required">*</span></label>
                       <xsl:choose>
                       		<xsl:when test="item/id">
                       			<input name="model_to_extend_id" hidden="hidden" type="text" value="{item/model_to_extend_id}" /> 
                       			<select class="form-control" 
		                       		required="" 
		                       		ng-model="vm.modelId"
		                       		ng-change="vm.modelChanged(vm.modelId)" disabled="">
		                       		<option value="">-- None --</option>
		                       		<option ng-repeat="option in vm.models" value="{{{{option.id}}}}">{{option.name}}</option>                       		                      	
		                        </select>
                       		</xsl:when>
                       		<xsl:otherwise>
                       			<select name="model_to_extend_id" class="form-control" 
		                       		required="" 
		                       		ng-model="vm.modelId"
		                       		ng-change="vm.modelChanged(vm.modelId)">
		                       		<option value="">-- None --</option>
		                       		<option ng-repeat="option in vm.models" value="{{{{option.id}}}}">{{option.name}}</option>                       		                      	
		                       </select>
                       		</xsl:otherwise>
                       </xsl:choose>
                       
                     </div>
                   </div>                   
                   <div class="col-sm-6 col-md-4">
                     <div class="form-group">
                       <label class="form-label">Champ <span class="form-required">*</span></label>
                       <select name="model_field_id" class="form-control" 
                       		required="" 
                       		ng-model="vm.modelFieldId">
                       		<option value="">-- None --</option>
                       		<option ng-repeat="option in vm.fields" value="{{{{option.id}}}}">{{option.name}}</option>
                       </select>
                     </div>
                   </div>
                   <div class="col-sm-6 col-md-4">
                     <div class="form-group">
                       <label class="form-label">Comparateur <span class="form-required">*</span></label>                       
                       <select name="comparator_id" class="form-control" required="">                         
                         <xsl:variable name="source" select="item" />
                         <xsl:for-each select="comparators/comparator">                             	                     
                         	<option>
                         		<xsl:if test="$source and id = $source/comparator_id"> 
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
                   <div class="col-sm-6 col-md-4">
                     <div class="form-group">
                       <label class="form-label">Référence <span class="form-required">*</span></label>
                       <select name="reference_id" class="form-control" 
                       		required="" 
                       		ng-model="vm.referenceId">
                       		<option value="">-- None --</option>
                       		<option ng-repeat="option in vm.references" value="{{{{option.id}}}}">{{option.name}}</option>
                       </select>
                     </div>
                   </div> 
                   <div class="col-sm-6 col-md-4">
                     <div class="form-group">
                       <label class="form-label">Champ à étendre <span class="form-required">*</span></label>
                       <select name="field_to_extend_id" class="form-control" 
                       		required="" 
                       		ng-model="vm.fieldToExtendId">
                       		<option value="">-- None --</option>
                       		<option ng-repeat="option in vm.fields" value="{{{{option.id}}}}">{{option.name}}</option>
                       </select>
                     </div>
                   </div>                                    
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">                    
                    <a role="button" class="btn btn-primary" href="/collect/aggregated-model/formular/extended-to-model-expression/edit?id={expr_id}&amp;model={model_id}&amp;formular={formular_id}">
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
					   
					   vm.references = []]> 
						   <xsl:for-each select="datafield_references/datafield">                             	                     		                       	
		                       	{	
		                       		'id': "<xsl:value-of select="id"/>", 
		                       		'name': "<xsl:value-of select="name"/>"
		                       	},
		                   </xsl:for-each>
		                    <![CDATA[];	
		                    
					   vm.models = []]> 
						   <xsl:for-each select="data_sheet_models/data_sheet_model">                             	                     
		                       	{	
		                       		'id': "<xsl:value-of select="id"/>", 
		                       		'name': "<xsl:value-of select="full_name"/>",
		                       		'fields': [
		                       			<xsl:variable name="model_id" select="id"/>
		                       			<xsl:for-each select="../../model_datafields/datafield">
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
							        	vm.modelId = "<xsl:value-of select="item/model_to_extend_id"/>";
							        	vm.modelFieldId = "<xsl:value-of select="item/model_field_id"/>";
							        	vm.fieldToExtendId = "<xsl:value-of select="item/field_to_extend_id"/>";
							        	vm.modelChanged(vm.modelId);							        								        										        	
							        	vm.referenceId = "<xsl:value-of select="item/reference_id"/>";							        			        
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