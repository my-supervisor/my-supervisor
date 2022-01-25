<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Minlessika - Edit my profile</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12" ng-controller="AppCtrl as vm">
           <form class="card" action="/user-profile/save" method="post">
           	   <div class="card-header">
           	   		<h3 class="card-title">Editer mon profil</h3>
           	   </div>
               <div class="card-body">                 
                 <div class="row">
                 	<div class="col-md-4 order-md-2 mb-4">
						<img ng-src="{{{{vm.photoPublished}}}}" class="img-thumbnail mb-3" alt="Photo de profil"/>
						<input type="text" hidden="hidden" name="photo" ng-model="vm.photoPublished" />
						<div class="row">
							<div class="col-sm-4 col-md-4">
								<label class="btn btn-primary btn-file btn-block ml-auto">
							    Editer <input type="file" image="vm.photo" accept="image/*" resize-max-height="100" resize-max-width="100" resize-quality="0.7" resize-type="image/jpeg" ng-image-compress=""/>
							</label>
							</div> 
						</div>
					</div>
					<div class="col-md-8 order-md-1">
					   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">Name <span class="form-required">*</span></label>
	                        <input name="name" type="text" class="form-control" placeholder="Saisir un nom" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/name"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Address line 1 <span class="form-required">*</span></label>
	                        <input name="address_line1" type="text" class="form-control" placeholder="Enter an address" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/address_line1"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Address line 2 </label>
	                        <input name="address_line2" type="text" class="form-control" placeholder="Enter an address">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/address_line2"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">City <span class="form-required">*</span></label>
	                        <input name="city" type="text" class="form-control" placeholder="Enter a city" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/city"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">State / Province <span class="form-required">*</span></label>
	                        <input name="state_or_province" type="text" class="form-control" placeholder="Enter an location" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/state_or_province"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Company </label>
	                        <input name="company" type="text" class="form-control" placeholder="Enter your company">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/company"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Country <span class="form-required">*</span></label>
	                       <select name="country_id" class="form-control" required="">                         
	                         <xsl:variable name="user" select="item" />
	                         <xsl:for-each select="countries/country">                             	                     
	                         	<option>
	                         		<xsl:if test="id = $user/country_id"> 
										<xsl:attribute name="selected">selected</xsl:attribute> 
									</xsl:if>
	                         		<xsl:attribute name="value">
	                         			<xsl:value-of select="id"/>
	                         		</xsl:attribute>
	                         		(+<xsl:value-of select="phone_code"/>) <xsl:value-of select="name"/>                         		                       	
	                         	</option>
	                         </xsl:for-each>                         
	                       </select>
	                     </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Phone 1 <span class="form-required">*</span></label>
	                        <input name="phone1" type="text" class="form-control" placeholder="Phone without phone code" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/phone1"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Phone 2 </label>
	                        <input name="phone2" type="text" class="form-control" placeholder="Phone without phone code">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/phone2"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>	                   
	                   <div class="col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Preferred language <span class="form-required">*</span></label>
	                       <select name="preferred_language_id" class="form-control" required="">                         
	                         <xsl:variable name="user" select="item" />
	                         <xsl:for-each select="languages/language">                             	                     
	                         	<option>
	                         		<xsl:if test="id = $user/preferred_language_id"> 
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
	                   <div class="col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Time zone <span class="form-required">*</span></label>
	                       <select name="time_zone_id" class="form-control" required="">                         
	                         <xsl:variable name="user" select="item" />
	                         <xsl:for-each select="time_zones/time_zone">                             	                     
	                         	<option>
	                         		<xsl:if test="id = $user/time_zone_id"> 
										<xsl:attribute name="selected">selected</xsl:attribute> 
									</xsl:if>
	                         		<xsl:attribute name="value">
	                         			<xsl:value-of select="id"/>
	                         		</xsl:attribute>
	                         		<xsl:value-of select="id"/>                         		                       	
	                         	</option>
	                         </xsl:for-each>                         
	                       </select>
	                     </div>
	                   </div>
	                   <div class="col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Preferred currency <span class="form-required">*</span></label>
	                       <select name="preferred_currency_id" class="form-control" required="">                         
	                         <xsl:variable name="user" select="item" />
	                         <xsl:for-each select="currencies/currency">                             	                     
	                         	<option>
	                         		<xsl:if test="id = $user/preferred_currency_id"> 
										<xsl:attribute name="selected">selected</xsl:attribute> 
									</xsl:if>
	                         		<xsl:attribute name="value">
	                         			<xsl:value-of select="id"/>
	                         		</xsl:attribute>
	                         		<xsl:value-of select="name"/> (<xsl:value-of select="code"/>)                 		                       	
	                         	</option>
	                         </xsl:for-each>                         
	                       </select>
	                     </div>
	                   </div>
					</div>                    
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/home" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Update</button>
                  </div>
               	</div>
             </form>
        </div>
	</xsl:template>  	
	<xsl:template match="page" mode="customScript">
		<script type="text/javascript">
        <![CDATA[		
            var app = angular.module("app", ['ngImageCompress']);			
	
			app.controller("AppCtrl", ["$scope", function ($scope) {
				   var vm = this;
				    
				   $scope.$watch('vm.photo', function (newValue, oldValue) {
			            if (newValue && newValue.compressed)
			                vm.photoPublished = newValue.compressed.dataURL;
			       });
			       
				   this.$onInit = function(){
						vm.photoPublished = "]]><xsl:value-of select="item/photo"/><![CDATA[";				   	    							
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);				
        ]]>
        </script>
	</xsl:template>	
</xsl:stylesheet>