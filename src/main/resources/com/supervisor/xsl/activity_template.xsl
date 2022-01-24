<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Configurer les modèles d'activité</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="col-12">
           <div class="card">
             <div class="card-header">
               <h3 class="card-title">Modèles d'activité</h3>     
             </div>
             <div class="table-responsive">
               <table class="table card-table table-vcenter text-nowrap">
                 <thead>
                   <tr>
                     <th class="w-1">N°</th>
                     <th>Libellé</th>
                     <th>Concepteur</th>
                     <th>Etat</th>
                     <th></th>
                     <th></th>
                     <th></th>
                     <th></th>
                   </tr>
                 </thead>
                 <tbody>
                 	<xsl:for-each select="activity_templates/activity_template">
                    	<tr>
		                     <td>
		                     	<span class="text-muted"><xsl:value-of select="position()"/></span>
		                     </td>
		                     <td><xsl:value-of select="name"/></td>	                     
		                     <td>
		                       <xsl:value-of select="designer"/>
		                     </td>
		                     <td>
		                       <xsl:value-of select="state"/>
		                     </td>
		                     <td class="text-right">
	                            <a href="/activity/template/create-activity/edit?template={id}" class="btn btn-secondary btn-sm">Créer une activité</a>
	                         </td>
	                         <td class="text-right">
		                         <xsl:choose>
		                         	<xsl:when test="is_owner = 'false'">
		                         		<!-- Do nothing -->
		                         	</xsl:when>
		                         	<xsl:otherwise>
		                         		<xsl:choose>
			                            	<xsl:when test="state_id = 'CREATED'">
			                            		<a href="/activity/template/publish/edit?template={id}" class="btn btn-secondary btn-sm">Publier</a>
			                            	</xsl:when>
			                            	<xsl:otherwise>
			                            		<a href="/activity/template/publish/edit?template={id}&amp;id={id}" class="btn btn-secondary btn-sm">Publication</a>
			                            	</xsl:otherwise>
			                            </xsl:choose>
		                         	</xsl:otherwise>
		                         </xsl:choose>
	                         </td>
		                     <td>
		                       <xsl:if test="is_owner = 'true'">
		                       		<a class="icon" href="/activity/template/edit?id={id}" data-toggle="tooltip" data-placement="top" title="Modifier">
			                         	<i class="fe fe-edit"></i>
			                        </a>
		                       </xsl:if>		                       
		                     </td>
		                     <td>
		                       <xsl:if test="is_owner = 'true'">
		                       		<a class="icon" href="/activity/template/delete?id={id}" onclick="return confirm('Voulez-vous supprimer ce modèle ?');" data-toggle="tooltip" data-placement="top" title="Supprimer">
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