<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:template match="page">  		
		<html lang="en">
		  <head>
		  	<xsl:comment>Required meta tags</xsl:comment>
	   		<meta charset="utf-8"/>
	   		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
		    
		    <xsl:comment>Material Design for Bootstrap CSS</xsl:comment>
	   		<link rel="stylesheet" href="/com/webviewer/vendors/mdbootstrap-4.5.3/css/bootstrap.min.css" />
	   		
	   		<xsl:comment>Font Awesome for Icon CSS</xsl:comment>
	   		<link rel="stylesheet" href="/com/webviewer/vendors/fontawesome-5.0.13/css/fontawesome-all.min.css" />  		
	   		
	   		<xsl:comment>Toastr CSS</xsl:comment>
	   		<link rel="stylesheet" href="/com/webviewer/vendors/toastrjs/toastr.min.css" /> 
	   		
	   		<link rel="stylesheet" href="/com/supervisor/css/main.css" />
	   		
	   		<link rel="icon" href="/com/webviewer/img/favicon.ico" type="image/x-icon"/>
    		<link rel="shortcut icon" type="image/x-icon" href="/com/webviewer/img/favicon.ico" />  		
		    <title>
		    	<xsl:text>Supervisor</xsl:text>
		    </title>
		  </head>
		  <body class="text-center">
			    <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
			  <header class="masthead mb-auto">
			    <div class="inner">
			      <h3 class="masthead-brand">Supervisor</h3>
			      <nav class="nav nav-masthead justify-content-center">
			        <a class="nav-link active" href="javascript:void(0)">Home</a>
			        <xsl:if test="not(identity)">
			        	<a class="nav-link" href="{module_links/minlessika}/login">Log in</a>
			        </xsl:if>			        	
				    <xsl:if test="identity">
				    	<a class="nav-link" href="{module_links/minlessika}?PsByFlag=PsLogout">Log out</a>
				    </xsl:if>		        
			      </nav>
			    </div>
			  </header>
			
			  <main role="main" class="inner cover">
			    <xsl:apply-templates select="." mode="body"/>
			  </main>
			
			  <footer class="mastfoot mt-auto">
			    <div class="inner">
			      <p>Copyright © 2018 <a href="{module_links/minlessika}">Minlessika</a>. All rights reserved.</p>			      
			    </div>
			  </footer>
			</div>			
		  </body>
		</html>
	</xsl:template>
</xsl:stylesheet>