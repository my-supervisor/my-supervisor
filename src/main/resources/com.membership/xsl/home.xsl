<?xml version="1.0"?>
<!DOCTYPE castlist [
<!ENTITY nbsp '&#160;'>
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/com/membership/xsl/layout.xsl"/> 
  	<xsl:template match="page" mode="head">  	 
	    <title>
	      <xsl:text>Minlessika - Use your resources efficiently</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="my-3 my-md-5">
          <div class="container">
            <div class="page-header">
              <h1 class="page-title">
                Applications
              </h1>
            </div>
            <div class="row row-cards">            
              <xsl:for-each select="module_links/link">
                    <xsl:if test="module='supervisor'">
                    	<div class="col-sm-6 col-md-3">
			                <div class="card">
			                  <div class="card-body d-flex flex-column">
			                    <h4>
			                    	<a href="{url}">Supervisor</a>
			                    	<xsl:choose>
				                    	<xsl:when test="count(../../applications/application[starts-with(module, 'supervisor')]) > 0">
				                    		<span class="badge badge-success" style="font-size: small">Used</span>
				                    	</xsl:when>
				                    	<xsl:otherwise>
				                    		<span class="badge badge-secondary" style="font-size: small">Unused</span>
				                    	</xsl:otherwise>
				                    </xsl:choose>
			                    </h4>			                    		                    
			                    <p>Have an overview on all your activities.</p>                                                                                            
			                  </div>
			                </div>
			             </div>
                    </xsl:if>
                    <xsl:if test="module='coding'">
                    	<div class="col-sm-6 col-md-3">
			                <div class="card">
			                  <div class="card-body d-flex flex-column">
			                  	<h4>
			                  		<a href="{url}">Coding</a>
			                  		<xsl:choose>
				                    	<xsl:when test="count(../../applications/application[starts-with(module, 'coding')]) > 0">
				                    		<span class="badge badge-success" style="font-size: small">Used</span>
				                    	</xsl:when>
				                    	<xsl:otherwise>
				                    		<span class="badge badge-secondary" style="font-size: small">Unused</span>
				                    	</xsl:otherwise>
				                    </xsl:choose>
			                  	</h4>			                    			                    
			                    <p>Outsource your software project or add support to your team.</p>                                                                                             
			                  </div>
			                </div>
			             </div>
                    </xsl:if>
                    <xsl:if test="module='accounting'">
                    	<div class="col-sm-6 col-md-3">
			                <div class="card">
			                  <div class="card-body d-flex flex-column">
			                  	<h4>
			                  		<a href="{url}">Accounting</a>
			                  		<xsl:choose>
				                    	<xsl:when test="count(../../applications/application[starts-with(module, 'accounting')]) > 0">
				                    		<span class="badge badge-success" style="font-size: small">Used</span>
				                    	</xsl:when>
				                    	<xsl:otherwise>
				                    		<span class="badge badge-secondary" style="font-size: small">Unused</span>
				                    	</xsl:otherwise>
				                    </xsl:choose>
			                  	</h4>			                    			                    
			                    <p>Keep track of your finances and control your rights and obligations.</p>                                                                                             
			                  </div>
			                </div>
			             </div>
                    </xsl:if>
              </xsl:for-each>
            </div>
          </div>
        </div>
	</xsl:template>  	
	<xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>