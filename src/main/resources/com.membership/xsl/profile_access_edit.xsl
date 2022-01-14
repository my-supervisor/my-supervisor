<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Editer un droit d'accès</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<form action="/admin/profile/access/save?id={item/id}&amp;profile={item/profile_id}" method="post">
			<div class="text-wrap p-lg-6">
	           <h2 class="mt-0 mb-4">Editer un droit d'accès - Profil <xsl:value-of select="item/profile"/></h2>           
			   <div class="row">
			   		<div class="col-md-6">
                      <div class="form-group">
                        <label class="form-label">Code</label>
                        <input type="text" class="form-control" disabled="" value="{item/code}"/>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="form-label">Libellé</label>
                        <input type="text" name="name" class="form-control" value="{item/name}" disabled="" />
                      </div>
                    </div>
                    <xsl:for-each select="profile_access_params/profile_access_param">
                   		<div class="col-sm-12 col-md-12">
		                     <fieldset class="form-fieldset">
			                       <legend>Paramètre <xsl:value-of select="position()"/></legend>
			                       <input hidden="hidden" type="text" name="param_id" value="{id}"/>
			                       <div class="row">	  		                                            		
				                       <div class="col-md-6">
					                     <div class="form-group">
					                       <label class="form-label">Code</label>
					                       <input type="text" class="form-control" disabled="" value="{code}" />
					                     </div>
					                   </div>
									   <div class="col-md-6">
					                     <div class="form-group">
					                       <label class="form-label">Libellé</label>
					                       <input type="text" class="form-control" disabled="" value="{name}" />
					                     </div>
					                   </div>
					                   <div class="col-md-4">
					                     <div class="form-group">
					                       <label class="form-label">Type de valeur</label>
					                       <input type="text" class="form-control" disabled="" value="{value_type}" />
					                     </div>
					                   </div>
					                   <div class="col-md-4">
					                     <div class="form-group">
					                       <label class="form-label">Valeur <span class="form-required">*</span></label>
					                       <input name="param_value" type="text" class="form-control" placeholder="Entrez une valeur" required="" value="{value}" />
					                     </div>
					                   </div>
					                   <div class="col-md-4">
					                     <div class="form-group">
					                       <label class="form-label">Quantificateur</label>
					                       <input type="text" class="form-control" disabled="" value="{quantifier}" />
					                     </div>
					                   </div>
					                   <div class="col-md-12">
					                     <div class="form-group">
					                       <label class="form-label">Message</label>
					                       <textarea rows="2" class="form-control" disabled="">
					                       		<xsl:value-of select="message"/>
					                       </textarea>
					                      </div>
					                    </div>					                   
			                       </div>	                       
		                     </fieldset>
	                   </div>
                   </xsl:for-each>
			   </div>       
	        </div>
	        <div class="card-footer text-right">
	          <a href="/admin/profile/edit?id={item/profile_id}" class="btn btn-link">Annuler</a>
              <button type="submit" class="btn btn-primary">Modifier</button>
            </div>
		</form>		
	</xsl:template>  
	<xsl:template match="page" mode="customScript"></xsl:template>		
</xsl:stylesheet>