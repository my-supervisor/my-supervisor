<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:include href="/com/supervisor/xsl/indicators/data_usage_rule.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer un tableau dynamique à 2 colonnes</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/dynamic-table-2-col-setting/save?shortname=dynamic-table-2-col&amp;source={source}" method="post" ng-controller="AppController as vm">
               <xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/dynamic-table-2-col-setting/save?shortname=dynamic-table-2-col&amp;source=</xsl:text><xsl:value-of select="source"/><xsl:text>&amp;id=</xsl:text><xsl:value-of select="item/id"/>
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
                       <label class="form-label">Mode de classement <span class="form-required">*</span></label>
                       <select name="ordering_mode_id" class="form-control" required="">                         
                         <xsl:variable name="item" select="item" />
                         <xsl:for-each select="ordering_modes/ordering_mode">                             	                     
                         	<option>
                         		<xsl:if test="$item and id = $item/ordering_mode_id"> 
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
                        <label class="form-label">Nombre maxi d'éléments à afficher <span class="form-required">*</span></label>
                        <input name="nb_max_of_elements_to_show" step="0" type="number" class="form-control" placeholder="Saisir un entier" required="">
                        	<xsl:if test="item">
					            <xsl:attribute name="value">
					              <xsl:value-of select="item/nb_max_of_elements_to_show"/>
					            </xsl:attribute>
					        </xsl:if>
                        </input>
                      </div>
                   </div>                                                                                           
                  </div>
                  <div class="row">
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
                   <div class="col-md-4">
	                   	<div class="form-group">
	                   	   <label class="form-label">Evolution périodique</label>
	                       <label class="custom-switch">
	                         <input name="manage_evolution_percent" type="checkbox" class="custom-switch-input">
	                         	<xsl:if test="not(item) or (item and item/manage_evolution_percent='true')"> 
								<xsl:attribute name="checked">checked</xsl:attribute> 
							</xsl:if>
	                         </input>
	                         <span class="custom-switch-indicator"></span>
	                         <span class="custom-switch-description">Gérer pourcentage d'évolution</span>
	                       </label>
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
	                       <textarea name="description" rows="5" class="form-control" placeholder="Faire une description...">
	                       		<xsl:if test="item">
						            <xsl:value-of select="item/description"/>
						        </xsl:if>
	                       </textarea>
	                      </div>
	                    </div>
                   </div>
                  <div>
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
		
				app.controller("AppController", ["$scope", "$http", function ($scope, $http) {
					   var vm = this;
					   
					   vm.addPeriodicity = function(){
				   			vm.periodicityState = "added";
					   }	
					   
					   vm.removePeriodicity = function(){
					   	    vm.periodicityState = "removed";
					   }
					   
					   this.$onInit = function(){
				                   	   
							]]> 

			                   <xsl:if test="item and item/id">
			                   
			                   <xsl:choose>
			                   	<xsl:when test="item/has_periodicity = 'true'">
			                   		vm.periodicityState = "added";
			                   	</xsl:when>
			                   	<xsl:otherwise>
			                   		vm.periodicityState = "removed";
			                   	</xsl:otherwise>
			                   </xsl:choose>				                   			                      			                   					                   				                   					                  			                   				                   					                 	                   							                   
			                   		                  		
			                   </xsl:if>				   
		                    <![CDATA[		                   		                    
					   };
			    }]);	
				
				angular.bootstrap(document, ['app']);			
	        ]]>
	     </script> 
	</xsl:template>
</xsl:stylesheet>