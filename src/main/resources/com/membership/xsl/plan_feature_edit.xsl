<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Editer une fonctionnalité de plan</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<form action="/admin/plan/feature/save" method="post">
		    <xsl:if test="item and item/id">
	            <xsl:attribute name="action">
	              <xsl:text>/admin/plan/feature/save?id=</xsl:text><xsl:value-of select="item/id"/>
	            </xsl:attribute>
	        </xsl:if>
	        <input type="text" name="plan_id" hidden="hidden" value="{plan/id}"/>
			<div class="p-lg-6">
	           <h2 class="mt-0 mb-4">Editer une fonctionnalité du plan <xsl:value-of select="plan/name"/></h2>           
			   <div class="row">
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
                        <label class="form-label">Ordre <span class="form-required">*</span></label>
                        <input name="order" type="number" class="form-control" placeholder="Saisir un ordre" required="">
                        	<xsl:attribute name="value">
				              <xsl:value-of select="item/order"/>
				            </xsl:attribute>
                        </input>
                      </div>
                   </div>
                   <div class="col-md-6">
                 		<div class="form-group">
	                      <label class="custom-switch">
			                     <input name="basic" type="checkbox" class="custom-switch-input">
			                     	<xsl:if test="item/basic='true'"> 
										<xsl:attribute name="checked">checked</xsl:attribute> 
									</xsl:if>
			                     </input>
		                     	<span class="custom-switch-indicator"></span>
		                    	<span class="custom-switch-description">Est basique ?</span>
		                   </label>
	                    </div>
                   </div>
                   <div class="col-md-6">
                 		<div class="form-group">
	                      <label class="custom-switch">
			                     <input name="enabled" type="checkbox" class="custom-switch-input">
			                     	<xsl:if test="item/enabled='true' or (not(item) or not(item/id))"> 
										<xsl:attribute name="checked">checked</xsl:attribute> 
									</xsl:if>
			                     </input>
		                     	<span class="custom-switch-indicator"></span>
		                    	<span class="custom-switch-description">Est activé ?</span>
		                   </label>
	                    </div>
                   </div>
              	   <div class="col-md-12">
                     <div class="form-group">
                       <label class="form-label">Description</label>
                       <textarea name="description" rows="5" class="form-control" placeholder="Faire une description...">
                       		<xsl:if test="item">
					            <xsl:value-of select="item/description"/>
					        </xsl:if>
                       </textarea>
                      </div>
                    </div>
			   </div>       
	        </div>
	        <div class="card-footer text-right">
	          <a href="/admin/plan/edit?id={plan/id}" class="btn btn-link">Annuler</a>
              <button type="submit" class="btn btn-primary">Enregistrer</button>
            </div>
		</form>		
	</xsl:template>  
	<xsl:template match="page" mode="customScript"></xsl:template>		
</xsl:stylesheet>