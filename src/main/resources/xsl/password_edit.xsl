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
      <xsl:text>MySupervisor - Changer mon mot de passe</xsl:text>
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
    <script type="text/javascript"><![CDATA[		
            var app = angular.module("app", []);			
	
			app.controller("AppCtrl", ["$scope", function ($scope) {
				   var vm = this;
				    
				   this.$onInit = function(){

				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);			
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
