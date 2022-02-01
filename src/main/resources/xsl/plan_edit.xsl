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
      <xsl:text>Administration - Minlessika - Editer un plan</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <form action="/admin/plan/save" method="post">
      <xsl:if test="item and item/id">
        <xsl:attribute name="action">
          <xsl:text>/admin/plan/save?id=</xsl:text>
          <xsl:value-of select="item/id"/>
        </xsl:attribute>
      </xsl:if>
      <div class="p-lg-6">
        <h2 class="mt-0 mb-4">Editer un plan</h2>
        <div class="row">
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Référence <span class="form-required">*</span></label>
              <input name="reference" type="text" class="form-control" placeholder="Saisir un code" required="">
                <xsl:attribute name="value">
                  <xsl:value-of select="item/reference"/>
                </xsl:attribute>
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
              <label class="form-label">Profil <span class="form-required">*</span></label>
              <select name="profile_id" class="form-control" required="">
                <xsl:if test="item and item/id">
                  <xsl:attribute name="disabled">
                    <xsl:text>disabled</xsl:text>
                  </xsl:attribute>
                </xsl:if>
                <xsl:variable name="plan" select="item"/>
                <xsl:for-each select="profiles/profile">
                  <option>
                    <xsl:if test="$plan and id = $plan/profile_id">
                      <xsl:attribute name="selected">selected</xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="value">
                      <xsl:value-of select="id"/>
                    </xsl:attribute>
                    <xsl:value-of select="name"/>
                  </option>
                </xsl:for-each>
              </select>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Ordre <span class="form-required">*</span></label>
              <input name="order" type="number" class="form-control" placeholder="Saisir un ordre" required="">
                <xsl:attribute name="value">
                  <xsl:value-of select="item/order"/>
                </xsl:attribute>
              </input>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Prix <span class="form-required">*</span></label>
              <input name="price" type="number" step="any" class="form-control" placeholder="Saisir un prix" required="">
                <xsl:attribute name="value">
                  <xsl:value-of select="item/price"/>
                </xsl:attribute>
              </input>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="custom-switch">
                <input name="preferred" type="checkbox" class="custom-switch-input">
                  <xsl:if test="item/preferred='true'">
                    <xsl:attribute name="checked">checked</xsl:attribute>
                  </xsl:if>
                </input>
                <span class="custom-switch-indicator"/>
                <span class="custom-switch-description">Est préféré ?</span>
              </label>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="custom-switch">
                <input name="enabled" type="checkbox" class="custom-switch-input">
                  <xsl:if test="item/enabled='true' or (not(item) or not(item/id))">
                    <xsl:attribute name="checked">checked</xsl:attribute>
                  </xsl:if>
                </input>
                <span class="custom-switch-indicator"/>
                <span class="custom-switch-description">Est activé</span>
              </label>
            </div>
          </div>
          <xsl:if test="item and item/id">
            <div class="col-md-12">
              <h4 class="mt-0 mb-4">Fonctionnalités</h4>
              <div class="table-responsive">
                <div class="card">
                  <table class="table card-table table-vcenter text-nowrap">
                    <thead>
                      <tr>
                        <th class="w-1">N°</th>
                        <th>Ordre</th>
                        <th>Libellé</th>
                        <th colspan="2">
                          <a href="/admin/plan/feature/edit?plan={item/id}" class="btn btn-sm btn-primary pull-right"><i class="fa fa-plus"/> Ajouter</a>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <xsl:for-each select="plan_features/plan_feature">
                        <tr>
                          <td>
                            <span class="text-muted">
                              <xsl:value-of select="position()"/>
                            </span>
                          </td>
                          <td>
                            <xsl:value-of select="order"/>
                          </td>
                          <td>
                            <xsl:value-of select="name"/>
                          </td>
                          <td>
                            <a class="icon" href="/admin/plan/feature/edit?id={id}&amp;plan={../../item/id}">
                              <i class="fe fe-edit"/>
                            </a>
                          </td>
                          <td>
                            <a class="icon" href="/admin/plan/feature/delete?id={id}&amp;plan={../../item/id}" onclick="return confirm('Voulez-vous supprimer cette fonctionnalité ?');">
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
        <a href="/admin/plan" class="btn btn-link">Annuler</a>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
      </div>
    </form>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
