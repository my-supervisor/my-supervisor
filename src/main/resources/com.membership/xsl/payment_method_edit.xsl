<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Editer un mode paiement</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<form action="/admin/payment-method/save?id={item/id}" method="post">
			<div class="p-lg-6">
	           <h2 class="mt-0 mb-4">Editer un mode paiement</h2>           
			   <div class="row">
			   		<div class="col-md-4 order-md-2 mb-4">
						<img src="{item/logo}" class="img-thumbnail" alt="Logo"/>
					</div>
					<div class="col-md-8 order-md-1">
					   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Libellé <span class="form-required">*</span></label>
	                        <input name="name" type="text" class="form-control" placeholder="Saisir un libellé" required="">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/name"/>
					            </xsl:attribute>				            
	                        </input>
	                      </div>
	                   </div> 
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Type</label>
	                        <input type="text" class="form-control" placeholder="Saisir un libellé" disabled="disabled">
	                        	<xsl:attribute name="value">
					              <xsl:value-of select="item/type"/>
					            </xsl:attribute>				            
	                        </input>
	                      </div>
	                   </div> 
	                   <div class="col-md-6">
	                 		<div class="form-group">
		                      <label class="custom-switch">
				                     <input type="checkbox" class="custom-switch-input" disabled="disabled">
				                     	<xsl:if test="item/online='true'"> 
											<xsl:attribute name="checked">checked</xsl:attribute> 
										</xsl:if>
				                     </input>
			                     	<span class="custom-switch-indicator"></span>
			                    	<span class="custom-switch-description">Est un paiement en ligne ?</span>
			                   </label>
		                    </div>
	                   </div>                 
	                   <div class="col-md-6">
	                 		<div class="form-group">
		                      <label class="custom-switch">
				                     <input name="enabled" type="checkbox" class="custom-switch-input">
				                     	<xsl:if test="item/enabled='true'"> 
											<xsl:attribute name="checked">checked</xsl:attribute> 
										</xsl:if>
				                     </input>
			                     	<span class="custom-switch-indicator"></span>
			                    	<span class="custom-switch-description">Est activé ?</span>
			                   </label>
		                    </div>
	                   </div>
	                   <xsl:if test="item/type_id = 'MOBILE_MONEY' or item/type_id = 'ASSOCIATE'">
	                   	<div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Pseudo</label>
	                        <input name="username" type="text" class="form-control" placeholder="Saisir un Pseudo" value="{item/username}" />
	                      </div>
	                   </div>
	                   <div class="col-md-6">
	                      <div class="form-group">
	                        <label class="form-label">Mot de passe</label>
	                        <input name="password" type="password" class="form-control" placeholder="Saisir un mot de passe" value="{item/password}" />
	                      </div>
	                   </div>
	                   </xsl:if>
					</div>				   
			   </div>       
	        </div>
	        <div class="card-footer text-right">
	          <a href="/admin/payment-method" class="btn btn-link">Annuler</a>
              <button type="submit" class="btn btn-primary">Enregistrer</button>
            </div>
		</form>		
	</xsl:template>  
	<xsl:template match="page" mode="customScript"></xsl:template>		
</xsl:stylesheet>