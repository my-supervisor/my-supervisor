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
      <xsl:text>MySupervisor - Publier un modèle d'activité</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12" ng-controller="AppCtrl as vm">
      <form class="card" action="/activity/template/publish/save" method="post">
        <xsl:if test="item and item/id">
          <xsl:attribute name="action">
            <xsl:text>/activity/template/publish/save?id=</xsl:text>
            <xsl:value-of select="item/id"/>
          </xsl:attribute>
        </xsl:if>
        <div class="card-header">
          <h3 class="card-title">Publier - <xsl:value-of select="template_name"/> - <xsl:value-of select="item/state"/></h3>
          <div class="card-options">
            <div class="card-options">
              <xsl:if test="item/id">
                <xsl:choose>
                  <xsl:when test="item/state_id = 'OBSOLETE'">
                    <a role="button" class="btn btn-primary" href="/activity/template/published/available?id={item/id}" onclick="return confirm('Voulez-vous rendre disponible ce modèle ?');"><i class="fa fa-check"/> Rendre disponible
					                </a>
                  </xsl:when>
                  <xsl:otherwise>
                    <a role="button" class="btn btn-primary" href="/activity/template/published/obsolete?id={item/id}" onclick="return confirm('Voulez-vous rendre obsolète ce modèle ?');"><i class="fa fa-trash"/> Rendre obsolète
					                </a>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:if>
            </div>
          </div>
        </div>
        <div class="card-body">
          <input type="text" hidden="hidden" name="template_id" value="{template_id}"/>
          <div class="row">
            <div class="col-md-4 order-md-2 mb-4">
              <img ng-src="{{{{vm.photoPublished}}}}" class="img-thumbnail mb-3" alt="Image activité"/>
              <input type="text" hidden="hidden" name="icon" ng-model="vm.photoPublished"/>
              <div class="row">
                <div class="col-sm-4 col-md-4">
                  <label class="btn btn-primary btn-file btn-block ml-auto">
									    Editer <input type="file" image="vm.photo" accept="image/*" resize-max-height="200" resize-max-width="300" resize-quality="0.7" resize-type="image/jpeg" ng-image-compress=""/>
									</label>
                </div>
              </div>
            </div>
            <div class="col-md-8 order-md-1">
              <h4 class="mb-3">Informations</h4>
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <label class="form-label">Version</label>
                    <input type="text" class="form-control" disabled="" value="{item/version}"/>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label class="form-label">Profil <span class="form-required">*</span></label>
                    <select name="profile_id" class="form-control" required="">
                      <xsl:variable name="activity" select="item"/>
                      <xsl:for-each select="profiles/profile">
                        <option>
                          <xsl:if test="id = $activity/profile_id">
                            <xsl:attribute name="selected">selected</xsl:attribute>
                          </xsl:if>
                          <xsl:attribute name="value">
                            <xsl:value-of select="id"/>
                          </xsl:attribute>
                          <xsl:value-of select="name"/>
                        </option>
                      </xsl:for-each>
                    </select>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="card-footer text-right">
          <div class="d-flex">
            <a href="/activity/template" class="btn btn-link">Annuler</a>
            <button type="submit" class="btn btn-primary ml-auto">Publier</button>
          </div>
        </div>
      </form>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript">
    <script type="text/javascript"><![CDATA[		
            var app = angular.module("app", ['ngImageCompress']);			
	
			app.controller("AppCtrl", ["$scope", function ($scope) {
				   var vm = this;
	                    
	               $scope.$watch('vm.photo', function (newValue, oldValue) {
			            if (newValue && newValue.compressed)
			                vm.photoPublished = newValue.compressed.dataURL;
			       });
       
				   this.$onInit = function(){
				   	    vm.photoPublished = "]]><xsl:value-of select="item/icon"/><![CDATA[";
				   	    if(vm.photoPublished == "")
							vm.photoPublished = "data:image/jpeg;base64,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";
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);			
        ]]></script>
  </xsl:template>
</xsl:stylesheet>
