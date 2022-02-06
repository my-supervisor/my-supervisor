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
      <xsl:text>MySupervisor - Modifier l'opérateur de comparaison d'une possibilité</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12">
      <form class="card" action="/collect/aggregated-model/formular/case-expression/when-case/comparison/save" method="post">
        <div class="card-header">
          <h3 class="card-title">Modifier opérateur de comparaison - expression <i><xsl:value-of select="item/expression"/></i></h3>
        </div>
        <div class="card-body">
          <input name="id" type="text" hidden="hidden" value="{item/id}"/>
          <input name="formular_id" hidden="hidden" type="text" value="{formular_id}"/>
          <input name="model_id" hidden="hidden" type="text" value="{model_id}"/>
          <input name="expression_id" hidden="hidden" type="text" value="{item/expression_id}"/>
          <div class="row">
            <div class="col-sm-6 col-md-4">
              <div class="form-group">
                <label class="form-label">Comparateur <span class="form-required">*</span></label>
                <select name="comparator_id" class="form-control" required="">
                  <xsl:variable name="when_case" select="item"/>
                  <xsl:for-each select="comparators/comparator">
                    <option>
                      <xsl:if test="$when_case and id = $when_case/comparator_id">
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
          </div>
        </div>
        <div class="card-footer text-right">
          <div class="d-flex">
            <a role="button" class="btn btn-primary" href="/collect/aggregated-model/formular/case-expression/edit?id={item/expression_id}&amp;formular={formular_id}&amp;model={model_id}"><i class="fa fa-arrow-left"/> Retour
	                </a>
            <button type="submit" class="btn btn-primary ml-auto">Enregistrer</button>
          </div>
        </div>
      </form>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
