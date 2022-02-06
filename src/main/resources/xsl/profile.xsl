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
  <xsl:strip-space elements="*"/>
  <xsl:include href="/xsl/admin_layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>MySupervisor - Profils</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="">
      <h2 class="mt-0 mb-4">Profils</h2>
      <div class="table-responsive">
        <div class="card">
          <table class="table card-table table-vcenter text-nowrap">
            <thead>
              <tr>
                <th class="w-1">N°</th>
                <th>Libellé</th>
                <th>Module</th>
                <th colspan="2">
                  <a href="/admin/profile/edit" class="btn btn-sm btn-primary pull-right"><i class="fa fa-plus"/> Ajouter</a>
                </th>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="profiles/profile">
                <tr>
                  <td>
                    <span class="text-muted">
                      <xsl:value-of select="position()"/>
                    </span>
                  </td>
                  <td>
                    <xsl:value-of select="name"/>
                  </td>
                  <td>
                    <xsl:value-of select="module"/>
                  </td>
                  <td>
                    <a class="icon" href="/admin/profile/edit?id={id}">
                      <i class="fe fe-edit"/>
                    </a>
                  </td>
                  <td>
                    <a class="icon" href="/admin/profile/delete?id={id}" onclick="return confirm('Voulez-vous supprimer ce profil ?');">
                      <i class="fe fe-trash"/>
                    </a>
                  </td>
                </tr>
              </xsl:for-each>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
