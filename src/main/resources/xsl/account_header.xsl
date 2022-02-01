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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:interactive="http://www.minlessika.com/Header" version="2.0">
  <xsl:template name="account">
    <div class="d-flex order-lg-2 ml-auto">
      <div class="dropdown d-md-flex">
        <a class="nav-link icon" data-toggle="dropdown">
          <i class="fa fa-shopping-cart"/>
          <xsl:if test="count(payment_requests/payment_request) &gt; 0">
            <span class="badge badge-danger">
              <xsl:value-of select="count(payment_requests/payment_request)"/>
            </span>
            <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
              <xsl:for-each select="payment_requests/payment_request">
                <xsl:if test="position() &gt; 1">
                  <div class="dropdown-divider"/>
                </xsl:if>
                <a href="{../../module_links/minlessika}/payment-request/edit?id={id}" class="dropdown-item d-flex">
                  <div>
					            Payment request N° <xsl:value-of select="reference"/>
					            <div class="small text-muted">Price : <xsl:value-of select="amount"/> - <xsl:value-of select="fromNow"/></div>
					          </div>
                </a>
              </xsl:for-each>
            </div>
          </xsl:if>
        </a>
      </div>
      <div class="dropdown d-md-flex">
        <a class="nav-link icon" data-toggle="dropdown">
          <i class="fa fa-th"/>
        </a>
        <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
          <xsl:for-each select="applications/application">
            <xsl:choose>
              <xsl:when test="module='supervisor'">
                <xsl:variable name="is_current_module" select="../../current_module='supervisor'"/>
                <a href="{../../module_links/link[starts-with(module, 'supervisor')]/url}" class="dropdown-item d-flex">
                  <xsl:if test="$is_current_module">
                    <xsl:attribute name="href">
                      <xsl:text>javascript:void(0)</xsl:text>
                    </xsl:attribute>
                  </xsl:if>
                  <div>
                    <xsl:text>Supervisor</xsl:text>
                    <xsl:if test="$is_current_module">
                      <xsl:text> (currently used) </xsl:text>
                    </xsl:if>
                  </div>
                </a>
              </xsl:when>
              <xsl:when test="module='coding'">
                <xsl:variable name="is_current_module" select="../../current_module='coding'"/>
                <a href="{../../module_links/link[starts-with(module, 'coding')]/url}" class="dropdown-item d-flex">
                  <xsl:if test="$is_current_module">
                    <xsl:attribute name="href">
                      <xsl:text>javascript:void(0)</xsl:text>
                    </xsl:attribute>
                  </xsl:if>
                  <div>
                    <xsl:text>Coding</xsl:text>
                    <xsl:if test="$is_current_module">
                      <xsl:text> (currently used) </xsl:text>
                    </xsl:if>
                  </div>
                </a>
              </xsl:when>
              <xsl:when test="module='accounting'">
                <xsl:variable name="is_current_module" select="../../current_module='accounting'"/>
                <a href="{../../module_links/link[starts-with(module, 'accounting')]/url}" class="dropdown-item d-flex">
                  <xsl:if test="$is_current_module">
                    <xsl:attribute name="href">
                      <xsl:text>javascript:void(0)</xsl:text>
                    </xsl:attribute>
                  </xsl:if>
                  <div>
                    <xsl:text>Accounting</xsl:text>
                    <xsl:if test="$is_current_module">
                      <xsl:text> (currently used) </xsl:text>
                    </xsl:if>
                  </div>
                </a>
              </xsl:when>
            </xsl:choose>
          </xsl:for-each>
          <div class="dropdown-divider"/>
          <a href="{module_links/minlessika}/home" class="dropdown-item text-muted-dark">View all applications</a>
        </div>
      </div>
      <div class="dropdown">
        <a href="#" class="nav-link pr-0 leading-none" data-toggle="dropdown">
          <span class="avatar" style="background-image: url({identity/photo})"/>
          <span class="ml-2 d-none d-lg-block">
            <span class="text-default">
              <xsl:value-of select="identity/name"/>
            </span>
            <small class="text-muted d-block mt-1">
              <xsl:value-of select="identity/profile"/>
            </small>
          </span>
        </a>
        <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
          <a class="dropdown-item" href="{module_links/minlessika}/user-profile/edit"><i class="dropdown-icon fe fe-user"/> My Profile
		        </a>
          <a class="dropdown-item" href="{module_links/minlessika}/password/edit"><i class="dropdown-icon fa fa-key"/> Change my password
		        </a>
          <xsl:if test="current_module='coding' and identity/is_admin = 'true'">
            <a class="dropdown-item" href="{module_links/link[starts-with(module, 'coding')]/url}/admin"><i class="dropdown-icon fa fa-cog"/> Settings
		        </a>
          </xsl:if>
          <xsl:if test="current_module='accounting'">
            <a class="dropdown-item" href="{module_links/link[starts-with(module, 'accounting')]/url}/admin"><i class="dropdown-icon fa fa-cog"/> Settings
		        </a>
          </xsl:if>
          <xsl:if test="identity/is_admin = 'true'">
            <a class="dropdown-item" href="{module_links/minlessika}/admin"><i class="dropdown-icon fa fa-cog"/> Administration
		        </a>
          </xsl:if>
          <div class="dropdown-divider"/>
          <a class="dropdown-item" href="{module_links/minlessika}?PsByFlag=PsLogout"><i class="dropdown-icon fe fe-log-out"/> Logout
		        </a>
        </div>
      </div>
      <!-- 
		    <div class="dropdown">
		      <a class="nav-link icon" data-toggle="dropdown">
		        <i class="fa fa-2x fa-th"></i>
		      </a>
		      <div class="dropdown-menu dropdown-menu-right dropdown-menu-arrow">
		        <a href="/smart-energy/home" class="dropdown-item d-flex">
		          <div>
		            <strong>Smart Energy</strong>
		          </div>
		        </a>
		      </div>
		    </div>
		     -->
    </div>
    <a href="#" class="header-toggler d-lg-none ml-3 ml-lg-0" data-toggle="collapse" data-target="#headerMenuCollapse">
      <span class="header-toggler-icon"/>
    </a>
  </xsl:template>
</xsl:stylesheet>
