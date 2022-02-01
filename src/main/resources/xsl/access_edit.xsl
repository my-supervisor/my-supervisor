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
      <xsl:text>Administration - Minlessika - Editer un droit d'accès</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <form action="/admin/access/save?id={item/id}" method="post">
      <div class="text-wrap p-lg-6">
        <h2 class="mt-0 mb-4">Editer un droit d'accès</h2>
        <div class="row">
          <div class="col-md-6">
            <div class="form-group">
              <label class="form-label">Code</label>
              <input type="text" name="code" class="form-control" disabled="" value="{item/code}"/>
              <input type="hidden" name="module" value="{item/module}"/>
            </div>
          </div>
          <div class="col-md-12">
            <div class="form-group">
              <label class="form-label">Libellé <span class="form-required">*</span></label>
              <input type="text" name="name" class="form-control" value="{item/name}" required=""/>
            </div>
          </div>
          <xsl:for-each select="access_params/access_param">
            <div class="col-sm-12 col-md-12">
              <fieldset class="form-fieldset">
                <legend>Paramètre <xsl:value-of select="position()"/></legend>
                <input hidden="hidden" type="text" name="param_id" value="{id}"/>
                <div class="row">
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="form-label">Code</label>
                      <input type="text" class="form-control" disabled="" value="{code}"/>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <label class="form-label">Libellé <span class="form-required">*</span></label>
                      <input name="param_name" type="text" class="form-control" placeholder="Entrez un nom" required="" value="{name}"/>
                    </div>
                  </div>
                  <div class="col-md-4">
                    <div class="form-group">
                      <label class="form-label">Type de valeur</label>
                      <input type="text" class="form-control" disabled="" value="{value_type}"/>
                    </div>
                  </div>
                  <div class="col-md-4">
                    <div class="form-group">
                      <label class="form-label">Valeur par défaut <span class="form-required">*</span></label>
                      <input name="param_default_value" type="text" class="form-control" placeholder="Entrez une valeur" required="" value="{default_value}"/>
                    </div>
                  </div>
                  <div class="col-md-4">
                    <div class="form-group">
                      <label class="form-label">Quantificateur</label>
                      <input type="text" class="form-control" disabled="" value="{quantifier}"/>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="form-label">Message <span class="form-required">*</span></label>
                      <textarea name="param_message" rows="2" class="form-control" placeholder="Saisir un message..." required="">
                        <xsl:value-of select="message"/>
                      </textarea>
                    </div>
                  </div>
                </div>
              </fieldset>
            </div>
          </xsl:for-each>
        </div>
      </div>
      <div class="card-footer text-right">
        <a href="/admin/access" class="btn btn-link">Annuler</a>
        <button type="submit" class="btn btn-primary">Modifier</button>
      </div>
    </form>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
