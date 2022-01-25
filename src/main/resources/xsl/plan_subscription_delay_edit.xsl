<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Durée d'un abonnement</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/plan/subscription/order" method="post" ng-controller="AppCtrl as vm">
		       <div class="card-header">
		       		<h3 class="card-title"><xsl:text>Durée de l'abonnement au plan - </xsl:text><i><xsl:value-of select="plan/name"/> (<xsl:value-of select="plan/viewPrice"/>)</i></h3>
		       </div>
               <div class="card-body">  
                 <input name="plan_id" type="text" hidden="hidden" value="{plan/id}"/>             	
                 <div class="row">
					<div class="col-md-4">
                      <div class="form-group">
                        <label class="form-label">Nombre de mois <span class="form-required">*</span></label>
                        <input name="delay" type="number" min="1" class="form-control" required="" ng-model="vm.quantity" ng-change="vm.quantityChanged(vm.quantity)"/>
                      </div>
                   </div>
                   <div class="col-md-4">
                      <div class="form-group">
                        <label class="form-label">Total HT estimé en <xsl:value-of select="currency"/></label>
                        <input type="text" class="form-control" disabled="disabled" ng-model="vm.total"/>
                      </div>
                   </div>
                 </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/pricing" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Commander</button>
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
				   		
				   var price = ]]><xsl:value-of select="plan/price"/><![CDATA[;
				   vm.total = 0;
				   
				   vm.quantityChanged = function(newQuantity){
				   		if(newQuantity != undefined){
				   			vm.total = (newQuantity * price).toLocaleString();
				   		}else{
				   			vm.total = 0;
				   		}
				   }					 
				   
				   this.$onInit = function(){
						vm.quantity = ]]><xsl:value-of select="item/delay"/><![CDATA[;
						vm.quantityChanged(vm.quantity);
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);			
        ]]>
        </script>  
	</xsl:template>
</xsl:stylesheet>