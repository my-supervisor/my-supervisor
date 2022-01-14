<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
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
	    <link rel="stylesheet" href="/com/membership/css/site.css"/>
	    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,300i,400,400i,500,500i,600,600i,700,700i&amp;subset=latin-ext"/>	    
	    <link href="/com/webviewer/vendors/tabler/css/dashboard.css" rel="stylesheet" />	    	    
	    <link href="/com/webviewer/vendors/tabler/plugins/charts-c3/plugin.css" rel="stylesheet" />
	   	<link rel="stylesheet" href="/com/webviewer/vendors/toastrjs/toastr.min.css" />
	   		    
	    <xsl:apply-templates select="." mode="head"/>
	  </head>
	  <body class="">
	    <div class="page">
	      <div class="page-main">	        
	        <div class="container">				
				<div class="page-header">
					<h1 class="page-title">Administration</h1>
				</div>
				<div class="row">
	              <div class="col-lg-3 order-lg-1 mb-4">
	                <a href="/home" class="btn btn-block btn-primary mb-6">
	                  <i class="fa fa-arrow-left mr-2"></i>Retourner à l'application
	                </a>

	                <div class="list-group list-group-transparent mb-0">
	                  <a href="/admin/profile" class="list-group-item list-group-item-action">
	                    <xsl:if test="menu='profile'">
	                        <xsl:attribute name="href">
	                          <xsl:text>javascript:void(0)</xsl:text>
	                        </xsl:attribute>
	                    	<xsl:attribute name="class">
	                          <xsl:text>list-group-item list-group-item-action active</xsl:text>
	                        </xsl:attribute>
	                    </xsl:if>
	                  	<span class="icon mr-3"><i class="fe fe-user"></i></span>Profils
	                  </a>
	                  <a href="/admin/user" class="list-group-item list-group-item-action">
	                    <xsl:if test="menu='user'">
	                        <xsl:attribute name="href">
	                          <xsl:text>javascript:void(0)</xsl:text>
	                        </xsl:attribute>
	                    	<xsl:attribute name="class">
	                          <xsl:text>list-group-item list-group-item-action active</xsl:text>
	                        </xsl:attribute>
	                    </xsl:if>
	                  	<span class="icon mr-3"><i class="fa fa-users"></i></span>Utilisateurs
	                  </a>
	                  <a href="/admin/access" class="list-group-item list-group-item-action">
	                    <xsl:if test="menu='access'">
	                        <xsl:attribute name="href">
	                          <xsl:text>javascript:void(0)</xsl:text>
	                        </xsl:attribute>
	                    	<xsl:attribute name="class">
	                          <xsl:text>list-group-item list-group-item-action active</xsl:text>
	                        </xsl:attribute>
	                    </xsl:if>
	                  	<span class="icon mr-3"><i class="fa fa-lock"></i></span>Droits d'accès
	                  </a>
	                  <a href="/admin/tax" class="list-group-item list-group-item-action">
	                    <xsl:if test="menu='tax'">
	                        <xsl:attribute name="href">
	                          <xsl:text>javascript:void(0)</xsl:text>
	                        </xsl:attribute>
	                    	<xsl:attribute name="class">
	                          <xsl:text>list-group-item list-group-item-action active</xsl:text>
	                        </xsl:attribute>
	                    </xsl:if>
	                  	<span class="icon mr-3"><i class="fa fa-money"></i></span>Taxes
	                  </a>
	                  <a href="/admin/payment-method" class="list-group-item list-group-item-action">
	                    <xsl:if test="menu='payment-method'">
	                        <xsl:attribute name="href">
	                          <xsl:text>javascript:void(0)</xsl:text>
	                        </xsl:attribute>
	                    	<xsl:attribute name="class">
	                          <xsl:text>list-group-item list-group-item-action active</xsl:text>
	                        </xsl:attribute>
	                    </xsl:if>
	                  	<span class="icon mr-3"><i class="fa fa-money"></i></span>Modes de paiement
	                  </a>
	                  <a href="/admin/plan" class="list-group-item list-group-item-action">
	                    <xsl:if test="menu='plan'">
	                        <xsl:attribute name="href">
	                          <xsl:text>javascript:void(0)</xsl:text>
	                        </xsl:attribute>
	                    	<xsl:attribute name="class">
	                          <xsl:text>list-group-item list-group-item-action active</xsl:text>
	                        </xsl:attribute>
	                    </xsl:if>
	                  	<span class="icon mr-3"><i class="fa fa-lock"></i></span>Plans
	                  </a>
	                </div>
	              </div>
	              <div class="col-lg-9">
	                <div class="card">
	                  <div class="card-body">
	                    <xsl:apply-templates select="." mode="body"/>
	                  </div>
	                </div>
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
	                    <li class="list-inline-item"><a href="blog.minlessika.com" target="_blank">Documentation</a></li>
	                  </ul>
	                </div>
	              </div>
	            </div>
	            <div class="col-12 col-lg-auto mt-3 mt-lg-0 text-center">
	              Copyright © 2018 <a href="{module_links/minlessika}">Minlessika</a>. All rights reserved.
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
	    
	    <script type="text/javascript">
	    	<![CDATA[			
	    			$(document).ready(function () {
                        $('#input-tags').selectize({
                            delimiter: '|',
                            persist: false,
                            create: function (input) {
                                return {
                                    value: input,
                                    text: input
                                }
                            }
                        });
                    });	
                    	
					$('form').submit(function () {
					    $(this).find('input[type="checkbox"]').each( function () {
					        var checkbox = $(this);
					        if( checkbox.is(':checked')) {
					            checkbox.attr('value','true');
					        } else {
					            checkbox.after().append(checkbox.clone().attr({type:'hidden', value:'false'}));
					            checkbox.prop('disabled', true);
					        }
					    })
					});
			]]>
	    </script>
	    <xsl:if test="flash">
   			<script type="text/javascript">
	   			$(function() {
					 	 toastr.options = {
							  "closeButton": true,
							  "debug": false,
							  "newestOnTop": false,
							  "progressBar": false,
							  "positionClass": "toast-top-right",
							  "preventDuplicates": false,
							  "onclick": null,
							  "showDuration": "300",
							  "hideDuration": "1000",
							  "timeOut": "5000",
							  "extendedTimeOut": "1000",
							  "showEasing": "swing",
							  "hideEasing": "linear",
							  "showMethod": "fadeIn",
							  "hideMethod": "fadeOut"
							};
						 <xsl:choose>
						 	<xsl:when test="flash/level = 'FINE'">
						 		toastr.success("<xsl:value-of select="flash/message"/>", 'Succès');
						 	</xsl:when>
						 	<xsl:when test="flash/level = 'INFO'">
						 		toastr.info("<xsl:value-of select="flash/message"/>", 'Information');
						 	</xsl:when>
						 	<xsl:when test="flash/level = 'WARNING'">
						 		toastr.warning("<xsl:value-of select="flash/message"/>", 'Attention');
						 	</xsl:when>
						 	<xsl:otherwise>
						 		toastr.error("<xsl:value-of select="flash/message"/>", 'Erreur');
						 	</xsl:otherwise>
						 </xsl:choose>			 						 
					});					 
			</script>
   		</xsl:if>
   		
   		<xsl:apply-templates select="." mode="customScript"/>
   		
	  </body>
	</html>
	</xsl:template>
</xsl:stylesheet>