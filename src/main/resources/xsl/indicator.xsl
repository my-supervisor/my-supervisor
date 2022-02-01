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
      <xsl:text>Supervisor - Minlessika - Mes indicateurs</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Mes indicateurs</h3>
        </div>
        <div class="table-responsive">
          <table class="table card-table table-vcenter text-nowrap">
            <thead>
              <tr>
                <th class="w-1">N°</th>
                <th>Libellé</th>
                <th>Périodicité</th>
                <th>A partir de</th>
                <th/>
                <th/>
                <th/>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="indicators/indicator">
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
                    <xsl:value-of select="periodicity"/>
                  </td>
                  <td>
                    <xsl:value-of select="periodicity_reference"/>
                  </td>
                  <td class="text-right">
                    <a href="/indicator/template/generate?indic={id}" class="btn btn-secondary btn-sm" onclick="return confirm('Souhaitez-vous créer un modèle à partir de cet indicateur ?');">Créer un modèle</a>
                  </td>
                  <td>
                    <a class="icon" href="/indicator/edit?id={id}&amp;shortname={short_name}&amp;source=indicator">
                      <i class="fe fe-edit"/>
                    </a>
                  </td>
                  <td>
                    <a class="icon" href="/indicator/delete?id={id}&amp;source=indicator" onclick="return confirm('Voulez-vous supprimer cet indicateur ?');">
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
