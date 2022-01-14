<?xml version="1.0"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html"
		cdata-section-elements="script style" include-content-type="no"
		doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes" />
	<xsl:strip-space elements="*" />
	<xsl:include href="/com/supervisor/xsl/public_layout.xsl" />
	<xsl:template match="page" mode="body">
		<h1 class="cover-heading">Keep control.</h1>
	    <p class="lead">Supervisor is your ally in the efficient use of your resources. With him and thanks to his simplicity, you will always be a step ahead of others. You will be the only master aboard your ship.</p>
	    <p class="lead">
	      <a href="/start" class="btn btn-lg btn-secondary">Start now</a>      
	    </p>	
	</xsl:template>
</xsl:stylesheet>