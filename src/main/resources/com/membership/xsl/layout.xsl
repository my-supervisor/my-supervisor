<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:include href="/com/membership/xsl/account_header.xsl"/>
  	<xsl:include href="/com/webviewer/xsl/utilities.xsl"/>
  	<xsl:template match="page">  		
	  <html lang="en" dir="ltr">
	  <head>
	    <meta charset="UTF-8"/>
	    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
	    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
	    <meta http-equiv="Content-Language" content="en" />
	    <meta name="msapplication-TileColor" content="#2d89ef"/>
	    <meta name="theme-color" content="#4188c9"/>
	    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
	    <meta name="apple-mobile-web-app-capable" content="yes"/>
	    <meta name="mobile-web-app-capable" content="yes"/>
	    <meta name="HandheldFriendly" content="True"/>
	    <meta name="MobileOptimized" content="320"/>
	    <link rel="icon" href="/com/webviewer/img/favicon.ico" type="image/x-icon"/>
    	<link rel="shortcut icon" type="image/x-icon" href="/com/webviewer/img/favicon.ico" />
	    <!-- Generated: 2018-04-16 09:29:05 +0200 -->
	    <link rel="stylesheet" href="/com/webviewer/vendors/font-awesome-4.7.0/css/font-awesome.min.css"/>
	    <link rel="stylesheet" href="/com/webviewer/vendors/toastjs/toast.min.css"/>
	    <link rel="stylesheet" href="/com/webviewer/vendors/bootstrap4-tagsinput/tagsinput.css"/>	    
	    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,300i,400,400i,500,500i,600,600i,700,700i&amp;subset=latin-ext"/>	    
	    <link href="/com/webviewer/vendors/tabler/css/dashboard.css" rel="stylesheet" />	    	    
	    <link href="/com/webviewer/vendors/tabler/plugins/charts-c3/plugin.css" rel="stylesheet" />
	   	<link rel="stylesheet" href="/com/webviewer/vendors/toastrjs/toastr.min.css" />
	   		    
	    <xsl:apply-templates select="." mode="head"/>
	    
	  </head>
	  <body class="">
	    <div class="page">
	      <div class="page-main">
	        <div class="header py-4 fixed-top">
	          <div class="container">
	            <div class="d-flex">
	              <a class="header-brand" href="/">
	                <img src="/com/webviewer/img/logo_name.svg" class="header-brand-img" alt="minlessika logo"/>
	              </a>
	              <div class="collapse d-lg-flex p-0" id="headerMenuCollapse">
			          <div class="container">
			            <div class="row align-items-center">
			              <div class="col-lg order-lg-first">
			                <ul class="nav nav-tabs border-0 flex-column flex-lg-row" style="padding:0">
			                  <li class="nav-item">
			                    <a href="/home" class="nav-link" style="padding:0">
			                        <xsl:if test="menu = 'home'">
				                        <xsl:attribute name="class">
				                          <xsl:text>nav-link active</xsl:text>
				                        </xsl:attribute>
				                        <xsl:attribute name="href">
				                          <xsl:text>javascript:void(0)</xsl:text>
				                        </xsl:attribute>
				                    </xsl:if>
			                    	<i class="fe fe-home"></i> Home
			                    </a>			                    
			                  </li>	                
			                  <li class="nav-item">
			                    <a target="_blank" href="https://blog.minlessika.com" class="nav-link" style="padding:0"><i class="fa fa-question-circle"></i> Help</a>
			                  </li>
			                </ul>
			              </div>
			            </div>
			          </div>
			      </div>
	              <xsl:call-template name="account"/>
	            </div>
	          </div>
	        </div>
	        <div class="my-9 my-md-9">
	          <div class="container">
					<xsl:apply-templates select="." mode="body"/>
	          </div>
	        </div>
	      </div>
	      <div class="footer">
	        <div class="container">
	          <div class="row">
	            <div class="col-lg-8">
	              <div class="row">
	                <div class="col-6 col-md-3">
	                  <ul class="list-unstyled mb-0">
	                    <li><a href="#">Terms and Conditions</a></li>
	                    <li><a href="#">Privacy policy</a></li>
	                  </ul>
	                </div>
	                <div class="col-6 col-md-3">
	                  <ul class="list-unstyled mb-0">
	                    <li><a href="/feedback/edit">Send a feedback</a></li>
	                    <li><a href="#">Security center</a></li>
	                  </ul>
	                </div>
	                <div class="col-6 col-md-3">
	                  <ul class="list-unstyled mb-0">
	                    <li><a href="#">About</a></li>
	                  </ul>
	                </div>
	              </div>
	            </div>
	            <div class="col-lg-4 mt-4 mt-lg-0">
	              Use your resources efficiently.
	            </div>
	          </div>
	        </div>
	      </div>
	      <footer class="footer">
	        <div class="container">
	          <div class="row align-items-center flex-row-reverse">
	            <div class="col-auto ml-lg-auto">
	              <div class="row align-items-center">
	                <div class="col-auto">
	                  <ul class="list-inline list-inline-dots mb-0">
	                    <li class="list-inline-item"><a target="_blank" href="https://blog.minlessika.com">Blog</a></li>
	                  </ul>
	                </div>
	              </div>
	            </div>
	            <div class="col-12 col-lg-auto mt-3 mt-lg-0 text-center">
	              Copyright © 2019 <a href="{module_links/minlessika}">Minlessika</a>. All rights reserved.
	            </div>
	          </div>
	        </div>
	      </footer>
	    </div>
	    
	    <script src="/com/webviewer/vendors/tabler/js/vendors/jquery-3.2.1.min.js"></script>
	    <script src="/com/webviewer/vendors/javascript-detect-element-resize/jquery.resize.js"></script>
	    <script src="/com/webviewer/vendors/angular/angular.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/plugins/charts-c3/js/d3.v3.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/plugins/charts-c3/js/c3.min.js"></script>
	    <script src="/com/webviewer/vendors/angular-sanitize/angular-sanitize.min.js"></script>
	    <script src="/com/webviewer/vendors/angular-bind-html-compile/angular-bind-html-compile.min.js"></script>
	    <script src="/com/webviewer/vendors/toastjs/toast.min.js"></script>
	    <script src="/com/webviewer/vendors/angular-gridster/dist/angular-gridster.min.js"></script>
	    <script src="/com/webviewer/vendors/atmosphere.js/atmosphere.js"></script>
	    <script src="/com/webviewer/vendors/angular-animate/angular-animate.min.js"></script>
	    <script src="/com/webviewer/vendors/angular-touch/angular-touch.min.js"></script>
	    <script src="/com/webviewer/vendors/angular-guid/guid.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/vendors/bootstrap.bundle.min.js"></script>
	    <script src="/com/webviewer/vendors/angular-bootstrap/ui-bootstrap-tpls-3.0.4.min.js"></script>
	    <script src="/com/webviewer/vendors/bootstrap4-tagsinput/tagsinput.js"></script>
	    <script src="/com/webviewer/vendors/angular-c3-simple/dist/angular_c3_simple.js"></script>
	    <script src="/com/webviewer/vendors/gauge/gauge.min.js"></script>
	    <script src="/com/webviewer/vendors/angular-image-compress/angular-image-compress.js"></script>
	    <script src="/com/webviewer/vendors/toastrjs/toastr.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/vendors/jquery.sparkline.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/vendors/selectize.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/vendors/jquery.tablesorter.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/vendors/jquery-jvectormap-2.0.3.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/vendors/jquery-jvectormap-de-merc.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/vendors/jquery-jvectormap-world-mill.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/vendors/circle-progress.min.js"></script>
	    <script src="/com/webviewer/vendors/tabler/plugins/input-mask/js/jquery.mask.min.js"></script>	   
	    <script src="/com/webviewer/vendors/tabler/js/core.js"></script>
	    <script src="/com/webviewer/vendors/tabler/js/dashboard.js"></script>
	    <script src="/com/webviewer/js/indicator_module.js"></script>
   		
   		<xsl:call-template name='some-features-to-initialize' />
   		<xsl:call-template name='toast' />
   		<xsl:apply-templates select="." mode="customScript"/>
   		
	  </body>
	</html>
	</xsl:template>
	<xsl:template match="activities">
		<xsl:param name="activity_selected"/>
		<xsl:for-each select="activity">
			<xsl:apply-templates select=".">
				<xsl:with-param name="activity_selected" select="$activity_selected"/>
			</xsl:apply-templates>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="activity">
		<xsl:param name="activity_selected"/>
    	<a href="/home?activity={id}" class="dropdown-item ">
    	    <xsl:if test="$activity_selected">
    	    	<xsl:if test="$activity_selected/id = id">
    	    	    <xsl:attribute name="href">
                      <xsl:text>javascript:void(0)</xsl:text>
                    </xsl:attribute>
    	    		<i class="dropdown-icon fa fa-eye"></i>
    	    	</xsl:if>
    	    </xsl:if>
    		<xsl:value-of select="name"/>
    		<xsl:if test="default_shown = 'true' and ../../identity/id = owner_id">
    	    	<xsl:text> (Par défaut)</xsl:text>
    	    </xsl:if>
    	</a>
  	</xsl:template>
</xsl:stylesheet>