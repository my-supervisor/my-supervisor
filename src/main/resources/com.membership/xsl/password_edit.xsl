<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Minlessika - Changer mon mot de passe</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12" ng-controller="AppCtrl as vm">
           <form class="card" action="/password/save" method="post">
               <div class="card-body">
                 <h3 class="card-title">Changer mon mot de passe</h3>
                 <div class="row">
                   <div class="col-md-12">
                      <div class="form-group">
                        <label class="form-label">Mot de passe actuel <span class="form-required">*</span></label>
                        <input name="actual_password" type="password" class="form-control" placeholder="Saisir un mot de passe" required="">
                        	<xsl:attribute name="value">
				              <xsl:value-of select="item/actual_password"/>
				            </xsl:attribute>
                        </input>
                      </div>
                   </div>
				   <div class="col-md-6">
                      <div class="form-group">
                        <label class="form-label">Nouveau mot de passe<span class="form-required">*</span></label>
                        <input name="new_password" type="password" class="form-control" placeholder="Saisir un mot de passe" required="">
                        	<xsl:attribute name="value">
				              <xsl:value-of select="item/new_password"/>
				            </xsl:attribute>
                        </input>
                      </div>
                   </div>
                   <div class="col-md-6">
                      <div class="form-group">
                        <label class="form-label">Nouveau mot de passe confirmé<span class="form-required">*</span></label>
                        <input name="new_password_confirmed" type="password" class="form-control" placeholder="Saisir un mot de passe" required="">
                        	<xsl:attribute name="value">
				              <xsl:value-of select="item/new_password_confirmed"/>
				            </xsl:attribute>
                        </input>
                      </div>
                   </div>
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/home" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Appliquer</button>
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