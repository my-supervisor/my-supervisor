<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:include href="/xsla_usage_rule.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer un gauge</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/gauge-setting/save?shortname=gauge&amp;source={source}" method="post" ng-controller="AppController as vm">
               <xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/gauge-setting/save?shortname=gauge&amp;source=</xsl:text><xsl:value-of select="source"/><xsl:text>&amp;id=</xsl:text><xsl:value-of select="item/id"/>
		            </xsl:attribute>
		        </xsl:if>
		       <div class="card-header">
		       		<xsl:if test="item and item/id">
			            <h3 class="card-title">Modifier <xsl:value-of select="item/name"/></h3>
			        </xsl:if>
			        <xsl:if test="not(item) or not(item/id)">
			            <h3 class="card-title">Créer <xsl:value-of select="chart_name"/></h3>
			        </xsl:if>
			        <div class="card-options">
	                   <xsl:if test="item and item/id">
	                    	<a ng-if="vm.periodicityState == 'removed'" href="javascript:void(0)" role="button" class="btn btn-primary" ng-click="vm.addPeriodicity()">
			                  	<i class="fa fa-plus"></i> Ajouter périodicité
			                </a>
			                <a ng-if="vm.periodicityState == 'added'" href="javascript:void(0)" role="button" class="btn btn-primary" ng-click="vm.removePeriodicity()">
			                  	<i class="fa fa-minus"></i> Supprimer périodicité
			                </a>
	                    </xsl:if>
	                </div>
		       </div>
               <div class="card-body">                
		        <input type="text" name="activity_id" hidden="hidden" value="{activity/id}"/>
                 <div class="row">
                   <xsl:if test="item/is_template = 'true'">
                   		<div class="col-md-12">
	                      	<div class="form-group">
		                        <label class="form-label">Libellé <span class="form-required">*</span></label>
		                        <input name="name" type="text" class="form-control" placeholder="Saisir un nom de modèle" required="">
		                        	<xsl:if test="item">
							            <xsl:attribute name="value">
							              <xsl:value-of select="item/name"/>
							            </xsl:attribute>
							        </xsl:if>
		                        </input>
		                    </div>
	                   </div>
	                   <div class="col-md-12">
	                      	<div class="form-group">
		                        <label class="form-label">Etiquettes <span class="form-required">*</span></label>
		                        <input id="input-tags" name="tags" type="text" class="form-control" placeholder="Saisir des tags" required="">
		                        	<xsl:if test="item">
							            <xsl:attribute name="value">
							              <xsl:value-of select="item/tags"/>
							            </xsl:attribute>
							        </xsl:if>
		                        </input>
		                    </div>
	                   </div>
                   </xsl:if>
                   <div class="col-sm-6 col-md-4">
                     <div class="form-group">
                       <label class="form-label">Titre <span class="form-required">*</span></label>
                       <input name="label" type="text" class="form-control" placeholder="Saisir un nom" required="">
                       		<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/label"/>
					            </xsl:attribute>
					        </xsl:if>
                       </input>
                     </div>
                   </div>
                   <div class="col-sm-6 col-md-4">
                     <div class="form-group">
                       <label class="form-label">Type de gauge <span class="form-required">*</span></label>
                       <select name="gauge_type_id" class="form-control" required="">                         
                         <xsl:variable name="item" select="item" />
                         <xsl:for-each select="gauge_types/gauge_type">                             	                     
                         	<option>
                         		<xsl:if test="$item and id = $item/gauge_type_id"> 
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
                   <div class="col-sm-4 col-md-2">
                     <div class="form-group">
                       <label class="form-label">Valeur minimale <span class="form-required">*</span></label>
                       <input name="min" type="number" class="form-control" placeholder="Saisir un nombre" required="">
                       		<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/min"/>
					            </xsl:attribute>
					        </xsl:if>
                       </input>
                     </div>
                   </div>
                   <div class="col-sm-4 col-md-2">
                     <div class="form-group">
                       <label class="form-label">Valeur maximale <span class="form-required">*</span></label>
                       <input name="max" type="number" class="form-control" placeholder="Saisir un nombre" required="">
                       		<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/max"/>
					            </xsl:attribute>
					        </xsl:if>
                       </input>
                     </div>
                   </div>
                   <div class="col-sm-4 col-md-2">
                     <div class="form-group">
                       <label class="form-label">Division majeure <span class="form-required">*</span></label>
                       <input name="major_ticks" type="number" class="form-control" placeholder="Saisir un nombre" required="" value="10">
                       		<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/major_ticks"/>
					            </xsl:attribute>
					        </xsl:if>
                       </input>
                     </div>
                   </div>
                   <div class="col-sm-4 col-md-2">
                     <div class="form-group">
                       <label class="form-label">Division mineure <span class="form-required">*</span></label>
                       <input name="minor_ticks" type="number" class="form-control" placeholder="Saisir un nombre" required="" value="2">
                       		<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/minor_ticks"/>
					            </xsl:attribute>
					        </xsl:if>
                       </input>
                     </div>
                   </div>
                   <div class="col-sm-6 col-md-4">
                     <div class="form-group">
                       <label class="form-label">Symbole unité</label>
                       <input name="unity_symbol" type="text" class="form-control" placeholder="Saisir un symbole">
                       		<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/unity_symbol"/>
					            </xsl:attribute>
					        </xsl:if>
                       </input>
                     </div>
                   </div>
                   <div class="col-sm-6 col-md-4">
                   		<div class="form-group">
	                     <label class="form-label">Position du symbole</label>
	                     <div class="selectgroup w-100">
	                       <label class="selectgroup-item">
	                         <input type="radio" name="symbol_position" value="LEFT" class="selectgroup-input">
	                         	<xsl:if test="item and item/symbol_position='LEFT'">
						            <xsl:attribute name="checked">
						              <xsl:text>checked</xsl:text>
						            </xsl:attribute>
						        </xsl:if>
	                         </input>
	                         <span class="selectgroup-button">Gauche</span>
	                       </label>
	                       <label class="selectgroup-item">
	                         <input type="radio" name="symbol_position" value="RIGHT" class="selectgroup-input">
	                         	<xsl:if test="not(item) or (item and item/symbol_position='RIGHT')">
						            <xsl:attribute name="checked">
						              <xsl:text>checked</xsl:text>
						            </xsl:attribute>
						        </xsl:if>
	                         </input>
	                         <span class="selectgroup-button">Droite</span>
	                       </label>
	                     </div>
	                   </div>
                   </div>                                                   
                  </div>
                  <xsl:if test="item/id">
                   		<div class="row" ng-if="vm.periodicityState == 'added'">
	                   		<div class="col-sm-6 col-md-3">
		                      <div class="form-group">
		                        <input hidden="" type="text" name="periodicity_state" ng-model="vm.periodicityState" />
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
	                   </div>
                   </xsl:if>
                   <xsl:call-template name="indicator_dynamic_param_list">
	                	<xsl:with-param name="indic_id" select="item/id"/>
	                	<xsl:with-param name="is_indicator_template" select="item/is_template"/>
	                	<xsl:with-param name="indicator_dynamic_params" select="indicator_dynamic_params"/>
	                	<xsl:with-param name="source" select="source" />
	                	<xsl:with-param name="short_name" select="item/short_name" />
	               </xsl:call-template>
                   <xsl:call-template name="data_usage_rule_list">
	                	<xsl:with-param name="indic_id" select="item/id"/>
	                	<xsl:with-param name="is_indicator_template" select="item/is_template"/>
	                	<xsl:with-param name="data_usage_rules" select="data_links"/>
	                	<xsl:with-param name="source" select="source"/>
	               </xsl:call-template>
                   <div class="row">
                   		<div class="col-md-12">
		                     <div class="form-group">
		                       <label class="form-label">Description</label>
		                       <textarea name="description" rows="2" class="form-control" placeholder="Faire une description...">
		                       		<xsl:if test="item">
							            <xsl:value-of select="item/description"/>
							        </xsl:if>
		                       </textarea>
		                      </div>
		                    </div> 
		                    <div class="col-md-12 mb-0">
		                    	<label class="form-label">Zones (<a href="javascript:void(0)" ng-click="vm.addZone()"><i class="fa fa-plus"></i> Nouvelle zone</a>)</label>
		                    	<div class="table-responsive form-control">
				                    <table class="table card-table table-vcenter text-nowrap">
				                      <thead>
				                        <tr>
				                          <th class="w-1">N°</th>
				                          <th>Couleur</th>
				                          <th>Minimum</th>
				                          <th>Maximum</th>
				                          <th></th>
				                        </tr>
				                      </thead>
				                      <tbody>		                        
				                        <tr ng-repeat="zone in vm.zones" ng-hide="zone.state == 'removed'">
				                          <td>
				                          	<span class="text-muted">{{$index + 1}}</span>
				                          	<input hidden="" type="text" name="zone_id" ng-model="zone.id" />
				                          	<input hidden="" type="text" name="zone_color_id" ng-model="zone.colorId" />
				                          	<input hidden="" type="text" name="zone_min" ng-model="zone.min" />
				                          	<input hidden="" type="text" name="zone_max" ng-model="zone.max" />
				                          	<input hidden="" type="text" name="zone_edit_state" ng-model="zone.state" />
				                          </td>
				                          <td>		                            
				                            <select class="form-control" 
					                            required=""
					                            ng-model="zone.color"
					                            ng-change="vm.colorChanged($index)"
					                            ng-options="v as v.name for v in vm.colors">                                               
						                     </select>
				                          </td>
				                          <td>
				                            <input type="number" ng-model="zone.min" ng-change="vm.zoneChanged($index)" class="form-control" placeholder="Saisir un nombre" required=""></input>
				                          </td>		                          
				                          <td>
				                          	<input type="number" ng-model="zone.max" ng-change="vm.zoneChanged($index)" class="form-control" placeholder="Saisir un nombre" required=""></input>
				                          </td>	
				                          <td>
						                       <a class="icon" ng-click="vm.removeZone($index)">
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
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="{cancel_url}" class="btn btn-link">Annuler</a>                  
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>
             </form>
        </div>        
	</xsl:template>  		
	<xsl:template match="page" mode="customScript">
		<script type="text/javascript">
        <![CDATA[		
            $(document).ready(function () {
                        $('#input-tags').selectize({
                            delimiter: ',',
                            persist: false,
                            create: function (input) {
                                return {
                                    value: input,
                                    text: input
                                }
                            }
                        });
                    });
                    
			var app = angular.module("app", [])
			                 .config(["$provide", function($provide){
						        
			                 }]);			

			app.controller("AppController", ["$scope", function ($scope) {
				   var vm = this;
				   		
	               vm.colors = []]> 
					   <xsl:for-each select="colors/color">                             	                     
	                       	{	'id': "<xsl:value-of select="id"/>", 
	                       		'name': "<xsl:value-of select="name"/>"
	                       	},
	                   </xsl:for-each>
	                    <![CDATA[];
				   
				   vm.addZone = function(){
				   		var zone = { state: 'added' };
				   		vm.zones.push(zone);
				   }
				   
				   vm.removeZone = function(index){	
				   		var zone = vm.zones[index];
				   		if(zone.state == "added")
				   			vm.zones.splice(index, 1);
				   		else
				   			zone.state = "removed";
				   }
				   
				   vm.zoneChanged = function(index){
				   		var zone = vm.zones[index];
				   		if(zone.state != "added")
			   			{
			   				zone.state = "modified";	
			   			}				   	
				   }
				   
				   vm.colorChanged = function(index){
				   		vm.zones[index].colorId = vm.zones[index].color.id;
				   		vm.zoneChanged(index);
				   }
				   
				   vm.addPeriodicity = function(){
			   			vm.periodicityState = "added";
				   }	
				   
				   vm.removePeriodicity = function(){
				   	    vm.periodicityState = "removed";
				   }
				   
				   this.$onInit = function(){
						]]> 
		                   <xsl:if test="not(item) or not(item/id)">
		                   		<![CDATA[
		                   		vm.zones = [];
		                   		vm.addZone();
		                   		]]>
		                   </xsl:if>		
		                   
		                   <xsl:if test="item and item/id">
		                   		
		                   		<xsl:choose>
				                   	<xsl:when test="item/has_periodicity = 'true'">
				                   		vm.periodicityState = "added";
				                   	</xsl:when>
				                   	<xsl:otherwise>
				                   		vm.periodicityState = "removed";
				                   	</xsl:otherwise>
			                    </xsl:choose>
			                   
		                        <![CDATA[
		                        vm.zones = []]> 
				                   <xsl:for-each select="gauge_zones/gauge_zone">
				                   		{	'id': <xsl:value-of select="id"/>, 
				                       		'min': <xsl:value-of select="min"/>,
				                       		'max': <xsl:value-of select="max"/>,
				                       		'colorId': "<xsl:value-of select="color_id"/>"						                       		
				                       	},
				                   </xsl:for-each>						   
			                    <![CDATA[];
	                    
		                   		angular.forEach(vm.zones, function(zone){
		                   			angular.forEach(vm.colors, function(color){
		                   				if(zone.colorId == color.id)
		                   					zone.color = color;
		                   			});			                   			
		                   		});
		                   		]]>
		                   </xsl:if>				   
	                    <![CDATA[
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);			
        ]]>
        </script>
	</xsl:template>
</xsl:stylesheet>