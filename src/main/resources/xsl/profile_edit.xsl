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
      <xsl:text>Administration - Minlessika - Editer un profil</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <form action="/admin/profile/save" method="post">
      <xsl:if test="item and item/id">
        <xsl:attribute name="action">
          <xsl:text>/admin/profile/save?id=</xsl:text>
          <xsl:value-of select="item/id"/>
        </xsl:attribute>
      </xsl:if>
      <div class="p-lg-6">
        <h2 class="mt-0 mb-4">Editer un profil</h2>
        <div class="row">
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Code <span class="form-required">*</span></label>
              <input type="text" name="code" class="form-control" placeholder="Saisir un code" value="{item/code}" required="">
                <xsl:if test="item and item/id">
                  <xsl:attribute name="disabled">
                    <xsl:text>disabled</xsl:text>
                  </xsl:attribute>
                </xsl:if>
              </input>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Libellé <span class="form-required">*</span></label>
              <input type="text" name="name" class="form-control" placeholder="Saisir un libellé" value="{item/name}" required=""/>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Module <span class="form-required">*</span></label>
              <xsl:if test="item/id">
                <input hidden="hidden" type="text" name="module" value="{item/module}"/>
              </xsl:if>
              <select name="module" class="form-control" required="">
                <xsl:if test="item/id">
                  <xsl:attribute name="disabled">disabled</xsl:attribute>
                </xsl:if>
                <xsl:variable name="item" select="item"/>
                <xsl:for-each select="modules/module">
                  <option>
                    <xsl:if test="code = $item/module">
                      <xsl:attribute name="selected">selected</xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="value">
                      <xsl:value-of select="code"/>
                    </xsl:attribute>
                    <xsl:value-of select="name"/>
                  </option>
                </xsl:for-each>
              </select>
            </div>
          </div>
          <xsl:if test="item and item/id and item/is_admin='false'">
            <div class="col-md-6">
              <div class="form-group">
                <label class="form-label">Parent <span class="form-required">*</span></label>
                <select name="parent_id" class="form-control" required="">
                  <option value="0">Aucun parent</option>
                  <xsl:variable name="profile" select="item"/>
                  <xsl:for-each select="profiles/profile">
                    <xsl:if test="not(id = $profile/id)">
                      <option>
                        <xsl:if test="id = $profile/parent_id">
                          <xsl:attribute name="selected">selected</xsl:attribute>
                        </xsl:if>
                        <xsl:attribute name="value">
                          <xsl:value-of select="id"/>
                        </xsl:attribute>
                        <xsl:value-of select="name"/>
                      </option>
                    </xsl:if>
                  </xsl:for-each>
                </select>
              </div>
            </div>
            <div class="col-md-12">
              <h4 class="mt-0 mb-4">Droits d'accès</h4>
              <div class="table-responsive">
                <div class="card">
                  <table class="table card-table table-vcenter text-nowrap">
                    <thead>
                      <tr>
                        <th class="w-1">N°</th>
                        <th>Libellé</th>
                        <th>Nb params</th>
                        <th colspan="2">
                          <a href="/admin/profile/access/no-added?profile={item/id}" class="btn btn-sm btn-primary pull-right"><i class="fa fa-plus"/> Ajouter</a>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <xsl:for-each select="profile_accesses/profile_access">
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
                            <xsl:choose>
                              <xsl:when test="nb_params = 0">
                                <xsl:text>Aucun</xsl:text>
                              </xsl:when>
                              <xsl:otherwise>
                                <xsl:value-of select="nb_params"/>
                              </xsl:otherwise>
                            </xsl:choose>
                          </td>
                          <td>
                            <a class="icon" href="/admin/profile/access/edit?id={id}&amp;profile={../../item/id}">
                              <i class="fe fe-edit"/>
                            </a>
                          </td>
                          <td>
                            <a class="icon" href="/admin/profile/access/delete?id={id}&amp;profile={../../item/id}" onclick="return confirm('Voulez-vous supprimer cet accès ?');">
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
          </xsl:if>
        </div>
      </div>
      <div class="card-footer text-right">
        <a href="/admin/profile" class="btn btn-link">Annuler</a>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
      </div>
    </form>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
