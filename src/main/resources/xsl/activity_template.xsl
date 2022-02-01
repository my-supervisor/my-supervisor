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
      <xsl:text>Supervisor - Minlessika - Configurer les modèles d'activité</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Modèles d'activité</h3>
        </div>
        <div class="table-responsive">
          <table class="table card-table table-vcenter text-nowrap">
            <thead>
              <tr>
                <th class="w-1">N°</th>
                <th>Libellé</th>
                <th>Concepteur</th>
                <th>Etat</th>
                <th/>
                <th/>
                <th/>
                <th/>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="activity_templates/activity_template">
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
                    <xsl:value-of select="designer"/>
                  </td>
                  <td>
                    <xsl:value-of select="state"/>
                  </td>
                  <td class="text-right">
                    <a href="/activity/template/create-activity/edit?template={id}" class="btn btn-secondary btn-sm">Créer une activité</a>
                  </td>
                  <td class="text-right">
                    <xsl:choose>
                      <xsl:when test="is_owner = 'false'">
                        <!-- Do nothing -->
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:choose>
                          <xsl:when test="state_id = 'CREATED'">
                            <a href="/activity/template/publish/edit?template={id}" class="btn btn-secondary btn-sm">Publier</a>
                          </xsl:when>
                          <xsl:otherwise>
                            <a href="/activity/template/publish/edit?template={id}&amp;id={id}" class="btn btn-secondary btn-sm">Publication</a>
                          </xsl:otherwise>
                        </xsl:choose>
                      </xsl:otherwise>
                    </xsl:choose>
                  </td>
                  <td>
                    <xsl:if test="is_owner = 'true'">
                      <a class="icon" href="/activity/template/edit?id={id}" data-toggle="tooltip" data-placement="top" title="Modifier">
                        <i class="fe fe-edit"/>
                      </a>
                    </xsl:if>
                  </td>
                  <td>
                    <xsl:if test="is_owner = 'true'">
                      <a class="icon" href="/activity/template/delete?id={id}" onclick="return confirm('Voulez-vous supprimer ce modèle ?');" data-toggle="tooltip" data-placement="top" title="Supprimer">
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
