<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="2.0">
	<xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  	<xsl:include href="/xsl/base_layout.xsl"/>
  	<xsl:include href="/xsl/utilities.xsl"/>
  	<xsl:template match="page">  		
		<html lang="en">
		  <xsl:call-template name="head" />
		  <body>
		  	<xsl:comment>Main Navigation</xsl:comment>
		  	<header>
		  		<xsl:comment>Navbar</xsl:comment>
				<nav class="navbar navbar-expand-lg navbar-dark fixed-top scrolling-navbar">
					<div class="container">
				      <a class="navbar-brand" href="/">
					    <span style="margin-right: 2px"><img src="/img/favicon.ico" width="50px"/></span>
				        <strong>MySupervisor</strong>
				      </a>
				      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent-7" aria-controls="navbarSupportedContent-7" aria-expanded="false" aria-label="Toggle navigation">
				        <span class="navbar-toggler-icon"></span>
				      </button>
				      <div class="collapse navbar-collapse" id="navbarSupportedContent-7">
				        <xsl:comment>Links</xsl:comment>
					        <ul class="navbar-nav mr-auto">
					            <li class="nav-item">
					                <xsl:if test="menu = 'home'">
				                        <xsl:attribute name="class">
				                          <xsl:text>nav-item active</xsl:text>
				                        </xsl:attribute>
				                        <xsl:attribute name="href">
				                          <xsl:text>javascript:void(0)</xsl:text>
				                        </xsl:attribute>
				                    </xsl:if>
					                <a class="nav-link" href="/">
					                	<xsl:choose>
					                		<xsl:when test="lang='fr'">
					                			<xsl:text>Accueil</xsl:text>
					                		</xsl:when>
					                		<xsl:otherwise>
					                			<xsl:text>Home</xsl:text>
					                		</xsl:otherwise>
					                	</xsl:choose>
						                <xsl:if test="menu = 'home'">
					                        <span class="sr-only"><xsl:text>(current)</xsl:text></span>
					                    </xsl:if>					                
					                </a>
					            </li>
					            <xsl:for-each select="module_links/link">
					                <xsl:if test="module = 'supervisor'">
					                <li class="nav-item">
						                <a class="nav-link" href="{url}">
						                	<xsl:text>Supervisor</xsl:text>
						                </a>
						            </li>
					                </xsl:if>
					            	<xsl:if test="module = 'coding'">
					                <li class="nav-item">
						                <a class="nav-link" href="{url}">
						                	<xsl:text>Coding</xsl:text>
						                </a>
						            </li>
					                </xsl:if>
					                <xsl:if test="module = 'accounting'">
					                <li class="nav-item">
						                <a class="nav-link" href="{url}">
						                	<xsl:text>Accounting</xsl:text>
						                </a>
						            </li>
					                </xsl:if>
					            </xsl:for-each>				            
					            <li class="nav-item">
					                <a target="_blank" class="nav-link" href="https://blog.minlessika.com">
					                	<xsl:text>Blog</xsl:text>
					                </a>
					            </li>
					            <xsl:if test="not(identity)">
						        	<li class="nav-item">
						        		<xsl:if test="menu = 'contact'">
					                        <xsl:attribute name="class">
					                          <xsl:text>nav-item active</xsl:text>
					                        </xsl:attribute>
					                        <xsl:attribute name="href">
					                          <xsl:text>javascript:void(0)</xsl:text>
					                        </xsl:attribute>
					                    </xsl:if>
						                <a class="nav-link" href="/contacts">
						                	<xsl:text>Contact</xsl:text>
						                	<xsl:if test="menu = 'home'">
						                        <span class="sr-only"><xsl:text>(current)</xsl:text></span>
						                    </xsl:if>	
						                </a>
						            </li>
					            </xsl:if>					            
					        </ul>
					        <xsl:comment>Links</xsl:comment>
					
					        <ul class="navbar-nav ml-auto">
					        	<xsl:if test="not(identity)">
					        		<li class="nav-item">
					        			<xsl:if test="menu = 'registration'">
					                        <xsl:attribute name="class">
					                          <xsl:text>nav-item active</xsl:text>
					                        </xsl:attribute>
					                        <xsl:attribute name="href">
					                          <xsl:text>javascript:void(0)</xsl:text>
					                        </xsl:attribute>
					                    </xsl:if>
			                            <a class="nav-link waves-effect waves-light" href="/registration">
			                                <i class="fa fa-user-plus"></i>
			                                <xsl:choose>
						                		<xsl:when test="lang='fr'">
						                			<xsl:text> Créer un compte</xsl:text>
						                		</xsl:when>
						                		<xsl:otherwise>
						                			<xsl:text> Sign up</xsl:text>
						                		</xsl:otherwise>
						                	</xsl:choose>
			                                <xsl:if test="menu = 'registration'">
						                        <span class="sr-only"><xsl:text>(current)</xsl:text></span>
						                    </xsl:if>
			                            </a>
			                        </li>
			                        <li class="nav-item">
			                        	<xsl:if test="menu = 'login'">
					                        <xsl:attribute name="class">
					                          <xsl:text>nav-item active</xsl:text>
					                        </xsl:attribute>
					                        <xsl:attribute name="href">
					                          <xsl:text>javascript:void(0)</xsl:text>
					                        </xsl:attribute>
					                    </xsl:if>
			                            <a class="nav-link waves-effect waves-light" href="/login">
			                                <i class="fa fa-sign-in-alt"></i>
			                                <xsl:choose>
						                		<xsl:when test="lang='fr'">
						                			<xsl:text> Se connecter</xsl:text>
						                		</xsl:when>
						                		<xsl:otherwise>
						                			<xsl:text> Log in</xsl:text>
						                		</xsl:otherwise>
						                	</xsl:choose>
			                                <xsl:if test="menu = 'login'">
						                        <span class="sr-only"><xsl:text>(current)</xsl:text></span>
						                    </xsl:if>
			                            </a>
			                        </li>
			                        <li class="nav-item dropdown">
			                            <xsl:choose>
			                            	<xsl:when test="lang='fr'">
			                            		<a class="nav-link dropdown-toggle" href="/lang/fr" id="dropdown09" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="flag-icon flag-icon-fr"></span> Français</a>
					                            <div class="dropdown-menu" aria-labelledby="dropdown09">
					                                <a class="dropdown-item" href="/lang/en"><span class="flag-icon flag-icon-us"> </span>  English</a>
					                            </div>
			                            	</xsl:when>
			                            	<xsl:otherwise>
			                            		<a class="nav-link dropdown-toggle" href="/lang/en" id="dropdown09" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="flag-icon flag-icon-us"></span> English</a>
					                            <div class="dropdown-menu" aria-labelledby="dropdown09">
					                                <a class="dropdown-item" href="/lang/fr"><span class="flag-icon flag-icon-fr"> </span>  Français</a>
					                            </div>
			                            	</xsl:otherwise>
			                            </xsl:choose>			                            
			                        </li>
					            </xsl:if>					            
					            <xsl:if test="identity">
					            	<li class="nav-item">
			                            <a class="nav-link waves-effect waves-light" href="/home">
			                                <i class="fa fa-user"></i> <xsl:text> </xsl:text><xsl:value-of select="identity/name"/>
			                            </a>
			                        </li>
			                        <li class="nav-item">
			                            <a class="nav-link waves-effect waves-light" href="{links/link[@rel='takes:logout']/@href}">
			                                <i class="fa fa-sign-out-alt"></i> <xsl:text> Log out</xsl:text>
			                            </a>
			                        </li>
					            </xsl:if>					            					        	
		                    </ul>			        
				      </div>
				    </div>				
				</nav>
				<!-- Full Page Intro -->
				  <div class="view" style="background-image: url('/img/backend.jpg'); background-repeat: no-repeat; background-size: cover; background-position: center center;">
				    <!-- Mask & flexbox options-->
				    <div class="mask rgba-gradient d-flex justify-content-center align-items-center">
				      <!-- Content -->
				      <div class="container">
				        <xsl:apply-templates select="." mode="body"/>
				      </div>
				      <!-- Content -->				      
				    </div>
				    <!-- Mask & flexbox options-->
				  </div>
				  <!-- Full Page Intro -->
		  	</header>		  	
			<xsl:comment>Main Navigation</xsl:comment>
			
		    <xsl:call-template name="footer"/>		
		    
		    <xsl:comment>Site CSS</xsl:comment>
	   		<link rel="stylesheet" href="/css/site.css" />
		    <xsl:call-template name="import-jslib"/>
   			<xsl:call-template name='toast' />
    		<script src="/js/script.js"></script>
		  </body>
		</html>
	</xsl:template>
</xsl:stylesheet>