<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Minlessika - Feedback</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12" ng-controller="AppCtrl as vm">
           <form class="card" action="/feedback/send" method="post">
               <div class="card-header">
                 <h3 class="card-title">Envoyer un feeback</h3>
               </div>
               <div class="card-body">
                 <div class="form-group">
                   <div class="row align-items-center">
                     <label class="col-sm-2">Objet:</label>
                     <div class="col-sm-10">
                       <input name="object" type="text" class="form-control" required=""/>
                     </div>
                   </div>
                 </div>
                 <textarea required="" name="message" rows="10" class="form-control"></textarea>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/home" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Envoyer message</button>
                  </div>
               	</div>
             </form>
        </div>        
	</xsl:template>  		
	<xsl:template match="page" mode="customScript">
		<script type="text/javascript">
        <![CDATA[		
            var app = angular.module("app", []);			
	
			app.controller("AppCtrl", ["$scope", function ($scope) {
				   var vm = this;
				    
				   this.$onInit = function(){

				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);				
        ]]>
        </script>
	</xsl:template>
</xsl:stylesheet>