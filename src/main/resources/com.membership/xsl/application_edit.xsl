<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Editer une application</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<form action="/admin/user/app/save" method="post">
		    <xsl:if test="item and item/id">
	            <xsl:attribute name="action">
	              <xsl:text>/admin/user/app/save?id=</xsl:text><xsl:value-of select="item/id"/>
	            </xsl:attribute>
	        </xsl:if>
			<div class="p-lg-6">
	           <h2 class="mt-0 mb-4">Editer une application</h2>           
			   <div class="row">
                   <div class="col-md-6">
                     <div class="form-group">
                       <label class="form-label">Module <span class="form-required">*</span></label>
                       <xsl:if test="not(item/id)">	                        
                       		<input hidden="hidden" type="text" name="user_id" value="{user_id}"/>
                       	</xsl:if>
                       <xsl:if test="item/id">	                        
                       		<input hidden="hidden" type="text" name="module" value="{item/module}"/>
                       	</xsl:if>
                       <select name="module" class="form-control" required=""> 
                       	<xsl:if test="item/id">	                       	    
                       		<xsl:attribute name="disabled">disabled</xsl:attribute>
                       	</xsl:if>                       
                         <xsl:variable name="item" select="item" />
                         <xsl:for-each select="modules/module">                             	                     
                         	<option>
                         		<xsl:if test="code = $item/module"> 
									<xsl:attribute name="selected">selected</xsl:attribute> 
								</xsl:if>
                         		<xsl:attribute name="value">
                         			<xsl:value-of select="code"/>
                         		</xsl:attribute>
                         		<xsl:value-of select="name"/>                      		                       	
                         	</option>
                         </xsl:for-each>                         
                       </select>
                     </div>
                   </div>
                   <xsl:if test="item/id">                   
                   <div class="col-md-6">
                     <div class="form-group">
                       <label class="form-label">Profil <span class="form-required">*</span></label>
                       <select name="profile_id" class="form-control" required="">                 
                         <xsl:variable name="item" select="item" />
                         <xsl:for-each select="profiles/profile">                             	                     
                         	<option>
                         		<xsl:if test="id = $item/profile_id"> 
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
                   </xsl:if>
			   </div>       
	        </div>
	        <div class="card-footer text-right">
	          <xsl:choose>
	          	<xsl:when test="item/id">
	          		<a href="/admin/user/edit?id={item/userId}" class="btn btn-link">Annuler</a>
	          	</xsl:when>
	          	<xsl:otherwise>
	          		<a href="/admin/user/edit?id={user_id}" class="btn btn-link">Annuler</a>
	          	</xsl:otherwise>
	          </xsl:choose>
	          
              <button type="submit" class="btn btn-primary">Enregistrer</button>
            </div>
		</form>		
	</xsl:template>  
	<xsl:template match="page" mode="customScript"></xsl:template>		
</xsl:stylesheet>