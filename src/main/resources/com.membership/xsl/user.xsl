<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Utilisateurs</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="">
           <h2 class="mt-0 mb-4">Utilisateurs</h2>           
	   		<div class="table-responsive">
	   			<div class="card">
		         <table class="table card-table table-vcenter text-nowrap">
		           <thead>
		             <tr>
		               <th class="w-1">N°</th>
		               <th>Photo</th> 
		               <th>Nom</th>   
		               <th>E-mail</th>
		               <th>Profil</th>           
		               <th>Activé</th>    
		               <th colspan="2">
		               		<a href="/admin/user/edit" class="btn btn-sm btn-primary pull-right"><i class="fa fa-plus"></i> Ajouter</a>
		               </th>
		             </tr>
		           </thead>
		           <tbody>
		           	<xsl:for-each select="profiles/profile">
		              	<tr>
		                <td><span class="text-muted"><xsl:value-of select="position()"/></span></td>
		                <td>
		                  <img src="{photo}" alt="User photo" height="42" width="42" />
		                </td>
		                <td>
		                  <xsl:value-of select="name"/>
		                </td>
		                <td>
		                  <xsl:value-of select="email"/>
		                </td>
		                <td>
		                  <xsl:value-of select="profile"/>
		                </td>
		                <td>
		                  <xsl:choose>
		                  	<xsl:when test="active = 'true'">
		                  		<xsl:text>Activé</xsl:text>
		                  	</xsl:when>
		                  	<xsl:otherwise>
		                  		<xsl:text>Désactivé</xsl:text>
		                  	</xsl:otherwise>
		                  </xsl:choose>
		                </td>
		                <td>
		                  <a class="icon" href="/admin/user/edit?id={id}">
		                    <i class="fe fe-edit"></i>
		                  </a>
		                </td>
		                <td>
	                       <a class="icon" href="/admin/user/delete?id={id}" onclick="return confirm('Voulez-vous supprimer cet utilisateur ? Cette action supprimera toutes les données sur lui de manière irrévocable !');">
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
	<xsl:template match="page" mode="customScript">
	
	</xsl:template>
</xsl:stylesheet>