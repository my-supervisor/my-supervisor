<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Liste des types d'indicateur</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <div class="card">
             <div class="card-header">
               <h3 class="card-title">Types d'indicateur</h3>
               <div class="card-options">
	               <a role="button" class="btn btn-primary" href="/indicator/template">
	               	<i class="fa fa-plus"></i> A partir d'un modèle
	               </a>
	           </div>             
             </div>             
             <div class="table-responsive">
               <table class="table card-table table-vcenter text-nowrap">
                 <thead>
                   <tr>
                     <th class="w-1">N°</th>
                     <th>Libellé</th>
                     <th>Description</th>
                     <th></th>
                   </tr>
                 </thead>
                 <tbody>
                    <xsl:variable name="activity" select="activity"/>
                 	<xsl:for-each select="indicator_types/indicator_type">
                    	<tr>
	                     <td><span class="text-muted"><xsl:value-of select="position()"/></span></td>
	                     <td>
	                       <xsl:value-of select="name"/>
	                     </td>
	                     <td>
	                       <xsl:value-of select="description"/>
	                     </td>
	                     <td>
	                       <a href="/{short_name}-setting/edit?activity={$activity/id}&amp;shortname={short_name}&amp;source={../../source}" class="btn btn-secondary btn-sm">
	                       		Créer
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