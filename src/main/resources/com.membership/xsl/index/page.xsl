<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:include href="/com/membership/xsl/website_layout.xsl"/>
  	<xsl:template match="page" mode="body">  		
		<!--Grid row-->
        <div class="row mt-5 flex-center">
          <!--Grid column-->
          <div class="col-md-6 mb-5 mt-md-0 mt-5 white-text text-center text-md-center">
            <h1 class="h1-responsive font-weight-bold wow fadeInLeft" data-wow-delay="0.3s"><xsl:value-of select="$lang.slogan1"/></h1>
            <hr class="hr-light wow fadeInLeft" data-wow-delay="0.3s"/>
            <h6 class="mb-3 wow fadeInRight" data-wow-delay="0.3s"><xsl:value-of select="$lang.slogan2"/></h6>
          </div>
          <!--Grid column-->
        </div>
        <!--Grid row-->
	</xsl:template>
</xsl:stylesheet>