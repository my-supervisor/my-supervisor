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
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>MySupervisor - Gérer les feuilles de données</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Feuilles de données</h3>
        </div>
        <div class="table-responsive">
          <table class="table card-table table-vcenter text-nowrap">
            <thead>
              <tr>
                <th class="w-1">Date de création</th>
                <th>Date de production</th>
                <th>Référence</th>
                <th>Libellé</th>
                <th>Propriétaire</th>
                <th>Autheur</th>
                <th/>
                <th/>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="data_sheets/data_sheet">
                <tr>
                  <td>
                    <span class="text-muted">
                      <xsl:value-of select="creation_date"/>
                    </span>
                  </td>
                  <td>
                    <xsl:value-of select="date"/>
                  </td>
                  <td>
                    <xsl:value-of select="reference"/>
                  </td>
                  <td>
                    <xsl:value-of select="name"/>
                  </td>
                  <td>
                    <xsl:value-of select="owner"/>
                  </td>
                  <td>
                    <xsl:value-of select="creator"/>
                  </td>
                  <td>
                    <a class="icon" href="/collect/sheet/edit?id={id}">
                      <i class="fe fe-edit"/>
                    </a>
                  </td>
                  <td>
                    <xsl:if test="../../identity/id = owner_id">
                      <a class="icon" href="/collect/sheet/delete?id={id}" onclick="return confirm('Désirez-vous supprimer cette feuille ?');">
                        <i class="fe fe-trash"/>
                      </a>
                    </xsl:if>
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
