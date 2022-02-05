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
          <a class="dropdown-item" href="/user-profile/edit"><i class="dropdown-icon fe fe-user"/> My Profile</a>
          <a class="dropdown-item" href="/password/edit"><i class="dropdown-icon fa fa-key"/> Change my password</a>
          <xsl:if test="identity/is_admin = 'true'">
            <a class="dropdown-item" href="/admin"><i class="dropdown-icon fa fa-cog"/> Administration</a>
          </xsl:if>
          <div class="dropdown-divider"/>
          <a class="dropdown-item" href="?PsByFlag=PsLogout"><i class="dropdown-icon fe fe-log-out"/> Logout</a>
        </div>
      </div>
    </div>
    <a href="#" class="header-toggler d-lg-none ml-3 ml-lg-0" data-toggle="collapse" data-target="#headerMenuCollapse">
      <span class="header-toggler-icon"/>
    </a>
  </xsl:template>
</xsl:stylesheet>
