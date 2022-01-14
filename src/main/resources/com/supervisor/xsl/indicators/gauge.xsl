<?xml version="1.0"?>
<xsl:stylesheet 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:interactive="http://www.minlessika.com/Gauge"
	version="2.0">
	
	<xsl:template name="gauge">
   		<script type="text/ng-template" id="gauge.html">
   			<div class="card" style="height: 100%; margin-bottom:0;">
   			    <div class="card-header">
                	<h3 class="card-title">{{item.label}}</h3>
                	<div class="card-options">		                 
	            		<div class="text-right">
			                <xsl:call-template name="indicator_options">
			                	<xsl:with-param name="identity" select="identity"/>
			                	<xsl:with-param name="activity_selected" select="activity_selected"/>
			                	<xsl:with-param name="shortname" select="'gauge'"/>
			                </xsl:call-template>
		            	</div>			                
         			</div>
                </div>	     	                        
	            <div class="card-body d-flex align-items-center justify-content-center text-center">	
	              <div class="dimmer" ng-class="active">
		              	<div class="loader"></div>
		              	<div class="dimmer-content d-flex align-items-center">
		              		<div class="col-sm-12">
			              		<![CDATA[ 
					              	<canvas id="gauge-{{id}}"></canvas>      
					            ]]>
			                </div>
		              	</div>
	              </div>                    	        	              	           
	            </div>
	          </div>
   		</script>
   		<script type="text/javascript">
        	<![CDATA[		    		    	    		 
	    			(function(app){
					   'use strict';						
						
					    app.directive('gauge', gauge);
					
					    gauge.$inject = ["$http", "$document"]; 
					    function gauge($http, $document) {
					        return {
					            restrict: 'EA',
					            scope: {
							      code: '=',
							      id: '='
							    },
					            templateUrl: "gauge.html",
					            link: function (scope, element, attrs) {
					            	var gauge;

					            	scope.$on(scope.code, function(event, args) {
										var url = '/indicator/gauge?id=' + scope.id + '&activity=' + ]]><xsl:value-of select="activity_selected/id"/><![CDATA[;
										
										if(args.date){										    
											url = url + "&date=" + args.date.toISOString().substring(0, 10);
										}																						
											
										scope.loadingData = args.showLoader;
									    $http.get(url, {}, null)
					        			     .then(
					        					function(result){				
					        					    scope.loadingData = false;						        						
					        			            scope.item = result.data;
					        			            draw();
					        				 	},
					        				 	function(error){
					        				 		if(error.status == 401)
					        				 			$window.location.href='/login';
					        				 	}
					        				 );		
					        				 
									});

									function draw(){
           
					        			if(!gauge){
					        				var majorTicks = scope.item.graduations;
					        				
					        			    var highlights = [];
			        			            for(var i = 0; i < scope.item.zones.length; i++){
			        			            	var zone = scope.item.zones[i];					        			            	
			        			            	highlights.push(
			        			            		{
			        			            			"from": zone.min,
											            "to": zone.max,
											            "color": zone.color_code
			        			            		}
			        			            	);
			        			            }
		        			            		        			            	
					        				if(scope.item.gauge_type_short_name == 'radial-gauge'){
					        					gauge = new RadialGauge({
														    renderTo: 'gauge-' + scope.id,
														    width: 200,
														    height: 200,
														    units: scope.item.unity_symbol,
														    minValue: scope.item.min,
														    maxValue: scope.item.max,
														    majorTicks: majorTicks,
														    minorTicks: scope.item.minorTicks,
														    strokeTicks: true,
														    highlights: highlights,
														    colorPlate: "#fff",
														    borderShadowWidth: 0,
														    borders: false,
														    needleType: "arrow",
														    needleWidth: 2,
														    needleCircleSize: 7,
														    needleCircleOuter: true,
														    needleCircleInner: false,
														    animationDuration: 1500,
														    animationRule: "linear"
														}).draw();
					        				}else {
					        					gauge = new LinearGauge({
														    renderTo: 'gauge-' + scope.id,
														    width: 120,
														    height: 300,
														    units: scope.item.unity_symbol,
														    minValue: scope.item.min,
														    startAngle: 90,
														    ticksAngle: 180,
														    valueBox: true,
														    maxValue: scope.item.max,
														    majorTicks: majorTicks,
														    minorTicks: scope.item.minorTicks,
														    strokeTicks: true,
														    highlights: highlights,
														    colorPlate: "#fff",
														    borderShadowWidth: 0,
														    borders: 0,
														    needleType: "arrow",
														    needleWidth: 2,
														    needleCircleSize: 7,
														    needleCircleOuter: true,
														    needleCircleInner: false,
														    animationDuration: 1500,
														    animationRule: "linear",
														    barWidth: 10
														}).draw();
					        				}
					        			}
					        			
					        			gauge.value = scope.item.number;
					        			gauge.update({valueText: scope.item.number.toLocaleString()});								
									}
									
					        	    function init() {
					        	    	scope.loadingData = true;						        	    					        	    					        	   	   
					        	    }
					        	    
					        	    scope.$watch('loadingData', function(newValue, oldValue, scope){
					        	    	scope.active = {active: newValue == true};
					        	    });
					        	    
					        	    init();					        	    
					            }
					        };
					    }
					
					})(angular.module("app"));						
				]]>
      	</script>	
	</xsl:template>
</xsl:stylesheet>