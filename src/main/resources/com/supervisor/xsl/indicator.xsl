<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Mes indicateurs</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <div class="card">
             <div class="card-header">
               <h3 class="card-title">Mes indicateurs</h3>               
             </div>
             <div class="table-responsive">
               <table class="table card-table table-vcenter text-nowrap">
                 <thead>
                   <tr>
                     <th class="w-1">N°</th>
                     <th>Libellé</th>                  
                     <th>Périodicité</th>  
                     <th>A partir de</th> 
                     <th></th>                
                     <th></th>
                     <th></th>
                   </tr>
                 </thead>
                 <tbody>
                 	<xsl:for-each select="indicators/indicator">
                    	<tr>
	                     <td><span class="text-muted"><xsl:value-of select="position()"/></span></td>
	                     <td><xsl:value-of select="name"/></td>
	                     <td><xsl:value-of select="periodicity"/></td>
	                     <td>
	                       <xsl:value-of select="periodicity_reference"/>
	                     </td>
	                     <td class="text-right">
                            <a href="/indicator/template/generate?indic={id}" class="btn btn-secondary btn-sm" onclick="return confirm('Souhaitez-vous créer un modèle à partir de cet indicateur ?');">Créer un modèle</a>
                        </td>
	                     <td>
	                       <a class="icon" href="/indicator/edit?id={id}&amp;shortname={short_name}&amp;source=indicator">
	                         <i class="fe fe-edit"></i>
	                       </a>
	                     </td>
	                     <td>
	                     	<a class="icon" href="/indicator/delete?id={id}&amp;source=indicator" onclick="return confirm('Voulez-vous supprimer cet indicateur ?');">
	                         	<i class="fe fe-trash"></i>
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