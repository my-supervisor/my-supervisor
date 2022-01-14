<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Partager</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/sharing/share?type={resource/type_id}&amp;resource={resource/id}" method="post" ng-controller="AppCtrl as vm">
		       <div class="card-header">
		       		<h3 class="card-title"><xsl:text>Partager - </xsl:text><i><xsl:value-of select="resource/name"/></i></h3>
		       </div>
               <div class="card-body">               	
                 <div class="row">
                    <div class="col-md-12 mb-0">
                     	<label class="form-label">Abonnés (<a href="javascript:void(0)" ng-click="vm.add()"><i class="fa fa-plus"></i> Nouvel utilisateur</a>)</label>
                     	<div class="table-responsive form-control">
		                    <table class="table card-table table-vcenter text-nowrap">
		                      <thead>
		                        <tr>
		                          <th class="w-1">N°</th>
		                          <th>Adresse mail</th>
		                          <th></th>
		                        </tr>
		                      </thead>
		                      <tbody>		                        
		                        <tr ng-repeat="item in vm.subscribers">
		                          <td>
		                          	<span class="text-muted">{{$index + 1}}</span>
		                          	<input hidden="" type="text" name="item_email" ng-model="item.email" />
		                          </td>	                          
		                          <td>
		                          	<input ng-model="item.email" type="text" class="form-control" placeholder="Saisir une adresse mail" required=""></input>
		                          </td>	
		                          <td>
				                       <a class="icon" ng-click="vm.remove($index)">
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
                    <a href="/sharing?type={resource/type_id}&amp;resource={resource/id}" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Partager</button>
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
	
			app.controller("AppCtrl", ["$scope", function ($scope) {
				   var vm = this;
				   		
				   vm.subscribers = []]> 
					   <xsl:for-each select="subscribers/subscriber">                             	                     
	                       	{
	                       		'email': "<xsl:value-of select="email"/>"
	                       	},
	                   </xsl:for-each>
	                    <![CDATA[];
	                    	
				   
				   vm.add = function(){
				   		vm.subscribers.push({});
				   }
				   
				   vm.remove = function(index){	
				   		vm.subscribers.splice(index, 1);
				   }
				   
				   this.$onInit = function(){
						if(vm.subscribers.length == 0)
							vm.add();
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);				
        ]]>
        </script>
	</xsl:template>
</xsl:stylesheet>