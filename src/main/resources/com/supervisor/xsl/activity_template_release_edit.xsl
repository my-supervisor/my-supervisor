<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Créer une release de modèle d'activité</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-lg-12">
			<form action="/activity/template/release/save" method="post">
				<xsl:if test="item and item/id">
		            <xsl:attribute name="action">
		              <xsl:text>/activity/template/release/save?id=</xsl:text><xsl:value-of select="item/id"/>
		            </xsl:attribute>
		        </xsl:if>
				<div class="card">
	                <div class="card-header">	                  	                  
	                  	<xsl:if test="item and item/id">
				            <h3 class="card-title">Release <i><xsl:value-of select="item/version"/></i> de l'activité <i><xsl:value-of select="template/name"/></i></h3>
				            <div class="card-options">
			                  <a role="button" class="btn btn-primary" href="/activity/template/edit?id={template/id}">
			                  	<i class="fa fa-arrow-left"></i> Retour
			                  </a>
			                </div>
				        </xsl:if>
				        <xsl:if test="not(item) or not(item/id)">
				            <h3 class="card-title">Nouvelle release de l'activité <i><xsl:value-of select="template/name"/></i></h3>
				        </xsl:if>				        
	                </div>
	                <div class="card-body">
	                  <div class="row">
		                    <xsl:if test="not(item/id)">		                    	
		                    	<input name="release_activity_src_id" hidden="hidden" type="text" class="form-control" value="{release_activity_src_id}"/>
		                    </xsl:if>
		                    <input name="release_template_id" hidden="hidden" type="text" class="form-control" value="{template/id}"/>	                        
	                        <div class="col-md-12">
		                      <div class="form-group">
		                        <label class="form-label">Version de la release<span class="form-required">*</span></label>
		                        <input name="version" type="text" class="form-control" placeholder="Ex 1.0.0.0" required="">
		                        	<xsl:choose>
		                        		<xsl:when test="not(item)">
		                        			<xsl:attribute name="value">
								              <xsl:value-of select="template/version"/>
								            </xsl:attribute>
		                        		</xsl:when>
		                        		<xsl:otherwise>
		                        			<xsl:if test="item">
									            <xsl:attribute name="value">
									              <xsl:value-of select="item/version"/>
									            </xsl:attribute>
									        </xsl:if>
		                        		</xsl:otherwise>
		                        	</xsl:choose>		                        			                        	
		                        </input>		                        
		                      </div>
		                    </div>		                    
		                    <div class="col-md-12">
		                      <div class="form-group">
		                        <label class="form-label">Notes <span class="form-required">*</span></label>
		                        <textarea name="notes" rows="10" class="form-control" placeholder="Décrire les nouvelles modifications de la release..." required="">
		                        	<xsl:if test="item">
							            <xsl:value-of select="item/notes"/>
							        </xsl:if>
		                        </textarea>
		                      </div>
		                    </div>                
	                  </div>                  
	                </div>
	                <div class="card-footer text-right">
	                  <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
	                </div>
	              </div>
			</form>
		</div>
	</xsl:template>  	
	<xsl:template match="page" mode="customScript"></xsl:template>	
</xsl:stylesheet>