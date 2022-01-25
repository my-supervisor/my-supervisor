<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Mettre à jour une activité</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-lg-12">
			<form action="/activity/update/apply?activity={activity_id}&amp;template={item/template_id}&amp;release={item/id}" method="post">
				<div class="card">
	                <div class="card-header">	                  	                  
	                  	<h3 class="card-title">Release <i><xsl:value-of select="item/version"/></i> de l'activité <i><xsl:value-of select="item/template"/></i></h3>
			            <div class="card-options">
		                  <a role="button" class="btn btn-primary" href="/home?activity={activity_id}">
		                  	<i class="fa fa-arrow-left"></i> Retour
		                  </a>
		                </div>				        
	                </div>
	                <div class="card-body">
	                  <div class="row">	                        
	                        <div class="col-md-12">
		                      <div class="form-group">
		                        <label class="form-label">Version de la release</label>
		                        <input name="version" type="text" class="form-control" disabled="" value="{item/version}"/>		                        
		                      </div>
		                    </div>		                    
		                    <div class="col-md-12">
		                      <div class="form-group">
		                        <label class="form-label">Notes</label>
		                        <textarea name="notes" rows="10" class="form-control" disabled="">
		                        	<xsl:if test="item">
							            <xsl:value-of select="item/notes"/>
							        </xsl:if>
		                        </textarea>
		                      </div>
		                    </div>                
	                  </div>                  
	                </div>
	                <div class="card-footer text-right">
	                  <button type="submit" class="btn btn-primary ml-auto">Appliquer</button>
	                </div>
	              </div>
			</form>
		</div>
	</xsl:template>  	
	<xsl:template match="page" mode="customScript"></xsl:template>	
</xsl:stylesheet>