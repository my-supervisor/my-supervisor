<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Activity Store</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="my-3 my-md-5" app="app">
          <div class="container" ng-controller="AppCtrl as vm">
            <div class="page-header">
              <h1 class="page-title">
                Store
              </h1>
              <div class="page-subtitle">
              		{{vm.totalCount}} activités - {{vm.pagesCount}} pages
              </div>
              <div class="page-options d-flex">
                <div class="form-group">
                  <div class="selectgroup selectgroup-pills">
                    <label class="selectgroup-item">
                      <input type="radio" name="value" value="POPULAR" class="selectgroup-input" ng-model="vm.facet" ng-change="vm.search()"/>
                      <span class="selectgroup-button">Populaires</span>
                    </label>
                    <label class="selectgroup-item">
                      <input type="radio" name="value" value="RECENTS" class="selectgroup-input" ng-model="vm.facet" ng-change="vm.search()"/>
                      <span class="selectgroup-button">Récents</span>
                    </label>
                    <label class="selectgroup-item">
                      <input type="radio" name="value" value="MORE_USED" class="selectgroup-input" ng-model="vm.facet" ng-change="vm.search()"/>
                      <span class="selectgroup-button">Plus utilisés</span>
                    </label>
                    <label class="selectgroup-item">
                      <input type="radio" name="value" value="MORE_VISITED" class="selectgroup-input" ng-model="vm.facet" ng-change="vm.search()"/>
                      <span class="selectgroup-button">Plus visités</span>
                    </label>
                  </div>
                </div>  
                <div class="form-group">
                  <input type="text" class="form-control" placeholder="Rechercher par étiquette..." id="input-tags" ng-change="vm.search()" ng-model="vm.filter"/>
                </div>              
              </div>
            </div>
            <div class="row row-cards">              
              <div class="col-sm-6 col-md-3" ng-repeat="item in vm.items">
                <div class="card">
                  <a href="/activity/template/published/visualise?id={{{{item.id}}}}">
                    <img ng-src="{{{{item.icon}}}}" alt="Photo of indicator" class="card-img-top"/>
                  </a>
                  <div class="card-body d-flex flex-column">
                    <h4><a href="/activity/template/published/visualise?id={{{{item.id}}}}">{{item.name}}</a></h4>                    
                    <div class="d-flex align-items-center pt-5 mt-auto">
                    	<div class="avatar avatar-md mr-3" style="background-image: url({{{{item.designerPhoto}}}})"></div>
                    	<div>
	                      <div>{{item.designer}}</div>
	                      <small class="d-block text-muted">{{item.fromNow}}</small>
	                    </div>	                    
                    </div>  
                    <div class="ml-auto text-muted pt-3">                  
                      <a href="javascript:void(0)" class="icon"><i class="fe fe-eye mr-1"></i> {{item.nbViews}}</a>                      
                      <a href="javascript:void(0)" class="icon d-md-inline-block ml-3"><i class="fe fe-heart mr-1"></i> {{item.nbLikes}}</a>
                      <a href="javascript:void(0)" class="icon d-none d-md-inline-block ml-3"><i class="fa fa-download mr-1"></i> {{item.nbSubscriptions}}</a>
                    </div>  
                    <div class="text-muted  pt-1">               
                      <span class="tag tag-addon tag-success">
						  {{item.profile}}
					  </span>   
                    </div>                                                                       
                  </div>
                </div>
              </div>
            </div>
            <div class="row d-flex justify-content-center">
                <ul uib-pagination="" first-text="Premier" last-text="Dernier" previous-text="Précédent" next-text="Suivant" total-items="vm.totalCount" ng-model="vm.currentPage" items-per-page="vm.nbItemsPerPage" max-size="vm.pageSize" num-pages="vm.pagesCount" class="pagination-md" rotate="false" boundary-links="true" force-ellipses="true" ng-change="vm.pageChanged()"></ul>           	
            </div>
          </div>
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
                    
				var app = angular.module("app", ['ui.bootstrap']);			
	
				app.controller("AppCtrl", ["$scope", "$http", function ($scope, $http) {
					   var vm = this;
		                   
		               vm.totalCount = 0;
		               
		               vm.search = function() {							
				            var config = {
				                params: {
				                    page: vm.currentPage,
				                    itemsPerPage: vm.nbItemsPerPage,
				                    tags: vm.filter,
				                    facet: vm.facet
				                }
				            };
				
				            vm.loadingData = true;
				            return $http.get('/activity/template/published/search', config).then(
						            function(response){
						            	vm.loadingData = false;
						            	
						            	vm.totalCount = response.data.count;						            
							            vm.items = response.data.items;
						            },
						            function(error){
						            	vm.loadingData = false;
						            }
				            );
				        }
         
		               vm.pageChanged = function(){
		               		vm.search();
		               };		             		              
		               
					   this.$onInit = function(){
					   					   	    
					   	    vm.nbItemsPerPage = 12;
					   	    vm.pageSize = 5;
					   	    vm.currentPage = 1;
					   	    vm.facet = "RECENTS";
					   	    
					   	    vm.search();
					   };
			    }]);	
				
				angular.bootstrap(document, ['app']);			
        ]]>
        </script>
	</xsl:template>
</xsl:stylesheet>