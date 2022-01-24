<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Editer un utilisateur</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<form action="/admin/user/save" method="post" ng-controller="AppCtrl as vm">
		    <xsl:if test="item and item/id">
	            <xsl:attribute name="action">
	              <xsl:text>/admin/user/save?id=</xsl:text><xsl:value-of select="item/id"/>
	            </xsl:attribute>
	        </xsl:if>
			<div class="p-lg-6">
	           <h2 class="mt-0 mb-4">Editer un utilisateur</h2>           
			   <div class="row">
			   		<div class="col-md-6 order-md-2 mb-4">
						<div class="col-md-12">
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
						<div class="col-md-12 mt-5">
							<h4 class="mb-4">Applications</h4>           
					   		<div class="table-responsive">
					   			<div class="card">
						         <table class="table table-sm table-vcenter text-nowrap">
						           <thead>
						             <tr>
						               <th class="w-1">N°</th>
						               <th>Application</th>   
						               <th>Profil</th>                
						               <th>
						               		<a href="/admin/user/app/edit?user={item/id}" class="btn btn-sm btn-primary pull-right"><i class="fa fa-plus"></i> Ajouter</a>
						               </th>
						             </tr>
						           </thead>
						           <tbody>
						           	<xsl:for-each select="applications/application">
						              	<tr>
						                <td><span class="text-muted"><xsl:value-of select="position()"/></span></td>
						                <td>
						                  <xsl:value-of select="module"/>
						                </td>
						                <td>
						                  <xsl:value-of select="profile"/>
						                </td>
						                <td>
						                  <a class="icon" href="/admin/user/app/edit?id={id}">
						                    <i class="fe fe-edit"></i>
						                  </a>
						                </td>
						              </tr>
						              </xsl:for-each>                   
						           </tbody>
						         </table>
						        </div>
					      </div>
						</div>
					</div>
					<div class="col-md-6 order-md-1">
					   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">Nom <span class="form-required">*</span></label>
	                        <input name="name" type="text" class="form-control" placeholder="Saisir un nom" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/name"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">E-mail <span class="form-required">*</span></label>
	                        <input name="email" type="email" class="form-control" placeholder="Saisir une adresse mail" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/email"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-12">
	                     <div class="form-group">
	                       <label class="form-label">Profil <span class="form-required">*</span></label>
	                       <select name="profile_id" class="form-control" required="">                         
	                         <xsl:variable name="user" select="item" />
	                         <xsl:for-each select="profiles/profile">                             	                     
	                         	<option>
	                         		<xsl:if test="$user and id = $user/profile_id"> 
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
	                   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">Address line 1 <span class="form-required">*</span></label>
	                        <input name="address_line1" type="text" class="form-control" placeholder="Enter an address" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/address_line1"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">Address line 2 </label>
	                        <input name="address_line2" type="text" class="form-control" placeholder="Enter an address">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/address_line2"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">City <span class="form-required">*</span></label>
	                        <input name="city" type="text" class="form-control" placeholder="Enter a city" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/city"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">State / Province <span class="form-required">*</span></label>
	                        <input name="state_or_province" type="text" class="form-control" placeholder="Enter an location" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/state_or_province"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">Company </label>
	                        <input name="company" type="text" class="form-control" placeholder="Enter your company">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/company"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-12">
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
	                   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">Phone 1 <span class="form-required">*</span></label>
	                        <input name="phone1" type="text" class="form-control" placeholder="Phone without phone code" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/phone1"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>
	                   <div class="col-md-12">
	                      <div class="form-group">
	                        <label class="form-label">Phone 2 </label>
	                        <input name="phone2" type="text" class="form-control" placeholder="Phone without phone code">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/phone2"/>
					            </xsl:attribute>
	                        </input>
	                      </div>
	                   </div>	                   
	                   <div class="col-md-12">
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
	                   <div class="col-md-12">
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
	                   <div class="col-md-12">
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
	                   <div class="col-md-12 mb-0">
                    		<div class="form-group">
			                      <label class="custom-switch">
					                     <input name="is_company" type="checkbox" class="custom-switch-input">
					                     	<xsl:if test="item/is_company='true'"> 
												<xsl:attribute name="checked">checked</xsl:attribute> 
											</xsl:if>
					                     </input>
				                     	<span class="custom-switch-indicator"></span>
				                    	<span class="custom-switch-description">I am a company</span>
				                   </label>
		                     </div>
	                   </div>
                       <div class="col-md-12 mb-0">
                    		<div class="form-group">
			                      <label class="custom-switch">
					                     <input name="is_submitted_to_vat" type="checkbox" class="custom-switch-input">
					                     	<xsl:if test="item/is_submitted_to_vat='true'"> 
												<xsl:attribute name="checked">checked</xsl:attribute> 
											</xsl:if>
					                     </input>
				                     	<span class="custom-switch-indicator"></span>
				                    	<span class="custom-switch-description">I am submitted to VAT</span>
				                   </label>
		                     </div>
	                   </div>
	                </div>
			   </div>       
	        </div>
	        <div class="card-footer text-right">
	          <a href="/admin/user" class="btn btn-link">Annuler</a>
	          <xsl:if test="item/active = 'false'">
	          	<a href="/admin/user/activate?id={item/id}&amp;active=true" class="btn btn-primary mr-1" onclick="return confirm('Voulez-vous activer cet utilisateur ?');">Activer</a>
	          </xsl:if>
	          <xsl:if test="item/active = 'true'">
	          	<a href="/admin/user/activate?id={item/id}&amp;active=false" class="btn btn-primary mr-1" onclick="return confirm('Voulez-vous désactiver cet utilisateur ?');">Désactiver</a>
	          	<a href="/admin/user/change-password/edit?user={item/id}" class="btn btn-primary mr-1">Changer mot de passe</a>
	          </xsl:if>
              <button type="submit" class="btn btn-primary">Enregistrer</button>
            </div>
		</form>		
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
						var photoPublished = "]]><xsl:value-of select="item/photo"/><![CDATA[";	
						var defaultPhoto = "data:image/png;base64,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";
						
						if(photoPublished == ""){
							vm.photoPublished = defaultPhoto;
						}else {
							vm.photoPublished = photoPublished;
						}		   	    							
				   };
		    }]);	
			
			angular.bootstrap(document, ['app']);			
        ]]>
        </script>
	</xsl:template>	
</xsl:stylesheet>