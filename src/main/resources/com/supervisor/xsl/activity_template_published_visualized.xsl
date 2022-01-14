<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Visualiser un modèle d'activité</xsl:text>
	    </title>	 
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12" ng-controller="AppCtrl as vm">
           <div class="card">
		       <div class="card-header">
		       		<h3 class="card-title"><xsl:value-of select="item/name"/></h3>
		       		<div class="card-options">
	                	<div class="card-options">
	                	  <xsl:choose>
	                	  	<xsl:when test="item/liked = 'true'">
	                	  	    <span style="color: green;"><i class="fe fe-heart"></i> J'aime</span>	                	  		
	                	  	</xsl:when>
	                	  	<xsl:otherwise>
	                	  		<a role="button" class="btn btn-primary" href="/activity/template/published/like?id={item/id}" onclick="return confirm('Appréciez-vous cette activité ?');">			                  	
				                  	<i class="fe fe-heart"></i> J'aime
				                </a>
	                	  	</xsl:otherwise>
	                	  </xsl:choose>                  
		                </div>
	                </div>		        
		       </div>
               <div class="card-body">
 					<div class="row">
 						<div class="col-md-4 order-md-2 mb-4">
 							<img ng-src="{{{{vm.photoPublished}}}}" class="img-thumbnail mb-3" alt="Image indicateur"/>
 						</div>
 						<div class="col-md-8 order-md-1">
 							<h4 class="mb-3">Informations</h4>
 							<div class="row">
			                   <div class="col-md-6">
			                      <div class="form-group">
			                        <label class="form-label">Version</label>
			                        <p><xsl:value-of select="item/version"/></p>			                        
			                      </div>
			                   </div>
			                   <div class="col-md-6">
			                      <div class="form-group">
			                        <label class="form-label">Licence</label>
			                        <p><xsl:value-of select="item/license"/></p>
			                      </div>
			                   </div>
			                   <div class="col-md-12">
				                     <div class="form-group">
				                       <label class="form-label">Description</label>
				                       <p><xsl:value-of select="item/description"/></p>
				                     </div>
			                   </div>
		                   </div>
 						</div>
 					</div>
               </div>
               <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/store" class="btn btn-link">Annuler</a>
                    <a role="button" class="btn btn-primary ml-auto" href="/activity/template/published/download?id={item/id}" onclick="return confirm('Souhaitez-vous vous abonner à cette activité ?');">			                  	
	                  	<i class="fa fa-download"></i> S'abonner
	                </a>
                  </div>
               </div>
             </div>
        </div>                     
	</xsl:template>  
	<xsl:template match="page" mode="customScript">
		<script type="text/javascript">
        <![CDATA[		
            var app = angular.module("app", []);			
	
			app.controller("AppCtrl", ["$scope", function ($scope) {
				   var vm = this;
	                   
				   this.$onInit = function(){
				   	    vm.photoPublished = "]]><xsl:value-of select="item/icon"/><![CDATA[";
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);				
        ]]>
        </script>
	</xsl:template>		
</xsl:stylesheet>