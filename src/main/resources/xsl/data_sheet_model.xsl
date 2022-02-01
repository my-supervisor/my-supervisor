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
      <xsl:text>Supervisor - Minlessika - Configurer les modèles de feuille de données</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Modèles de feuilles de données</h3>
          <div class="card-options">
            <a role="button" class="btn btn-primary" href="/collect/model/edit"><i class="fa fa-plus"/> Nouveau
                  </a>
          </div>
        </div>
        <div class="table-responsive">
          <table class="table card-table table-vcenter text-nowrap">
            <thead>
              <tr>
                <th class="w-1">N°</th>
                <th>Code</th>
                <th>Libellé</th>
                <th>Activité</th>
                <th>Etat</th>
                <th>Propriétaire</th>
                <th/>
                <th/>
                <th/>
                <th/>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="data_sheet_models/data_sheet_model">
                <tr>
                  <td>
                    <span class="text-muted">
                      <xsl:value-of select="position()"/>
                    </span>
                  </td>
                  <td>
                    <xsl:value-of select="code"/>
                  </td>
                  <td>
                    <xsl:value-of select="name"/>
                  </td>
                  <td>
                    <xsl:value-of select="activity"/>
                  </td>
                  <td>
                    <xsl:choose>
                      <xsl:when test="active = 'true'">
                        <xsl:text>Activé</xsl:text>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text>Désactivé</xsl:text>
                      </xsl:otherwise>
                    </xsl:choose>
                  </td>
                  <td>
                    <xsl:value-of select="owner"/>
                  </td>
                  <td class="text-right">
                    <a href="/collect/sheet/edit?model={id}" class="btn btn-secondary btn-sm">Créer une feuille</a>
                  </td>
                  <xsl:if test="../../identity/id = owner_id">
                    <td class="text-right">
                      <a href="/sharing?type=DATA_SHEET_MODEL&amp;resource={id}" class="btn btn-secondary btn-sm">Partager</a>
                    </td>
                  </xsl:if>
                  <xsl:if test="not(../../identity/id = owner_id)">
                    <td class="text-right">
                      <a href="/sharing/subscriber/unshare?type=DATA_SHEET_MODEL&amp;resource={id}" class="btn btn-secondary btn-sm" onclick="return confirm('Voulez-vous vous désabonner de ce modèle ?');">Se désabonner</a>
                    </td>
                  </xsl:if>
                  <td>
                    <xsl:if test="../../identity/id = owner_id">
                      <a class="icon" href="/collect/model/edit?id={id}">
                        <i class="fe fe-edit"/>
                      </a>
                    </xsl:if>
                  </td>
                  <td>
                    <xsl:if test="../../identity/id = owner_id">
                      <a class="icon" href="/collect/model/delete?id={id}" onclick="return confirm('Voulez-vous supprimer ce modèle ?');">
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
