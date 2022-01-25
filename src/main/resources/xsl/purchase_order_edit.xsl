<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE castlist [
<!ENTITY nbsp '&#160;'>
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Minlessika - Edit a purchase order</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
	    <div class="page-header">
          <h1 class="page-title">
            Purchaser order N° <xsl:value-of select="item/reference"/> - <xsl:value-of select="item/status"/>
          </h1>
        </div>
        <div class="row">
        	<div class="col-lg-12">
        	    <div class="row">
        	    	<div class="col-12">
        	    		<div class="card">
		        			<div class="table-responsive">
		        				<table class="table table-hover table-outline table-vcenter text-nowrap card-table">
		        					<thead>
				                        <tr>
				                          <th>Product</th>
				                          <th>Description</th>
				                          <th>Quantity</th>
				                          <th>Unit price HT</th>
				                          <th>Total price HT</th>
				                        </tr>
				                    </thead>
				                    <tbody>
				                    	<xsl:for-each select="order_lines/order_line">
				                    		<tr>
				                    			<td><xsl:value-of select="name"/></td>
				                    			<td><xsl:value-of select="description"/></td>
				                    			<td class="text-center"><xsl:value-of select="quantity"/></td>
				                    			<td class="text-right"><xsl:value-of select="unit_price"/></td>
				                    			<td class="text-right"><xsl:value-of select="ht_amount"/></td>
				                    		</tr>
				                    	</xsl:for-each>
				                    </tbody>
				                    <tfoot>
				                        <xsl:for-each select="order_taxes/order_tax">
				                        <tr>
				                          <th colspan="4" class="text-right"><xsl:value-of select="name"/></th>
				                          <td class="text-right"><xsl:value-of select="amount"/></td>
				                        </tr>
				                        </xsl:for-each>
				                    	<tr>
				                          <th colspan="4" class="text-right">Total</th>
				                          <td class="text-right"><xsl:value-of select="item/ttc_amount"/></td>
				                        </tr>
				                        <tr>
				                          <th colspan="4" class="text-right">Montant payé</th>
				                          <td class="text-right"><xsl:value-of select="item/paid_amount"/></td>
				                        </tr>
				                    </tfoot>
		        				</table>
		        			</div>
		        		</div>
        	    	</div>
        	    </div>	        	
	        </div>
        </div>                  
	</xsl:template>  	
	<xsl:template match="page" mode="customScript"></xsl:template>	
</xsl:stylesheet>