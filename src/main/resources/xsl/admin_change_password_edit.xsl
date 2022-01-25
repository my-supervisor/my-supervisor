<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Changer mot de passe</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<form action="/admin/user/change-password/save?user={user/id}" method="post">
			<div class="p-lg-6">
	           <h2 class="mt-0 mb-4">Changer mot de passe - Utilisateur <xsl:value-of select="user/name"></xsl:value-of></h2>           
			   <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="form-label">Mot de passe <span class="form-required">*</span></label>
                        <input type="password" name="password" class="form-control" placeholder="Saisir un mot de passe" required="" />
                      </div>
                    </div>
			   </div>       
	        </div>
	        <div class="card-footer text-right">
	          <a href="/admin/user/edit?id={user/id}" class="btn btn-link">Annuler</a>
              <button type="submit" class="btn btn-primary">Enregistrer</button>
            </div>
		</form>		
	</xsl:template>
	<xsl:template match="page" mode="customScript"></xsl:template>  		
</xsl:stylesheet>