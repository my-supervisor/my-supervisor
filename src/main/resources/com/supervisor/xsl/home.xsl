<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE castlist [
<!ENTITY nbsp '&#160;'>
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:include href="/com/supervisor/xsl/indicators/indicator_options.xsl"/>
  	<xsl:include href="/com/supervisor/xsl/indicators/number_oriented.xsl"/>
  	<xsl:include href="/com/supervisor/xsl/indicators/goal_number.xsl"/>
  	<xsl:include href="/com/supervisor/xsl/indicators/chart_camembert.xsl"/>
  	<xsl:include href="/com/supervisor/xsl/indicators/gauge.xsl"/>
  	<xsl:include href="/com/supervisor/xsl/indicators/dynamic_table_2_col.xsl"/> 
  	<xsl:template match="page" mode="head">  	
  		<link rel="stylesheet" href="/com/webviewer/vendors/angular-gridster/dist/angular-gridster.min.css"/>    
	    <title>
	      <xsl:text>Supervisor - Minlessika - Utiliser de manière efficiente vos ressources</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<xsl:if test="activity_selected">
			<div ng-controller="AppCtrl as vm">
				 <div class="page-header">
				    <div class="col-md-12 px-md-0">
				 		<div class="dropdown pull-left">	
				 		  <a href="javascript:void(0)" data-toggle="dropdown">
			           		<h1 class="page-title"><xsl:value-of select="activity_selected/name" />&nbsp;<span ng-if="vm.online" class="badge badge-success" style="font-size: small">Online</span><span ng-if="!vm.online" class="badge badge-danger" style="font-size: small">Offline</span></h1>				           		           
			          	  </a>				          	  		          	 			                                      	
			              <div class="dropdown-menu dropdown-menu-left">
			              	<a href="javascript:void(0)" ng-click="vm.refresh()" class="dropdown-item pull-left">
			       		  		<i class="dropdown-icon fa fa-refresh"></i> Actualiser
			       		  	</a>
			       		  	<xsl:if test="not(identity/id = activity_selected/owner_id)">
			       		  		<a href="/sharing/subscriber/unshare?type=ACTIVITY&amp;resource={activity_selected/id}" onclick="return confirm('Voulez-vous vous désabonner de cette activité ?');" class="dropdown-item">
				       		  		<i class="dropdown-icon fa fa-share-alt"></i> Se désabonner
				       		  	</a>
			       		  	</xsl:if>
			       		  	<xsl:if test="identity/id = activity_selected/owner_id">
			       		  	
			       		  	    <xsl:if test="(not(activity_selected/designer_id) or identity/id = activity_selected/designer_id) and activity_selected/can_create_indicator = 'true'">
			       		  	    	<a href="/indicator/type?activity={activity_selected/id}&amp;source=activity{activity_selected/id}" class="dropdown-item">
					       		  		<i class="dropdown-icon fa fa-plus"></i> Créer un indicateur
					       		  	</a> 
			       		  	    </xsl:if>
			       		  	    
			       		  		<xsl:if test="not(activity_selected/designer_id) and activity_selected/can_create_template = 'true'">
			       		  	    	<a ng-href="/activity/template/generate?activity={activity_selected/id}" class="dropdown-item" onclick="return confirm('Souhaitez-vous créer un modèle à partir de cette activité ?');">
			         		  			<i class="dropdown-icon fa fa-file"></i> Créer un modèle
			         		  		</a>
			       		  	    </xsl:if>		
			       		  	    <xsl:if test="identity/id = activity_selected/designer_id and activity_selected/template_src_id > 0">
			       		  	    	<a ng-href="/activity/template/release/edit?template={activity_selected/template_src_id}&amp;activity={activity_selected/id}" class="dropdown-item">
			         		  			<i class="dropdown-icon fa fa-refresh"></i> Mettre à jour le modèle
			         		  		</a>
			       		  	    </xsl:if>		       		  	
				       		  	<a href="javascript:void(0)" ng-click="vm.organize()" class="dropdown-item" ng-if="!vm.inOrganizeMode">
		         		  			<i class="dropdown-icon fa fa-first-order"></i> Organiser
		         		  		</a> 
		         		  		<a href="javascript:void(0)" ng-click="vm.saveOrganization()" class="dropdown-item" ng-if="vm.inOrganizeMode">
		         		  			<i class="dropdown-icon fa fa-save"></i> Sauvegarder organisation
		         		  		</a> 
				       		  	<xsl:if test="activity_selected/default_shown = 'false'">
				       		  		<a href="/activity/default?id={activity_selected/id}" class="dropdown-item">
					       		  		<i class="dropdown-icon fa fa-eye"></i> Afficher par défaut
					       		  	</a>
				       		  	</xsl:if>   
				       		  	<a href="/sharing?type=ACTIVITY&amp;resource={activity_selected/id}" class="dropdown-item">
				       		  		<i class="dropdown-icon fa fa-share-alt"></i> Partager
				       		  	</a>  
				       		  	<xsl:if test="activity_selected/can_interacte_activities = 'true' and total_interactions > 0">
				       		  		<a href="/activity/interaction?activity={activity_selected/id}" class="dropdown-item">
					       		  		<i class="dropdown-icon fa fa-share-alt"></i> Interagir avec des activités <span class="tag tag-rounded tag-blue"><xsl:value-of select="active_interactions" /> / <xsl:value-of select="total_interactions" /></span>
					       		  	</a>
				       		  	</xsl:if>				       		  	
				       		</xsl:if>
				       		<!-- 
				       		<div class="dropdown-divider"></div>
				       		<a ng-href="#" ng-click="vm.goToDate()" class="dropdown-item">
			       		  		<i class="dropdown-icon fa fa-calendar"></i> Aller à la date
			       		  	</a>
			       		  	<a ng-href="#" class="dropdown-item">
			       		  		<i class="dropdown-icon fa fa-calendar"></i> Aller à la période précédente
			       		  	</a>
			       		  	<a ng-href="#" class="dropdown-item">
			       		  		<i class="dropdown-icon fa fa-calendar"></i> Aller à la période suivante
			       		  	</a>
				       		 -->				       		
				       		<xsl:if test="identity/id = activity_selected/owner_id">		       		  	    		  	
				       		  	<div class="dropdown-divider"></div>
				       		  	<a href="/activity?id={activity_selected/id}" class="dropdown-item">
				                	<i class="dropdown-icon fa fa-building"></i> Propriétés
				                </a>
				                <xsl:if test="activity_selected/app_owner_name = 'supervisor'">
				                <a href="/activity/delete?id={activity_selected/id}" class="dropdown-item" onclick="return confirm('Voulez-vous supprimer cette activité ? Toutes les données liées seront irrévocablement supprimées !');">
				       		  		<i class="dropdown-icon fe fe-trash"></i> Supprimer
				       		  	</a>
				                </xsl:if>				       		  				               
			       		  	</xsl:if>       		  		              	
			              </div>			              		                            
			           </div>				           
			           	<div class="dropdown pull-right">
			           	      <xsl:if test="identity/id = activity_selected/owner_id and activity_selected/is_up_to_date = 'false'">
				           	      <a href="/activity/update/edit?id={activity_selected/id}" class="btn btn-sm mr-1" style="background:orange; color:white">
							    	<i class="fa fa-exclamation-circle" aria-hidden="true"></i> Mise à jour disponible
							      </a>	
						      </xsl:if>
			           	      <div class="btn-group btn-group-sm mr-1">
								  <button type="button" class="btn btn-primary" ng-click="vm.goToPreviousDate()" ng-disabled="vm.periodSeeking"><i class="fa fa-angle-double-left"></i></button>
								  <button type="button" class="btn btn-info" ng-click="vm.goToDate()" ng-disabled="vm.periodSeeking">{{vm.period.begin}} à {{vm.dateAnalysis ? vm.period.end : "Aujourd'hui"}}</button>
								  <button type="button" class="btn btn-success" ng-click="vm.goToRealTime()" ng-show="vm.dateAnalysis" ng-disabled="vm.periodSeeking">Passer en temps réel</button>
								  <button type="button" class="btn btn-primary" ng-click="vm.goToNextDate()" ng-disabled="vm.periodSeeking"><i class="fa fa-angle-double-right"></i></button>
							  </div>							  			           	     
						      <xsl:if test="count(data_sheet_models/data_sheet_model) > 0 and activity_selected/app_owner_name = 'supervisor'">
								  <div class="dropdown">
								  	  <button type="button" class="btn btn-sm btn-primary dropdown-toggle mr-1" data-toggle="dropdown">
									    Mes formulaires
									  </button>
									  <div class="dropdown-menu dropdown-menu-right">
									    <xsl:for-each select="data_sheet_models/data_sheet_model">
									    	<a target="_blank" class="dropdown-item" href="/collect/sheet/edit?model={id}"><xsl:value-of select="name"/></a>
									    </xsl:for-each>							    
									  </div>
								  </div>								  
							  </xsl:if>
							  <xsl:if test="count(activities_linked/activity) > 0">
								  <div class="dropdown">
								  	  <button type="button" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown">
									    Mes activités liées
									  </button>
									  <div class="dropdown-menu dropdown-menu-right">
									    <xsl:for-each select="activities_linked/activity">
									    	<a target="_blank" class="dropdown-item" href="/home?activity={id}"><xsl:value-of select="name"/></a>
									    </xsl:for-each>							    
									  </div>
								  </div>								  
							  </xsl:if>
						</div>			           				       
				   </div>				 	
				 </div>	         
		         <div gridster="vm.gridsterConfig">
		         	<ul>
				        <li gridster-item="item" ng-repeat="item in vm.indicators" bind-html-compile="item.template"></li>
				    </ul>
		         </div>	
	         </div>	         		       	                 
	         <script type="text/ng-template" id="goToDate.html">
			 	<form ng-submit="vm.takeDate(vm.date, vm.goToRealtime)">
			 		<div class="modal-header">
						<h4 class="modal-title">Aller à la date</h4>
					</div>
					<div class="modal-body">
			             <div class="form-group" ng-if="!vm.goToRealtime">
			               <label for="type">Date <span class="form-required">*</span></label>
			               <input type="date" class="form-control" ng-model="vm.date" placeholder="Saisir une date" required=""/>
			             </div>		
			             <div class="form-group">
	                         <label class="custom-control custom-checkbox">
	                            <input type="checkbox" class="custom-control-input" ng-model="vm.goToRealtime" />
	                            <span class="custom-control-label">Revenir au temps réel ?</span>
	                          </label>
	                      </div>	
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">Choisir</button>
						<button type="button" class="btn btn-danger" ng-click="vm.cancelEdit()">Annuler</button>
					</div>
			 	</form>
			</script>				         
		</xsl:if>
	</xsl:template>  	
	<xsl:template match="page" mode="customScript">
		<xsl:if test="activity_selected">
			<script type="text/javascript">
	         	<![CDATA[
	         	
				if (!Date.prototype.toISODate) {
				  Date.prototype.toISODate = function() {
				    return this.getFullYear() + '-' +
				           ('0'+ (this.getMonth()+1)).slice(-2) + '-' +
				           ('0'+ this.getDate()).slice(-2);
				  }
				}

	         	var app = angular.module("app", ["ui.bootstrap", "indicator.module", "gridster", "angular-bind-html-compile"])
				                 .config(["$provide", function($provide){				               
							        $provide.decorator('$uibModal', function ($delegate) {
							            var open = $delegate.open;
							
							            $delegate.open = function (options) { // avoid closing by backdrop click
							                options = angular.extend(options || {}, {
							                    backdrop: 'static',
							                    keyboard: false
							                });
							
							                return open(options);
							            };
							            return $delegate;
							        });
				                 }]);
	         	]]>
	         </script>	 
	         
	         <xsl:call-template name="number_oriented"/>	
	         <xsl:call-template name="goal_number"/>
	         <xsl:call-template name="chart_camembert"/>
	         <xsl:call-template name="gauge"/>    
	         <xsl:call-template name="dynamic_table_2_col"/>     
	         <script type="text/javascript">
	         	<![CDATA[
			            app.controller('goToDateCtrl', ['data', '$uibModalInstance', 
						function (data, $uibModalInstance, Guid){
							var vm = this;							
							
							vm.date = data.date;
							
							vm.cancelEdit = cancelEdit;
							vm.takeDate = takeDate;						
							
							function takeDate(date, goToRealtime){
							    
							    var now = new Date();
							    now.setHours(0,0,0,0);

								goToRealtime = goToRealtime == true || date.getTime() == now.getTime();

								$uibModalInstance.close(
										{ 
										  realtime: goToRealtime, 
										  date: goToRealtime == true ? undefined : date
										}
								);
							};
							
							function cancelEdit(){
								$uibModalInstance.dismiss();
							}
							   
							this.$onInit = function(){
								if(vm.date)
									vm.goToRealtime = false;							  					  				  							
							}
							
						}]);
			     
					    app.controller("AppCtrl", ["$scope", "$rootScope", "$compile", "$uibModal", "$sce", "$http", function ($scope, $rootScope, $compile, $uibModal, $sce, $http) {
				   			var vm = this;
				   			vm.indicators = [];
				   			vm.online = false;					   		
				   			var toasthub = undefined;
				   			
						    var socket = atmosphere;
						    var subSocket;
							var transport = 'websocket';											
							
							vm.gridsterConfig = {
								columns: 8, // number of columns in the grid
								pushing: true, // whether to push other items out of the way
								floating: false, // whether to automatically float items up so they stack
								swapping: false, // whether or not to have items switch places instead of push down if they are the same size
								width: 'auto', // width of the grid. "auto" will expand the grid to its parent container
								colWidth: 'auto', // width of grid columns. "auto" will divide the width of the grid evenly among the columns
								rowHeight: 'match', // height of grid rows. 'match' will make it the same as the column width, a numeric value will be interpreted as pixels, '/2' is half the column width, '*5' is five times the column width, etc.
								margins: [10, 10], // margins in between grid items
								outerMargin: false,
								sparse: false, // "true" can increase performance of dragging and resizing for big grid (e.g. 20x50)
								isMobile: false, // toggle mobile view
								mobileBreakPoint: 600, // width threshold to toggle mobile mode
								mobileModeEnabled: true, // whether or not to toggle mobile mode when screen width is less than mobileBreakPoint
								minColumns: 1, // minimum amount of columns the grid can scale down to
								minRows: 1, // minimum amount of rows to show if the grid is empty
								maxRows: 100, // maximum amount of rows in the grid
								defaultSizeX: 2, // default width of an item in columns
								defaultSizeY: 1, // default height of an item in rows
								minSizeX: 1, // minimum column width of an item
								maxSizeX: null, // maximum column width of an item
								minSizeY: 1, // minumum row height of an item
								maxSizeY: null, // maximum row height of an item
								saveGridItemCalculatedHeightInMobile: false, // grid item height in mobile display. true- to use the calculated height by sizeY given
								resizable: { // options to pass to resizable handler
									enabled: false,
									handles: ['s', 'e', 'n', 'w', 'se', 'ne', 'sw', 'nw']
								},
								draggable: { // options to pass to draggable handler
									enabled: false,
									scrollSensitivity: 20, // Distance in pixels from the edge of the viewport after which the viewport should scroll, relative to pointer
									scrollSpeed: 15 // Speed at which the window should scroll once the mouse pointer gets within scrollSensitivity distance
								}
							};			
														
							vm.goToDate = goToDate;
							vm.goToPreviousDate = goToPreviousDate;
							vm.goToNextDate = goToNextDate;
							vm.goToRealTime = goToRealTime;
							
							function goToDate(){
								$uibModal.open({
					                templateUrl: 'goToDate.html',
					                controller: 'goToDateCtrl as vm',
					                resolve: {
					                    data: {
					                    	date : vm.dateAnalysis
					                    }
					                }
						            }).result.then(function (config) {
						               if(config.date == vm.dateAnalysis)
						               		return;
						               		
						               if(config.realtime){
						               		vm.dateAnalysis = undefined;
						               }else {
						               		vm.dateAnalysis = config.date;						               								               	
						               }
						               
						               var url = '/activity/period?activity=' + ]]><xsl:value-of select="activity_selected/id" /><![CDATA[;
						               if(vm.dateAnalysis)
						               		url += "&date=" + vm.dateAnalysis.toISODate();
						               		
						               vm.periodSeeking = true;
						               $http.get(url, null)
									         .then(
										         function(success){
										         	vm.period = success.data;
										         	vm.periodSeeking = false;
										         },
										         function(error){
										            vm.periodSeeking = false;
										         	// toastr.error('Période non récupérée suite à une erreur !');
										         }
										     );							               
						               		
						               vm.refresh();
						            }, function () {
						
						            }
					            );
							}
							
							function goToPreviousDate(){
								var url = '/activity/period/previous?activity=' + ]]><xsl:value-of select="activity_selected/id" /><![CDATA[;
						               if(vm.dateAnalysis)
						               		url += "&date=" + vm.dateAnalysis.toISODate();
						               		
						               vm.periodSeeking = true;
						               $http.get(url, null)
									         .then(
										         function(success){
										            vm.periodSeeking = false;
										         	vm.period = success.data;
										         	vm.dateAnalysis = new Date(vm.period.end);
										         	vm.refresh();
										         },
										         function(error){
										         	vm.periodSeeking = false;
										         	// toastr.error('Période non récupérée suite à une erreur !');
										         }
										     );							               						               								              
							}
							
							function goToNextDate(){
								var url = '/activity/period/next?activity=' + ]]><xsl:value-of select="activity_selected/id" /><![CDATA[;
						               if(vm.dateAnalysis)
						               		url += "&date=" + vm.dateAnalysis.toISODate();
						               		
						               vm.periodSeeking = true;
						               $http.get(url, null)
									         .then(
										         function(success){
										         	vm.periodSeeking = false;
										         	vm.period = success.data;
										         	vm.dateAnalysis = new Date(vm.period.today);
										         	vm.refresh();
										         },
										         function(error){
										         	vm.periodSeeking = false;
										         	// toastr.error('Période non récupérée suite à une erreur !');
										         }
										     );							               						               								              
							}
							
							function goToRealTime(){
								var url = '/activity/period?activity=' + ]]><xsl:value-of select="activity_selected/id" /><![CDATA[;
						               		
				               vm.periodSeeking = true;
				               $http.get(url, null)
							         .then(
								         function(success){
								            vm.periodSeeking = false;
								         	vm.period = success.data;
								         	vm.dateAnalysis = undefined;
								         	vm.refresh();
								         },
								         function(error){
								         	vm.periodSeeking = false;
								         	// toastr.error('Période non récupérée suite à une erreur !');
								         }
								     );							               						               								              
							}
							
							vm.organize = function() {
								vm.inOrganizeMode = true;
								vm.gridsterConfig.resizable.enabled = true;
								vm.gridsterConfig.draggable.enabled = true;									
							}
							
							vm.saveOrganization = function(){
							    
							    // enregistrer
							    var locations = [];
							    angular.forEach(vm.indicators, function(indicator){
							    	var loc = {
							    		'id': indicator.id,
							    		'sizeX': indicator.sizeX,
							    		'sizeY': indicator.sizeY,
							    		'row': indicator.row,
							    		'col': indicator.col
							    	}
							    	
							    	locations.push(loc);
							    });
							    
							    $http.post('/activity/organize?activity=' + ]]><xsl:value-of select="activity_selected/id" /><![CDATA[, locations)
							         .then(
								         function(success){
								         	vm.inOrganizeMode = false;
											vm.gridsterConfig.resizable.enabled = false;
											vm.gridsterConfig.draggable.enabled = false;
											
											toastr.success('Activité organisée avec succès !');
								         },
								         function(error){
								         	toastr.error('Activité non organisée suite à une erreur !');
								         }
								     );																								
							}
							
							vm.refresh = function(){
								angular.forEach(vm.indicators, function(indic){
									$rootScope.$broadcast(indic.code, {date: vm.dateAnalysis, showLoader: true});
								});	
							}
							
							$scope.$watch('vm.online', function(newValue, oldValue){

							 	if(newValue){
							 	    if(toasthub){
							 	    	toasthub._close();
							 	    	toasthub = undefined;
							 	    }
						    		
							 		// mettre à jour tous les indicateurs
							 		vm.refresh();
							 	}
							});
								
							// We are now ready to cut the request
						    var request = { url: ']]><xsl:value-of select="ws_bare" /><![CDATA[/ws/activity',
						        contentType : "application/json",
						        logLevel : 'info',
						        transport : transport ,
						        trackMessageLength : true,
						        reconnectInterval : 5000,
						        maxReconnectOnClose: 5,
						        attachHeadersAsQueryString: true,
						        headers: {
						        	activity:]]><xsl:value-of select="activity_selected/id" /><![CDATA[
						        }
						    };
						    
						    request.onOpen = function(response) {
							    								 							    		
							    $scope.$apply(function(){
									vm.online = true;
								});
						    	
						    	// toastr.info('Atmosphere connected using ' + response.transport);
						        transport = response.transport;
					
						        // Carry the UUID. This is required if you want to call subscribe(request) again.
						        request.uuid = response.request.uuid;
						    };
						    
						    request.onClientTimeout = function(r) {
						    	// toastr.info('Client closed the connection after a timeout. Reconnecting in ' + request.reconnectInterval);
						        
						        setTimeout(function (){
						            subSocket = socket.subscribe(request);
						        }, request.reconnectInterval);
						    };
						    
						    request.onReopen = function(response) {
						    	// toastr.info('Atmosphere re-connected using ' + response.transport);
						    	$scope.$apply(function(){
									vm.online = true;
								});
						    };
						    
						    // For demonstration of how you can customize the fallbackTransport using the onTransportFailure function
						    request.onTransportFailure = function(errorMsg, request) {
						        // atmosphere.util.info(errorMsg);
						        request.fallbackTransport = "long-polling";
						        // toastr.info('Atmosphere Chat. Default transport is WebSocket, fallback is ' + request.fallbackTransport);
						    };
						    
						    request.onMessage = function (response) {
						    	// toastr.info('Server sent event');
						    	
						    	// console.log(response);
						        var message = response.responseBody;
						        
						        try {
						            var activity = atmosphere.util.parseJSON(message);
						            // console.log(activity);
						            
						            angular.forEach(activity.indics, function(indic){
										$rootScope.$broadcast(indic.code, {date: vm.dateAnalysis, showLoader: false});
									});							           							           
						            
						        } catch (e) {
						            // console.log('This doesn\'t look like a valid JSON: ', message);
						            return;
						        }
						    };
						    
						    request.onClose = function(response) {
						    	$scope.$apply(function(){
									vm.online = false;
								});
						    };
						    							    
						    request.onError = function(response) {
						    	// console.log(response);
						    	// toastr.info('Sorry, but there\'s some problem with your socket or the server is down');			
						    	
						    	toasthub = new Toast({
											  message: 'Vous êtes actuellement déconnecté du serveur.',
											  type: 'danger',
											  customButtons: [
											    {
											      text: 'Ressayer',
											      onClick: function() {
											        toasthub._close();
											        toasthub = undefined;
											        subSocket = socket.subscribe(request);
											      }
											    }
											  ]
											});	
											
								$scope.$apply(function(){
									vm.online = false;
								});			    								    	
						    };
						    
						    request.onReconnect = function(request, response) {
						        // toastr.info('Connection lost, trying to reconnect. Trying to reconnect ' + request.reconnectInterval);
						    };	
						    
						    $scope.$on("$destroy", function handler() {
						        // destruction code here
								socket.unsubscribe();
						    });													

							function adaptIndicator(indicator){
							
							    var style = "";
							    var clazz;
							    switch(indicator.shortName){
							    	case "number-oriented": 
							    	    clazz = "";								    		
										style = "";
										break;
									case "chart-camembert": 
							    	    clazz = "";	
							    	    style = "";							    		
										break;
									case "gauge": 
							    	    clazz = "";	
							    	    style = "";							    		
										break;
									case "goal-number": 
							    	    clazz = "";	
							    	    style = "";							    		
										break;
							    }
							    
							    var html = String.format('<{0} class="{3}" style="{4}" id="\'{1}\'" code="\'{2}\'"></{0}>', indicator.shortName, indicator.id, indicator.code, clazz, style);
								indicator.template = html;
							}
	
							this.$onInit = function(){
								vm.dateAnalysisName = "Aujourd'hui";
								var indicators = []]> 
					   				<xsl:for-each select="indicators/indicator">                             	                     
				                       	{	'id': <xsl:value-of select="id"/>, 
				                       		'name': "<xsl:value-of select="name"/>",
				                       		'type': "<xsl:value-of select="type"/>",
				                       		'typeId': <xsl:value-of select="type_id"/>,
				                       		'code': "<xsl:value-of select="code"/>",
				                       		'shortName': "<xsl:value-of select="short_name"/>",
				                       		'sizeX': <xsl:value-of select="sizeX"/>,
				                       		'sizeY': <xsl:value-of select="sizeY"/>,
				                       		'row': <xsl:value-of select="row"/>,
				                       		'col': <xsl:value-of select="col"/>
				                       	},
				                   	</xsl:for-each>
					   			<![CDATA[];
					   			
					   			for(var i = 0; i < indicators.length; i++) {
					   				adaptIndicator(indicators[i]);
					   			}	
					   			
					   			vm.indicators = indicators;	   			
																									
								subSocket = socket.subscribe(request);
								
								vm.periodSeeking = true;
								$http.get('/activity/period?activity=' + ]]><xsl:value-of select="activity_selected/id" /><![CDATA[, null)
							         .then(
								         function(success){
								         	vm.period = success.data;
								         	vm.periodSeeking = false;
								         },
								         function(error){
								            vm.periodSeeking = false;
								         	// toastr.error('Période non récupérée suite à une erreur !');
								         }
								     );	
							}								
					 }]);
					
					angular.bootstrap(document, ['app']);					
				]]>
	         </script>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>