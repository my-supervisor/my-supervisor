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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>Minlessika - Mobile payment</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <form class="card" action="/payment-method/mobile-money/finalize" method="post" ng-controller="AppCtrl as vm">
        <div class="card-header">
          <h3 class="card-title">
            <xsl:text>Mobile payment of payment request N° </xsl:text>
            <i><xsl:value-of select="request/reference"/> (<xsl:value-of select="request/amount"/>)</i>
          </h3>
        </div>
        <div class="card-body">
          <input name="request_id" type="text" hidden="hidden" value="{request/id}"/>
          <input name="method_id" type="text" hidden="hidden" value="{method/id}"/>
          <input name="transaction_id" type="text" hidden="hidden" ng-model="vm.transactionId"/>
          <div class="row">
            <div class="col-md-4 order-md-2 mb-4">
              <img src="{method/logo}" class="img-thumbnail" alt="Logo"/>
            </div>
            <div class="col-md-8 order-md-1">
              <div class="col-md-12">
                <div class="form-group">
                  <label class="form-label">Mobile number</label>
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Saisir votre numéro mobile à 10 chiffres" ng-model="vm.number"/>
                    <span class="input-group-append">
                      <button class="btn btn-primary" type="button" ng-if="!vm.alreadySend &amp;&amp; !vm.demanding" ng-disabled="!vm.number" ng-click="vm.beginTransaction(vm.number)">Start the transaction</button>
                      <button class="btn btn-primary" type="button" ng-if="vm.alreadySend &amp;&amp; !vm.demanding" ng-disabled="!vm.number" ng-click="vm.beginTransaction(vm.number)">Start new transaction</button>
                      <button class="btn btn-primary" type="button" ng-if="vm.demanding">Request in progress...</button>
                    </span>
                  </div>
                </div>
                <div class="alert alert-primary" ng-if="vm.alreadySend">A payment resquest has been sent to you. Please, confirm it.</div>
              </div>
            </div>
          </div>
        </div>
        <div class="card-footer text-right">
          <div class="d-flex">
            <a href="/payment-request/edit?id={request/id}" class="btn btn-link">Retour to payment request</a>
            <button type="submit" class="btn btn-primary ml-auto" ng-disabled="!vm.alreadySend">Finalise payment</button>
          </div>
        </div>
      </form>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript">
    <script type="text/javascript"><![CDATA[		
            var app = angular.module("app", [])
				                 .config(["$provide", function($provide){
							        
				                 }]);			
	
			app.controller("AppCtrl", ["$scope", "$http", function ($scope, $http) {
				   var vm = this;
				   					
				   var requestId = ]]><xsl:value-of select="request/id"/><![CDATA[;
				   var methodId = ]]><xsl:value-of select="method/id"/><![CDATA[;
				   	 
				   vm.beginTransaction = function(number){
				   							   		
				   		if(number.length != 10){
				   			toastr.info("Entrez 10 chiffres s'il vous plait !", 'Information');
				   			return;
				   		}
				   		
				   		vm.demanding = true;
				   		$http.post('/payment-method/mobile-money/begin?request=' + requestId + '&method=' + methodId, {number : number})
				   			 .then(
					   			 function(success) {
					   			 
					   			    switch(success.data.code){
					   			    	case "400":
					   			    		toastr.info(success.data.message);
					   			    		break;
					   			    	case "500":
					   			    		toastr.error(success.data.message);
					   			    		break;
					   			    	default:
					   			    		vm.alreadySend=true;
					   			    		vm.transactionId=success.data.transactionId;
					   			    		break;
					   			    }
					   			    
					   			    vm.demanding = false;						   			 						   			 	
					   			 },
					   			 function(error) {
					   			 	vm.demanding = false;
					   			 	toastr.error("An error occured !");						   			 	
					   			 }
				   			 );					   			 					   	
				   }
				   
				   this.$onInit = function(){
				   	    ]]><xsl:choose><xsl:when test="mobile_payment_receipt">
				   	    		vm.alreadySend = true;
				   	    		vm.number="<xsl:value-of select="mobile_payment_receipt/number"/>";
				   	    		vm.transactionId="<xsl:value-of select="mobile_payment_receipt/transaction_id1"/>";
				   	    	</xsl:when><xsl:otherwise>
				   	    		vm.alreadySend = false;
				   	    	</xsl:otherwise></xsl:choose><![CDATA[							
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);				
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
