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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:interactive="http://www.minlessika.com/GoalNumber" version="2.0">
  <xsl:template name="goal_number">
    <script type="text/ng-template" id="goalNumber.html">
      <div class="card" style="height: 100%; margin-bottom:0; min-height: 135px;">
        <div class="card-body d-flex align-items-center justify-content-center text-center" style="padding:0; margin: 10px">
          <div class="dimmer" ng-class="active" style="width: 100%; height: 100%; position:absolute;">
            <div class="loader"/>
            <div class="dimmer-content" style="width: 100%; height: 100%; position:absolute;">
              <div class="pull-right">
                <xsl:call-template name="indicator_options">
                  <xsl:with-param name="identity" select="identity"/>
                  <xsl:with-param name="activity_selected" select="activity_selected"/>
                  <xsl:with-param name="shortname" select="'goal-number'"/>
                </xsl:call-template>
              </div>
              <div class="d-flex d-flex align-items-center justify-content-center flex-column" style="width: 100%; height: 100%">
                <div style="width: 100%; height: 100%">
                  <div class="h5" style="margin:0">{{item.label}}</div>
                  <div class="display-4 font-weight-bold mb-1">{{numberOf(item)}}</div>
                  <div class="progress progress-sm mb-0"><![CDATA[
						                  	<div class="progress-bar" style="width: {{item.numberInPercent}}%; background-color: {{item.color_code}}"></div>
						                ]]></div>
                  <div class="row">
                    <div class="text-muted text-left col-6 col-md-6" ng-if="item.numberInPercent != -77700">{{item.numberInPercent}}%</div>
                    <div class="text-muted text-left col-6 col-md-6" ng-if="item.numberInPercent == -77700"><![CDATA[&infin;]]></div>
                    <div class="text-muted text-right col-6 col-md-6">{{item.goal.toLocaleString()}}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </script>
    <script type="text/javascript"><![CDATA[		    		    	    		 
	    			(function(app){
					   'use strict';						
						
					    app.directive('goalNumber', goalNumber);
					
					    goalNumber.$inject = ["$http"]; 
					    function goalNumber($http) {
					        return {
					            restrict: 'EA',
					            scope: {
							      code: '=',
							      id: '='
							    },
					            templateUrl: "goalNumber.html",
					            link: function (scope, element, attrs) {
					            	
					            	scope.numberOf = function(item){
					            		if(!item)
					            			return 0;
					            		
					            		if(item.unitySymbol == '')
					            			return item.number.toLocaleString();
					            		else
				            			{
				            				if(item.symbolPosition == 'LEFT')
				            					return item.unitySymbol + ' ' + item.number.toLocaleString();
				            				else
				            					return item.number.toLocaleString() + ' ' + item.unitySymbol;
				            			}
					            	}
					            	
					            	scope.$on(scope.code, function(event, args) {
										var url = '/indicator/goal-number?id=' + scope.id + '&activity=' + ]]><xsl:value-of select="activity_selected/id"/><![CDATA[;
											
										if(args.date){										    
											url = url + "&date=" + args.date.toISOString().substring(0, 10);
										}				
												
										scope.loadingData = args.showLoader;
									    $http.get(url, {}, null)
					        			     .then(
					        					function(result){				
					        					    scope.loadingData = false;						        						
					        			            scope.item = result.data;
					        				 	},
					        				 	function(error){
					        				 		if(error.status == 401)
					        				 			$window.location.href='/login';
					        				 	}
					        				 );		
					        				 
									});

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
				]]></script>
  </xsl:template>
</xsl:stylesheet>
