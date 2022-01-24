<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Gérer les feuilles de données</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <div class="card">
             <div class="card-header">
               <h3 class="card-title">Feuilles de données</h3>               
             </div>
             <div class="table-responsive">
               <table class="table card-table table-vcenter text-nowrap">
                 <thead>
                   <tr>
                     <th class="w-1">Date de création</th>
                     <th>Date de production</th>
                     <th>Référence</th>
                     <th>Libellé</th>  
                     <th>Propriétaire</th>
                     <th>Autheur</th>                     
                     <th></th>
                     <th></th>
                   </tr>
                 </thead>
                 <tbody>
                 	<xsl:for-each select="data_sheets/data_sheet">
                    	<tr>
	                     <td><span class="text-muted"><xsl:value-of select="creation_date"/></span></td>
	                     <td><xsl:value-of select="date"/></td>
	                     <td><xsl:value-of select="reference"/></td>
	                     <td>
	                       <xsl:value-of select="name"/>
	                     </td>
	                     <td>
	                       <xsl:value-of select="owner"/>
	                     </td>
	                     <td>
	                       <xsl:value-of select="creator"/>
	                     </td>
	                     <td>
	                       <a class="icon" href="/collect/sheet/edit?id={id}">
	                         <i class="fe fe-edit"></i>
	                       </a>
	                     </td>
	                     <td>
	                     	<xsl:if test="../../identity/id = owner_id">
		                       <a class="icon" href="/collect/sheet/delete?id={id}" onclick="return confirm('Désirez-vous supprimer cette feuille ?');">
		                         <i class="fe fe-trash"></i>
		                       </a>
	                       </xsl:if>
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