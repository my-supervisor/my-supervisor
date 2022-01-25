<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:strip-space elements="*"/>
  	<xsl:include href="/xsl/layout.xsl"/>
  	<xsl:template match="page" mode="head">
	    <title>
	      <xsl:text>Supervisor - Minlessika - Aucune activité</xsl:text>
	    </title>
	</xsl:template>
	<xsl:template match="page" mode="body">
		<div class="py-9 text-center">
			<div class="col-md-6 mx-auto">
		    	<h1>Aucune activité</h1>
				<p class="lead">
					Vous ne suivez actuellement aucune activité. Vous pouvez en créer à partir de vos modèles ou vous abonner aux modèles du Store.
				</p>
				<p class="lead">
					<a href="/activity/template" class="btn btn-lg btn-secondary mr-1"><i class="fa fa-file"></i> Mes modèles</a>
					<a href="/store" class="btn btn-lg btn-secondary"><i class="fa fa-cloud"></i> Modèles du store</a>
				</p>
		    </div>
		</div>		
	</xsl:template> 
	<xsl:template match="page" mode="customScript"></xsl:template> 		
</xsl:stylesheet>