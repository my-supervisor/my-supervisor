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
      <xsl:text>Supervisor - Minlessika - Gérer les interactions</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-6">
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Interactions - <xsl:value-of select="activity/name"/></h3>
          <div class="card-options">
            <a role="button" class="btn btn-primary" href="/home?activity={activity/id}"><i class="fa fa-arrow-left"/> Retour
                  </a>
          </div>
        </div>
        <div class="table-responsive">
          <table class="table card-table table-vcenter text-nowrap">
            <thead>
              <tr>
                <th>Activité</th>
                <th>Etat</th>
                <th/>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="interactions/interaction">
                <tr>
                  <td>
                    <xsl:value-of select="actor"/>
                  </td>
                  <td>
                    <xsl:choose>
                      <xsl:when test="active = 'true'">
	                     			Activée
	                     		</xsl:when>
                      <xsl:otherwise>
	                     			Désactivée
	                     		</xsl:otherwise>
                    </xsl:choose>
                  </td>
                  <td>
                    <xsl:choose>
                      <xsl:when test="active = 'true'">
                        <a href="/activity/interaction/activate?activate=false&amp;actor={actor_id}&amp;receiver={receiver_id}" class="btn btn-secondary btn-sm" onclick="return confirm('Souhaitez-vous désactiver cette interaction ?');">Désactiver</a>
                      </xsl:when>
                      <xsl:otherwise>
                        <a href="/activity/interaction/activate?activate=true&amp;actor={actor_id}&amp;receiver={receiver_id}" class="btn btn-secondary btn-sm" onclick="return confirm('Souhaitez-vous activer cette interaction ?');">Activer</a>
                      </xsl:otherwise>
                    </xsl:choose>
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
