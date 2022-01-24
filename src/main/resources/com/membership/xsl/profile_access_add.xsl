<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Ajouter un droit d'accès à un profil</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<form action="/admin/profile/access/add?profile={profile/id}" method="post">
			<div class="p-lg-6">
	           <h2 class="mt-0 mb-4">Ajouter un droit d'accès - Profil <xsl:value-of select="profile/name" /></h2>           
			   <div class="row">
					<div class="col-md-12">         
				   		<div class="table-responsive">
				   			<div class="card">
					         <table class="table card-table table-vcenter text-nowrap">
					           <thead>
					             <tr>
					               <th></th>
					               <th class="w-1">N°</th>
					               <th>Libellé</th>   
					               <th>Nb params</th>
					             </tr>
					           </thead>
					           <tbody>
					           	<xsl:for-each select="accesses/access">
					              	<tr>
						              	<td>
						              	    <input name="access_checked" type="checkbox"/>
						              		<input name="access_id" type="text" hidden="hidden" value="{id}"/>
						              	</td>
						                <td><span class="text-muted"><xsl:value-of select="position()"/></span></td>
						                <td>
						                  <xsl:value-of select="name"/>
						                </td>
						                <td>
						                  <xsl:choose>
						                  	<xsl:when test="nb_params = 0">
						                  		<xsl:text>Aucun</xsl:text>
						                  	</xsl:when>
						                  	<xsl:otherwise>
						                  		<xsl:value-of select="nb_params"/>
						                  	</xsl:otherwise>
						                  </xsl:choose>		                  
						                </td>
					                </tr>
					              </xsl:for-each>                   
					           </tbody>
					         </table>
					        </div>
				      </div>
					</div>
			   </div>       
	        </div>
	        <div class="card-footer text-right">
	          <a href="/admin/profile/edit?id={profile/id}" class="btn btn-link">Annuler</a>
              <button type="submit" class="btn btn-primary">Ajouter</button>
            </div>
		</form>		
	</xsl:template>  
	<xsl:template match="page" mode="customScript"></xsl:template>		
</xsl:stylesheet>