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
      <xsl:text>MySupervisor - Importer données</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="col-12" app="app">
      <form class="card" action="/collect/model/data/import/save" method="post">
        <div class="card-header">
          <h3 class="card-title">MySupervisor - Importer données dans Modèle <xsl:value-of select="item/code"/>-<xsl:value-of select="item/name"/></h3>
        </div>
        <div class="card-body">
          <div class="row">
            <input hidden="hidden" type="text" name="id" value="{item/id}"/>
            <div class="col-md-12">
              <div class="form-group">
                <label class="form-label">Source<span class="form-required">*</span></label>
                <select name="source_id" class="form-control" required="">
                  <xsl:variable name="item" select="item"/>
                  <option value="">-- Sélectionner un modèle --</option>
                  <xsl:for-each select="data_sheet_models/data_sheet_model">
                    <xsl:if test="not(id = $item/id)">
                      <option><xsl:attribute name="value"><xsl:value-of select="id"/></xsl:attribute><xsl:value-of select="code"/> - <xsl:value-of select="name"/>														                         		                         		                       
			                         	</option>
                    </xsl:if>
                  </xsl:for-each>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="card-footer text-right">
          <div class="d-flex">
            <a href="/collect/model/edit?id={item/id}" class="btn btn-link">Annuler</a>
            <button type="submit" class="btn btn-primary ml-auto">Importer</button>
          </div>
        </div>
      </form>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="customScript"/>
</xsl:stylesheet>
