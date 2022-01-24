<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/supervisor/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Pricing</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="page-header">
          <h1 class="page-title">
            Pricing
          </h1>
        </div>
        <div class="row">
          <xsl:for-each select="plans/plan">
          	<div class="col-sm-6 col-lg-3">
	            <div class="card">
	              <xsl:if test="preferred = 'true'">
	              	<div class="card-status bg-green"></div>
	              </xsl:if>
	              <div class="card-body text-center">
	                <div class="card-category"><xsl:value-of select="name"/></div>
	                <div class="display-5 my-4"><xsl:value-of select="viewPrice"/><xsl:text> HT / Mois</xsl:text></div>
	                <ul class="list-unstyled leading-loose">
	                  <xsl:variable name="plan" select="."/>
	                  <xsl:for-each select="../../plan_features/plan_feature">
	                    <xsl:if test="plan_id = $plan/id">
	                        <xsl:choose>
	                        	<xsl:when test="basic = 'true'">
	                        		<li><xsl:value-of select="name"/></li>
	                        	</xsl:when>
	                        	<xsl:when test="enabled = 'true'">
	                        		<li><i class="fe fe-check text-success mr-2" aria-hidden="true"></i> <xsl:value-of select="name"/></li>
	                        	</xsl:when>
	                        	<xsl:otherwise>
	                        		<li><i class="fe fe-x text-danger mr-2" aria-hidden="true"></i> <xsl:value-of select="name"/></li>
	                        	</xsl:otherwise>
	                        </xsl:choose>	           
	                    </xsl:if>	                  	
	                  </xsl:for-each>	                  
	                </ul>
	                <div class="text-center mt-6">
	                  <xsl:choose>
	                  	<xsl:when test="price = 0">
	                  		<a class="btn btn-secondary btn-block">Gratuit</a>
	                  	</xsl:when>
	                  	<xsl:otherwise>
	                  		<xsl:if test="preferred = 'false'">
		                    	<a href="/pricing/subscription/delay?plan={id}" class="btn btn-secondary btn-block">Choisir plan</a>
			             	</xsl:if>	
			              	<xsl:if test="preferred = 'true'">
			              		<a href="/pricing/subscription/delay?plan={id}" class="btn btn-green btn-block">Choisir plan</a>
			              	</xsl:if>
	                  	</xsl:otherwise>
	                  </xsl:choose>	                  	                 
	                </div>
	              </div>
	            </div>
	          </div>
          </xsl:for-each>
        </div>
	</xsl:template>  	
	<xsl:template match="page" mode="customScript"></xsl:template>	
</xsl:stylesheet>