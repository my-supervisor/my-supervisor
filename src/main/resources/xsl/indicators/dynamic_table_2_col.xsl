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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:interactive="http://www.minlessika.com/DynamicTable2Col" version="2.0">
  <xsl:template name="dynamic_table_2_col">
    <script type="text/ng-template" id="dynamicTable2Col.html">
      <div class="card" style="height: 100%; margin-bottom:0; min-height: 285px;">
        <div class="card-header">
          <h3 class="card-title">{{item.label}}</h3>
          <div class="card-options">
            <div class="text-right">
              <xsl:call-template name="indicator_options">
                <xsl:with-param name="identity" select="identity"/>
                <xsl:with-param name="activity_selected" select="activity_selected"/>
                <xsl:with-param name="shortname" select="'dynamic-table-2-col'"/>
              </xsl:call-template>
            </div>
          </div>
        </div>
        <div class="card-body p-0" style="height: 15rem">
          <div class="dimmer" ng-class="active">
            <div class="loader"/>
            <div class="dimmer-content">
	              			
	              		</div>
          </div>
          <table class="table card-table">
            <tr ng-repeat="post in posts">
              <td class="text-left px-1"><![CDATA[
			              	   <div class="text-orange" ng-if="item.manageEvolutionPercent && post.increaseInPercent == -77700">
				                 &infin;                     
				               </div>
				               <div class="text-red" ng-if="item.manageEvolutionPercent && post.increaseInPercent &lt;= 0  && post.increaseInPercent != -77700">
				                 {{post.increaseInPercent}}%
				                 <i class="fe fe-chevron-down"></i>	                      
				               </div>
							   <div class="text-green" ng-if="item.manageEvolutionPercent && post.increaseInPercent > 0">
				                 {{post.increaseInPercent}}%
				                 <i class="fe fe-chevron-up"></i>	                      
				               </div>  
				             ]]></td>
              <td class="text-left">{{post.name}}</td>
              <td class="text-right">
                <span class="text-muted">{{numberOf(post)}}</span>
              </td>
            </tr>
          </table>
        </div>
      </div>
    </script>
    <script type="text/javascript"><![CDATA[
	    			(function(app, d3){
						    						
						    app.directive('dynamicTable2Col', dynamicTable2Col);
						
						    dynamicTable2Col.$inject = ["$http", "$window", "c3SimpleService"]; 
						    
						    function dynamicTable2Col($http, $window, c3SimpleService) {
						        return {
						            restrict: 'EA',
						            scope: {
								      code: '=',
								      id: '='
								    },
						            templateUrl: "dynamicTable2Col.html",
						            link: function (scope, element, attrs) {
						        	    						        	    	
						            	scope.$on(scope.code, function(event, args) {
						            	
											var url = '/indicator/dynamic-table-2-col?id=' + scope.id + '&activity=' + ]]><xsl:value-of select="activity_selected/id"/><![CDATA[;
											
											if(args.date){										    
												url = url + "&date=" + args.date.toISOString().substring(0, 10);
											}								
											
											scope.loadingData = args.showLoader;
										    $http.get(url, {}, null)
						        			     .then(
						        					function(result){				
						        					    scope.loadingData = false;					        						
						        			            scope.item = result.data;
						        			            
						        			            var posts = [];
						        			            for(var i = 0; i < result.data.names.length; i++){
						        			            	posts.push(
						        			            		{
						        			            			name: result.data.names[i], 
						        			            			value: result.data.values[i],
						        			            			increaseRate: result.data.increaseRates[i],
						        			            			increaseInPercent: result.data.increaseInPercents[i]
						        			            		}
						        			            	);
						        			            }		
						        			            
						        			            scope.posts = posts;							            												            											            
						        				 	},
						        				 	function(error){
						        				 		if(error.status == 401)
						        				 			$window.location.href='/login';
						        				 	}
						        				 );		
						        				 
										});
	
										scope.numberOf = function(post){
						            		if(!post)
						            			return 0;
						            		
						            		if(scope.item.unitySymbol == '')
						            			return post.value.toLocaleString();
						            		else
					            			{
					            				if(scope.item.symbolPosition == 'LEFT')
					            					return scope.item.unitySymbol + ' ' + post.value.toLocaleString();
					            				else
					            					return post.value.toLocaleString() + ' ' + scope.item.unitySymbol;
					            			}
						            	}
						            	
						        	    function init() {
						        	    	scope.loadingData = true;
						        	    	scope.posts = [];				                      						                      				 				                      						                      		   			        			
						        	    }
						        	    
						        	    scope.$watch('loadingData', function(newValue, oldValue, scope){
						        	    	scope.active = {active: newValue == true};
						        	    });						        	   						        	    
					        	    
						        	    init();
						            }
						        };
						    }
						
						})(angular.module("app"), d3);    		    					
				]]></script>
  </xsl:template>
</xsl:stylesheet>
