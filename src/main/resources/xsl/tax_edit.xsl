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
      <xsl:text>Administration - Minlessika - Editer une taxe</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <form action="/admin/tax/save?id={item/id}" method="post">
      <div class="p-lg-6">
        <h2 class="mt-0 mb-4">Editer une taxe</h2>
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
              <label class="form-label">Type de valeur</label>
              <input type="text" class="form-control" placeholder="Saisir un libellé" disabled="disabled">
                <xsl:attribute name="value">
                  <xsl:value-of select="item/value_type"/>
                </xsl:attribute>
              </input>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Valeur <span class="form-required">*</span></label>
              <input name="value" type="number" step="any" class="form-control" placeholder="Saisir une valeur" required="">
                <xsl:attribute name="value">
                  <xsl:value-of select="item/value"/>
                </xsl:attribute>
              </input>
            </div>
          </div>
        </div>
      </div>
      <div class="card-footer text-right">
        <a href="/admin/tax" class="btn btn-link">Annuler</a>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
      </div>
    </form>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
