<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Droits d'accès</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="">
            <h2 class="mt-0 mb-4">Droits d'accès</h2>           
	   		<div class="table-responsive">
	   			<div class="card">
		         <table class="table card-table table-vcenter text-nowrap">
		           <thead>
		             <tr>
		               <th class="w-1">N°</th>
		               <th>Libellé</th>
		               <th>Module</th>  
		               <th>Nb de paramètres</th>                 
		               <th></th>
		             </tr>
		           </thead>
		           <tbody>
		           	<xsl:for-each select="accesses/access">
		              	<tr>
		                <td><span class="text-muted"><xsl:value-of select="position()"/></span></td>
		                <td>
		                  <xsl:value-of select="name"/>
		                </td>
		                <td>
		                  <xsl:value-of select="module"/>
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
		                <td>
		                  <a class="icon" href="/admin/access/edit?id={id}&amp;module={module}">
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
	</xsl:template>  
	<xsl:template match="page" mode="customScript"></xsl:template>		
</xsl:stylesheet>