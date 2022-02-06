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
      <xsl:text>MySupervisor - Editer une fonctionnalité de plan</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <form action="/admin/plan/feature/save" method="post">
      <xsl:if test="item and item/id">
        <xsl:attribute name="action">
          <xsl:text>/admin/plan/feature/save?id=</xsl:text>
          <xsl:value-of select="item/id"/>
        </xsl:attribute>
      </xsl:if>
      <input type="text" name="plan_id" hidden="hidden" value="{plan/id}"/>
      <div class="p-lg-6">
        <h2 class="mt-0 mb-4">Editer une fonctionnalité du plan <xsl:value-of select="plan/name"/></h2>
        <div class="row">
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Libellé <span class="form-required">*</span></label>
              <input name="name" type="text" class="form-control" placeholder="Saisir un libellé" required="">
                <xsl:attribute name="value">
                  <xsl:value-of select="item/name"/>
                </xsl:attribute>
              </input>
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
              <label class="custom-switch">
                <input name="basic" type="checkbox" class="custom-switch-input">
                  <xsl:if test="item/basic='true'">
                    <xsl:attribute name="checked">checked</xsl:attribute>
                  </xsl:if>
                </input>
                <span class="custom-switch-indicator"/>
                <span class="custom-switch-description">Est basique ?</span>
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
                <span class="custom-switch-description">Est activé ?</span>
              </label>
            </div>
          </div>
          <div class="col-md-12">
            <div class="form-group">
              <label class="form-label">Description</label>
              <textarea name="description" rows="5" class="form-control" placeholder="Faire une description...">
                <xsl:if test="item">
                  <xsl:value-of select="item/description"/>
                </xsl:if>
              </textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="card-footer text-right">
        <a href="/admin/plan/edit?id={plan/id}" class="btn btn-link">Annuler</a>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
      </div>
    </form>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
