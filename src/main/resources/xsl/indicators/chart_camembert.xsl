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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:interactive="http://www.minlessika.com/ChartCamembert" version="2.0">
  <xsl:template name="chart_camembert">
    <script type="text/ng-template" id="chartCamembert.html">
      <div class="card" style="height: 100%; margin-bottom:0; min-height: 285px;">
        <div class="card-header">
          <h3 class="card-title">{{item.label}}</h3>
          <div class="card-options">
            <div class="text-right">
              <xsl:call-template name="indicator_options">
                <xsl:with-param name="identity" select="identity"/>
                <xsl:with-param name="activity_selected" select="activity_selected"/>
                <xsl:with-param name="shortname" select="'chart-camembert'"/>
              </xsl:call-template>
            </div>
          </div>
        </div>
        <div class="card-body d-flex align-items-center justify-content-center text-center" style="padding: 0">
          <div class="dimmer" ng-class="active" style="width: 100%; height: 100%; position:absolute;">
            <div class="loader"/>
            <div class="dimmer-content" style="width: 100%; height: 100%; position:absolute;"><![CDATA[
				              <div ng-if="!loadingData && item.columns.length > 0"  style="width: 100%; height: 100%; position:absolute;">	              		 
						              	<div c3-simple id="chart-camembert-{{id}}" config="chart" style="height: 100%;"></div>        			            
				              </div>	
				               
				              <div ng-if="!loadingData && item.columns.length == 0"  style="width: 100%; height: 100%; position:absolute;">
				              		Aucune donnée trouvée
				              </div>  
				              ]]></div>
          </div>
        </div>
      </div>
    </script>
    <script type="text/javascript"><![CDATA[
	    			(function(app, d3){
						    						
						    app.directive('chartCamembert', chartCamembert);
						
						    chartCamembert.$inject = ["$http", "$window", "c3SimpleService"]; 
						    
						    function chartCamembert($http, $window, c3SimpleService) {
						        return {
						            restrict: 'EA',
						            scope: {
								      code: '=',
								      id: '='
								    },
						            templateUrl: "chartCamembert.html",
						            link: function (scope, element, attrs) {
						        	    						        	    	
						            	scope.$on(scope.code, function(event, args) {
						            	
											var url = '/indicator/chart-camembert?id=' + scope.id + '&activity=' + ]]><xsl:value-of select="activity_selected/id"/><![CDATA[;
											
											if(args.date){										    
												url = url + "&date=" + args.date.toISOString().substring(0, 10);
											}								
											
											scope.loadingData = args.showLoader;
										    $http.get(url, {}, null)
						        			     .then(
						        					function(result){				
						        					    scope.loadingData = false;						        						
						        			            scope.item = result.data;
						        			            
						        			            var columns = [];
						        			            for(var i = 0; i < scope.item.columns.length; i++){
						        			            	columns.push([scope.item.columns[i], scope.item.values[i]]);
						        			            }
						        			            
						        			            var names = {};
						        			            for(var i = 0; i < scope.item.names.length; i++){
						        			            	names[scope.item.columns[i]] = scope.item.names[i];
						        			            }

						        			            scope.chart.data.columns = columns;								                      				
								                      	scope.chart.data.names = names;											                      				
											            scope.chart.data.type = scope.item.camembert_type_id;
											            scope.chart.legend.position = 'right';
											            
											            if(scope.chart.data.type == 'pie')
											            	scope.chart.pie = {
											            		label: {
											            		    threshold: 0.1,
														            format: function (value, ratio, id) {
														                return value.toLocaleString();
														            }
														        }
											            	};
											            if(scope.chart.data.type == 'donut')
											            	scope.chart.donut = {
											            		title : scope.item.sub_label,
											            		label: {
											            		    threshold: 0.1,
														            format: function (value, ratio, id) {
														                return value.toLocaleString();
														            }
														        }
											            	};
											            
											            scope.chart.tooltip = {
														        format: {
														            title: function (d) { return d; },
														            value: function (value, ratio, id) {
    																	f = d3.format(".1f");
														                return value.toLocaleString() + ' / ' + f(ratio * 100) + '%';
														            }
														        }
														    };											            												            											            
						        				 	},
						        				 	function(error){
						        				 		if(error.status == 401)
						        				 			$window.location.href='/login';
						        				 	}
						        				 );		
						        				 
										});
	
						        	    function init() {
						        	    	scope.loadingData = true;						        	    

						        	    	scope.chart = {
				                      			data: {
				                      				columns: [
				                      				],
				                      				type: 'pie',
				                      				colors: {
				                      				},
				                      				names: {
				                      				}
				                      			},
				                      			axis: {
				                      			},
											     legend: {
											        show: true
											    },
				                      			padding: {
				                      				bottom: 0,
				                      				top: 0
				                      			},
				                      			options: {
				                      				responsive: true
				                      			}
				                      		};				                      						                      				 				                      						                      		   			        			
						        	    }
						        	    
						        	    scope.$watch('loadingData', function(newValue, oldValue, scope){
						        	    	scope.active = {active: newValue == true};
						        	    });						        	   
						        	    
						        	    angular.element($window).bind('resize', function(){
						        	        var chart = c3SimpleService["#chart-camembert-" + scope.id];
											setTimeout(function () {
											    chart.resize();
											}, 300);
									    });
					        	    
						        	    init();
						            }
						        };
						    }
						
						})(angular.module("app"), d3);    		    					
				]]></script>
  </xsl:template>
</xsl:stylesheet>
