<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2018-2022 Minlessika

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to read
the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="html" cdata-section-elements="script style" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/xsl/account_header.xsl"/>
  <xsl:include href="/xsl/utilities.xsl"/>
  <xsl:template match="page">
    <html lang="en" dir="ltr">
      <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
        <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
        <meta http-equiv="Content-Language" content="en"/>
        <meta name="msapplication-TileColor" content="#2d89ef"/>
        <meta name="theme-color" content="#4188c9"/>
        <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <meta name="mobile-web-app-capable" content="yes"/>
        <meta name="HandheldFriendly" content="True"/>
        <meta name="MobileOptimized" content="320"/>
        <link rel="icon" href="/img/favicon.ico" type="image/x-icon"/>
        <link rel="shortcut icon" type="image/x-icon" href="/img/favicon.ico"/>
        <!-- Generated: 2018-04-16 09:29:05 +0200 -->
        <link rel="stylesheet" href="/css/font-awesome-4.7.0.min.css"/>
        <link rel="stylesheet" href="/css/tagsinput.css"/>
        <link rel="stylesheet" href="/css/site.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,300i,400,400i,500,500i,600,600i,700,700i&amp;subset=latin-ext"/>
        <link href="/css/dashboard.css" rel="stylesheet"/>
        <link href="/css/charts-c3-plugin.css" rel="stylesheet"/>
        <link rel="stylesheet" href="/css/toastr.min.css"/>
        <xsl:apply-templates select="." mode="head"/>
      </head>
      <body class="">
        <div class="page">
          <div class="page-main">
            <div class="header py-4 fixed-top">
              <div class="container">
                <div class="d-flex">
                  <a class="header-brand" href="{module_links/minlessika}">
                    <img src="/com/supervisor/img/logo_supervisor_name_min.png" class="header-brand-img" alt="supervisor logo"/>
                  </a>
                  <div class="collapse d-lg-flex p-0" id="headerMenuCollapse">
                    <div class="container">
                      <div class="row align-items-center">
                        <div class="col-lg order-lg-first">
                          <ul class="nav nav-tabs border-0 flex-column flex-lg-row" style="padding:0">
                            <li class="nav-item">
                              <a href="/home" class="nav-link" style="padding:0"><xsl:if test="menu = 'home'"><xsl:attribute name="class"><xsl:text>nav-link active</xsl:text></xsl:attribute><xsl:attribute name="href"><xsl:text>javascript:void(0)</xsl:text></xsl:attribute></xsl:if><i class="fe fe-home"/> Accueil
			                    </a>
                            </li>
                            <li class="nav-item">
                              <a href="javascript:void(0)" class="nav-link" data-toggle="dropdown" style="padding:0"><xsl:if test="menu = 'activities'"><xsl:attribute name="class"><xsl:text>nav-link active</xsl:text></xsl:attribute></xsl:if><i class="fe fe-activity"/> Mes activités
			                    </a>
                              <div class="dropdown-menu dropdown-menu-arrow">
                                <xsl:apply-templates select="activities">
                                  <xsl:with-param name="activity_selected" select="activity_selected"/>
                                </xsl:apply-templates>
                                <xsl:if test="count(activities/activity) &gt; 0">
                                  <div class="dropdown-divider"/>
                                </xsl:if>
                                <a href="/activity" class="dropdown-item"><xsl:if test="menu = 'activities' and submenu = 'new_activity'"><xsl:attribute name="href"><xsl:text>javascript:void(0)</xsl:text></xsl:attribute></xsl:if><i class="dropdown-icon fa fa-plus"/> Créer une activité
	                    		  </a>
                                <div class="dropdown-divider"/>
                                <a href="/activity/template" class="dropdown-item"><xsl:if test="smenu = 'activities' and ubmenu = 'template'"><xsl:attribute name="href"><xsl:text>javascript:void(0)</xsl:text></xsl:attribute></xsl:if><i class="dropdown-icon fa fa-file"/> Mes modèles d'activité
	                    		  </a>
                              </div>
                            </li>
                            <li class="nav-item">
                              <a href="javascript:void(0)" class="nav-link" data-toggle="dropdown" style="padding:0"><xsl:if test="menu = 'collect'"><xsl:attribute name="class"><xsl:text>nav-link active</xsl:text></xsl:attribute></xsl:if><i class="fa fa-database"/> Collecte de données
			                    </a>
                              <div class="dropdown-menu dropdown-menu-arrow">
                                <a href="/collect/sheet" class="dropdown-item"><xsl:if test="submenu = 'data_sheet'"><xsl:attribute name="href"><xsl:text>javascript:void(0)</xsl:text></xsl:attribute></xsl:if><i class="dropdown-icon fa fa-database"/> Feuilles de données
	                    		  </a>
                                <div class="dropdown-divider"/>
                                <a href="/collect/model" class="dropdown-item"><xsl:if test="submenu = 'data_sheet_model'"><xsl:attribute name="href"><xsl:text>javascript:void(0)</xsl:text></xsl:attribute></xsl:if><i class="dropdown-icon fa fa-cog"/> Modèles de feuille de données
	                    		  </a>
                                <a href="/collect/aggregated-model" class="dropdown-item"><xsl:if test="submenu = 'aggregated_model'"><xsl:attribute name="href"><xsl:text>javascript:void(0)</xsl:text></xsl:attribute></xsl:if><i class="dropdown-icon fa fa-cog"/> Modèles agrégés
	                    		  </a>
                              </div>
                            </li>
                            <li class="nav-item">
                              <a href="/store" class="nav-link" style="padding:0"><xsl:if test="menu = 'store'"><xsl:attribute name="class"><xsl:text>nav-link active</xsl:text></xsl:attribute></xsl:if><i class="fa fa-cloud"/> Store
			                    </a>
                            </li>
                            <xsl:if test="identity/is_simple_user = 'true'">
                              <li class="nav-item">
                                <a href="/pricing" class="btn btn-sm btn-outline-primary">Upgrade from 0.5 EURO</a>
                              </li>
                            </xsl:if>
                            <xsl:if test="identity/is_admin = 'false' and identity/is_simple_user = 'false'">
                              <a href="/pricing" class="btn btn-sm btn-outline-primary">Need more privileges</a>
                            </xsl:if>
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
                        <li>
                          <a href="#">Conditions générales</a>
                        </li>
                        <li>
                          <a href="#">Confidentialité</a>
                        </li>
                      </ul>
                    </div>
                    <div class="col-6 col-md-3">
                      <ul class="list-unstyled mb-0">
                        <li>
                          <a href="/feedback/edit">Envoyer un feedback</a>
                        </li>
                        <li>
                          <a href="#">Centre de sécurité</a>
                        </li>
                      </ul>
                    </div>
                    <div class="col-6 col-md-3">
                      <ul class="list-unstyled mb-0">
                        <li>
                          <a href="#">A propos</a>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
                <div class="col-lg-4 mt-4 mt-lg-0">
	              Utiliser de manière efficiente vos ressources.
	            </div>
              </div>
            </div>
          </div>
          <footer class="footer">
            <div class="container">
              <div class="row align-items-center flex-row-reverse">
                <div class="col-12 col-lg-auto mt-3 mt-lg-0 text-center">
	              Copyright © 2018-2022 Minlessika. All rights reserved.
	            </div>
              </div>
            </div>
          </footer>
        </div>
        <script src="/js/jquery-3.2.1.min.js"/>
        <script src="/js/jquery.resize.js"/>
        <script src="/js/angular.min.js"/>
        <script src="/js/d3.v3.min.js"/>
        <script src="/js/c3.min.js"/>
        <script src="/js/angular-sanitize.min.js"/>
        <script src="/js/angular-bind-html-compile.min.js"/>
        <script src="/js/angular-gridster.min.js"/>
        <script src="/js/atmosphere.min.js"/>
        <script src="/js/angular-animate.min.js"/>
        <script src="/js/angular-touch.min.js"/>
        <script src="/js/guid.min.js"/>
        <script src="/js/bootstrap.bundle.min.js"/>
        <script src="/js/ui-bootstrap-tpls-3.0.4.min.js"/>
        <script src="/js/tagsinput.js"/>
        <script src="/js/angular_c3_simple.min.js"/>
        <script src="/js/gauge.min.js"/>
        <script src="/js/angular-image-compress.js"/>
        <script src="/js/toastr.min.js"/>
        <script src="/js/jquery.sparkline.min.js"/>
        <script src="/js/selectize.min.js"/>
        <script src="/js/jquery.tablesorter.min.js"/>
        <script src="/js/jquery-jvectormap-2.0.3.min.js"/>
        <script src="/js/jquery-jvectormap-de-merc.js"/>
        <script src="/js/jquery-jvectormap-world-mill.js"/>
        <script src="/js/circle-progress.min.js"/>
        <script src="/js/jquery.mask.min.js"/>
        <script src="/js/core.js"/>
        <script src="/js/dashboard.js"/>
        <script src="/js/indicator_module.js"/>
        <xsl:call-template name="some-features-to-initialize"/>
        <xsl:call-template name="toast"/>
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
          <i class="dropdown-icon fa fa-eye"/>
        </xsl:if>
      </xsl:if>
      <xsl:value-of select="name"/>
      <xsl:if test="default_shown = 'true' and ../../identity/id = owner_id">
        <xsl:text> (Par défaut)</xsl:text>
      </xsl:if>
    </a>
  </xsl:template>
</xsl:stylesheet>
