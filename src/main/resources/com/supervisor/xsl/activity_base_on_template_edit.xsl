<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Générer une activité à partir d'un modèle</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12" app="app">
           <form class="card" action="/activity/template/create-activity/save" method="post" ng-controller="AppController as vm">
		        <div class="card-header">
		        	<h3 class="card-title">Créer une activité</h3>
		        </div>
               <div class="card-body">                
                 <div class="row">
                       <input hidden="hidden" type="text" name="id" value="{item/id}"/>	
	                   <div class="col-md-12">
		                     <div class="form-group">
		                       <label class="form-label">Libellé <span class="form-required">*</span></label>
		                       <input name="name" type="text" class="form-control" placeholder="Saisir un nom" required="">
		                       		<xsl:attribute name="value">
						              <xsl:value-of select="item/name"/>
						            </xsl:attribute>
		                       </input>
		                     </div>	                     
	                    </div>
                   		<div class="col-sm-6 col-md-4">
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
	                   	 <div class="col-sm-6 col-md-4">
		                     <div class="form-group">
		                       <label class="form-label">Unité de périodicité<span class="form-required">*</span></label>
		                       <select name="periodicity_unit_id" class="form-control custom-select" required="">                         
		                         <xsl:variable name="item" select="item" />
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
		                 <div class="col-sm-6 col-md-4">
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
	                   <xsl:if test="count(activity_params/activity_param) > 0">
                       <div class="col-md-12 mb-0">
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
				                        </tr>
				                      </thead>
				                      <tbody>	
				                        <tr ng-repeat="param in vm.activityParams">
				                          <td>
				                          	<span class="text-muted">{{$index + 1}}</span>
				                          	<input hidden="" type="text" name="param_index" ng-model="$index" />
				                          	<input hidden="" type="text" name="param_id" ng-model="param.id" />
				                          	<input hidden="" type="text" name="param_model_id" ng-model="param.modelId" />
				                          	<input hidden="" type="text" name="param_code" ng-model="param.code" />
				                          	<input hidden="" type="text" name="param_state" ng-model="param.state" />
				                          </td>
				                          <td>		                           
				                            {{param.name}}			                     
				                          </td>			                          
				                          <td>
				                          	{{param.type}}
				                          </td>	
				                          <td>
				                          	<input name="param_value" ng-model="param.value" ng-change="vm.activityParamChanged($index)" step="any" type="{{{{vm.adaptTypeOfField(param.typeId)}}}}" class="form-control" placeholder="Saisir une valeur" required=""></input>
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
                    <a href="/activity/template" class="btn btn-link">Annuler</a>                  
                    <button type="submit" class="btn btn-primary ml-auto">Créer</button>
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
					   
					   
					   vm.activityParamChanged = function(index){
					   		vm.activityParams[index].state = "modified";
					   }		   
					   
					   this.$onInit = function(){					   			

				            vm.activityParams = []]> 
			                       <xsl:for-each select="activity_params/activity_param">                             	                     
				                       	{	'id': <xsl:value-of select="id"/>, 
				                       		'name': "<xsl:value-of select="name"/>",
				                       		'code': "<xsl:value-of select="code"/>",
				                       		'modelId': <xsl:value-of select="model_id"/>,
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
					            	                   		               
					   };
			    }]);	
				
				angular.bootstrap(document, ['app']);			
        ]]>
        </script>
	</xsl:template>	
</xsl:stylesheet>