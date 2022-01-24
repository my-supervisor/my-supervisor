<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Changer le concepteur</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <form class="card" action="/activity/template/change-designer/save?activity={activity/id}" method="post">
		        <div class="card-header">
		       		<h3 class="card-title"><xsl:text>Changer le concepteur - Modèle </xsl:text><i><xsl:value-of select="activity/name"/></i></h3>		        
		       </div>
               <div class="card-body">             
                 <div class="row">                 
                   <div class="col-sm-12 col-md-6">
	                     <div class="form-group">
	                       <label class="form-label">Email du nouveau concepteur <span class="form-required">*</span></label>
	                       <input name="email" type="email" class="form-control" placeholder="Saisir une adresse mail" required=""/>                       
	                     </div>
	                 </div>                                                 
                  </div>
                </div>
                <div class="card-footer text-right">
                 <div class="d-flex">
                    <a href="/activity/template/edit?id={activity/id}" class="btn btn-link">Annuler</a>
                    <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
                  </div>
               	</div>
             </form>
        </div>
	</xsl:template>  	
	<xsl:template match="page" mode="customScript"></xsl:template>	
</xsl:stylesheet>