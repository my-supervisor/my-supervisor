<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/admin_layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Administration - Minlessika - Modes de paiement</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="">
           <h2 class="mt-0 mb-4">Modes de paiement</h2>           
	   		<div class="table-responsive">
	   			<div class="card">
		         <table class="table card-table table-vcenter text-nowrap">
		           <thead>
		             <tr>
		               <th class="w-1">N°</th>
		               <th>Logo</th>
		               <th>Libellé</th>          
		               <th>Type</th>
		               <th></th>
		             </tr>
		           </thead>
		           <tbody>
		           	<xsl:for-each select="payment_methods/payment_method">
		              	<tr>
		                <td><span class="text-muted"><xsl:value-of select="position()"/></span></td>
		                <td>
		                  <img src="{logo}" style="width:100px; height:auto"/>
		                </td>
		                <td>
		                  <xsl:value-of select="name"/>
		                </td>
		                <td>
		                  <xsl:value-of select="type"/>
		                </td>
		                <td>
		                  <a class="icon" href="/admin/payment-method/edit?id={id}">
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